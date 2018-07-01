<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib uri="http://example.com/functions" prefix="f" %>
<html>
<head>
    <title>Meals</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<center>
    <h1>Meals</h1>
</center>
<div align="center">
    <table border="1" cellpadding="5">
        <tr>
            <th>Date</th>
            <th>Description</th>
            <th>Calories</th>
            <th>Action</th>

        </tr>
        <c:forEach var="meal" items="${mealWithExceeds}">

            <tr style="${meal.exceed ? 'background-color: red': ''}">
                <td><c:out value="${f:formatLocalDateTime(meal.dateTime)}"/></td>
                <td><c:out value="${meal.description}"/></td>
                <td><c:out value="${meal.calories}"/></td>
                <td>
                    <a href="meals?action=edit&id=<c:out value='${meal.id}' />">Edit</a>

                    <a href="meals?action=delete&id=<c:out value='${meal.id}' />">Delete</a>
                </td>
            </tr>
        </c:forEach>
    </table>
    <h3>
        <a href="meals?action=new">Add New Meal</a>
    </h3>
</div>

</body>
</html>
