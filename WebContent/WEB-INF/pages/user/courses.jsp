<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
<%@include file="/WEB-INF/fragments/user_fragments/headTag.jspf"%>
<body ng-app="app" ng-cloak ng-controller="coursesController" <%--nv-file-drop="" uploader="uploader"--%>>

<%@include file="/WEB-INF/fragments/user_fragments/bodyHeader.jspf"%>
<div class="container">
    <div ng-repeat="course in courses">
        {{course.title}}
    </div>
</div>
<%@ include file="/WEB-INF/fragments/user_fragments/footer.jspf"%>
</body>
</html>