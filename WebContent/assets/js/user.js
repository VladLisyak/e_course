/**
 * Created by Влад on 07.02.2016.
 */
// user.js
// create angular app
var app = angular.module('app', ['ngMessages']);
app.run(function($rootScope, $http) {
    $rootScope.changeLang = function(id){
        $http.post("/locale?newLocale="+id, {}).success(function(data, status) {
            $rootScope.locale = id;
            $rootScope.lang = data;
        })
    };

    $rootScope.defineLang = function(){
        $http.get("/locale", {}).success(function(data) {
            $rootScope.changeLang(data);
        })
    };

    $rootScope.checkLang = function(lang){
        if (lang== $rootScope.lang['language']){
            return true;
        }
    };

    $rootScope.hide = function(){
        $('#editRow').modal('hide');
    };


    $rootScope.closeNoty = function(){
        if ( $rootScope.failedNote) {
            $rootScope.failedNote.close();
            $rootScope.failedNote = undefined;
        }
    };

    $rootScope.failNoty = function(reason) {
        $rootScope.closeNoty();
        $rootScope.failedNote = noty({
            text: reason,
            type: 'error',
            layout: 'bottomRight',
            timeout: 2500
        });
    };

    $rootScope.successNoty = function(reason) {
        $rootScope.closeNoty();
        $rootScope.failedNote = noty({
            text: reason,
            type: 'success',
            layout: 'bottomRight',
            timeout: 2500
        });
    };
});
app.controller('loginRegisterController',

    function($scope, $http, $rootScope) {
        $scope.notyFlag = "new";
        $scope.nameTemplate = "^[A-ZА-Яа-яa-z]+$";
        $scope.loginTemplate = "^[A-ZА-Яа-яa-z0-9_-]+$";
        $scope.emailTemplate = "^[_A-Za-z0-9-\+]+(\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\.[A-Za-z0-9]+)*(\.[A-Za-z]{2,})$";
        $scope.passwordTemplate = "^[A-ZА-Яа-яa-z0-9_-]+[-\s][A-ZА-Яа-яa-z0-9_-]+$";

        $scope.login = '/user/login';
        $scope.register = '/user/register';

        $scope.failedNote = undefined;

        $scope.sortTypes = {};
        
        $scope.formData = {};


        // function to submit the form after all validation has occurred
        $scope.submitForm = function(path) {
            console.log($scope.formData);
            $http({
                method  : 'POST',
                url     : path,
                data    : $.param($scope.formData),  // pass in data as strings
                headers : { 'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8' }  // set the headers so angular passing info as form data (not request payload)
            }).success(function (){
                switch (path) {
                    case $scope.login:{
                        $scope.notyFlag = $rootScope.lang['logged'];
                        break;
                    }
                    case $scope.register:{
                        $scope.notyFlag =  $rootScope.lang['registered'];
                        console.log($scope.notyFlag);
                        break;
                    }

                    default:
                }
                if(!($scope.notyFlag.localeCompare('new')==0)){
                    $rootScope.successNoty($scope.notyFlag);
                    $scope.notyFlag = 'new';
                }

                setTimeout($scope.locationChange, 3000);
            }).error(function (data){
                    $scope.message = data;
                    console.log(data);
                    $rootScope.failNoty($scope.message.substring($scope.message.indexOf('<u>'), $scope.message.indexOf('</u>')));
            });
        };

        $scope.locationChange = function(){
            window.location.href = 'user/home';
        };

        angular.element(document).ready(function () {
            $rootScope.defineLang();
        });
});

app.controller('coursesController',

    function($scope, $http, $rootScope) {
        $rootScope.userId = undefined;

        angular.element(document).ready(function () {
            $rootScope.defineLang();
            $http.get("/ajax/course", {})
                .success(function(data) {
                $scope.courses = data;
            }).error(function (data){
                $scope.message = data;
                console.log(data);
                $rootScope.failNoty($scope.message.substring($scope.message.indexOf('<u>'), $scope.message.indexOf('</u>')));
            });
        });

        $scope.subscribeToCourse = function(id){
            $http.post("/ajax/subscription/"+id, {})
                .success(function(data) {
                    $rootScope.successNoty($scope['operationSuccess']);
                    $rootScope.hide();
                }).error(function(data){
                    $scope.message = data;
                    $rootScope.failNoty($scope.message.substring($scope.message.indexOf('<u>'), $scope.message.indexOf('</u>')));
                });
        };

        $scope.detailsData = {};

        $scope.courseDetails = function(object){
            console.log(object);
            $scope.detailsData = object;
            $('#editRow').modal();
        }
});

app.directive('appFilereader', function($q) {
    var slice = Array.prototype.slice;

    return {
        restrict: 'A',
        require: '?ngModel',
        link: function(scope, element, attrs, ngModel) {
            if (!ngModel) return;

            ngModel.$render = function() {};

            element.bind('change', function(e) {
                var element = e.target;

                $q.all(slice.call(element.files, 0).map(readFile))
                    .then(function(values) {
                        if (element.multiple) ngModel.$setViewValue(values);
                        else ngModel.$setViewValue(values.length ? values[0] : null);
                    });

                function readFile(file) {
                    var deferred = $q.defer();

                    var reader = new FileReader();
                    reader.onload = function(e) {
                        deferred.resolve(e.target.result);
                    };
                    reader.onerror = function(e) {
                        deferred.reject(e);
                    };
                    reader.readAsDataURL(file);

                    return deferred.promise;
                }

            }); //change

        } //link
    }; //return
});
