<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@ taglib tagdir = "/WEB-INF/tags" prefix="t" %>
<%@ tag description="Template of game board" pageEncoding="UTF-8" %>
<%@ attribute name="model" type="pl.mwinc.demo.ttt.view.BoardView" required="true" %>
<%@ attribute name="gameId" type="java.lang.Integer" required="true" %>

<div id="gameBoardTableWrap">
<table id="gameBoardTable" data-gameid="${gameId}">
    <tbody>
        <!-- horizontal axis row -->
        <tr>
            <td class="boardHorizontalAxis boardVerticalAxis"/> <!-- diagonal empty cell -->
            <c:forEach var="col" begin="0" end="${model.size - 1}">
                <td class="boardHorizontalAxis"><jsp:text>${col}</jsp:text></td>
            </c:forEach>
        </tr>
        <c:forEach var="row" items="${model.fields}">
            <tr>
                <td class="boardVerticalAxis">${row['key']}</td>
                <c:forEach var="col" items="${row['value']}">
                <t:boardfield col="${col['key']}" row="${row['key']}" symbol="${col['value']}"/>
                </c:forEach>
            </tr>
        </c:forEach>
    </tbody>
</table>
</div>