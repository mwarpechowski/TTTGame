<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@ taglib tagdir = "/WEB-INF/tags" prefix="t" %>
<%@ taglib uri = "http://www.springframework.org/tags" prefix = "spring" %>

<div id="gameBoardBox" class="gameBoard">
    <div id="gameConfigInfo">
        <span id="playerX" class="playerName">
            <img src='<spring:url value="/img/x.png"/>' alt="X">
            <span class="label">:</span>
            <span class="value">${game.playerX}</span>
        </span>
        <span id="playerO" class="playerName">
            <img src='<spring:url value="/img/o.png"/>' alt="O">
            <span class="label">:</span>
            <span class="value">${game.playerO}</span>
        </span>
    </div>
    <div id="gameControls">
        <div id="hideAxesButton" class="button">
            <img src='<spring:url value="/img/axes.png"/>' alt="Axes icon" title="Show axes">
        </div>
        <div id="undoLastMoveButton" class="button" onclick="undoLastMove()">
            <img src='<spring:url value="/img/undo.png"/>' alt="UNDO" title="Undo last move"/>
        </div>
        <div id="showMovesHistoryButton" class="button">
            <img src='<spring:url value="/img/list.png"/>' alt="List icon" title="Show moves history"/>
        </div>
    </div>
    <div><ul>TODO:
        <li>Add dynamic board size support</li>
        <li>Fireworks for winning player</li>
        <li>Resizing board
            <ul>
                <li>Add Slider for resizing</li>
                <li>Add resize finction</li>
            </ul>
        </li>
        <li>Saving preferences in cookies</li>
        <li>Marking move effects (predicting)</li>
    </ul></div>
    <t:boardtable model="${game.board}"/>
</div>
