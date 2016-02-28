courseUrl = "/ajax/course?byParam=BEFORE_START&r=TUTOR";
inProgressUrl = "/ajax/course?byParam=IN_PROGRESS&r=TUTOR";
finishedUrl = "/ajax/course?byParam=FINISHED&r=TUTOR";
var datatableApi;
$(function () {
    datatableApi = $('#before').DataTable({
        "sAjaxSource": courseUrl,
        "sAjaxDataProp": "",
        "bPaginate": false,
        "bInfo": false,
        "aoColumns": [
            {
                "mData": "title",
                "bSortable": false
            },
            {
                "mData": "startDate"
            },
            {
                "mData": "endDate"
            },
            {
                "mData": "themes",
                "mRender": function (data, type, row) {
                    var strToReturn = "";
                    data.forEach(function(item){
                        strToReturn = strToReturn + '<h6><span class = "badge">'+ item + '</span></h6>'
                    });
                    return strToReturn;
                }
            },
            {
                "mData": "subscribersCount"
            },
            {
                "bSortable": false,
                "sDefaultContent": "",
                "mRender": renderDetailsBtn
            },
            {
                "bSortable": false,
                "sDefaultContent": "",
                "mRender": renderSubscribersBtn
            }
        ],
        "aaSorting": [
            [
                0,
                "asc"
            ]
        ]
    });
});
$(function () {
    datatableApi = $('#active').DataTable({
        "sAjaxSource": inProgressUrl,
        "sAjaxDataProp": "",
        "bPaginate": false,
        "bInfo": false,
        "aoColumns": [
            {
                "mData": "title",
                "bSortable": false
            },
            {
                "mData": "startDate"
            },
            {
                "mData": "endDate"
            },
            {
                "mData": "themes",
                "mRender": function (data, type, row) {
                    var strToReturn = "";
                    data.forEach(function(item){
                        strToReturn = strToReturn + '<h6><span class = "badge">'+ item + '</span></h6>'
                    });
                    return strToReturn;
                }
            },
            {
                "mData": "subscribersCount"
            },
            {
                "bSortable": false,
                "sDefaultContent": "",
                "mRender": renderDetailsBtn
            },
            {
                "bSortable": false,
                "sDefaultContent": "",
                "mRender": renderSubscribersBtn
            }
        ],
        "aaSorting": [
            [
                0,
                "asc"
            ]
        ]
    });
});
$(function () {
    datatableApi = $('#finished').DataTable({
        "sAjaxSource": finishedUrl,
        "sAjaxDataProp": "",
        "bPaginate": false,
        "bInfo": false,
        "aoColumns": [
            {
                "mData": "title",
                "bSortable": false
            },
            {
                "mData": "startDate"
            },
            {
                "mData": "endDate"
            },
            {
                "mData": "themes",
                "mRender": function (data, type, row) {
                    var strToReturn = "";
                    data.forEach(function(item){
                        strToReturn = strToReturn + '<h6><span class = "badge">'+ item + '</span></h6>'
                    });
                    return strToReturn;
                }
            },
            {
                "mData": "subscribersCount"
            },
            {
                "bSortable": false,
                "sDefaultContent": "",
                "mRender": renderDetailsBtn
            },
            {
                "bSortable": false,
                "sDefaultContent": "",
                "mRender": renderMarksBtn
            },
            {
                "bSortable": false,
                "sDefaultContent": "",
                "mRender": renderOdtReportBtn
            },
            {
                "bSortable": false,
                "sDefaultContent": "",
                "mRender": renderPdfReportBtn
            }
        ],
        "aaSorting": [
            [
                0,
                "asc"
            ]
        ]
    });
});

function renderOdtReportBtn(data, type, row) {
    return '<a class="btn btn-primary btn-outline btn-primary btn-sm toggle link-button" href="/tutor/report/report.odt?courseId=' + row.id +'"><i class="fa fa-file-word-o"> </i> ODT</a>';
}

function renderPdfReportBtn(data, type, row) {
    return '<a class="btn btn-danger btn-outline btn-primary btn-sm toggle link-button" href="/tutor/report/report.pdf?courseId=' + row.id +'" target="_blank"><i class="fa fa-file-pdf-o"> </i> PDF</a>';
}

function renderMarksBtn(data, type, row) {
    return '<a class="btn btn-default details-btn" onclick = "showJournal(' + row.id + ')"><i class = "glyphicon glyphicon-pencil"></i></a>';
}

function renderSubscribersBtn(data, type, row) {
    return '<a class="btn btn-default details-btn" onclick = "showSubscribers(' + row.id + ')"><i class = "glyphicon glyphicon-pencil"></i></a>';
}

function renderDetailsBtn(data, type, row) {
    return '<a class="btn btn-primary details-btn" onclick = "details(' + row.id + ')"><i class = "glyphicon glyphicon-search"></i></a>';
}

function details(id) {
    angular.element(document.getElementById('body')).scope().details(id);
}

function showJournal(id){
    angular.element(document.getElementById('body')).scope().get(id, 'journal');
}

function showSubscribers(id){
    angular.element(document.getElementById('body')).scope().get(id, 'subscribers');
}