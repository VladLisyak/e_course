/**
 * Created by Влад on 21.02.2016.
 */
var app = angular.module('app')

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
                url: '/ajax/course/'+id
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