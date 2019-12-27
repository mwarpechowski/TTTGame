<%@ tag description="Overall Page template" pageEncoding="UTF-8" %>
<%@ attribute name="title" fragment="true" %>

<html>
<head>
    <jsp:include page="../view/private/pageHead.jsp" />
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