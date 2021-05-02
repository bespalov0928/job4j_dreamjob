<%@ page import="ru.job4j.dream.model.Post" %>
<%@ page import="ru.job4j.dream.store.PsqlStore" %>
<%@ page language="java" pageEncoding="UTF-8" session="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>photoUpload</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/js/bootstrap.min.js"></script>
</head>
<body>

<%
    String id = request.getParameter("id");
%>
<div class="container">
    <h2>Upload image</h2>
    <%--<form action="<c:url value='/photoUpload.do?id=<%=id%>'/>" method="post" enctype="multipart/form-data">--%>
    <form action="<%=request.getContextPath()%>/photoUpload.do?id=<%=id%>" method="post" enctype="multipart/form-data">
        <div class="checkbox">
            <input type="file" name="file">
        </div>
        <button type="submit" class="btn btn-default">Загрузить</button>
    </form>
</div>

</body>
</html>
