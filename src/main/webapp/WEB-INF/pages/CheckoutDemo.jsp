<%@ page isELIgnored="false" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>PaymentTest</title>
</head>
<body>
<form action="${pageContext.request.contextPath}/chargeImmediately" method="POST">
  <script
    src="https://checkout.stripe.com/checkout.js" class="stripe-button"
    data-key="${publishable_key }"
    data-amount="${ amount}"
    data-name="Personal shopping cart"
    data-description="Your Order"
    data-image="https://stripe.com/img/documentation/checkout/marketplace.png"
    data-zip-code="true"
    data-billing-address="true"
    data-locale="auto"
    data-currency="cad">
  </script>
</form>
<!-- <script src="https://checkout.stripe.com/checkout.js"></script>

<button id="customButton">Purchase</button>

<script>
var handler = StripeCheckout.configure({
  key: 'pk_test_6pRNASCoBOKtIshFeQd4XMUh',
  image: 'https://stripe.com/img/documentation/checkout/marketplace.png',
  locale: 'auto',
  token: function(token) {
    // You can access the token ID with `token.id`.
    // Get the token ID to your server-side code for use.
  }
});

document.getElementById('customButton').addEventListener('click', function(e) {
  // Open Checkout with further options:
  handler.open({
    name: 'Stripe.com',
    description: '2 widgets',
    zipCode: true,
    amount: 2000
  });
  e.preventDefault();
});

// Close Checkout on page navigation:
window.addEventListener('popstate', function() {
  handler.close();
});
</script> -->
</body>
</html>