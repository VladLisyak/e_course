<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!doctype html>
<html>
<head>

</head>
<body ng-controller = "coursesCtrl">
<div class="modal fade" id="courseModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
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
                        <div style="margin-bottom: 25px"  ng-class="{ 'has-error': courseForm.password.$touched && courseForm.password.$invalid }">
                            <label class="control-label" for="name">{lang.title}}</label>
                            <input type="text" id = "name" ng-model = "detailsData.title" class="form-control"
                                   ng-minlength="5"
                                   ng-maxlength="20"
                                   required>
                            <div class="help-block" ng-messages="courseForm.name.$error" ng-show="courseForm.name.$touched">
                                <p ng-message="minlength">{lang.tooShort}}</p>
                                <p ng-message="maxlength">{lang.tooLong}}</p>
                                <p ng-message="required">{lang.required}}</p>
                            </div>
                        </div>

                        <div style="margin-bottom: 25px"  ng-class="{ 'has-error': courseForm.desc.$touched && courseForm.desc.$invalid }">
                        <label class="control-label" for="desc">{lang.description}}</label>
                        <textarea id = "desc" class="form-control" ng-model = "detailsData.description" class="form-control"
                                  required />
                            <div class="help-block" ng-messages="courseForm.desc.$error" ng-show="courseForm.desc.$touched">
                                <p ng-message="required">{lang.required}}</p>
                            </div>
                        </div>

                         <label class="control-label" for="count">{lang.subscribers}}</label>
                         <span id = "count" class="form-control" ng-model = "detailsData.subscribersCount" class="form-control">{{detailsData.subscribersCount}}</span>

                        <label class="control-label" for="date">{lang.when}}</label>
                            <div class="form-control" id = "date">
                                {lang.from}}
                                <uib-datepicker ng-model="detailsData.startDate" min-date="now" show-weeks="false" class="well well-sm"></uib-datepicker>
                                {lang.to}}
                                <uib-datepicker ng-model="detailsData.startDate" min-date="now + 1" show-weeks="false" class="well well-sm"></uib-datepicker>
                            </div>

                        <div style="margin-bottom: 25px"  ng-class="{ 'has-error': courseForm.tutor.$touched && courseForm.tutor.$invalid }">
                            <label class="control-label" for="tutor">{lang.tutor}}</label>
                        <select class="form-control" id = "tutor" ng-model = "detailsData.tutor"
                                ng-multiple="false" data-placeholder="{lang.roles}}"
                                chosen
                                class="form-control chosen"
                                ng-options="tutor for tutor in tutors" required>
                        </select>
                        </div>

                        <div style="margin-bottom: 25px"  ng-class="{ 'has-error': courseForm.themes.$touched && courseForm.themes.$invalid }">
                        <label class="control-label" for="themes">{lang.themes}}</label>
                        <select class="form-control" id = "themes" ng-model = "detailsData.themes"
                                ng-multiple="true" multiple data-placeholder="{lang.roles}}"
                                chosen
                                class="form-control"
                                ng-options="theme for theme in themes" required>
                        </select>
                        </div>
                        <div class="form-control" class="row" ng-show = "newImage.localeCompare('')==0" style="margin: 0 auto;">
                            <img src="/uploads/courses/{{detailsData.image}}" width="300" height="300">
                        </div>
                        <input type="file" class="form-control" name="image" placeholder="{image}}" ng-model="newImage" accept="image/*" app-filereader/>

                    </div>
                 </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-primary"  ng-show="!courseForm.$invalid" ng-click="submit()">{lang.Update}}</button>
                        <button type="button" class="btn btn-default" ng-click="hide('courseModal')">{lang.Cancel}}</button>
                    </div>
            </div>
            </form>
        </div>
    </div>
</div>
</body>
</html>