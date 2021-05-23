<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="ru.job4j.dream.store.PsqlStore" %>
<%@ page import="ru.job4j.dream.model.Candidate" %>
<%@ page import="ru.job4j.dream.store.PsqlStore" %>
<%@ page import="java.lang.reflect.Array" %>
<%@ page import="java.util.ArrayList" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<!doctype html>
<html lang="en">
<head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
          integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
    <script src="https://code.jquery.com/jquery-3.4.1.slim.min.js"
            integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n"
            crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"
            integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo"
            crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"
            integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6"
            crossorigin="anonymous"></script>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <title>AJAX</title>
    <title>Работа мечты</title>
</head>
<body>

<script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
<script>
    function validate() {
        valid = true;
        if (document.contact_form.name.value == "") {
            alert("Пожалуйста заполните поле 'Имя'.")
            valid = false;
        }
        return valid;
    }

    function readCity() {
        $.ajax({
            type: 'GET',
            url: 'http://localhost:8080/dreamjob/city',
            //     data: {text: $('#exampleInputEmail1').val()},
            dataType: 'json'
        }).done(function (data) {
            var objSel = document.getElementById("city");
            var cities = "";
            for (i = 0; i < data.length; i++) {
                cities += "<option value=" + data[i] + ">" + data[i] + "</option>";
            }
            $('#city').html(cities);
        }).fail(function (err) {
            alert("err");
            alert(err);
        });
    }
</script>


<%
    String id = request.getParameter("id");
    Candidate candidate = new Candidate(0, "", "");
    if (id != null) {
        candidate = PsqlStore.instOf().findByIdCan(Integer.valueOf(id));
    }
%>

<div class="container pt-3">
    <div class="row">
        <div class="card" style="width: 100%">
            <div class="card-header">
                <ul class="nav">
                    <%if (id == null) {%>
                    <li>Новый кандидат</li>
                    <%} else {%>
                    <li>Редактирование кандидата</li>
                    <%}%>
                    <li>
                        <a class="nav-link" href="<%=request.getContextPath()%>/auto.do">
                            <c:out value="${user.name}"/>|Выйти</a>
                    </li>
                </ul>

            </div>
            <div class="card-body">
                <form name="contact_form" action="<%=request.getContextPath()%>/candidate.do?id=<%=candidate.getId()%>"
                      method="post" onsubmit="return validate();">
                    <div class="form-group">
                        <label>Имя</label>
                        <%--<input type="text" class="form-control" name="name">--%>
                        <input type="text" class="form-control" name="name" value="<%=candidate.getName()%>" id="name"  onclick="return readCity();">
                        <label for="city">Город</label>
                        <select class="form-control" name="city" id="city">
                            <%--<option>мужчина</option>--%>
                            <%--<option>женщина</option>--%>
                        </select>
                        </button>

                    </div>
                    <button type="submit" class="btn btn-primary">Сохранить</button>
                </form>
                <form action="<%=request.getContextPath()%>/candidateDelete.do?id=<%=candidate.getId()%>" method="post">
                    <button type="submit" class="btn btn-primary">Удалить</button>
                </form>

            </div>
        </div>
    </div>
</div>
</body>
</html>

