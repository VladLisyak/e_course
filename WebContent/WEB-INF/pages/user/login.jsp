<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
<%@include file="/WEB-INF/fragments/headTag.jspf"%>
<body ng-app="app" ng-cloak class="ng-cloak" ng-controller="loginRegisterController" <%--nv-file-drop="" uploader="uploader"--%>>

<%@include file="/WEB-INF/fragments/user_fragments/bodyHeader.jspf"%>
<div class="container">
    <div id="loginbox" style="margin-top:50px;" class="mainbox col-md-6 col-md-offset-3 col-sm-8 col-sm-offset-2">
        <div class="panel panel-info" >
            <div class="panel-heading">
                <div class="panel-title">{{lang.login}}</div>
                <div style="float:right; font-size: 80%; position: relative; top:-10px"><a href="${pageContext.request.contextPath}/user/forgetPassword">{{lang.forgotPassword}}</a></div>
            </div>

            <div style="padding-top:30px" class="panel-body" >

                <div style="display:none" id="login-alert" class="alert alert-danger col-sm-12"></div>

                <form id="loginform" name="userForm" action="" class="form-horizontal" role="form"  novalidate>


                    <div style="margin-bottom: 25px" ng-class="{ 'has-error': userForm.login.$touched && userForm.login.$invalid }">
                        <label for="login">{{lang.userLogin}}</label>
                        <input id="login-username" type="text" id="login" class="form-control" name="login" placeholder="login"
                               ng-model="formData.login"
                               ng-minlength="5"
                               ng-maxlength="20"
                               required>
                        <div class="help-block" ng-messages="userForm.login.$error" ng-show="userForm.login.$touched">
                            <p ng-message="minlength">{{lang.tooShort5}}</p>
                            <p ng-message="maxlength">{{lang.tooLong20}}</p>
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
                            <a id="btn-login" ng-click ="submitForm(login)" ng-show="!userForm.$invalid" class="btn btn-success">{{lang.login}}</a>
                        </div>
                    </div>


                    <div class="form-group">
                        <div class="col-md-12 control">
                            <div style="border-top: 1px solid#888; padding-top:15px; font-size:85%" >
                                {{lang.accountMissing}}
                                <a onClick="$('#loginbox').hide(); $('#signupbox').show()">
                                    {{lang.registerHere}}
                                </a>
                            </div>
                        </div>
                    </div>
                </form>

            </div>
        </div>
    </div>
    <div id="signupbox" style="display:none; margin-top:50px" class="mainbox col-md-6 col-md-offset-3 col-sm-8 col-sm-offset-2">
        <div class="panel panel-info">
            <div class="panel-heading">
                <div class="panel-title">{{lang.register}}</div>
                <div style="float:right; font-size: 85%; position: relative; top:-10px"><a id="signinlink" onclick="$('#signupbox').hide(); $('#loginbox').show()">Sign In</a></div>
            </div>
            <div class="panel-body" >
                <form id="signupform" name = "signupForm" class="form-horizontal" role="form" enctype="multipart/form-data" novalidate>

                    <div class="form-group" ng-class="{ 'has-error': signupForm.name.$touched && signupForm.name.$invalid }">
                        <label for="name" class="col-md-3 control-label">{{lang.name}}</label>
                        <div class="col-md-9">
                            <input type="text" id = "name" class="form-control" ng-pattern = "nameTemplate" name="name" placeholder="{{lang.name}}"
                                   ng-model="formData.name"
                                   ng-minlength="3"
                                   ng-maxlength="100"
                                   required>
                            <div class="help-block" ng-messages="signupForm.name.$error" ng-show="signupForm.name.$touched">
                                <p ng-message="minlength">{{lang.tooShort3}}</p>
                                <p ng-message="maxlength">{{lang.tooLong100}}</p>
                                <p ng-message="required">{{lang.required}}</p>
                                <p ng-message="pattern">{{lang.invalidFormat}}</p>
                            </div>
                        </div>
                    </div>

                    <div class="form-group" ng-class="{ 'has-error': signupForm.login.$touched && signupForm.login.$invalid }">
                        <label for="userLogin" class="col-md-3 control-label">{{lang.login}}</label>
                        <div class="col-md-9">
                            <input type="text" id="userLogin" class="form-control" ng-pattern = "loginTemplate" name="login" placeholder="{{lang.login}}"
                                   ng-model="formData.login"
                                   ng-minlength="5"
                                   ng-maxlength="100"
                                   required>
                            <div class="help-block" ng-messages="signupForm.login.$error" ng-show="signupForm.login.$touched">
                                <p ng-message="minlength">{{lang.tooShort5}}</p>
                                <p ng-message="maxlength">{{lang.tooLong100}}</p>
                                <p ng-message="required">{{lang.required}}</p>
                                <p ng-message="pattern">{{lang.invalidFormat}}</p>
                            </div>
                        </div>
                    </div>

                    <div class="form-group" ng-class="{ 'has-error': signupForm.email.$touched && signupForm.email.$invalid }">
                        <label for="email" class="col-md-3 control-label">{{lang.email}}</label>
                        <div class="col-md-9">
                            <input type="email" id = "email" class="form-control" ng-pattern = "emailTemplate" name="email" placeholder="{{lang.email}}"
                                   ng-model="formData.email"
                                   ng-minlength="5"
                                   ng-maxlength="100"
                                   required>
                            <div class="help-block" ng-messages="signupForm.email.$error" ng-show="signupForm.email.$touched">
                                <p ng-message="minlength">{{lang.tooShort5}}</p>
                                <p ng-message="maxlength">{{lang.tooLong100}}</p>
                                <p ng-message="required">{{lang.required}}</p>
                                <p ng-message="email">{{lang.invalidEmail}}</p>
                                <p ng-message="pattern">{{lang.invalidFormat}}</p>
                            </div>
                        </div>
                    </div>

                    <div class="form-group" ng-class="{ 'has-error': signupForm.password.$touched && signupForm.password.$invalid }">
                        <label for="password" class="col-md-3 control-label">{{lang.password}}</label>
                        <div class="col-md-9">
                            <input type="password" class="form-control"  ng-pattern="passwordTemplate" name="password" placeholder="{{lang.password}}"
                                   ng-model="formData.password"
                                   ng-minlength="5"
                                   ng-maxlength="100"
                                   required>
                            <div class="help-block" ng-messages="signupForm.password.$error" ng-show="signupForm.password.$touched">
                                <p ng-message="minlength">{{lang.tooShort5}}</p>
                                <p ng-message="maxlength">{{lang.tooLong100}}</p>
                                <p ng-message="pattern">{{lang.invalidFormat}}</p>
                                <p ng-message="required">{{lang.required}}</p>
                            </div>
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="password" class="col-md-3 control-label">{{lang.image}}</label>
                        <div class="col-md-9">
                            <input type="file" class="form-control" name="image" placeholder="{image}}" ng-model="formData.image" accept="image/*" app-filereader/>
                        </div>
                    </div>

                    <div class="form-group">
                        <!-- Button -->
                        <div class="col-md-offset-3 col-md-9">
                            <button id="btn-signup" type="button" ng-click = "submitForm(register)" ng-show="!signupForm.$invalid" class="btn btn-info"><i class="icon-hand-right"></i> &nbsp {{lang.register}}</button>
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