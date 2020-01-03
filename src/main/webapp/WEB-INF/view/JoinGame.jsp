<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri = "http://www.springframework.org/tags" prefix = "spring" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@ taglib tagdir = "/WEB-INF/tags" prefix="t" %>

<t:genericpage>
    <jsp:attribute name="title">Join Game</jsp:attribute>
    <jsp:attribute name="pageSpecificScript"><script src='<spring:url value="/js/join_game.js"/>'></script></jsp:attribute>
    <jsp:body>
        <c:set var="contextPath" value="${pageContext.servletContext.contextPath}" scope="session"/>
        <div id="newGameDiv" name="newGameDiv">
            <jsp:include page="private/newGameForm.jsp"/>
        </div>
        <div id="existingGamesList" name="existingGamesList">
            <jsp:include page="private/gamesList.jsp"/>
        </div>
    </jsp:body>
</t:genericpage>
