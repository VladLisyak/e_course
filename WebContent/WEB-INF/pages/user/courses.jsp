<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
<%@include file="/WEB-INF/fragments/headTag.jspf" %>
<body ng-app="app" ng-cloak class="ng-cloak" ng-controller="coursesController" <%--nv-file-drop="" uploader="uploader"--%>>

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
        <div class="form-group  col-md-3">
            <label for="limit">{lang.viewOnPage}}</label>
            <input type="number" id = "limit" min="1" max="25" class="input-xs col-xs-1 form-control input-md pull-right" ng-model="searchData.limit"/>
        </div>
        <div class="form-group  col-md-3">
        <div class=" col-xs-12 pull-right">
            <button type="button" class="btn btn-info btn-top-margin " ng-click="filter()">{lang.filter}}</button>
        </div>
            <div class="col-xs-12 pull-right">
            <button type="button" class="btn btn-primary btn-top-margin " ng-click="reset()">{lang.reset}}</button>
        </div>
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

        <div dir-paginate="course in courses | itemsPerPage: searchData.limit" total-items="totalCourses" current-page="pagination.current">
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
                        <p>
                        <c:choose>
                        <c:when test="${empty sessionScope.currentUser}">
                            <p><a href="${pageContext.request.contextPath}/user/login">{lang.loginTo}}</a> {lang.subscribe}}
                            </p>
                        </c:when>
                        <c:otherwise>
                        <a ng-show="(!course.subscribed) && (course.status.localeCompare('FINISHED')!=0)"
                              ng-click="subscribeToCourse(course.id)" class="btn btn-primary" role="button">{lang.subscribe}}</a>
                            </c:otherwise>
                        </c:choose>
                        <div>
                            <a class="btn btn-default" ng-click="courseDetails(course)" role="button">{lang.details}}</a>
                        </div>
                        </p>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<div class="row">
<dir-pagination-controls class = "col-md-4 col-md-offset-5" on-page-change="pageChanged(newPageNumber)"></dir-pagination-controls>
</div>
<%@ include file="/WEB-INF/fragments/footer.jspf" %>
<%@ include file="/WEB-INF/fragments/user_fragments/userScripts.jsp" %>
<%@ include file="/WEB-INF/fragments/user_fragments/modal.jspf" %>
</body>
</html>