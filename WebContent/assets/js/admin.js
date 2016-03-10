/**
 * Created by Влад on 22.02.2016.
 */

var app = angular.module('admin.controllers', [ 'ngRoute', 'ngResource', 'ngMessages' ]);


app.controller('homeCtrl',
    function ($scope, $location, UserFactory, $rootScope, UsersFactory) {

    }
);
app.controller('loginCtrl',
    function ($scope, $location, UserFactory, $rootScope, UsersFactory, $http) {
        $scope.notyFlag = "new";
        $scope.loginTemplate = "^[A-ZА-Яа-яa-z0-9_-]+$";
        $scope.passwordTemplate = "^[A-ZА-Яа-яa-z0-9_-]+[-\s][A-ZА-Яа-яa-z0-9_-]+$";

        $scope.formData = {};


        // function to submit the form after all validation has occurred
        $scope.submitForm = function() {
            $http({
                method  : 'POST',
                url     : "/admin/login",
                data    : $.param($scope.formData),  // pass in data as strings
                headers : { 'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8' }  // set the headers so angular passing info as form data (not request payload)
            }).success(function (){
                $rootScope.successNoty($rootScope.lang['success']);
                setTimeout($scope.locationChange, 1000);

            }).error(function (data){
                $scope.message = data;

                $rootScope.failNoty(data);
            });
        };

        $scope.locationChange = function(){
            window.location.href = 'admin/main/';
        };
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
                $scope.message = data;
                $rootScope.failNoty($scope.message);

            });
        };


        $scope.confirm = function(user){
            var user = user;
            $rootScope.detailsData = {};
            delete user['$$hashKey'];
            user.enabled = 'ACTIVE';

            UserFactory.update(user, function success(data) {
                $rootScope.successNoty($rootScope.lang['success']);
                $scope.updateWaiting();
            }, function error(data) {
                $scope.message = data;
                $rootScope.failNoty($scope.message);

            });
            $rootScope.hide();
        };


    });

app.controller('activeCtrl',
    function ($scope, $location, UserFactory, $rootScope, UsersFactory, $http) {
        $rootScope.usersFromDb = UsersFactory.active();
        $scope.formData = {};
        $scope.updateActive = function(){
            $rootScope.usersFromDb = UsersFactory.active();
        };

        $scope.ban = function(user){
            user.enabled = 'BANNED';
            UserFactory.update(user, function success(data) {
                $rootScope.successNoty($rootScope.lang['success']);
                $scope.updateActive();
            }, function error(data) {
                $scope.message = data;
                $rootScope.failNoty($scope.message);

            });
        };

        $scope.showCourses = function(user){
            $http({
                method  : 'get',
                url     : '/ajax/course?forStudent=true&studentId='+user.id,
                headers : { 'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8' }  // set the headers so angular passing info as form data (not request payload)
            }).success(function (data){
               $scope.formData = data;

            }).error(function (data){
                $scope.message = data;
                $rootScope.failNoty($scope.message.substring($scope.message.indexOf('<u>'), $scope.message.indexOf('</u>')));
            });
            $rootScope.showWithName(user, "modal2");
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
                $scope.message = data;
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
                $rootScope.hide();
            }, function error(data) {
                $scope.message = data;
                $rootScope.failNoty($scope.message);

            });
        };

    });
app.controller('tutorsCtrl',
    function ($scope, $location, UserFactory, CourseFactory, $rootScope) {
        $scope.emailTemplate = "^[_A-Za-z0-9-\+]+(\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\.[A-Za-z0-9]+)*(\.[A-Za-z]{2,})$";
        $scope.nameTemplate = "^[A-ZА-Яа-яa-z]+$";
        $scope.loginTemplate = "^[A-ZА-Яа-яa-z0-9_-]+$";
        $scope.passwordTemplate = "^[A-ZА-Яа-яa-z0-9_-]+[-\s][A-ZА-Яа-яa-z0-9_-]+$";

        $scope.newImage = "";

        

        function updateTutors(){
           $scope.updateTutors();
        }

        updateTutors();

        $scope.submit = function(){
            var user = $rootScope.detailsData;
            $rootScope.detailsData = {};
            if($scope.newImage.length>0){
                user.image = $scope.newImage;
            }
            $scope.newImage = "";

            delete user['$$hashKey'];
            UserFactory.update(user, function success(data) {
                $rootScope.successNoty($rootScope.lang['success']);
                updateTutors();
            }, function error(data) {
                $scope.message = data;
                $rootScope.failNoty($scope.message);

            });
            $rootScope.hide();
        };

        $scope.postTutor = function(){
            var user = $rootScope.detailsData;
            $rootScope.detailsData = {};
                user.image = $scope.newImage;
            $scope.addTutorForm.$setUntouched();

            $scope.message = "";
            delete user['$$hashKey'];
            UserFactory.post(user, function success(data) {
                $rootScope.successNoty($rootScope.lang['success']);
                updateTutors();
            }, function error(data) {
                $scope.message = data;
                $rootScope.failNoty($scope.message);

            });
            user = "";
            $scope.newImage = "";
            $rootScope.hideWithName('addTutor');
        };

        $scope.getCount = function(id){
            return $rootScope.usersWithCourses[id].length
        };

        $scope.delete = function (user){
            user.image = $scope.newImage;
            delete user['$$hashKey'];
            UserFactory.delete(user, function success(data) {
                $rootScope.successNoty($rootScope.lang['success']);
                $scope.updateTutors();
            }, function error(data) {
                $rootScope.failNoty($rootScope.lang['deletingError']);
            });
            $rootScope.hide();
        };



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
            $rootScope.courses = CourseFactory.query();

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

        $rootScope.updateCourses = function(){
            $rootScope.courses = CourseFactory.query();
        };

        $scope.deleteCourse = function (course){
            course.image = $scope.newImage;
            delete course['$$hashKey'];
            CourseFactory.delete(course, function success(data) {
                $rootScope.successNoty($rootScope.lang['success']);
                $rootScope.courses = CourseFactory.query();
                console.log($rootScope.courses);
            }, function error(data) {
                $rootScope.failNoty($rootScope.lang['deletingError']);
            });
            $rootScope.hide();
        };


        $scope.showCourses = function(user){
            $rootScope.currUser = user.id;
            $rootScope.showWithName(user, "modal2");
        };


        $scope.postCourse = function(){
            if($scope.newImage.localeCompare("")!=0){
                $rootScope.detailsData.image = $scope.newImage;
            }
            CourseFactory.post($rootScope.detailsData, function success(){
                $rootScope.successNoty($rootScope.lang['success']);
                $rootScope.updateTutors();
                    $rootScope.courses = CourseFactory.query();


                $rootScope.detailsData = {};
                $rootScope.hideWithName('addCourseModal');

            }, function error(data) {
                $rootScope.failNoty(data);
            });
            $scope.courseForm.$setUntouched();
            $scope.newImage = "";
        };

            $http.get("/ajax/themes")
                .then(function(response){
                $scope.themes = response.data;
            });

            $scope.submit = function(){
                if($scope.newImage.localeCompare("")!=0){
                    $rootScope.detailsData.image = $scope.newImage;
                }
                CourseFactory.update($rootScope.detailsData, function success(){
                    $rootScope.successNoty($rootScope.lang['success']);
                    $rootScope.updateTutors();
                    $rootScope.detailsData = {};
                    $rootScope.hideWithName('courseModal');
                }, function error(data) {
                    $rootScope.failNoty(data);
                });
                $scope.newImage = "";
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
        post: { method: 'POST', headers : { 'Content-Type': 'application/json; charset=UTF-8' }  // set the headers so angular passing info as form data (not request payload)
        },
        delete: { method: 'DELETE'},
        update: { method: 'PUT'}
    })
});

services.factory('CourseFactory', function ($resource) {
    return $resource('/ajax/course/:id', {id:'@id', r:"ADMIN"}, {
        query: { method: 'GET', isArray: true},
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
            templateUrl : 'static/blackList.html',
            controller : 'blackListCtrl'
        }).when('/courses', {
            templateUrl : 'static/courses.html',
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

main.run(function($rootScope, $http, $location, UserFactory, CourseFactory) {
    $rootScope.currUser = 0;
    $rootScope.message = "";
    $rootScope.tutors = UserFactory.tutors();
    $rootScope.roles = ["STUDENT", "TUTOR"];
    $rootScope.lang = {};
    $rootScope.courses = {};

   /* $rootScope.updateTutors = function(){
     $rootScope.tutors = UserFactory.tutors();
    };*/

    $rootScope.changeLang = function (id) {
        $http.post("/locale?newLocale=" + id, {}).success(function (data, status) {
            $rootScope.locale = id;
            $rootScope.lang = data;
            console.log($rootScope.lang);
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

    $rootScope.updateTutors = function(){
        $rootScope.tutors = UserFactory.tutors();
        $rootScope.usersFromDb = UserFactory.tutors(function success(){
            $rootScope.usersFromDb.forEach(function(item, i, arr){
                $rootScope.usersWithCourses[item.id] = CourseFactory.byTutor({tId : item.id});
            });
        });
    };

    $rootScope.usersFromDb = {};

    $rootScope.usersWithCourses ={};

    $rootScope.showDetails = function(object){
        $rootScope.detailsData = $rootScope.clone(object);
        $('#modal').modal();
    };

    $rootScope.endMinDate = "";
    $rootScope.showWithName = function(object, name){
        $rootScope.detailsData = $rootScope.clone(object);
        if($rootScope.detailsData.startDate!=undefined){
        $rootScope.detailsData.startDate = new Date($rootScope.detailsData.startDate);
        $rootScope.detailsData.endDate = new Date($rootScope.detailsData.endDate);}

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
        console.log(reason);
        var message;
        if(reason!=undefined){
            if(reason.data!=undefined){
                console.log(reason);
                reason = reason.data;
                console.log(reason);
            }
            console.log(reason);
            message = reason.substring(reason.indexOf('<u>'), reason.indexOf('</u>'));
            if(message==undefined||message.length==0){
                message = reason;
            }
        }else{
            message = $rootScope.lang['fail'];
        }
        $rootScope.closeNoty();
        $rootScope.failedNote = noty({
            text: message,
            type: 'error',
            layout: 'bottomRight',
            timeout: 1000
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
            timeout: 1000
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