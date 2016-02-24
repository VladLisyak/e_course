<%@include file="../../fragments/admin_fragments/header.jsp" %>
<%@taglib prefix="n" tagdir="/WEB-INF/tags" %>
<!DOCTYPE html>
<html>
<%--<!--suppress HtmlUnknownTarget -->--%>
<head>
    <%@include file="../../fragments/admin_fragments/head.jspf" %>
</head>
<body ng-app = "adminApp"  ng-cloak class="ng-cloak">
<div id="wrapper">
        <n:navbar title="E_course"/>
        <div id="page-wrapper">

            <br>
            <div ng-view class="container">
                <div class="row">
                    <div ng-view class = "container">

                    </div>
                </div>
            </div>
        </div>
</div>

<%@include file="../../fragments/admin_fragments/coursesModal.jsp" %>
<%@include file="../../fragments/footer.jspf" %>
<script src="assets/js/admin.js" type="text/javascript"></script>
<script src="<c:url value="/webjars/metisMenu/1.1.2/metisMenu.js"/>"></script>
<script src="<c:url value="../../../assets/js/custom.min.js"/>"></script>
</body>
</html>