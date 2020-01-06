<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri = "http://www.springframework.org/tags" prefix = "spring" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@ taglib tagdir = "/WEB-INF/tags" prefix="t" %>

<t:genericpage>
    <jsp:attribute name="title">Game ${game.id}</jsp:attribute>
    <jsp:attribute name="pageSpecificScript">
        <script>var gameId=${game.id}</script>
        <script src='<spring:url value="/js/game.js"/>'></script>
    </jsp:attribute>
    <jsp:body>
        <c:set var="contextPath" value="${pageContext.servletContext.contextPath}" scope="session"/>
        <jsp:include page="private/gameBoard.jsp" />
    </jsp:body>
</t:genericpage>
