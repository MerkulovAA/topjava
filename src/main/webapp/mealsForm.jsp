<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="f" uri="http://example.com/functions" %>
<html>
<head>
    <title>Meals</title>
</head>
<body>
<center>
    <h1>Meals Management</h1>
</center>
<div align="center">
    ${meal != null ? '<form action="meals?action=update" method="post">' : '<form action="meals?action=create" method="post">'}
    <table border="1" cellpadding="5">
        <caption>
            <h2>
                ${meal != null? 'Edit Meal' : 'Add New Meal'}
            </h2>
        </caption>
        <input type="hidden" name="id" value="<c:out value="${meal == null ? '' : meal.id}"/>"/>
        <tr>
            <th>Date and time:</th>
            <td>
                <input type="datetime-local" name="dateTime" size="45"
                       value="<c:out value="${meal == null ? '' : meal.dateTime}"/>"/>
            </td>
        </tr>
        <tr>
            <th>Description:</th>
            <td>
                <input type="text" name="description" size="45"
                       value="<c:out value='${meal.description}' />"
                />
            </td>
        </tr>
        <tr>
            <th>Colories:</th>
            <td>
                <input type="text" name="calories" size="5"
                       value="<c:out value='${meal.calories}' />"
                />
            </td>
        </tr>
        <tr>
            <td colspan="2" align="center">
                <input type="submit" value="Save"/>
            </td>
        </tr>
    </table>
    </form>
</div>
</body>
</html>
