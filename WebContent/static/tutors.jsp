<!doctype html>
<html>
<head>

</head>

<body ng-cloak class="ng-cloak" ng-controller = "tutorsCtrl">
<div class="table-responsive">
    <table class="table table-bordered table-hover">
        <thead>
        <tr>
            <th>#id</th>
            <th>{lang.userName}}</th>
            <th>{lang.userEmail}}</th>
            <th>{lang.coursesCount}}</th>
            <th></th>
            <th></th>
        </tr>
        </thead>
        <tbody>
            <tr ng-repeat="user in usersFromDb" ng-click = showCourses(user)>
                <td>{{user.id}}</td>
                <td>{{user.name}}</td>
                <td>
                    {{user.email}}
                </td>
                <td>{{usersWithCourses['{{user.id}}'].size}}</td>
                <td class="col-lg-1">
                    <a class="btn btn-warning btn-sm" ng-click = "showDetails(user)">{lang.ban}}</a>
                </td>
                <td class="col-lg-1">
                    <a class="btn btn-danger btn-sm" ng-click = "delete(user)">{lang.details}}</a>
                </td>
            </tr>
        </tbody>
    </table>
</div>
<div class="modal fade" id="modal2" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h2 class="modal-title" id="myModalLabel2">{lang.userDetails}}</h2>
            </div>
            <div class="modal-body">
                <div class="row">
                    <div class="col-lg-12">
                        <div class="table-responsive">
                            <h3>{lang.tutorsCourses}}</h3>
                            <table class="table table-bordered table-hover">
                                <thead>
                                <tr>
                                    <th>#id</th>
                                    <th>{lang.title}}</th>
                                    <th>{lang.startDate}}</th>
                                    <th>{lang.endDate}}</th>
                                    <th>{lang.themes}}</th>
                                    <th>{lang.subscribers}}</th>
                                    <th></th>
                                    <th></th>
                                </tr>
                                </thead>
                                <tbody>
                                <tr ng-repeat="course in usersWithCourses[currUser.id]">
                                    <td>{{course.title}}</td>
                                    <td>{{course.startDate}}</td>
                                    <td>
                                        {{course.endDate}}
                                    </td>
                                    <td>
                                        <h6 ng-repeat="theme in course.themes"><span class = "badge">{{theme}}</span></h6>
                                    </td>
                                    <td>
                                        {{course.subscribersCount}}
                                    </td>
                                    <td class="col-lg-1">
                                        <a class="btn btn-warning btn-sm" ng-click = "showDetails(course, 'courseModal')">{lang.details}}</a>
                                    </td>
                                    <td class="col-lg-1">
                                        <a class="btn btn-danger btn-sm" ng-click = "hide('modal2')">{lang.ban}}</a>
                                    </td>
                                </tr>
                                </tbody>
                            </table>
                        </div>
                </div>
            </div>
        </div>
        </div>
</div>

    <div class="modal fade" id="modal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h2 class="modal-title" id="myModalLabel">{lang.userDetails}}</h2>
            </div>
            <div class="modal-body">
                <form id="loginform" name="tutorForm" action="" class="form-horizontal" role="form" enctype="multipart/form-data" novalidate>
                <div class="row">
                    <div class="col-lg-12">
                    <div class="form-group" ng-class="{ 'has-error': tutorForm.name.$touched && tutorForm.name.$invalid }">
                        <label class="control-label" for="name">{lang.name}}</label>
                        <input type="text" id = "name" ng-model = "detailsData.name" class="form-control" ng-model="formData.name"
                               ng-minlength="3"
                               ng-maxlength="100"
                               required>
                        <div class="help-block" ng-messages="tutorForm.name.$error" ng-show="tutorForm.name.$touched">
                            <p ng-message="minlength">{lang.tooShort3}}</p>
                            <p ng-message="maxlength">{lang.tooLong100}}</p>
                            <p ng-message="required">{lang.required}}</p>
                            <p ng-message="pattern">{lang.invalidFormat}}</p>
                        </div>
                    </div>
                    <div class="form-group" ng-class="{ 'has-error': tutorForm.email.$touched && tutorForm.email.$invalid }">

                        <label class="control-label" for="email">{lang.email}}</label>
                        <input type="text" id = "email" ng-model = "detailsData.email" class="form-control" ng-model="formData.email"
                               ng-minlength="5"
                               ng-maxlength="100"
                               required>
                        <div class="help-block" ng-messages="tutorForm.email.$error" ng-show="tutorForm.email.$touched">
                            <p ng-message="minlength">{lang.tooShort5}}</p>
                            <p ng-message="maxlength">{lang.tooLong100}}</p>
                            <p ng-message="required">{lang.required}}</p>
                            <p ng-message="email">{lang.invalidEmail}}</p>
                            <p ng-message="pattern">{lang.invalidFormat}}</p>
                        </div>
                    </div>
                    <div class="form-group" ng-class="{ 'has-error': tutorForm.login.$touched && tutorForm.login.$invalid }">
                        <label class="control-label" for="login">{lang.login}}</label>
                        <input type="text" id = "login" ng-model = "detailsData.login" class="form-control" disabledplaceholder="{lang.login}}"
                               ng-model="formData.login"
                               ng-minlength="5"
                               ng-maxlength="100"
                               required>
                        <div class="help-block" ng-messages="tutorForm.login.$error" ng-show="tutorForm.login.$touched">
                            <p ng-message="minlength">{lang.tooShort5}}</p>
                            <p ng-message="maxlength">{lang.tooLong100}}</p>
                            <p ng-message="required">{lang.required}}</p>
                            <p ng-message="pattern">{lang.invalidFormat}}</p>
                        </div>
                     </div>
                        
                        <div class="radio form-control" >
                            <label>
                                <input type="radio" class="radio-button" ng-model="detailsData.enabled" name="status" ng-checked = "true" checked value="ACTIVE">
                                {lang.active}}
                            </label>
                            <label>
                                <input type="radio" class="radio-button" ng-model="detailsData.enabled" name="status" value="BANNED">
                                {lang.ban}}
                            </label>
                        </div>
                        <div class="help-block" ng-messages="tutorForm.email.$error" ng-show="tutorForm.email.$touched">
                            <label class="control-label" for="roles">{lang.roles}}</label>
                            <select id = "roles" required ng-model = "detailsData.roles"
                                    ng-multiple="true" multiple data-placeholder="{lang.roles}}"
                                    class="form-control"
                                    ng-options="role for role in roles">
                            </select>
                        </div>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-primary" ng-show="!tutorForm.$invalid" ng-click="submit()">{lang.Update}}</button>
                    <button type="button" class="btn btn-default" ng-click="hide()">{lang.Cancel}}</button>
                </div>

                    <div class="form-control row" ng-show = "newImage.localeCompare('')==0" style="margin: 0 auto;">
                        <img src="/uploads/courses/{{detailsData.image}}" width="300" height="300">
                    </div>
                    <input type="file" class="form-control" name="image" placeholder="{image}}" ng-model="newImage" accept="image/*" app-filereader/>

                </form>
            </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>