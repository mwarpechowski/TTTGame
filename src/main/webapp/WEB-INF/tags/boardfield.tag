<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@ tag description="Template of game board field" pageEncoding="UTF-8" %>
<%@ attribute name="col" type="java.lang.Integer" required="true" %>
<%@ attribute name="row" type="java.lang.Integer" required="true" %>
<%@ attribute name="symbol" %>

<td id="boardField_${col}_${row}"
        class="boardField ${symbol}"
        data-col="${col}"
        data-row="${row}">
</td>