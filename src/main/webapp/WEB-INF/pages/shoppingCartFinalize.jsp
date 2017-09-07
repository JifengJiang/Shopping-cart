<%--
  Created by IntelliJ IDEA.
  User: jifengjiang
  Date: 2017-09-06
  Time: 9:23 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">

    <title>Shopping Cart Finalize</title>

    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/styles.css">

</head>
<body>
<jsp:include page="_header.jsp" />

<jsp:include page="_menu.jsp" />

<div class="page-title">Finalize</div>

<div class="container">
    <h3>Thank you for Order</h3>
    <%-- Your order number is: ${lastOrderedCart.orderNum} --%>
</div>

<jsp:include page="_footer.jsp" />

</body>
</html>
