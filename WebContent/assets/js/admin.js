/**
 * Created by Влад on 22.02.2016.
 */

var app = angular.module('admin.controllers', [ 'ngRoute', 'ngResource' ]);


app.controller('notConfirmedCtrl',
    function ($scope, $location, UserFactory, $rootScope, UsersFactory) {
        $scope.message = "";

        $rootScope.usersFromDb = UsersFactory.waiting();

        $scope.updateWaiting = function(){
            $rootScope.usersFromDb = UsersFactory.waiting();
            console.log($rootScope.usersFromDb);
        };

        $scope.ban = function(user){
            user.enabled = 'BANNED';
            UserFactory.update(user, function success(data) {
                $rootScope.successNoty($rootScope.lang['success']);
                $scope.updateWaiting();
            }, function error(data) {
                $scope.message = data.data;
                $rootScope.failNoty($scope.message);

            });
        };


        $scope.confirm = function(user){
            var user = $rootScope.detailsData;
            $rootScope.detailsData = {};
            delete user['$$hashKey'];
            user.enabled = 'ACTIVE';

            UserFactory.update(user, function success(data) {
                $rootScope.successNoty($rootScope.lang['success']);
                $scope.updateWaiting();
            }, function error(data) {
                $scope.message = data.data;
                $rootScope.failNoty($scope.message);

            });
            $rootScope.hide();
        };


    });

app.controller('activeCtrl',
    function ($scope, $location, UserFactory, $rootScope, UsersFactory) {
        $rootScope.usersFromDb = UsersFactory.active();

        $scope.updateActive = function(){
            $rootScope.usersFromDb = UsersFactory.active();
            console.log($scope.usersFromDb);
        };

        $scope.ban = function(user){
            user.enabled = 'BANNED';
            UserFactory.update(user, function success(data) {
                $rootScope.successNoty($rootScope.lang['success']);
                $scope.updateActive();
            }, function error(data) {
                $scope.message = data.data;
                $rootScope.failNoty($scope.message);

            });
        };

        $scope.message = "";

        $scope.submit = function(){
            var user = $rootScope.detailsData;
            $rootScope.detailsData = {};
            delete user['$$hashKey'];
            UserFactory.update(user, function success(data) {
                $rootScope.successNoty($rootScope.lang['success']);
                $scope.updateActive();
            }, function error(data) {
                $scope.message = data.data;
                $rootScope.failNoty($scope.message);

            });
            $rootScope.hide();
        }

    });
app.controller('blackListCtrl',
    function ($scope, $location, UserFactory, $rootScope, UsersFactory) {

        $rootScope.usersFromDb = UsersFactory.banned();

        $scope.updateBanned = function(){
            $rootScope.usersFromDb = UsersFactory.banned();
            console.log($scope.usersFromDb);
        };

        $scope.unban = function(user){
            user.enabled = 'ACTIVE';
            UserFactory.update(user, function success(data) {
                $rootScope.successNoty($rootScope.lang['success']);
                $scope.updateBanned();
            }, function error(data) {
                $scope.message = data.data;
                $rootScope.failNoty($scope.message);

            });
        };

    });
app.controller('tutorsCtrl',
    function ($scope, $location, UserFactory, CourseFactory) {
        $scope.newImage = "";
        $scope.usersFromDb = UserFactory.tutors();
        $scope.usersWithCourses ={};
        $scope.usersFromDb.forEach(function(item, i, arr){
            $scope.usersWithCourses[item.id+""] = CourseFactory.byTutor({tId : $scope.usersFromDb.id});
        });

        $scope.submit = new function (){

        };


        $scope.delete = function (user){

        };

        $scope.showCourses = function(courses){
            /*'modal2'*/
        }



    });
app.controller('administratorsCtrl', ['$scope', '$location',
    function ($scope, $location) {


    }]);
app.controller('coursesCtrl',
    function ($scope, $location, UserFactory, $http, $rootScope, CourseFactory) {
            $scope.newImage = "";
            $scope.themes = $http.get("/ajax/themes", {});

            $scope.submit = function(){
                if($scope.newImage.localeCompare("")!=0){
                    $rootScope.detailsData.image = $scope.newImage;
                }
                CourseFactory.put($rootScope.detailsData, function success(){
                    $rootScope.updateTutors();
                });
                $scope.newImage = "";
                $rootScope.detailsData = {};
            }





    });

var services = angular.module('admin.services', ['ngResource']);

services.factory('UsersFactory', function ($resource) {
    return $resource('/ajax/user', {}, {
        active: { method: 'GET', params:{getBy: "ACTIVE"}, isArray: true},
        banned: {method:'GET', params: {getBy: "BANNED"}, isArray: true},
        waiting: {method:'GET', params: {getBy: "WAITING"}, isArray: true}
    })
});

services.factory('UserFactory', function ($resource) {
    return $resource('/ajax/user/:id', {id:'@id'}, {
        admins: { method: 'GET', params:{role:'ADMIN'}},
        students: { method: 'GET', params:{role:'STUDENT'}},
        tutors: { method: 'GET', params:{role:'TUTOR'}},
        delete: { method: 'DELETE'},
        update: { method: 'PUT'},
    })
});

services.factory('CourseFactory', function ($resource) {
    return $resource('/ajax/course/:id', {id:'@id'}, {
        query: { method: 'GET', isArray : true},
        get: { method: 'GET'},
        post: { method: 'POST', headers : { 'Content-Type': 'application/json; charset=UTF-8' }  // set the headers so angular passing info as form data (not request payload)
        },
        delete: { method: 'DELETE'},
        update: { method: 'PUT', headers : { 'Content-Type': 'application/json; charset=UTF-8' }  // set the headers so angular passing info as form data (not request payload)
        },
        byTutor: {method: 'GET', params: {tId : '@tId'}}
    })
});



var main = angular.module('adminApp', ['admin.controllers', 'admin.services', 'angular.chosen', 'ui.bootstrap'])
    .config(function($routeProvider) {

        $routeProvider.when('/home', {
            templateUrl : 'static/home.html',
            controller : 'homeCtrl'
        }).when('/users/notConfirmed', {
            templateUrl : 'static/notConfirmed.html',
            controller : 'notConfirmedCtrl'
        }).when('/users/active', {
            templateUrl : 'static/active.html',
            controller : 'activeCtrl'
        }).when('/users/tutors', {
            templateUrl : 'static/tutors.jsp',
            controller : 'tutorsCtrl'
        }).when('/users/administrators', {
            templateUrl : 'static/administrators.htm',
            controller : 'administratorsCtrl'
        }).when('/users/blackList', {
            templateUrl : 'static/blackList.htm',
            controller : 'blackListCtrl'
        }).when('/courses', {
            templateUrl : 'static/courses.htm',
            controller : 'coursesCtrl'
        }).otherwise('/home');


    });

main.directive('appFilereader', function($q) {
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

main.run(function($rootScope, $http, $location, UserFactory) {
    $rootScope.message = "";
    $rootScope.tutors = UserFactory.tutors();
    $rootScope.roles = ["STUDENT", "TUTOR"];

    $rootScope.updateTutors = function(){
     $rootScope.tutors = UserFactory.tutors();
    };

    $rootScope.changeLang = function (id) {
        $http.post("/locale?newLocale=" + id, {}).success(function (data, status) {
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

    $rootScope.detailsData = {};

    $rootScope.showDetails = function(object){
        $rootScope.detailsData = object;
        $('#modal').modal();
    };

    $rootScope.showDetails = function(object, name){
        $rootScope.detailsData = object;
        $('#'+name).modal();
    };

    $rootScope.hide = function(){
        $rootScope.detailsData = {};
        $('#modal').modal('hide');
    };

    $rootScope.hide = function(name){
        $rootScope.detailsData = {};
        $('#'+name).modal('hide');
    };


    $rootScope.closeNoty = function(){
        if ( $rootScope.failedNote) {
            $rootScope.failedNote.close();
            $rootScope.failedNote = undefined;
        }
    };

    $rootScope.failNoty = function(reason) {
        var message = reason.substring(reason.indexOf('<u>'), reason.indexOf('</u>'));
        $rootScope.closeNoty();
        $rootScope.failedNote = noty({
            text: message,
            type: 'error',
            layout: 'bottomRight',
            timeout: 2500
        });
        $rootScope.message = "";
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

    angular.element(document).ready(function () {
        $rootScope.defineLang();
    });

});
