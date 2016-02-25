/**
 * Created by Влад on 22.02.2016.
 */

var app = angular.module('admin.controllers', [ 'ngRoute', 'ngResource' ]);


app.controller('homeCtrl',
    function ($scope, $location, UserFactory, $rootScope, UsersFactory) {

    }
);
   app.controller('notConfirmedCtrl',
    function ($scope, $location, UserFactory, $rootScope, UsersFactory) {
        $scope.message = "";

        $rootScope.usersFromDb = UsersFactory.waiting();

        $scope.updateWaiting = function(){
            $rootScope.usersFromDb = UsersFactory.waiting();
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
    function ($scope, $location, UserFactory, CourseFactory, $rootScope) {

        $scope.newImage = "";
        $scope.currUser = 0;
        $scope.updateTutors = function(){
            $scope.usersFromDb = UserFactory.tutors(function success(){
                $scope.usersFromDb.forEach(function(item, i, arr){
                    $scope.usersWithCourses[item.id+""] = CourseFactory.byTutor({tId : item.id});
                });
            });
        };
        $scope.usersFromDb = {};

        $scope.usersWithCourses ={};

        function updateTutors(){
           $scope.updateTutors();
        }

        updateTutors();

        $scope.submit = function(){
            var user = $rootScope.detailsData;
            console.log(user);
            $rootScope.detailsData = {};
            if($scope.newImage.length>0){
                user.image = $scope.newImage;
            }
            $scope.newImage = "";

            delete user['$$hashKey'];
            UserFactory.update(user, function success(data) {
                $rootScope.successNoty($rootScope.lang['success']);
                $scope.updateTutors();
            }, function error(data) {
                $scope.message = data.data;
                $rootScope.failNoty($scope.message);

            });
            $rootScope.hide();
        };

        $scope.getCount = function(id){
            return $scope.usersWithCourses[id].length
        };

        $scope.delete = function (user){
            user.image = $scope.newImage;
            delete user['$$hashKey'];
            console.log(user);
            UserFactory.delete(user, function success(data) {
                $rootScope.successNoty($rootScope.lang['success']);
                $scope.updateTutors();
            }, function error(data) {
                $rootScope.failNoty($rootScope.lang['deletingError']);
            });
            $rootScope.hide();
        };

        $scope.showCourses = function(user){
            $scope.currUser = user.id;
            $rootScope.showWithName(user, "modal2");
        }



    });
app.controller('administratorsCtrl', ['$scope', '$location',
    function ($scope, $location) {


    }]);
app.controller('coursesCtrl',
    function ($scope, $location, UserFactory, $http, $rootScope, CourseFactory) {
            $scope.newImage = "";
            $scope.themes = "";
            $scope.minDate = new Date();
            $scope.minDate.setDate($scope.minDate.getDate());

            $scope.open1 = function() {
                $scope.popup1.opened = true;
            };

            $scope.popup1 = {
                opened: false
            };

            $scope.open2 = function() {
                $scope.popup2.opened = true;
            };

            $scope.popup2 = {
                opened: false
            };

            $scope.getDate = function(){
                var max = new Date($rootScope.detailsData.startDate);
                max.setDate(max.getDate()+1);

                return new Date($rootScope.detailsData.startDate.getDate() + 1);
            };



        $http.get("/ajax/themes")
                .then(function(response){
                $scope.themes = response.data;
            });

            $scope.submit = function(){
                if($scope.newImage.localeCompare("")!=0){
                    $rootScope.detailsData.image = $scope.newImage;
                }
                console.log($rootScope.detailsData);
                CourseFactory.update($rootScope.detailsData, function success(){
                    $rootScope.successNoty($rootScope.lang['success']);
                    $scope.updateTutors();
                }, function error(data) {
                    $rootScope.failNoty(data);
                });
                $rootScope.hideWithName('courseModal');
                $scope.newImage = "";
                $rootScope.detailsData = {};
            };


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
        admins: { method: 'GET', params:{role:'ADMIN'}, isArray: true},
        students: { method: 'GET', params:{role:'STUDENT'}, isArray: true},
        tutors: { method: 'GET', params:{role:'TUTOR'}, isArray: true},
        delete: { method: 'DELETE'},
        update: { method: 'PUT'}
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
        byTutor: {method: 'GET', params: {tId : '@tId'}, isArray:true}
    })
});



var main = angular.module('adminApp', ['admin.controllers', 'ngMessages', 'admin.services', 'angular.chosen', 'ui.bootstrap', 'ngAnimate'])
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
            templateUrl : 'static/tutors.html',
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
    $rootScope.selectedTutor = {};

    $rootScope.showDetails = function(object){
        $rootScope.detailsData = $rootScope.clone(object);
        $('#modal').modal();
    };

    $rootScope.endMinDate = "";
    $rootScope.showWithName = function(object, name){
        $rootScope.detailsData = $rootScope.clone(object);

        $rootScope.detailsData.startDate = new Date($rootScope.detailsData.startDate);
        $rootScope.detailsData.endDate = new Date($rootScope.detailsData.endDate);

        $('#'+name).modal();
    };

    $rootScope.hide = function(){
        $rootScope.detailsData = {};
        $('#modal').modal('hide');
    };

    $rootScope.hideWithName = function(name){
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
        if(message.length==0){
            message = reason;
        }
        $rootScope.closeNoty();
        $rootScope.failedNote = noty({
            text: message,
            type: 'error',
            layout: 'bottomRight',
            timeout: 2500
        });
        $rootScope.message = "";
    };

    $rootScope.clone = function(object){
        return jQuery.extend(true, {}, object);
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

function clone(obj) {
    if (null == obj || "object" != typeof obj) return obj;
    var copy = obj.constructor();
    for (var attr in obj) {
        if (obj.hasOwnProperty(attr)) copy[attr] = obj[attr];
    }
    return copy;
}