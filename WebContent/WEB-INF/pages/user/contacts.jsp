<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<%@include file="/WEB-INF/fragments/headTag.jspf" %>
<body ng-app="app" id = "body" ng-cloak ng-controller="contactsController">
<%@include file="/WEB-INF/fragments/user_fragments/bodyHeader.jspf" %>
<div class="table-responsive">
    <table class="table table-bordered table-hover">
        <thead>
        <tr>
            <th>{lang.userName}}</th>
            <th>{lang.email}}</th>
            <th>{lang.coursesCount}}</th>
            <th></th>
        </tr>
        </thead>
        <tbody>
        <tr ng-repeat="tutor in tutors" ng-class="{ 'has-feedback': getUnread(${currentUser.id},  user.id) > 0 }">
            <td>{{tutor.name}}</td>
            <td>
                mailto:{{user.email}}
            </td>
            <td>
                {{getCount(user)}}
            </td>
            <td class="col-lg-1">
                <a class="btn btn-danger btn-sm" ng-click = "message(user)">{lang.dialog}}</a>
            </td>
        </tr>
        </tbody>
    </table>
</div>
<%@ include file="/WEB-INF/fragments/footer.jspf" %>
<%@ include file="/WEB-INF/fragments/user_fragments/modal.jspf" %>
<%@ include file="/WEB-INF/fragments/user_fragments/userScripts.jsp" %>
</body>
</html>