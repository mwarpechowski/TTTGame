<%@ taglib uri = "http://www.springframework.org/tags" prefix = "spring" %>
<%@ tag description="Overall Page template" pageEncoding="UTF-8" %>
<%@ attribute name="title" fragment="true" %>
<%@ attribute name="pageSpecificScript" fragment="true" %>

<html>
<head>
    <meta charset="utf-8">
    <meta name="author" content="Michał Warpechowski">
    <link rel="stylesheet" href='<spring:url value="/css/layout.css"/>'>
    <script>var contextPath='<%= request.getContextPath() %>'</script>
    <script src="https://ajax.aspnetcdn.com/ajax/jQuery/jquery-3.4.1.min.js"></script>
    <jsp:invoke fragment="pageSpecificScript"/>
    <title><jsp:invoke fragment="title"/></title>
</head>
<body>
    <div id="mainGridContainer">
        <div id="pageBody">
            <jsp:doBody/>
        </div>
        <header id="pageHeader">
            <jsp:include page="../view/private/header.jsp" />
        </header>
        <footer id="pageFooter">
            <jsp:include page="../view/private/footer.jsp" />
        </footer>
        <div id="pageLeftSidebar">
            <jsp:include page="../view/private/leftSidebar.jsp" />
        </div>
        <div id="pageRightSidebar">
            <jsp:include page="../view/private/rightSidebar.jsp" />
        </div>
    </div>
</body>
</html>