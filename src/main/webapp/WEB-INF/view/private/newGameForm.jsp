<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>

<div id="newGameFormBox" class="newGameForm">
    <form method="post" action="${contextPath}/game">
        <fieldset>
            <legend> New game configuration:</legend>
            <div id="playerXNameBox">
                <span id="playerXNameLabel">Player X:</span>
                <input type="text" size="20" id="playerXName" name="playerX" required="true">
            </div>
            <div id="playerONameBox">
                <span id="playerONameLabel">Player O:</span>
                <input type="text" size="20" id="playerOName" name="playerO" required="true">
            </div>
            <div id="winningLengthBox">
                <span id="winningLengthLabelPrefix">Play up to </span>
                <select id="gameMode" name="winningLength">
                    <option value="3" selected>3</option>
                    <option value="5">5</option>
                </select>
                <span id="winningLengthLabelPostfix">in row</span>
            </div>
            <div id="boardSizeBox">
                <span id="boardSizeLabel">Board size:</span>
                <span><input id="isFixedSizeBoard" type="checkbox" checked disabled>Fixed</span>
                <input id="boardSize" name="boardSize" type="number" min="3" max="200" value="3" readonly>
            </div>
            <input type="submit" value="New Game">
        </fieldset>
    </form>
</div>