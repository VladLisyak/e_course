<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<%@include file="/WEB-INF/fragments/headTag.jspf" %>
<body ng-app="app" id = "body" ng-cloak ng-controller="tutorContactsController">
<%@include file="/WEB-INF/fragments/tutor_fragments/bodyHeader.jspf" %>
<div class="container">
    <div class="table-responsive">
        <table class="table table-bordered table-hover">
            <thead>
            <tr>
                <th>{lang.userName}}</th>
                <th>{lang.email}}</th>
                <th></th>
            </tr>
            </thead>
            <tbody>
            <tr ng-repeat="tutor in tutors" >
                <td><div ng-class="{ 'has-feedback': count[tutor.id] > 0 }">{{tutor.name}}</div></td>
                <td>
                    <a href="mailto:{{tutor.email}}">{{tutor.email}}</a>
                </td>
                <td class="col-lg-1">
                    <a class="btn btn-info btn-sm" ng-show = "count[tutor.id] > 0" ng-click = "message(tutor.id)">{lang.messages}}: {{count[tutor.id]}}</a>
                    <a class="btn btn-danger btn-sm" ng-show = "count[tutor.id] <= 0" ng-click = "message(tutor.id)">{lang.dialog}}</a>
                </td>
            </tr>
            </tbody>
        </table>
    </div>
    <div class="modal fade" id="editRow" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
        <div class="modal-dialog" id = "hid">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                    <h2 class="modal-title" id="myModalLabel">{lang.Conversation}}</h2>
                </div>
                <div class="modal-body">
                    <div class="row col-lg-10 col-lg-offset-1">
                        <div ng-repeat = "message in messages">
                            <div class="row" ng-if = "opponentId == message.fromId" >
                                <h5 class="row col-md-4 col-lg-4 col-md-offset-8 col-lg-offset-8">{{message.referrerName}}:
                                    {{message.message}}</h5>
                            </div>
                            <div class="row" ng-if = "opponentId != message.fromId" >
                                <h5 class="row pull-left">{lang.you}}:
                                    {{message.message}}</h5>
                            </div>
                        </div>
                    </div>

                </div>
            </div>
            <div class="modal-footer">
                <input type = "text" max="30" maxlength="30" ng-model = "messageText.message"/>
                <button type="button" class="btn btn-primary" ng-disabled = "messageText.message.length <= 0"  ng-click="sendMessage()">{lang.Submit}}</button>
                <button type="button" class="btn btn-default" ng-click="hide()">{lang.Cancel}}</button>
            </div>
        </div>
    </div>
</div>
<%@ include file="/WEB-INF/fragments/footer.jspf" %>
<%@ include file="/WEB-INF/fragments/user_fragments/modal.jspf"%>
<%@ include file="/WEB-INF/fragments/tutor_fragments/tutorScripts.jspf" %>
</body>
</html>