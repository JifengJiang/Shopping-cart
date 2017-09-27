package com.cart.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cart.dao.ChargeDAO;
import com.cart.dao.OrderDAO;
import com.cart.dao.ProductDAO;
import com.cart.dao.UserDAO;
import com.cart.entity.ChargeInfo;
import com.cart.entity.Product;
import com.cart.entity.UserInfo;
import com.cart.model.CartInfo;
import com.cart.model.CartLineInfo;
import com.cart.model.ChargeInfoModel;
import com.cart.model.CustomerInfo;
import com.cart.model.PaginationResult;
import com.cart.model.ProductInfo;
import com.cart.service.CheckoutService;
import com.cart.util.Utils;
import com.cart.validator.CustomerInfoValidator;
import com.google.gson.Gson;
import com.stripe.Stripe;
import com.stripe.exception.APIConnectionException;
import com.stripe.exception.AuthenticationException;
import com.stripe.exception.CardException;
import com.stripe.exception.RateLimitException;
import com.stripe.exception.StripeException;
import com.stripe.exception.oauth.InvalidRequestException;
import com.stripe.model.Charge;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.WebUtils;

@Controller
// Enable Hibernate Transaction.
@Transactional
// Need to use RedirectAttributes
@EnableWebMvc
public class MainController {

	/*
	 * pay
	 * */
	
	private static final String CHARGE_CURRENCY = "cad";
	private int amount;
//	private List<Integer> userId =new ArrayList<Integer>();
	 private String checkFine="success";
	 private String checkFaile="faile";
	 
	 //for testing purpose
	 private String publicApiKey="pk_test_JUC987jX645xP9rX8oUNu3LY";
	 private String secretApiKey="sk_test_DkYIH4LZPnTgnzpjiZmMHX9J";
	
	@Autowired
	private UserDAO userDao;
	
    @Autowired
    private OrderDAO orderDAO;

    @Autowired
    private ProductDAO productDAO;

    @Autowired
   private ChargeDAO chargeDao;
    
    @Autowired
    private CheckoutService ckService;
    
    
    @Autowired
    private CustomerInfoValidator customerInfoValidator;

    @InitBinder
    public void myInitBinder(WebDataBinder dataBinder) {
        Object target = dataBinder.getTarget();
        if (target == null) {
            return;
        }
        System.out.println("Target=" + target);

        // For Cart Form.
        // (@ModelAttribute("cartForm") @Validated CartInfo cartForm)
        if (target.getClass() == CartInfo.class) {

        }
        // For Customer Form.
        // (@ModelAttribute("customerForm") @Validated CustomerInfo
        // customerForm)
        else if (target.getClass() == CustomerInfo.class) {
            dataBinder.setValidator(customerInfoValidator);
        }

    }

    @RequestMapping("/403")
    public String accessDenied() {
        return "/403";
    }

    @RequestMapping("/")
    public String home() {
        return "index";
    }

    // Product List page.
    // Danh sách sản phẩm.
    @RequestMapping({ "/productList" })
    public String listProductHandler(Model model, //
                                     @RequestParam(value = "name", defaultValue = "") String likeName,
                                     @RequestParam(value = "page", defaultValue = "1") int page) {
        final int maxResult = 5;
        final int maxNavigationPage = 10;

        PaginationResult<ProductInfo> result = productDAO.queryProducts(page, //
                maxResult, maxNavigationPage, likeName);

        model.addAttribute("paginationProducts", result);
        return "productList";
    }

    @RequestMapping({ "/buyProduct" })
    public String listProductHandler(HttpServletRequest request, Model model, //
                                     @RequestParam(value = "code", defaultValue = "") String code) {

        Product product = null;
        if (code != null && code.length() > 0) {
            product = productDAO.findProduct(code);
        }
        if (product != null) {

            // Cart info stored in Session.
            CartInfo cartInfo = Utils.getCartInSession(request);

            ProductInfo productInfo = new ProductInfo(product);

            cartInfo.addProduct(productInfo, 1);
        }
        // Redirect to shoppingCart page.
        return "redirect:/shoppingCart";
    }

    @RequestMapping({ "/shoppingCartRemoveProduct" })
    public String removeProductHandler(HttpServletRequest request, Model model, //
                                       @RequestParam(value = "code", defaultValue = "") String code) {
        Product product = null;
        if (code != null && code.length() > 0) {
            product = productDAO.findProduct(code);
        }
        if (product != null) {

            // Cart Info stored in Session.
            CartInfo cartInfo = Utils.getCartInSession(request);

            ProductInfo productInfo = new ProductInfo(product);

            cartInfo.removeProduct(productInfo);

        }
        // Redirect to shoppingCart page.
        return "redirect:/shoppingCart";
    }

    // POST: Update quantity of products in cart.
    @RequestMapping(value = { "/shoppingCart" }, method = RequestMethod.POST)
    public String shoppingCartUpdateQty(HttpServletRequest request, //
                                        Model model, //
                                        @ModelAttribute("cartForm") CartInfo cartForm) {

        CartInfo cartInfo = Utils.getCartInSession(request);
        cartInfo.updateQuantity(cartForm);

        // Redirect to shoppingCart page.
        return "redirect:/shoppingCart";
    }

    // GET: Show Cart
    @RequestMapping(value = { "/shoppingCart" }, method = RequestMethod.GET)
    public String shoppingCartHandler(HttpServletRequest request, Model model) {
        CartInfo myCart = Utils.getCartInSession(request);

        model.addAttribute("cartForm", myCart);
        return "shoppingCart";
    }

    // GET: Enter customer information.
    @RequestMapping(value = { "/shoppingCartCustomer" }, method = RequestMethod.GET)
    public String shoppingCartCustomerForm(HttpServletRequest request, Model model) {

        CartInfo cartInfo = Utils.getCartInSession(request);

        // Cart is empty.
        if (cartInfo.isEmpty()) {

            // Redirect to shoppingCart page.
            return "redirect:/shoppingCart";
        }

        CustomerInfo customerInfo = cartInfo.getCustomerInfo();
        if (customerInfo == null) {
            customerInfo = new CustomerInfo();
        }

        model.addAttribute("customerForm", customerInfo);

        return "shoppingCartCustomer";
    }

    // POST: Save customer information.
//    @Transactional
    @RequestMapping(value = { "/shoppingCartCustomer" }, method = RequestMethod.POST)
    public String shoppingCartCustomerSave(HttpServletRequest request, //
                                           Model model, //
                                           @ModelAttribute("customerForm") @Validated CustomerInfo customerForm, //
                                           BindingResult result, //
                                           final RedirectAttributes redirectAttributes, HttpServletResponse response) {

        // If has Errors.
        if (result.hasErrors()) {
            customerForm.setValid(false);
            // Forward to reenter customer info.
            return "shoppingCartCustomer";
        }

        customerForm.setValid(true);
        CartInfo cartInfo = Utils.getCartInSession(request);
        userDao.saveInfomation(customerForm);
        List<UserInfo> users = userDao.getUserId(customerForm);
        if (users.size()==0) {
			return"shoppingCartCustomer";
		}else
		{
			 cartInfo.setCustomerInfo(customerForm);
			 UserInfo user = users.get(0);
			 int userId = user.getId();
			 
		        response.addCookie(new Cookie("userId", Integer.toString(userId)));
//		        model.addAttribute("userInfo", customerForm);
		        // Redirect to Confirmation page.
		        return "redirect:/shoppingCartConfirmation";
		}
//        userId = userDao.getUserId(customerForm);
       
    }

    // GET: Review Cart to confirm.
    @RequestMapping(value = { "/shoppingCartConfirmation" }, method = RequestMethod.GET)
    public String shoppingCartConfirmationReview(HttpServletRequest request, Model model) {
        CartInfo cartInfo = Utils.getCartInSession(request);

        // Cart have no products.
        if (cartInfo.isEmpty()) {
            // Redirect to shoppingCart page.
            return "redirect:/shoppingCart";
        } else if (!cartInfo.isValidCustomer()) {
            // Enter customer info.
            return "redirect:/shoppingCartCustomer";
        }

        return "shoppingCartConfirmation";
    }

    // POST: Send Cart (Save).
    @RequestMapping(value = { "/shoppingCartConfirmation" }, method = RequestMethod.POST)
    // Avoid UnexpectedRollbackException (See more explanations)
    @Transactional(propagation = Propagation.NEVER)
    public String shoppingCartConfirmationSave(HttpServletRequest request, Model model) {
        CartInfo cartInfo = Utils.getCartInSession(request);
        String productId ="";
        int quantity =0;
        double productAmount=0.0;
        // Cart have no products.
        if (cartInfo.isEmpty()) {
            // Redirect to shoppingCart page.
            return "redirect:/shoppingCart";
        } else if (!cartInfo.isValidCustomer()) {
            // Enter customer info.
            return "redirect:/shoppingCartCustomer";
        }
        try {
        	//begin to process pay
        	List<CartLineInfo> cartInfos= cartInfo.getCartLines();
        	
        	for(CartLineInfo singleInfo:cartInfos)
        	{
        		productId = singleInfo.getProductInfo().getCode();
        		Product product = new Product();
        		
        		quantity = singleInfo.getQuantity();
        		product = productDAO.findProduct(productId);
        		
        		productAmount = product.getPrice();
        		amount += (int)((Math.ceil(productAmount))*100*quantity);
        	}
//        	Map<String, Object> item = new HashMap<String, Object>();
//    		item.put("publishable_key", publicApiKey);
//    		item.put("amount", amount);
//    		item.put("currency", CHARGE_CURRENCY);
    		model.addAttribute("publishable_key", publicApiKey);
    		model.addAttribute("amount", amount);
    		model.addAttribute("currency", CHARGE_CURRENCY);
//    		model.addAttribute("selectedPros", cartInfos);
    		Utils.storeLastOrderedProductInSession(request, cartInfos);
//            orderDAO.saveOrder(cartInfo);
    		return "CheckoutDemo";
        } catch (Exception e) {
            // Need: Propagation.NEVER?
            return "shoppingCartConfirmation";
        }
//        // Remove Cart In Session.
//        Utils.removeCartInSession(request);
//
//        // Store Last ordered cart to Session.
//        Utils.storeLastOrderedCartInSession(request, cartInfo);
        
        
        // Redirect to successful page.
//        return "redirect:/shoppingCartFinalize";
    }

    @RequestMapping(value = { "/shoppingCartFinalize" }, method = RequestMethod.GET)
    public String shoppingCartFinalize(HttpServletRequest request, Model model) {

        CartInfo lastOrderedCart = Utils.getLastOrderedCartInSession(request);

        if (lastOrderedCart == null) {
            return "redirect:/shoppingCart";
        }

        return "shoppingCartFinalize";
    }

    @RequestMapping(value = { "/productImage" }, method = RequestMethod.GET)
    public void productImage(HttpServletRequest request, HttpServletResponse response, Model model,
                             @RequestParam("code") String code) throws IOException {
        Product product = null;
        if (code != null) {
            product = this.productDAO.findProduct(code);
        }
        if (product != null && product.getImage() != null) {
            response.setContentType("image/jpeg, image/jpg, image/png, image/gif");
            response.getOutputStream().write(product.getImage());
        }
        response.getOutputStream().close();
    }

    @Transactional
    @RequestMapping(value = { "/chargeImmediately" }, method = RequestMethod.POST)
	public String stripe(@RequestParam Map<String, String> request,  ModelMap model, HttpServletResponse response, HttpServletRequest httpRequest)
	{
		String token = request.get("stripeToken");
		String status="";
		String check="";
		String afterMD5=Encryption.string2MD5(checkFine);
		Cookie idCookie = WebUtils.getCookie(httpRequest, "userId");
		List<CartLineInfo> selectedProducts = Utils.getLastOrderedProductInSession(httpRequest);
		String userId = idCookie.getValue();
		int id = Integer.valueOf(userId);
		CustomerInfo userInfo = (CustomerInfo)model.get("userInfo");
		if (token != null) {
			status=chargeImmediately(request, token,response, id, selectedProducts);
		}
		check=Encryption.convertMD5(status);
		if (check.equals(afterMD5)) {
			return "shoppingCartFinalize";
		}else
		{
			return"redirect:/shoppingCart";
		}
//		model.addAttribute("cur", "eur");
//		model.addAttribute("amt", "100000");
		
	}
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public String chargeImmediately(Map<String, String> request, String token, HttpServletResponse responese, int id, List<CartLineInfo> selectedProducts)
	{
//		ModelAndView mvc = new ModelAndView();
		
		Map<String, Object> item = new HashMap<String, Object>();
		String success = "system/online/success";
		String faile = "system/online/faile";
		Stripe.apiKey = "sk_test_DkYIH4LZPnTgnzpjiZmMHX9J";
		int result =0;
		Gson gson = new Gson();
		try {
//			RequestOptions requestOptions = RequestOptions.builder().setApiKey("YOUR-SECRET-KEY").build();
//			String token = request.getParameter("stripeToken");

			// Charge the user's card:
			Map<String, Object> params = new HashMap<String, Object>();
			//find a way to get amount from post request 
			params.put("amount", amount);
			params.put("currency", CHARGE_CURRENCY);
			params.put("description", "Example charge");
			params.put("source", token);
			Charge charge = Charge.create(params);
			String chargeId = charge.getId();
			String object = charge.getObject();
			long chargeAmount = charge.getAmount();
			int amount= new Long(chargeAmount).intValue();
//			List<Integer> userIds = userDao.getUserId(userInfo);
//			for(int userId: userIds)
//			{
				ChargeInfoModel chargeInfo = new ChargeInfoModel(chargeId, object,amount, id);
				chargeDao.saveCharge(chargeInfo);
//			}
				for(CartLineInfo singleProduct: selectedProducts)
				{
					ProductInfo selectProduct = new ProductInfo();
					selectProduct = singleProduct.getProductInfo();
					selectProduct.setStock(singleProduct.getQuantity());
					result=ckService.buyProduct(selectProduct, 1);
					if (result==-1) {
						throw  new Exception();
						
					}
				}
			
//			chargeInfo.setAmount(amount);
//			chargeInfo.setId(chargeId);
//			chargeInfo.setObject(object);
//			chargeInfo.setCreateDate(new Date());
			
			/*LOG.info("Payment charged to the following account: " + request);
			LOG.debug("Charge: " + charge);*/
//			model.put("charge_id", charge.getId());
//			mvc.setViewName("success");
//			mvc.addObject("success", item) ;
			
			return Encryption.convertMD5(Encryption.string2MD5(checkFine));
		} catch (RateLimitException   e) {
//			item.put("error", e.getMessage());
//			LOG.error("Payment declined for account: " + request);
//			mvc.setViewName("faile");
//			mvc.addObject("error",e.getMessage()) ;
//			model("error", e.getMessage());
			System.out.println(e.getMessage());
			return Encryption.convertMD5(Encryption.string2MD5(checkFaile));
		}catch (CardException  e) {
			// TODO: handle exception
			System.out.println("Status is: " + e.getCode());
			  System.out.println("Message is: " + e.getMessage());
			  return Encryption.convertMD5(Encryption.string2MD5(checkFaile));
		}catch (InvalidRequestException  e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
			return Encryption.convertMD5(Encryption.string2MD5(checkFaile));
		}catch (AuthenticationException  e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
			return Encryption.convertMD5(Encryption.string2MD5(checkFaile));
		}catch (APIConnectionException  e) {
			System.out.println(e.getMessage());
			return Encryption.convertMD5(Encryption.string2MD5(checkFaile));
		}catch(StripeException e)
		{
			System.out.println(e.getMessage());
			return Encryption.convertMD5(Encryption.string2MD5(checkFaile));
		}catch (Exception e) {
			System.out.println(e.getMessage());
			return Encryption.convertMD5(Encryption.string2MD5(checkFaile));
		}
		// Token is created using Stripe.js or Checkout!
		// Get the payment token ID submitted by the form:
	}
}
