<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@ taglib uri = "http://www.springframework.org/tags" prefix = "spring" %>

<div id="movesListBox">
    <ol>
        <c:forEach items="${moves}" var="move">
            <li id="move_${move.seqNumber}" class="move_${move.symbol}"
                onmouseenter="markBoardField(${move.col}, ${move.row}); markMoveListItem(${move.seqNumber});"
                onmouseleave="unmarkBoardField(${move.col}, ${move.row}); unmarkMoveListItem(${move.seqNumber});">
                <span>${move.symbol}: col=${move.col} row=${move.row}</span>
                <div title="just for test" id="boardField_${move.col}_${move.row}">Test div to be removed</div>
            </li>
        </c:forEach>
    </ol>
</div>