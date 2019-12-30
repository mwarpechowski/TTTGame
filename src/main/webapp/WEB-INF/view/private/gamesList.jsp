<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/fmt" prefix = "fmt" %>
<%@ taglib uri = "http://www.springframework.org/tags" prefix = "spring" %>

<div id="gamesListBox" class="gameList">
    <table id="gamesTable">
        <caption>Available games:</caption>
        <thead>
        <tr>
            <th>ID</th>
            <th>Date</th>
            <th>Player X</th>
            <th>Player O</th>
            <th>Status</th>
            <th>Action</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${games}" var="game">
            <tr>
                <td><span id="${game.id}_gameURL" class="url"><a href="${contextPath}/game/${game.id}">#<c:out
                        value="${game.id}"/></a></span>
                </td>
                <td><span id="${game.id}_gameDate" class="date">
                <fmt:parseDate value="${game.createDate}" pattern="yyyy-MM-dd'T'HH:mm:ss" var="parsedDateTime"
                               type="both"/>
                <fmt:formatDate pattern="dd.MM.yyyy HH:mm:ss" value="${parsedDateTime}"/>
                </span>
                </td>
                <td><span id="${game.id}_playerXName" class="playerName"><c:out value="${game.playerX}"/></span></td>
                <td><span id="${game.id}_playerOName" class="playerName"><c:out value="${game.playerO}"/></span></td>
                <td>
                    <div id="${game.id}_finishedIndicator"
                         title="
                        TODO: Add proper class depending on game finished or not
                        TODO: proper display of finished indicators
                        ">
                    </div>
                </td>
                <td>
                        <img src='<spring:url value="/img/delete_cross.png"/>' alt="Delete" about="Picture taken from HiClipart.com"
                             title="Delete"
                             class="deleteButton"
                             onclick="delete(${contextPath}/game/${game.id})">
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>