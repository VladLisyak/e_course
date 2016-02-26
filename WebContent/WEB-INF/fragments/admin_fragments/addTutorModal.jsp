<div class="modal fade" id="addTutor" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" ng-controller="tutorsCtrl">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h2 class="modal-title" id="myModalLabelAdd">{lang.userDetails}}</h2>
            </div>
            <div class="modal-body">
                <form id="addform" name="addTutorForm" action="" class="form-horizontal" role="form" enctype="multipart/form-data" novalidate>
                    <div class="row">
                        <div class="col-lg-10 col-lg-offset-1">
                            <div class="form-group" ng-class="{ 'has-error': addTutorForm.name.$touched && addTutorForm.name.$invalid }">
                                <label class="control-label" for="name">{lang.name}}</label>
                                <input type="text" id = "name" name = "name" ng-model = "detailsData.name" ng-pattern = "nameTemplate" placeholder="{lang.name}}" class="form-control" ng-model="formData.name"
                                       ng-minlength="3"
                                       ng-maxlength="100"
                                       required>
                                <div class="help-block" ng-messages="addTutorForm.name.$error" ng-show="addTutorForm.name.$touched">
                                    <p ng-message="minlength">{lang.tooShort3}}</p>
                                    <p ng-message="maxlength">{lang.tooLong100}}</p>
                                    <p ng-message="required">{lang.required}}</p>
                                    <p ng-message="pattern">{lang.invalidFormat}}</p>
                                </div>
                            </div>
                            <div class="form-group" ng-class="{ 'has-error': addTutorForm.email.$touched && addTutorForm.email.$invalid }">

                                <label class="control-label" for="email">{lang.email}}</label>
                                <input type="email" id = "email" name = "email" ng-pattern="emailTemplate" ng-model = "detailsData.email" class="form-control" placeholder="{lang.email}}" ng-model="formData.email"
                                       ng-minlength="5"
                                       ng-maxlength="100"
                                       required>
                                <div class="help-block" ng-messages="addTutorForm.email.$error" ng-show="addTutorForm.email.$touched">
                                    <p ng-message="minlength">{lang.tooShort5}}</p>
                                    <p ng-message="maxlength">{lang.tooLong100}}</p>
                                    <p ng-message="required">{lang.required}}</p>
                                    <p ng-message="email">{lang.invalidEmail}}</p>
                                    <p ng-message="pattern">{lang.invalidFormat}}</p>
                                </div>
                            </div>
                            <div class="form-group" ng-class="{ 'has-error': addTutorForm.login.$touched && addTutorForm.login.$invalid }">
                                <label class="control-label" for="login">{lang.login}}</label>
                                <input type="text" id = "login" name = "login" ng-pattern="loginTemplate" ng-model = "detailsData.login" class="form-control" placeholder="{lang.login}}"
                                       ng-model="formData.login"
                                       ng-minlength="5"
                                       ng-maxlength="100"
                                       required>
                                <div class="help-block" ng-messages="addTutorForm.login.$error" ng-show="addTutorForm.login.$touched">
                                    <p ng-message="minlength">{lang.tooShort5}}</p>
                                    <p ng-message="maxlength">{lang.tooLong100}}</p>
                                    <p ng-message="required">{lang.required}}</p>
                                    <p ng-message="pattern">{lang.invalidFormat}}</p>
                                </div>
                            </div>
                            <div class="form-group" ng-class="{ 'has-error': addTutorForm.password.$touched && addTutorForm.password.$invalid }">
                                <label class="control-label" for="password">{lang.password}}</label>
                                <input type="text" id = "password" name = "password" ng-template = "passwordTemplate" ng-model = "detailsData.password" class="form-control" placeholder="{lang.pasword}}"
                                       ng-model="formData.password"
                                       ng-minlength="5"
                                       ng-maxlength="100"
                                       required>
                                <div class="help-block" ng-messages="addTutorForm.password.$error" ng-show="addTutorForm.password.$touched">
                                    <p ng-message="minlength">{lang.tooShort5}}</p>
                                    <p ng-message="maxlength">{lang.tooLong100}}</p>
                                    <p ng-message="required">{lang.required}}</p>
                                    <p ng-message="pattern">{lang.invalidFormat}}</p>
                                </div>
                            </div>
                            <br>
                            <div class="row">
                                <input type="file" name="image" placeholder="{image}}" ng-model="newImage" accept="image/*" app-filereader/>
                            </div>
                        </div>
                    </div>

                    <div class="modal-footer">
                        <button type="button" class="btn btn-primary" ng-show="!addTutorForm.$invalid" ng-click="postTutor()">{lang.Add}}</button>
                        <button type="button" class="btn btn-default" ng-click="hideWithName('addTutor')">{lang.Cancel}}</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>