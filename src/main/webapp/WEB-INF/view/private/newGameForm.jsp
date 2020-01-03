<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>

<div id="newGameFormBox" class="newGameForm">
    <form method="post" action="${contextPath}/game">
        <fieldset>
            <legend> New game configuration:</legend>
            <div id="playerXNameBox">
                <span id="playerXNameLabel">Player X:</span>
                <input type="text" size="20" id="playerXName" name="playerX">
            </div>
            <div id="playerONameBox">
                <span id="playerONameLabel">Player O:</span>
                <input type="text" size="20" id="playerOName" name="playerO">
            </div>
            <div id="boardSizeBox">

                <span id="boardSizeLabel">Board size:</span>
                <input name="boardSize" type="number" min="3" max="200" value="3";
                       title="TODO: set default value, add hide when not used"
                >
            </div>
            <div id="winningLengthBox">
                <span id="winningLengthLabelPrefix">Mode:</span>
                <input list="playModes" name="winningLength">
                <datalist id="playModes">
                    <option value="3">
                    <option value="5">
                </datalist>
                <span id="winningLengthLabelPostfix">in row</span>
            </div>
            <input type="submit" value="New Game">
        </fieldset>
    </form>
</div>