ajaxUrl = "/ajax/subscription";
var datatableApi;

$(function () {
    datatableApi = $('#datatable3').DataTable({
        "sAjaxSource": ajaxUrl+'?sortBy=FINISHED',
        "sAjaxDataProp": "",
        "bPaginate": false,
        "bInfo": false,
        "aoColumns": [
            {
                "mData": "courseTitle",
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
                "bSortable": true,
                "mRender": function (data, type, row) {
                    var strToReturn = "";
                    data.forEach(function(item){
                        strToReturn = strToReturn + '<h6><span class = "badge">'+ item + '</span></h6>'
                    });
                    return strToReturn;
                }
            },
            {
                "mData": "mark",
                "bSortable": true
            },
            {
                "bSortable": false,
                "sDefaultContent": "",
                "mRender": renderDetailsBtn
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
    datatableApi = $('#datatable1').DataTable({
        "sAjaxSource": ajaxUrl+'?sortBy=BEFORE_START',
        "sAjaxDataProp": "",
        "bPaginate": false,
        "bInfo": false,
        "aoColumns": [
            {
                "mData": "courseTitle",
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
                "bSortable": true,
                "mRender": function (data, type, row) {
                    var strToReturn = "";
                    data.forEach(function(item){
                        strToReturn = strToReturn + '<h6><span class = "badge">'+ item + '</span></h6>'
                    });
                    return strToReturn;
                }
            },
            {
                "mData": "mark",
                "bSortable": true
            },
            {
                 "bSortable": false,
                 "sDefaultContent": "",
                 "mRender": renderDetailsBtn
             },
            {
                "bSortable": false,
                "sDefaultContent": "",
                "mRender": renderDeleteBtn
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
    datatableApi = $('#datatable2').DataTable({
        "sAjaxSource": ajaxUrl+'?sortBy=IN_PROGRESS',
        "sAjaxDataProp": "",
        "bPaginate": false,
        "bInfo": false,
        "aoColumns": [
            {
                "mData": "courseTitle",
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
                "bSortable": true,
                "mRender": function (data, type, row) {
                    var strToReturn = "";
                    data.forEach(function(item){
                        strToReturn = strToReturn + '<h6><span class = "badge">'+ item + '</span></h6>'
                    });
                    return strToReturn;
                }
            },
            {
                "mData": "mark",
                "bSortable": true
            },
            {
                "bSortable": false,
                "sDefaultContent": "",
                "mRender": renderDetailsBtn
            },
            {
                "bSortable": false,
                "sDefaultContent": "",
                "mRender": renderDeleteBtn
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

var failedNote;

function closeNoty() {
    if (failedNote) {
        failedNote.close();
        failedNote = undefined;
    }
}

function successNoty(text) {
    closeNoty();
    noty({
        text: text,
        type: 'success',
        layout: 'bottomRight',
        timeout: 1000
    });
}

function renderDeleteBtn(data, type, row) {
    console.log(row);
        return '<a class="btn btn-xs btn-danger" onclick="deleteRow(' + row.id + ');"><i class = "glyphicon glyphicon-remove"></i></a>';

    return data;
}

function renderDetailsBtn(data, type, row) {
       return '<a class="btn btn-xs btn-success details-btn" onclick = "details(' + row.courseId + ')"><i class = "glyphicon glyphicon-search"></i></a>';
 }

function deleteRow(id) {
    $.ajax({
        url: ajaxUrl + id,
        type: 'DELETE',
        success: function () {
            updateTable();
            successNoty('Deleted');
        }
    });
}

function details(id) {
    angular.element(document.getElementById('body')).scope().details(id);
}

function updateTable() {
    $.get(ajaxUrl, function (data) {
        updateTableByData(data);
    });
}

function updateTableByData(data) {
    datatableApi.clear().rows.add(data).draw();
}