<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
<%@include file="/WEB-INF/fragments/user_fragments/headTag.jspf" %>
<body ng-app="app" ng-cloak ng-controller="coursesController" <%--nv-file-drop="" uploader="uploader"--%>>

<%@include file="/WEB-INF/fragments/user_fragments/bodyHeader.jspf" %>
<div class="container">
    <input type="hidden" ng-model="id" value="${sessionScope.currentUser}">
    <form>
    <div >
        <div class="form-group  col-md-3">
            <label for="type">{lang.sortBy}}</label>
            <select class="form-control" ng-model="searchData.sortBy" id="type">
                <c:forEach items="${sortBy}" begin="1" var = "type">
                    <option>${type}</option>
                </c:forEach>
            </select>
            <label for="order">{lang.order}}</label>
            <select class="form-control col-md-3" ng-model="searchData.order" id="order">
                    <option>asc</option>
                    <option>desc</option>
            </select>
        </div>
        <div class="form-group  col-md-3">
            <label for="searchBy">{lang.searchBy}}</label>
            <select class="form-control" ng-model="searchData.searchBy" id="searchBy">
                <c:forEach items="${searchBy}" var = "type">
                    <option>${type}</option>
                </c:forEach>
            </select>
            <label ng-show="!searchData.searchBy.localeCompare('')==0" for="searchOn">{lang.order}}</label>
            <select ng-show="searchData.searchBy.localeCompare('theme')==0" class="form-control col-md-3" ng-model="searchData.search" id="searchOn">
                <c:forEach items="${themes}" var = "theme">
                    <option>${theme}</option>
                </c:forEach>
            </select>
            <input type="text" ng-show="!searchData.searchBy.localeCompare('theme')==0" class="form-control col-md-3" ng-model="searchData.search"/>
        </div>
    </div>
    </form>


    <br>
    <br>
    <br>
    <br>
    <br>
    <br>
    <div class="form-group">

    </div>
    <div class="view-box">

        <div ng-repeat="course in courses">
            <div class="col-sm-6 col-md-4">
                <div class="thumbnail">
                    <figure>
                        <img src="<c:url value="/uploads/courses/{{course.image}}"/>"<%-- alt="item1"--%> width="340"
                             height="300">
                    </figure>
                    <div class="caption">
                        <h3>{{course.title}}</h3>

                        <p>{{course.description}}</p>

                        <p role="presentation">
                            <small>{lang.subscribersCount}} <span class="badge">{{course.subscribersCount}}</span></small>
                        </p>
                        <c:choose>
                        <c:when test="${empty sessionScope.currUser}">
                            <p><a href="${pageContext.request.contextPath}/user/login">{lang.loginTo}}</a> {lang.subscribe}}
                            </p>
                        </c:when>
                        <c:otherwise>
                        <p><a href="#" ng-if="!(course.subscribed || course.status.localeCompare('FINISHED'))"
                              ng-click="subscribeToCourse(course.id)" class="btn btn-primary" role="button">{lang.subscribe}}</a>
                            </c:otherwise>
                            </c:choose>
                            <a class="btn btn-default" ng-click="courseDetails(course)" role="button">{lang.details}}</a>
                        </p>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<%@ include file="/WEB-INF/fragments/user_fragments/footer.jspf" %>
<div class="modal fade" id="editRow" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h2 class="modal-title" id="myModalLabel">{lang.courseDetails}}</h2>
            </div>
            <div class="modal-body">
                <img src="<c:url value="/uploads/courses/{{detailsData.image}}"/>"<%-- alt="item1"--%> width="570"
                     height="250">

                <div >
                    <h1 id="title">{{detailsData.title}}</h1>
                </div>

                <div >
                    <h2 id="description">{{detailsData.description}}</h2>
                </div>

                <div >
                    <p role="presentation">
                        <small>{lang.subscribersCount}} <span class="badge">{{detailsData.subscribersCount}}</span>
                        </small>
                    </p>
                </div>

                <div >
                    <h4>{lang.start}} {{detailsData.startDate}} | {lang.end}} {{detailsData.endDate}}</h4>
                </div>

                <div>
                        <h4><span ng-repeat="theme in detailsData.themes" class="label label-default">{{theme}}</span></h4>
                </div>

                <div class="space">
                    <div class="pull-right">
                    <img src="<c:url value="/uploads/users/{{detailsData.tutor.image}}"/>"<%-- alt="item1"--%>
                         width="80" height="80">
                    </div>
                    <h5>{lang.tutorIs}} <a href="${pageContext.request.contextPath}/user/tutor/{{detailsData.tutor.id}}">
                        {{detailsData.tutor.name}}</a></h5>
                </div>

                <div class="modal-footer">
                    <button type="button" class="btn btn-primary" ng-click="subscribeToCourse(course.id)">
                        {lang.subscribe}}
                    </button>
                    <button type="button" class="btn btn-default" ng-click="hide()">{lang.close}}</button>
                </div>
            </div>

        </div>
    </div>
</div>
</body>
</html>