<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://topjava.javawebinar.ru/functions" %>
<%--<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>--%>
<html>
<head>
    <title>Meal list</title>
    <style>
        .normal {
            color: green;
        }

        .exceeded {
            color: red;
        }
    </style>
</head>
<body>
<section>
    <h3><a href="index.html">Home</a></h3>
    <h2>Еда пользователя</h2>
    <hr/>
    <h3> Фильтрация по дате/времени </h3>
    <table cellpadding="2" cellspacing="7" frame="void">
        <thead>
        <tr>
            <th>От Даты</th>
            <th>До Даты</th>
            <th>От Времени</th>
            <th>До Времени</th>
        </tr>
        </thead>
        <tr>
            <form id="formDate" action="meals?action=getFilterDateTime" method="POST">
                <td><input type="date" name="startDate"></td>
                <td><input type="date" name="endDate"></td>
                <td><input type="time" name="startTime"></td>
                <td><input type="time" name="endTime">
                <td>
                <td>
                    <button type="submit">Фильтровать</button>
                </td>
                <td>
                    <button onclick="location.href='meals'" type="button">Отмена</button>
                </td>
            </form>
        </tr>
    </table>
    <table border="1" cellpadding="8" cellspacing="0">
        <thead>
        <tr>
            <th>Дата и время</th>
            <th>Описание</th>
            <th>Калории</th>
            <th></th>
            <th></th>
        </tr>
        </thead>
        <c:forEach items="${meals}" var="meal">
            <jsp:useBean id="meal" scope="page" type="ru.javawebinar.topjava.to.MealWithExceed"/>
            <tr class="${meal.exceed ? 'exceeded' : 'normal'}">
                <td>
                        <%--${meal.dateTime.toLocalDate()} ${meal.dateTime.toLocalTime()}--%>
                        <%--<%=TimeUtil.toString(meal.getDateTime())%>--%>
                        <%--${fn:replace(meal.dateTime, 'T', ' ')}--%>
                        ${fn:formatDateTime(meal.dateTime)}
                </td>
                <td>${meal.description}</td>
                <td>${meal.calories}</td>
                <td><a href="meals?action=update&id=${meal.id}">Обновить</a></td>
                <td><a href="meals?action=delete&id=${meal.id}">Удалить</a></td>
            </tr>
        </c:forEach>
    </table>
    <h1></h1>
    <a href="meals?action=create">Добавить еду</a>
</section>
</body>
</html>