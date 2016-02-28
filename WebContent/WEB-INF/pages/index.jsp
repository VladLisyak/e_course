<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
<%@include file="/WEB-INF/fragments/headTag.jspf"%>
<body ng-app="app" ng-cloak ng-controller="loginRegisterController" <%--nv-file-drop="" uploader="uploader"--%>>

<%@include file="/WEB-INF/fragments/user_fragments/bodyHeader.jspf"%>

    <h1 class="center center-align -align-center text-center">{lang.meetEcourse}}</h1>
    <img src="assets/img/homepage-hero-lg.jpg" style = "width: 100%; height : 300px">
    <h4 class="center center-align -align-center text-center">{lang.promotion}}</h4>

<div class="text-center">
    <a href="${pageContext.request.contextPath}/user/courses" class="btn btn-lg btn-warning">{lang.courses}}</a>
</div>

<%@ include file="/WEB-INF/fragments/footer.jspf"%>
<script type="text/javascript" src="assets/js/user.js"></script>
</body>
</html>