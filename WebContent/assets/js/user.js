/**
 * Created by Влад on 07.02.2016.
 */
// user.js
// create angular app
var app = angular.module('app', ['ngMessages']);
app.controller('loginRegisterController',['$rootScope',

    function($scope, $rootScope, $http) {
        $scope.notyFlag = "new";
        $scope.nameTemplate = "^[A-ZА-Яа-яa-z]+$";
        $scope.loginTemplate = "^[A-ZА-Яа-яa-z0-9_-]+$";
        $scope.emailTemplate = "^[_A-Za-z0-9-\+]+(\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\.[A-Za-z0-9]+)*(\.[A-Za-z]{2,})$";
        $scope.passwordTemplate = "^[A-ZА-Яа-яa-z0-9_-]+[-\s][A-ZА-Яа-яa-z0-9_-]+$";

        $scope.login = '/user/login';
        $scope.register = '/user/register';

        $scope.failedNote = undefined;


        $scope.formData = {};
        $scope.changeLang = function(id){
            console.log(id);

            $http.post("/locale?newLocale="+id, {}).success(function(data, status) {
                $scope.locale = id;
                $scope.lang = data;
            })
        };

        $scope.defineLang = function(){
            $http.get("/locale", {}).success(function(data) {
                $scope.changeLang(data);
            })
        };

        $scope.checkLang = function(lang){
            if (lang==$scope.lang.language){
                return true;
            }
        };


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

        $scope.successNoty = function(reason) {
            $scope.closeNoty();
            $scope.failedNote = noty({
                text: reason,
                type: 'success',
                layout: 'bottomRight',
                timeout: 2500
            });
        };



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
                        $scope.notyFlag = $scope.lang['logged'];
                        break;
                    }
                    case $scope.register:{
                        $scope.notyFlag = $scope.lang['registered'];
                        console.log($scope.notyFlag);
                        break;
                    }

                    default:
                }
                if(!($scope.notyFlag.localeCompare('new')==0)){
                    $scope.successNoty($scope.notyFlag);
                    $scope.notyFlag = 'new';
                }

                setTimeout($scope.locationChange, 3000);
            }).error(function (data){
                    $scope.message = data;
                    console.log(data);
                    $scope.failNoty($scope.message.substring($scope.message.indexOf('<u>'), $scope.message.indexOf('</u>')));
            });
        };

        $scope.locationChange = function(){
            window.location.href = 'user/home';
        };

        angular.element(document).ready(function () {
            $scope.defineLang();
        });
}]);

app.controller('coursesController',

    function($scope, $http) {

        angular.element(document).ready(function () {
            $http.get("/ajax/course", {})
                .success(function(data) {
                $scope.courses = data;
            }).error(function (data){
                $scope.message = data;
                console.log(data);
                $scope.failNoty($scope.message.substring($scope.message.indexOf('<u>'), $scope.message.indexOf('</u>')));
            });
        });
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
