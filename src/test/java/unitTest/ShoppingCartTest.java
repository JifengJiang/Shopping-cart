package unitTest;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import com.cart.dao.BuyDAO;
import com.cart.entity.Product;
import com.cart.model.ProductInfo;
import com.cart.service.CheckoutService;

public class ShoppingCartTest extends BaseTest{

	private MockMvc mockMvc;
	@Autowired
	private WebApplicationContext wtc;
	@Autowired
	CheckoutService ckService;
	@Autowired
	SessionFactory sf;

	
	@Before
	public void setUp() throws Exception
	{
		mockMvc = MockMvcBuilders.webAppContextSetup(wtc).build();
	}
	
	@Test
	@Transactional
	public void testUpdateTx()
	{
//		Session s1 = sf.openSession();
//		Session s2 = sf.openSession();
//	
//		Transaction tx=s1.beginTransaction();
//		s2.beginTransaction(); 
		int result1 = 0;
		int amountLeft=0;
//		try {
			ProductInfo product = new ProductInfo();
			product.setCode("S001");
			product.setName("Core Java");
			product.setPrice(100.00);
			product.setQuantity(4);
			ProductInfo product2 = new ProductInfo();
			product2.setCode("S001");
			product2.setName("Core Java");
			product2.setPrice(100.00);
			product2.setQuantity(4);
//			amountLeft = buyDao.getAmountByLock("S001");
//			buyDao.buyProduct(product);
//			int amount2 = buyDao.getAmountByLock("S001");
//			buyDao.buyProduct(product2);
			
			result1=this.ckService.buyProduct(product,0);
			int result2 = this.ckService.buyProduct(product2, 1);
//			tx.commit();
//			s1.close();
//			System.out.println("ok");
//		} catch (Exception e) {
////			tx.rollback();
//			tx.commit();
//			s1.close();
//			System.out.println("faile");
//		}
		
		
		
//		test1();
//		test2();
	}
	
//	@Test
//	@Transactional
//	public void test1()
//	{
//		Session s1 = sf.openSession();
//		int result1 = 0;
//		int amountLeft=0;
//		ProductInfo product = new ProductInfo();
//		product.setCode("S001");
//		product.setName("Core Java");
//		product.setPrice(100.00);
//		product.setQuantity(4);
//		amountLeft = ckService.getAvalibleNumber(product);
//		result1=this.ckService.buyProduct(product, amountLeft,0);
//	}
//	
//	@Test
//	@Transactional
//	public void test2()
//	{
//		int amountLeft=0;
//		ProductInfo product2 = new ProductInfo();
//		product2.setCode("S001");
//		product2.setName("Core Java");
//		product2.setPrice(100.00);
//		product2.setQuantity(4);
//		amountLeft = ckService.getAvalibleNumber(product2);
//		int result2 = this.ckService.buyProduct(product2, amountLeft,1);
//	}
}
