<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
<%@include file="/WEB-INF/fragments/headTag.jspf"%>
<body ng-app="app" ng-cloak ng-controller="loginRegisterController" <%--nv-file-drop="" uploader="uploader"--%>>

<%@include file="/WEB-INF/fragments/user_fragments/bodyHeader.jspf"%>

<div class="container">
    <div id="loginbox" style="margin-top:50px;" class="mainbox col-md-6 col-md-offset-3 col-sm-8 col-sm-offset-2">
        <div class="panel panel-info" >
            <div class="panel-heading">
                <div class="panel-title">{lang.passwordRecovery}}</div>
            </div>

            <div style="padding-top:30px" class="panel-body" >

                <div style="display:none" id="login-alert" class="alert alert-danger col-sm-12"></div>

                <form id="loginform" name="userForm" action="" class="form-horizontal" role="form"  novalidate>


                    <div class="form-group" ng-class="{ 'has-error': signupForm.email.$invalid }">
                        <label for="email" class="col-md-3 control-label">{lang.yourEmail}}</label>
                        <div class="col-md-9">
                            <input type="email" id = "email" class="form-control" ng-pattern = "emailTemplate" name="email" placeholder="{lang.email}}"
                                   ng-model="formData.email"
                                   ng-minlength="5"
                                   ng-maxlength="100"
                                   required>
                            <div class="help-block" ng-messages="signupForm.email.$error">
                                <p ng-message="minlength">{lang.tooShort5}}</p>
                                <p ng-message="maxlength">{lang.tooLong100}}</p>
                                <p ng-message="required">{lang.required}}</p>
                                <p ng-message="email">{lang.invalidEmail}}</p>
                                <p ng-message="pattern">{lang.invalidFormat}}</p>
                            </div>
                        </div>
                    </div>

                    <div style="margin-top:10px" class="form-group">
                        <!-- Button -->

                        <div class="col-sm-12 controls">
                            <a id="btn-login" ng-click ="sendEmailReminder()" ng-show="!userForm.$invalid" class="btn btn-success pull-right">{lang.remind}}</a>
                        </div>
                    </div>

                </form>

            </div>
        </div>
    </div>
</div>
<%@ include file="/WEB-INF/fragments/footer.jspf"%>
<%@ include file="/WEB-INF/fragments/user_fragments/userScripts.jsp" %>
</body>
</html>