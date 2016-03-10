ajaxUrl = "/ajax/subscription";
var datatableApi1;
var datatableApi2;
var datatableApi3;

$(function () {
    datatableApi1 = $('#datatable3').DataTable({
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
    datatableApi2 = $('#datatable1').DataTable({
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
                 "bSortable": false,
                 "sDefaultContent": "",
                 "mRender": renderDetailsBtn
             },
            {
                "bSortable": false,
                "sDefaultContent": "",
                "mRender": renderDeleteBtnStart
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
    datatableApi3 = $('#datatable2').DataTable({
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
                "bSortable": false,
                "sDefaultContent": "",
                "mRender": renderDetailsBtn
            },
            {
                "bSortable": false,
                "sDefaultContent": "",
                "mRender": renderDeleteBtnProg
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

function renderDeleteBtnProg(data, type, row) {
    console.log(row);
        return '<a class="btn btn-xs btn-danger" onclick="deleteRowProg(' + row.id + ');"><i class = "glyphicon glyphicon-remove"></i></a>';
    return data;
}

function renderDeleteBtnStart(data, type, row) {
    console.log(row);
        return '<a class="btn btn-xs btn-danger" onclick="deleteRowStart(' + row.id + ');"><i class = "glyphicon glyphicon-remove"></i></a>';
    return data;
}

function renderDetailsBtn(data, type, row) {
       return '<a class="btn btn-xs btn-success details-btn" onclick = "details(' + row.courseId + ')"><i class = "glyphicon glyphicon-search"></i></a>';
 }

function deleteRowProg(id) {
    $.ajax({
        url: ajaxUrl + '/'+id,
        type: 'DELETE',
        success: function () {
            updateTableProg();
            successNoty('Deleted');
        }
    });
}

function deleteRowStart(id) {
    $.ajax({
        url: ajaxUrl + '/'+id,
        type: 'DELETE',
        success: function () {
            updateTableStart();
            successNoty('Deleted');
        }
    });
}

function details(id) {
    angular.element(document.getElementById('body')).scope().details(id);
}

function updateTableProg() {
    $.get(ajaxUrl+'?sortBy=IN_PROGRESS', function (data) {
        updateTableByDataProg(data);
    });
}
function updateTableStart() {
    $.get(ajaxUrl+'?sortBy=BEFORE_START', function (data) {
        updateTableByDataStart(data);
    });
}

function updateTableByDataProg(data) {
    datatableApi3.clear().rows.add(data).draw();
}

function updateTableByDataStart(data) {
    datatableApi2.clear().rows.add(data).draw();
}