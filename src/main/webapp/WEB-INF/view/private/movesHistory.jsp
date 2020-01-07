<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@ taglib uri = "http://www.springframework.org/tags" prefix = "spring" %>

<div id="movesHistoryBox">
    <ol id="movesHistory">Moves history:
        <c:forEach items="${moves}" var="move">
            <li id="move_${move.seqNumber}"
                data-moveid="${move.seqNumber}" data-col="${move.col}" data-row="${move.row}">
                <span class="symbol">${move.symbol}:</span>
                <span class="column">col=${move.col}</span>
                <span class="row">row=${move.row}</span>
            </li>
        </c:forEach>
    </ol>
</div>