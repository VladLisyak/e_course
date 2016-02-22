courseUrl = "/ajax/course?sortBy=BEFORE_START";
inProgressUrl = "/ajax/course?sortBy=IN_PROGRESS";
finishedUrl = "/ajax/course?sortBy=FINISHED";
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


function renderDetailsBtn(data, type, row) {
    return '<a class="btn btn-primary details-btn" onclick = "details(' + row.id + ')"><i class = "glyphicon glyphicon-search"></i></a>';
}

function renderMarksBtn(data, type, row) {
    return '<a class="btn btn-default details-btn" onclick = "showJournal(' + row.id + ')"><i class = "glyphicon glyphicon-pencil"></i></a>';
}

function renderSubscribersBtn(data, type, row) {
    return '<a class="btn btn-default details-btn" onclick = "showSubscribers(' + row.id + ')"><i class = "glyphicon glyphicon-pencil"></i></a>';
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