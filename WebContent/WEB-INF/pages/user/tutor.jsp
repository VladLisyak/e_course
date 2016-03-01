<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
<%@include file="/WEB-INF/fragments/headTag.jspf" %>
<body ng-app="app" id = "body" ng-cloak ng-controller="tutorDetailsController">
<%@include file="/WEB-INF/fragments/user_fragments/bodyHeader.jspf" %>
<div class="container">
    <div class="row">
        <%--<div class="col-md-5  toppad  pull-right col-md-offset-3 ">
            &lt;%&ndash;<A href="edit.html" >Edit Profile</A>&ndash;%&gt;
            <br>
        </div>--%>
            <div class="col-xs-12 col-sm-12 col-md-8 col-lg-8 col-xs-offset-0 col-sm-offset-0 col-md-offset-3 col-lg-offset-2 toppad" >
                <div class="panel panel-info">
                    <div class="panel-heading">
                        <h3 class="panel-title">${tutor.name}</h3>
                    </div>
                    <div class="panel-body">
                        <div class="row">
                            <div class="col-md-3 col-lg-3 " align="center"> <img alt="User Pic" src="<c:url value="/uploads/users/${tutor.image}"/>" class="img-circle img-responsive"> </div>
                            <div class=" col-md-9 col-lg-9 ">
                            <table class="table table-user-information">
                                <tbody>
                                <tr>
                                    <td>{{lang.name}}:</td>
                                    <td>${tutor.name}</td>
                                </tr>
                                <tr>
                                    <td>{{lang.tlogin}}:</td>
                                    <td>${tutor.login}</td>
                                </tr>
                                <tr>
                                <tr>
                                    <td>Email</td>
                                    <td>${tutor.email}</td>
                                </tr>
                                <tr>
                                    <td>{{lang.coursesCount}}</td>
                                    <td>{{courses.length}}</td>
                                </tr>

                                </tbody>
                            </table>

                        <%--<a href="#" class="btn btn-primary">My Sales Performance</a>
                        <a href="#" class="btn btn-primary">Team Sales Performance</a>--%>
                        </div>
                    </div>
                </div>
                    <div class="panel-footer">
                            <input type="text"  class = "input-medium search-query form-control" ng-model = "searchText" placeholder="{{lang.search}}"/>
                    </div>
            <table class="table table-condensed">
            <thead>
                <tr>
                    <th></th>
                    <th>{{lang.title}}</th>
                    <th>{{lang.status}}</th>
                    <th>{{lang.startDate}}</th>
                    <th>{{lang.themes}}</th>
                    <th></th>
                    <th></th>
                </tr>
            </thead>
            <tbody>
            <tr ng-repeat = "course in courses | filter:searchText">
            <td><img alt="Course Pic" src="<c:url value="/uploads/courses/{{course.image}}"/>" height="50" width="50"/></td>
            <td><h4>{{course.title}}</h4></td>
            <td><span>{{course.status}}</span></td>
            <td><span>{{course.startDate}}</span></td>
            <td><h4 ng-repeat="theme in course.themes" ><span class="label label-default">{{theme}}</span></h4></td>
            <td><a class="btn btn-xs btn-success details-btn" ng-click = "courseDetails(course)"><i class = "glyphicon glyphicon-search"></i></a></td>
            <c:if test="${not empty currentUser}">
                <td><a href="#" ng-if="(!course.subscribed) && (course.status.localeCompare('FINISHED')!=0)"
                   ng-click="subscribeToCourse(course.id)" class="btn btn-xs btn-primary" role="button"><i class = "glyphicon glyphicon-pencil"></i></a></td>
            </c:if>
            </tbody>
            </table>
            </div>
        </div>
    </div>
</div>
<%@ include file="/WEB-INF/fragments/footer.jspf" %>
<%@ include file="/WEB-INF/fragments/user_fragments/modal.jspf" %>
<script src="assets/js/courseListDataTable.js" type="text/javascript"></script>
<%@ include file="/WEB-INF/fragments/user_fragments/userScripts.jsp" %>
</body>
</html>