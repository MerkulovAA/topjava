<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib uri="http://example.com/functions" prefix="f" %>
<html>
<head>
    <title>Meals</title>
</head>
<body>
<center>
    <h1>Meals</h1>
    <h2>
        <a href="meals">List All Meals</a>

    </h2>
</center>
<div align="center">
    <table border="1" cellpadding="5">
        <tr>
            <th>Date</th>
            <th>Description</th>
            <th>Calories</th>
        </tr>
        <c:forEach var="meal" items="${mealWithExceeds}">

            <tr style="${meal.exceed ? 'background-color: red': ''}">
                <td><c:out value="${f:formatLocalDateTime(meal.dateTime, 'yyyy-MM-dd HH:mm:ss')}"/></td>
                <td><c:out value="${meal.description}"/></td>
                <td><c:out value="${meal.calories}"/></td>
                            </tr>
        </c:forEach>
    </table>
</div>

</body>
</html>
