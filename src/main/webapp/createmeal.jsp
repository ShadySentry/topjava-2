<%--
  Created by IntelliJ IDEA.
  User: karav
  Date: 12.03.2018
  Time: 17:05
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Добавление Еды</title>
</head>
<body>
<form method="get" action="/meals">

    <input type="hidden" name="test" value="test">
    <input type="hidden" name="id" value="${meal.id}">
    <label>Дата/Время</label>
    <input type="datetime-local" name="datetime" value="${meal.dateTime}">
    <br>
    <label>Описание</label>
    <input type="text" name="description" value="${meal.description}">
    <br>
    <label>Калории</label>
    <input type="text" name="calories" value="${meal.calories}">
    <br>
    <input type="submit">


</form>

</body>
</html>
