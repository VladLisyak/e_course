<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!doctype html>
<html>
<head>

</head>
<body ng-controller = "coursesCtrl">
<div class="modal fade" id="addCourseModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabelAddCourse">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h2 class="modal-title" id="myModalLabel">{lang.courseDetails}}</h2>
            </div>
            <form id="courseForm" name = "courseForm" class="form-horizontal" role="form" enctype="multipart/form-data" novalidate>
                <div class="modal-body">
                    <div class="row">
                        <div class="col-lg-12">
                            <div style="margin-bottom: 25px"  ng-class="{ 'has-error': courseForm.title.$touched && courseForm.title.$invalid }">
                                <label class="control-label" for="title">{{lang.title}}</label>
                                <input type="text" id = "title" name = "title" ng-model = "detailsData.title" class="form-control"
                                       ng-minlength="5"
                                       ng-maxlength="20"
                                       required>
                                <div class="help-block" ng-messages="courseForm.title.$error" ng-show="courseForm.title.$touched">
                                    <p ng-message="minlength">{{lang.tooShort5}}</p>
                                    <p ng-message="maxlength">{{lang.tooLong20}}</p>
                                    <p ng-message="required">{{lang.required}}</p>
                                </div>
                            </div>

                            <div style="margin-bottom: 25px"  ng-class="{ 'has-error': courseForm.desc.$touched && courseForm.desc.$invalid }">
                                <label class="control-label" for="desc">{{lang.description}}</label>
                        <textarea id = "desc" class="form-control" name = "desc" ng-model = "detailsData.description" class="form-control"
                                  required></textarea>
                                <div class="help-block" ng-messages="courseForm.desc.$error" ng-show="courseForm.desc.$touched">
                                    <p ng-message="required">{{lang.required}}</p>
                                </div>
                            </div>

                            <label class="control-label" for="startDate">{{lang.startDate}}</label>


                            <div id = "startDate"  ng-class="{ 'has-error': courseForm.start.$touched && courseForm.start.$invalid }">
                                <p class="input-group">
                                    <input type="text" class="form-control" name = "start" uib-datepicker-popup="yyyy-MM-dd" ng-model="detailsData.startDate" is-open="popup1.opened" min-date="minDate" ng-required="true" close-text="Close" />
                                 <span class="input-group-btn">
                                     <button type="button" class="btn btn-default" ng-click="open1()"><i class="glyphicon glyphicon-calendar"></i></button>
                                 </span>
                                </p>
                            </div>

                            <label class="control-label" for="endDate">{{lang.endDate}}</label>
                            <div id = "endDate"  ng-class="{ 'has-error': courseForm.end.$touched && courseForm.end.$invalid }">
                                <p class="input-group">
                                    <input type="text" class="form-control" name = "end" uib-datepicker-popup="yyyy-MM-dd" ng-model="detailsData.endDate" is-open="popup2.opened" min-date="getDate()" ng-required="true" close-text="Close" />
                                 <span class="input-group-btn">
                                     <button type="button" class="btn btn-default" ng-click="open2()"><i class="glyphicon glyphicon-calendar"></i></button>
                                 </span>
                                </p>
                            </div>

                            <div style="margin-bottom: 25px"  ng-class="{ 'has-error': courseForm.tutor.$touched && courseForm.tutor.$invalid }">
                                <label class="control-label" for="tutor">{lang.tutor}}</label>
                                <select class="form-control" name = "tutor" id = "tutor" ng-model = "detailsData.tutor"
                                        ng-multiple="false" data-placeholder="{lang.tutor}}"
                                        chosen
                                        class="form-control"
                                        ng-options="tutor.name for tutor in tutors"
                                        required>
                                </select>
                            </div>

                            <div style="margin-bottom: 25px" ng-class="{ 'has-error': courseForm.themes.$touched && courseForm.themes.$invalid }">
                                <label class="control-label" for="themes">{{lang.themes}}</label>
                                <select id = "themes" name = "themes" ng-model = "detailsData.themes"
                                        ng-multiple="true" multiple data-placeholder="{{lang.themes}}"
                                        chosen
                                        class="form-control"
                                        ng-options="theme for theme in themes" required>
                                </select>
                            </div>

                            <div class="row" ng-show = "newImage.localeCompare('')==0" style="margin: 0 auto;">
                                <img src="/uploads/courses/{{detailsData.image}}" width="100" height="100">
                                <input type="file" name="image" placeholder="{image}}" ng-model="newImage" accept="image/*" app-filereader/>
                            </div>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-primary"  ng-show="!courseForm.$invalid" ng-click="postCourse()">{{lang.Submit}}</button>
                        <button type="button" class="btn btn-default" ng-click="hideWithName('addCourseModal')">{{lang.Cancel}}</button>
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>
</body>
</html>