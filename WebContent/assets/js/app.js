/**
 * Created by Влад on 07.02.2016.
 */
// app.js
// create angular app
var app = angular.module('app', ['ngMessages']);
app.controller('mainController',

    function($scope, $http, $location) {
        $scope.formData = {};

        $scope.changeLang = function(id){
            console.log(id);
            $http.post("/locale?newLocale="+id, {}).success(function(data, status) {
                $scope.locale = id;
                $scope.lang = data;
            })

        };

        $scope.checkLang = function(lang){
            if (lang==$scope.lang.language){
                return true;
            }
        };

        $scope.failedNote = undefined;

        $scope.closeNoty = function(){
            if ($scope.failedNote) {
                $scope.failedNote.close();
                $scope.failedNote = undefined;
            }
        };

        $scope.failNoty = function(reason) {
            $scope.closeNoty();
            $scope.failedNote = noty({
                text: reason,
                type: 'error',
                layout: 'bottomRight',
                timeout: 2500
            });
        };

        angular.element(document).ready(function () {
            $scope.changeLang('ru');
        });

        // function to submit the form after all validation has occurred
        $scope.submitForm = function(path) {
            $http({
                method  : 'POST',
                url     : path,
                data    : $.param($scope.formData),  // pass in data as strings
                headers : { 'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8' }  // set the headers so angular passing info as form data (not request payload)
            }).success(function (){
                    window.location.href = 'user/courses';
            })
                .error(function (data){
                    $scope.message = data;
                    console.log(data);
                    $scope.failNoty($scope.message.substring($scope.message.indexOf('<u>'), $scope.message.indexOf('</u>')));
            });
        };


        $scope.login = '/user/login';
        $scope.register = '/user/register';
});

