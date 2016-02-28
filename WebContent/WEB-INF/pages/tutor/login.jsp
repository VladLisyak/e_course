<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
<%@include file="/WEB-INF/fragments/headTag.jspf" %>
<body ng-app="app" id = "body" ng-cloak ng-controller="mainTutorController">
<%@include file="/WEB-INF/fragments/tutor_fragments/bodyHeader.jspf" %>
<div class="container">
    <div id="loginbox" style="margin-top:50px;" class="mainbox col-md-6 col-md-offset-3 col-sm-8 col-sm-offset-2">
        <div class="panel panel-info" >
            <div class="panel-heading">
                <div class="panel-title">{{lang.login}}</div>
            </div>

            <div style="padding-top:30px" class="panel-body" >

                <div style="display:none" id="login-alert" class="alert alert-danger col-sm-12"></div>

                <form id="loginform" name="userForm" action="" class="form-horizontal" role="form"  novalidate>


                    <div style="margin-bottom: 25px" ng-class="{ 'has-error': userForm.login.$touched && userForm.login.$invalid }">
                        <label for="login">{{lang.userLogin}}</label>
                        <input id="login-username" type="text" id="login" class="form-control" name="login" placeholder="login"
                               ng-model="formData.login"
                               ng-minlength="5"
                               ng-maxlength="10"
                               required>
                        <div class="help-block" ng-messages="userForm.login.$error" ng-show="userForm.login.$touched">
                            <p ng-message="minlength">{{lang.tooShort3}}</p>
                            <p ng-message="maxlength">{{lang.tooLong100}}</p>
                            <p ng-message="required">{{lang.required}}</p>
                        </div>
                    </div>


                    <div style="margin-bottom: 25px"  ng-class="{ 'has-error': userForm.password.$touched && userForm.password.$invalid }">
                        <%--<span class="input-group-addon"><i class="glyphicon glyphicon-lock"></i></span>--%>
                        <label for="password">{{lang.userPassword}}</label>
                        <input id="login-password" id ="password" type="password" class="form-control" name="password" placeholder="password"
                               ng-model="formData.password"
                               ng-minlength="5"
                               ng-maxlength="20"
                               required>
                        <div class="help-block" ng-messages="userForm.password.$error" ng-show="userForm.password.$touched">
                            <p ng-message="minlength">{{lang.tooShort5}}</p>
                            <p ng-message="maxlength">{{lang.tooLong20}}</p>
                            <p ng-message="required">{{lang.required}}</p>
                        </div>
                    </div>

                    <div style="margin-top:10px" class="form-group">
                        <!-- Button -->

                        <div class="col-sm-12 controls">
                            <a id="btn-login" ng-click ="submitForm()" ng-show="!userForm.$invalid" class="btn btn-success">{{lang.login}}</a>
                        </div>
                    </div>


                    <div class="form-group">

                    </div>
                </form>

            </div>
        </div>
    </div>
</div>
<%@ include file="/WEB-INF/fragments/footer.jspf" %>
<%@ include file="/WEB-INF/fragments/tutor_fragments/tutorScripts.jspf" %>
</body>
</html>