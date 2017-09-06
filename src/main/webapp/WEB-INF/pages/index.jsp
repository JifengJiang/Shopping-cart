<%--
  Created by IntelliJ IDEA.
  User: jifengjiang
  Date: 2017-09-06
  Time: 9:21 AM
  To change this template use File | Settings | File Templates.
--%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">

    <title>Books Shop Online</title>

    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/styles.css">

</head>
<body>


<jsp:include page="_header.jsp" />
<jsp:include page="_menu.jsp" />

<div class="page-title">Shopping Cart Demo23333</div>

<div class="demo-container">
    <h3>Demo content</h3>
    <h1>${pageContext}</h1>
    <ul>
        <li>Buy online</li>
        <li>Admin pages</li>
        <li>Reports</li>
    </ul>
</div>


<jsp:include page="_footer.jsp" />

</body>
</html>