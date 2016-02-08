<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
<%@include file="/WEB-INF/fragments/user_fragments/headTag.jspf"%>
<body ng-app="validationApp" ng-controller="mainController">

<%@include file="/WEB-INF/fragments/user_fragments/bodyHeader.jspf"%>
<div class="container">
    <h2 class="page-header"><fmt:message key="app.login"/></h2>
    <div class="row">
        <form name="userForm" novalidate>

            <div class="form-group" ng-class="{ 'invalid': userForm.name.$touched && userForm.name.$invalid }">
                <label>Name</label>
                <input type="text" name="name" class="form-control" ng-class="{ 'invalid': userForm.name.$touched && userForm.name.$invalid }"
                       ng-model="main.name"
                       ng-minlength="5"
                       ng-maxlength="10"
                       required>

                <div class="help-block" ng-messages="userForm.name.$error" ng-if="userForm.name.$touched">
                    <p class="invalid" ng-message="minlength">Your name is too short.</p>
                    <p class="invalid" ng-message="maxlength">Your name is too long.</p>
                    <p class="invalid" ng-message="required">Your name is required.</p>
                </div>
            </div>

            <div class="form-group" ng-class="{ 'invalid': userForm.email.$touched && userForm.email.$invalid }">
                <label>Email</label>
                <input type="email" name="email" class="form-control"
                       ng-model="main.email"
                       ng-minlength="5"
                       ng-maxlength="20"
                       required>

                <div class="help-block" ng-messages="userForm.email.$error" ng-if="userForm.email.$touched">
                    <div ng-messages-include="messages.html"></div>
                </div>

            </div>

            <div class="form-group">
                <button type="submit" class="btn btn-danger">Submit</button>
            </div>

        </form>

        <pre>userForm.name.$error = {{ userForm.name.$error | json }}</pre>

    </div>
</div>
<%@ include file="/WEB-INF/fragments/user_fragments/footer.jspf"%>
</body>
</html>

