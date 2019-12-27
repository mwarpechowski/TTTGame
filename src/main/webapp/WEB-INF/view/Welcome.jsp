<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@ taglib tagdir = "/WEB-INF/tags" prefix="t" %>

<t:genericpage>
    <jsp:attribute name="title">Yet Another Tic Tak Toe Game</jsp:attribute>
    <jsp:body>
        <c:set var="contextPath" value="${pageContext.servletContext.contextPath}" scope="session"/>
        <jsp:include page="private/welcome.jsp" />
    </jsp:body>
</t:genericpage>