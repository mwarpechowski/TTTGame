<%@ taglib uri = "http://www.springframework.org/tags" prefix = "spring" %>

<div id="navigation" class="navigation">
    <span id="homeLing">
        <a href="${contextPath}">Home</a>
    </span>
    <span id="joinGameLink">
        <a href="${contextPath}/game">Join Game</a>
    </span>
    <span id="rulesLink">
        <a href="${contextPath}/rules">Rules</a>
    </span>
</div>
<hr/>
<div id="logoBox" class="logo">
    <span id="ticTacToeLabel">Tic-Tac-Toe</span>
    <img src='<spring:url value="/img/tic-tac-toe.png"/>' alt="(Yet another) Tic-Tac-Toe" about="Picture taken from HiClipart.com"/>
    <span id="byMWlabel">by Micha&lstrok; Warpechowski</span>
</div>
<hr/>
