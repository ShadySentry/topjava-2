<%--
  Created by IntelliJ IDEA.
  User: karav
  Date: 11.03.2018
  Time: 20:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c' %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>Meals</title>
<body>

<style>

    table, th, tr {

        border: 1px solid black;

    }
</style>


<table class="mealsTable">
    <thead>
    <tr>
        <th>Date</th>
        <th>Description</th>
        <th>Calories</th>
        <th>Exceed</th>
        <th>Edit</th>
        <th>Delete</th>
    </tr>
    </thead>
    <c:forEach items="${meals}" var="meal">
        <c:choose>
            <c:when test="${meal.exceed}">
                <style>
                    .${meal.exceed} {
                        color: red;
                    }
                </style>
            </c:when>
            <c:when test="${!meal.exceed}">
                <style>
                    .${meal.exceed} {
                        color: green;
                    }
                </style>
            </c:when>
        </c:choose>
        <tr class="${meal.exceed}">
            <fmt:parseDate pattern="yyyy-MM-dd'T'HH:mm"  value="${meal.dateTime}" var="date" />
            <fmt:formatDate value= "${date}" pattern="dd.MM.yyyy HH:mm" var= "d"/>
            <td>${d}</td>
            <td>${meal.description}</td>
            <td>${meal.calories}</td>
            <td>${meal.exceed}</td>
            <td>
                <form method="post" action="/meals"/>
                <input type="hidden" name="type" value="edit">
                <input type="hidden" name="id" value="${meal.id}">
                    <input type="submit" value="Edit">
                </form>
            </td>
            <td>
                <form method="post"action="/meals" >
                    <input type="hidden" name="type" value="delete">
                    <input type="hidden" name="id" value="${meal.id}">
                    <input type="submit" value="Delete">
                </form>
            </td>
        </tr>
    </c:forEach>
</table>
<form action="/meals" method="post">
    <input type="hidden" name="type" value="create">
    <input type="submit" name="add" value="Add">
</form>
</body>
</html>
