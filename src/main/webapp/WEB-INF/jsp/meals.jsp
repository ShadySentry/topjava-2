<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://topjava.javawebinar.ru/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<html>
<jsp:include page="fragments/headTag.jsp"/>
<body>
<jsp:include page="fragments/bodyHeader.jsp"/>
<script type="text/javascript" src="resources/js/mealdatatablesUtil.js" defer></script>
<script type="text/javascript" src="resources/js/mealDatatables.js" defer></script>

<div class="jumbotron">
    <div class="container">
        <h3><spring:message code="meal.title"/></h3>
        <br/>
        <a class="btn btn-primary" onclick="add()">
            <span class="glyphicon glyphicon-plus" aria-hidden="true"></span>
            <spring:message code="common.add"/>
        </a>
        <table class="table table-striped display" id="datatable">
            <thead>
            <tr>
                <th><spring:message code="meal.dateTime"/></th>
                <th><spring:message code="meal.description"/></th>
                <th><spring:message code="meal.calories"/></th>
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
                    <td><a><span class="glyphicon glyphicon-pencil" aria-hidden="true"></span></a></td>
                    <td><a class="delete" id="${meal.id}"><span class="glyphicon glyphicon-remove" aria-hidden="true"></span></a></td>
                </tr>
            </c:forEach>
        </table>
    </div>
</div>



<div class="modal fade" id="editRow">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h2 class="modal-title"><spring:message code="meal.add"/></h2>
            </div>
            <div class="modal-body">
                <form class="form-horizontal" id="detailsForm">
                    <input type="hidden" id="id" name="id">

                    <div class="form-group">
                        <label for="dateTime" class="control-label col-xs-3"><spring:message code="meal.dateTime"/></label>

                        <div class="col-xs-9">
                            <input type="datetime-local" class="form-control" id="dateTime" name="dateTime" placeholder="<spring:message code="meal.dateTime"/>">
                        </div>
                    </div>


                    <div class="form-group">
                        <label for="description" class="control-label col-xs-3"><spring:message code="meal.description"/></label>

                        <div class="col-xs-9">
                            <input type="text" class="form-control" id="description" name="description" placeholder="<spring:message code="meal.description"/>">
                        </div>
                    </div>


                    <div class="form-group">
                        <label for="dateTime" class="control-label col-xs-3"><spring:message code="meal.calories"/></label>

                        <div class="col-xs-9">
                            <input type="text" class="form-control" id="c" name="calories" placeholder="<spring:message code="meal.calories"/>">
                        </div>
                    </div>

                    <div class="form-group">
                        <div class="col-xs-offset-3 col-xs-9">
                            <button type="submit" class="btn btn-primary">
                                <span class="glyphicon glyphicon-ok" aria-hidden="true"></span>
                            </button>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>

<script>

    function makeEditable() {
        $(".delete").click(function () {
            deleteRow($(this).attr("id"));
        });

        $("#detailsForm").submit(function () {
            save();
            return false;
        });

        $(document).ajaxError(function (event, jqXHR, options, jsExc) {
            failNoty(jqXHR);
        });

        // solve problem with cache in IE: https://stackoverflow.com/a/4303862/548473
        $.ajaxSetup({cache: false});
    }

    function add() {
        $("#detailsForm").find(":input").val("");
        $("#editRow").modal();
    }

    function deleteRow(id) {
        $.ajax({
            url: ajaxUrl + id,
            type: "DELETE",
            success: function () {
                updateTable();
                successNoty("Deleted");
            }
        });
    }

    function updateTable() {
        $.get(ajaxUrl, function (data) {
            datatableApi.clear().rows.add(data).draw();
        });
    }

    function save() {
        var form = $("#detailsForm");
        $.ajax({
            type: "POST",
            url: ajaxUrl,
            data: form.serialize(),
            success: function () {
                $("#editRow").modal("hide");
                updateTable();
                successNoty("Saved");
            }
        });
    }

    var failedNote;

    function closeNoty() {
        if (failedNote) {
            failedNote.close();
            failedNote = undefined;
        }
    }

    function successNoty(text) {
        closeNoty();
        new Noty({
            text: "<span class='glyphicon glyphicon-ok'></span> &nbsp;" + text,
            type: 'success',
            layout: "bottomRight",
            timeout: 1000
        }).show();
    }

    function failNoty(jqXHR) {
        closeNoty();
        failedNote = new Noty({
            text: "<span class='glyphicon glyphicon-exclamation-sign'></span> &nbsp;Error status: " + jqXHR.status,
            type: "error",
            layout: "bottomRight"
        }).show();
    }
</script>


<jsp:include page="fragments/footer.jsp"/>
</body>
</html>