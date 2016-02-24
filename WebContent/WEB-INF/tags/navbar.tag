<%@tag pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@attribute name="title" required="true" %>

<nav class="navbar navbar-default navbar-static-top" role="navigation" style="margin-bottom: 0">
    <div class="navbar-header">
        <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
            <span class="sr-only">{lang.navigation}}</span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
        </button>
        <a class="navbar-brand" href="admin/main/#/home">${title}</a> <%--Change for angular--%>
    </div>
    <ul class="nav navbar-top-links navbar-right">
        <li class="dropdown">
            <a class="dropdown-toggle" data-toggle="dropdown" href="#">
                <i class="fa fa-language fa-fw"></i> <i class="fa fa-caret-down"></i>
            </a>
            <ul class="dropdown-menu locales">
                <c:forEach items="${applicationScope.locales}" var="locale" varStatus="status">
                    <li ng-click = "changeLang('${locale}')" ng-class="checkLang('${locale}') ? 'active dropdown-toggle' : ''" >
                        <a>
                            <div>
                                <img src="<c:url value="/assets/img/locales/${locale}.png"/>"/>
                                <span>${locale}</span>
                            </div>
                        </a>
                    </li>
                </c:forEach>
            </ul>
        </li>
        <li class="dropdown">
            <a class="dropdown-toggle" data-toggle="dropdown" href="#">
                <i class="fa fa-user fa-fw"></i> <i class="fa fa-caret-down"></i>
            </a>
            <ul class="dropdown-menu dropdown-user">
                <li>
                    <a href="${pageContext.request.contextPath}/admin/logout"><i class="fa fa-sign-out fa-fw"></i>
                        {lang.logout}}
                    </a>
                </li>
            </ul>
        </li>
    </ul>
    <div class="navbar-default sidebar" role="navigation">
        <div class="sidebar-nav navbar-collapse">
            <ul class="nav" id="side-menu">
                <li class="active">
                    <a href="admin/main/#/home"><i class="fa fa-dashboard fa-fw"></i> <%--Change to angular--%>
                        {lang.home}}
                    </a>
                </li>
                <li>
                    <a href="#">
                        <i class="fa fa-users fa-fw"></i>
                        {lang.users}}<span class="fa arrow"></span>
                    </a>
                    <ul class="nav nav-second-level">
                        <li>
                            <a href="admin/main/#/users/notConfirmed">
                                {lang.notConfirmed}}
                            </a>
                        </li>
                        <li>
                            <a href="admin/main/#/users/active">
                                {lang.active}}
                            </a>
                        </li>
                        <li>
                            <a href="admin/main/#/users/tutors">
                                {lang.tutors}}
                            </a>
                        </li>
                        <li>
                            <a href="admin/main/#/users/blackList">
                                {lang.blackList}}
                            </a>
                        </li>
                    </ul>
                </li>
                <li>
                    <a href="admin/main/#/courses">
                        <i class="fa fa-book fa-fw"></i>
                        {lang.courses}}
                    </a>
                </li>
            </ul>
        </div>
    </div>
</nav>