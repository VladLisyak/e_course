/**
 * Created by Влад on 21.02.2016.
 */
var app = angular.module('app')

app.controller('tutorContactsController',

    function($scope, $http, $rootScope, $location, UserFactory, CourseFactory, MessageFactory, $interval) {
        $scope.tutors = {};
        $scope.count = {};
        $scope.searchField = "";

        UserFactory.students(function success(data){
            $scope.tutors = data;
            $scope.init();
        });

        $scope.init = function(){
            $scope.tutors.forEach(function(item){
                $http.get("/ajax/messages/?referrerId=" + item.id + "&count=true", {}).success(function(data) {
                    console.log(data);
                    $scope.count[item.id] = data;
                })
            });
        };


        var stop;


        $scope.messaging = function() {
            // Don't start a new fight if we are already fighting
            if ( angular.isDefined(stop) ) return;

            stop = $interval($scope.updateMessages, 300);
        };

        $scope.updateMessages = function(){
            if($rootScope.flag){
                $scope.message($scope.opponentId);
            }else{
                $scope.stopMessaging();
            }
        };

        $scope.stopMessaging = function() {
            if (angular.isDefined(stop)) {
                $interval.cancel(stop);
                stop = undefined;
            }
        };

        $scope.$on('$destroy', function() {
            // Make sure that the interval is destroyed too
            $scope.stopMessaging();
        });

        $scope.opponentId = 0;
        $scope.currentId = 0;

        $scope.messages = {};
        $scope.messageText = {};
        $scope.messageText['message'] = "";

        $scope.sendMessage = function(){
            $scope.messageText['toId'] = $scope.opponentId;
            $scope.messageText['fromId'] = 0;
            $scope.date = new Date();
            $scope.referrerName = "";
            console.log($scope.messageText);
            MessageFactory.post($scope.messageText);
            $scope.messageText = {};
            $scope.messageText['message'] = "";
        };


        $scope.message = function(referrerId){
            $scope.opponentId = referrerId;
            $rootScope.flag = true;
            MessageFactory.getMessages({referrerId: referrerId}, function(data){
                $scope.messages = data;
                $scope.detailsData = $scope.messages;
                $scope.messages.forEach(function(item){
                    item.read=true;
                    if (item.toId != referrerId){
                        MessageFactory.update(item);}
                });
            });
            $scope.init();
            $rootScope.courseDetails($scope.messages);
            $scope.messaging();
        }


    })

.controller('mainTutorController',

    function($scope, $http, $rootScope) {
        $scope.get = function(id, idModal){
                $http({
                    method: 'GET',
                    url: '/ajax/subscription/'+id
                }).success(function(data) {
                    $scope.courseDetails(data, idModal);
                }).error(function(data){
                    $rootScope.failNoty($scope.message.substring($scope.message.indexOf('<u>'), $scope.message.indexOf('</u>')));
                });
        };

        $scope.subscribers = function(id){
                $http({
                    method: 'GET',
                    url: '/ajax/subscription/'+id
                }).success(function(data) {
                    $scope.courseDetails(data, "subscribers");
                }).error(function(data){
                    $rootScope.failNoty($scope.message.substring($scope.message.indexOf('<u>'), $scope.message.indexOf('</u>')));
                });
        };
       /* function lang(){
            $rootScope.defineLang();
        }
        lang();*/

        $scope.entries = {};

        $scope.courseDetails = function(object, id){
            $scope.entries = object;
            $('#'+id).modal();
        };

        $scope.hide = function(id){
            $('#'+id).modal('hide');
        };

        $scope.mark = 0;

        $scope.saveMark = function(entry){
            delete entry['$$hashKey'];
            $http({
                method: 'PUT',
                dataType : 'JSON',
                data: JSON.stringify(entry),
                headers : { 'Content-Type': 'application/json; charset=UTF-8' },
                url: '/ajax/subscription'
            }).success(function(data) {
                $rootScope.successNoty($scope.lang['saved']);
            }).error(function(data){
                $rootScope.failNoty($scope.message.substring($scope.message.indexOf('<u>'), $scope.message.indexOf('</u>')));
            });
        };

        $scope.formData = {};

        $scope.submitForm = function(path) {
            console.log($scope.formData);
            $http({
                method  : 'POST',
                url     : '/tutor/login',
                data    : $.param($scope.formData),  // pass in data as strings
                headers : { 'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8' }  // set the headers so angular passing info as form data (not request payload)
            }).success(function (){
                    $rootScope.successNoty($scope.lang['tutorLogged']);
                    setTimeout($scope.locationChange, 3000);
            }).error(function (data){
                $scope.message = data;
                $rootScope.failNoty($scope.message.substring($scope.message.indexOf('<u>'), $scope.message.indexOf('</u>')));
            });
        };

        $scope.details = function(id){
            $http({
                method: 'GET',
                url: '/ajax/course/'+id+"?r=TUTOR"
            }).success(function(data) {
                $rootScope.courseDetails(data);
            }).error(function(data){
                $rootScope.failNoty($scope.message.substring($scope.message.indexOf('<u>'), $scope.message.indexOf('</u>')));
            });
        };
/*
        $scope.hide = function(){
            $('#editRow').modal('hide');
        };*/

        $scope.locationChange = function(){
            window.location.href = 'tutor/journal';
        }
    });