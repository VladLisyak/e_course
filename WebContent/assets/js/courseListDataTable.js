ajaxUrl = "/ajax/subscription/";
$(function () {
    datatableApi = $('#datatable').DataTable({
        "sAjaxSource": ajaxUrl,
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
                "mData": "status"
            },
            {
                "mData": "themes",
                "bSortable": false
            },
            {
                "mData": "mark",
                "bSortable": true
            },
            {
                "bSortable": false,
                "sDefaultContent": "",
                "mRender": renderDeleteBtn
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
    if (Date.parse(row.endDate) > Date.now()) {
        return '<a class="btn btn-xs btn-danger" onclick="deleteRow(' + row.id + ');"><i class = "glyphicon glyphicon-remove"></i></a>';
    }
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