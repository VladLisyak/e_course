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
<%@include file="../../fragments/admin_fragments/addTutorModal.jsp" %>
<%@include file="../../fragments/admin_fragments/addCourseModal.jsp" %>
<%@include file="../../fragments/footer.jspf" %>
<script src="assets/js/admin.js" type="text/javascript"></script>
<script type="text/javascript">
    $(document).on('show.bs.modal', '.modal', function () {
    var zIndex = 1040 + (10 * $('.modal:visible').length);
    $(this).css('z-index', zIndex);
    setTimeout(function() {
        $('.modal-backdrop').not('.modal-stack').css('z-index', zIndex - 1).addClass('modal-stack');
    }, 0);
});</script>
<script src="<c:url value="/webjars/metisMenu/1.1.2/metisMenu.js"/>"></script>
<script src="<c:url value="assets/js/custom.min.js"/>"></script>
</body>
</html>