/**
 * Created by Влад on 07.02.2016.
 */
// user.js
// create angular app
var app = angular.module('app', ['ngMessages', 'angularUtils.directives.dirPagination', 'ngResource']);
app.run(function($rootScope, $http, $location) {
    $rootScope.changeLang = function(id){
        $http.post("/locale?newLocale="+id, {}).success(function(data, status) {
            $rootScope.locale = id;
            $rootScope.lang = data;
        })
    };

    $rootScope.flag = false;

    $rootScope.detailsData = {};

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

    $rootScope.courseDetails = function(object){
        $rootScope.detailsData = object;
        $('#editRow').modal();
    };

    $rootScope.hide = function(){
        $rootScope.flag = false;
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

    angular.element(document).ready(function () {
        $rootScope.defineLang();
    });
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

        $scope.sendEmailReminder = function(){
            $http({
                method  : 'POST',
                url     : '/user/forgetPassword',
                data    : $.param($scope.formData),  // pass in data as strings
                headers : { 'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8' }  // set the headers so angular passing info as form data (not request payload)
            }).success(function (){
                $rootScope.successNoty($rootScope.lang['remindSent']);
                setTimeout($scope.locationChange, 3000);

            }).error(function (data){
                $scope.message = data;
                $rootScope.failNoty($scope.message.substring($scope.message.indexOf('<u>'), $scope.message.indexOf('</u>')));
            });
        };

        $scope.locationChange = function(){
            window.location.href = 'user/home';
        };


    });

app.controller('coursesController',

    function($scope, $http, $rootScope) {
        $rootScope.userId = undefined;


        $scope.subscribeToCourse = function(id){
            $http.post("/ajax/subscription/"+id, {headers : { 'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8' }})
                .success(function(data) {
                    $rootScope.successNoty($scope.lang['operationSuccess']);
                    filter($scope.pagination.current);
                    $rootScope.hide();
                }).error(function(data){
                $scope.message = data;
                $rootScope.failNoty($scope.message.substring($scope.message.indexOf('<u>'), $scope.message.indexOf('</u>')));
            });
        };

        $scope.searchData = {};

        $scope.totalCourses = 0;
        $scope.searchData['limit'] = 6;// this should match however many results your API puts on one page

        filter(1);

        $scope.pagination = {
            current: 1
        };

        $scope.pageChanged = function(newPage) {
            filter(newPage);
        };

        $scope.filter = function(){
            filter(1);
            $scope.pagination.current = 1;
        };

        $scope.reset = function(){
            $scope.searchData['searchBy'] = undefined;
            $scope.searchData['search'] = undefined;
            $scope.searchData['sortBy'] = undefined;
            $scope.searchData['order'] = undefined;
            $scope.pagination.current = 1;
            filter(1);
        };

        function filter(pageNumber){
            $scope.searchData['offset'] = (pageNumber - 1)*$scope.searchData['limit'];
            $http({
                method: 'GET',
                url: '/ajax/course',
                params: $scope.searchData
            }).success(function(data) {
                $scope.courses=data['firstEntity'];
                $scope.totalCourses = data['secondEntity']
            }).error(function(data){
                $scope.message = data;
                $rootScope.failNoty($scope.message.substring($scope.message.indexOf('<u>'), $scope.message.indexOf('</u>')));
            });
        }

    });

app.controller('profileController',

    function($scope, $http, $rootScope) {

        $scope.deleteRow = function(id){
            $http({
                method: 'DELETE',
                url: '/ajax/subscription/'+id
            }).success(function(data) {
                $rootScope.successNoty(data);
            }).error(function(data){
                $rootScope.failNoty($scope.message.substring($scope.message.indexOf('<u>'), $scope.message.indexOf('</u>')));
            });
        };

        $rootScope.details = function(id){
            $http({
                method: 'GET',
                url: '/ajax/course/'+id
            }).success(function(data) {
                $rootScope.courseDetails(data);
            }).error(function(data){
                $rootScope.failNoty($scope.message.substring($scope.message.indexOf('<u>'), $scope.message.indexOf('</u>')));
            });
        };

    });

app.controller('tutorDetailsController',

    function($scope, $http, $rootScope, $location) {
        $scope.courses = {};
        getCourses();

        function getCourses(){
            var path = $location.absUrl();
            var pathArr = path.split("/");
            var id = pathArr[pathArr.length - 1];
            if(id!=undefined){
                $http({
                    method: 'GET',
                    url: '/ajax/course?tId='+id
                }).success(function(data) {
                    $scope.courses = data;
                    console.log($scope.courses);
                });}
        }
    });

app.controller('contactsController',

    function($scope, $http, $rootScope, $location, UserFactory, CourseFactory, MessageFactory, $interval) {
        $scope.tutors = {};
        $scope.count = {};

        UserFactory.tutors(function success(data){
            $scope.tutors = data;
            $scope.init();
        });

        $scope.init = function(){
            $scope.tutors.forEach(function(item){
                console.log(item);
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
            $scope.messaging();
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
        }


    });

app.factory('UserFactory', function ($resource) {
    return $resource('/ajax/user/:id', {id:'@id'}, {
        admins: { method: 'GET', params:{role:'ADMIN'}, isArray: true},
        students: { method: 'GET', params:{role:'STUDENT'}, isArray: true},
        tutors: { method: 'GET', params:{role:'TUTOR'}, isArray: true},
        post: { method: 'POST', headers : { 'Content-Type': 'application/json; charset=UTF-8' }  // set the headers so angular passing info as form data (not request payload)
        },
        delete: { method: 'DELETE'},
        update: { method: 'PUT', headers : { 'Content-Type': 'application/json; charset=UTF-8' }}
    })
});

app.factory('MessageFactory', function ($resource) {
    return $resource('/ajax/messages/:id', {id:'@id'}, {
        count: { method: 'GET', params:{count:'true', referrerId: '@rId'}},
        getMessages: { method: 'GET', params:{referrerId : '@rId'}, isArray: true},
        post: { method: 'POST'},
        update:{method: 'PUT'}
    })
});

app.factory('CourseFactory', function ($resource) {
    return $resource('/ajax/course/:id', {id:'@id'}, {
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
