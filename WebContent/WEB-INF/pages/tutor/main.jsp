<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
<%@include file="/WEB-INF/fragments/headTag.jspf" %>
<body ng-app="app" id = "body" ng-cloak ng-controller="mainTutorController">
<%@include file="/WEB-INF/fragments/tutor_fragments/bodyHeader.jspf" %>
<div class="container">
    <ul class="nav nav-pills nav-justified" role="tablist">
        <li role="presentation" class="active"><a href="#tab1" role="tab" data-toggle="tab">{lang.profile}}</a></li>
        <li role="presentation"><a href="#tab2" role="tab" data-toggle="tab">{lang.beforeStartCourses}}</a></li>
        <li role="presentation"><a href="#tab3" role="tab" data-toggle="tab">{lang.activeCourses}}</a></li>
        <li role="presentation"><a href="#tab4" role="tab" data-toggle="tab">{lang.finishedCourses}}</a></li>
    </ul>
    <div class="tab-content">
        <div role="tabpanel" class="tab-pane active" id="tab1">
            <div class="row">
                <%--<div class="col-md-5  toppad  pull-right col-md-offset-3 ">
                    &lt;%&ndash;<A href="edit.html" >Edit Profile</A>&ndash;%&gt;
                    <br>
                </div>--%>
                <div class="col-xs-12 col-sm-12 col-md-8 col-lg-6 col-xs-offset-0 col-sm-offset-0 col-md-offset-3 col-lg-offset-3 toppad" >
                    <div class="panel panel-info">
                        <div class="panel-heading">
                            <h3 class="panel-title">${currentUser.name}</h3>
                        </div>
                        <div class="panel-body">
                            <div class="row">
                                <div class="col-md-3 col-lg-3 " align="center"> <img alt="User Pic" src="<c:url value="/uploads/users/${currentUser.image}"/>" class="img-circle img-responsive"> </div>

                                <div class=" col-md-9 col-lg-9 ">
                                    <table class="table table-user-information">
                                        <tbody>
                                        <tr>
                                            <td>{lang.name}}:</td>
                                            <td>${currentUser.name}</td>
                                        </tr>
                                        <tr>
                                            <td>{lang.login}}:</td>
                                            <td>${currentUser.login}</td>
                                        </tr>
                                        <tr>
                                        <tr>
                                            <td>Email</td>
                                            <td>${currentUser.email}</td>
                                        </tr>
                                        <tr>
                                            <td>{lang.tutorCoursesCount}}</td>
                                            <td><span class="badge">${coursesCount}</span></td>
                                        </tr>
                                        </tbody>
                                    </table>
                                </div>
                            </div>
                        </div>
                        <div class="panel-footer">
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div role="tabpanel" class="tab-pane" id="tab2">
            <div class="jumbotron">
                <div class="container">
                    <div class="shadow">
                        <div class="view-box">
                            <h3>{lang.beforeStartCourses}}</h3>
                            <table class="table table-striped display" id="before">
                                <thead>
                                <tr>
                                    <th>{lang.title}}</th>
                                    <th>{lang.startDate}}</th>
                                    <th>{lang.endDate}}</th>
                                    <th>{lang.themes}}</th>
                                    <th>{{lang.subscribersCount}}</th>
                                    <th></th>
                                    <th></th>
                                </tr>
                                </thead>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div role="tabpanel" class="tab-pane" id="tab3">
            <div class="jumbotron">
                <div class="container">
                    <div class="shadow">
                        <div class="view-box">
                            <h3>{lang.activeCourses}}</h3>
                            <table class="table table-striped display" id="active">
                                <thead>
                                <tr>
                                    <th>{lang.title}}</th>
                                    <th>{lang.startDate}}</th>
                                    <th>{lang.endDate}}</th>
                                    <th>{lang.themes}}</th>
                                    <th>{{lang.subscribersCount}}</th>
                                    <th></th>
                                    <th></th>
                                </tr>
                                </thead>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div role="tabpanel" class="tab-pane" id="tab4">
            <div class="jumbotron">
                <div class="container">
                    <div class="shadow">
                        <div class="view-box">
                            <h3>{lang.finishedCourses}}</h3>
                            <table class="table table-striped display" id="finished">
                                <thead>
                                <tr>
                                    <th>{lang.title}}</th>
                                    <th>{lang.startDate}}</th>
                                    <th>{lang.endDate}}</th>
                                    <th>{lang.themes}}</th>
                                    <th>{{lang.subscribersCount}}</th>
                                    <th></th>
                                    <th></th>
                                    <th></th>
                                    <th></th>
                                </tr>
                                </thead>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<%@ include file="/WEB-INF/fragments/footer.jspf" %>
<%@ include file="/WEB-INF/fragments/tutor_fragments/journalModal.jspf" %>
<%@ include file="/WEB-INF/fragments/tutor_fragments/subscribersModal.jspf" %>
<%@ include file="/WEB-INF/fragments/tutor_fragments/tutorModal.jspf" %>
<script src="/assets/js/tutorViewDataTable.js"></script>
<script type="text/javascript">
    $(document).ready(function () {
        $(document).on('show.bs.modal', '.modal', function (event) {
            var zIndex = 1040 + (10 * $('.modal:visible').length);
            $(this).css('z-index', zIndex);
            setTimeout(function() {
                $('.modal-backdrop').not('.modal-stack').css('z-index', zIndex - 1).addClass('modal-stack');
            }, 0);
        });
    });
</script>
<%@ include file="/WEB-INF/fragments/tutor_fragments/tutorScripts.jspf" %>
</body>
</html>