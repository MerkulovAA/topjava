const ajaxUrl = "ajax/profile/meals/";
let datatableApi;

function updateTable() {
    $.ajax({
        type: "GET",
        url: ajaxUrl + "filter",
        data: $("#filter").serialize()
    }).done(updateTableByData);
}

function clearFilter() {
    $("#filter")[0].reset();
    $.get(ajaxUrl, updateTableByData);
}

// function getForm(form) {
//     // let value = form.find("input[name='dateTime']").val();
//     // form.find("input[name='dateTime']").val(value.substring(0,16).replace(' ', 'T'));
//     return form;
// }

$(function () {
    datatableApi = $("#datatable").DataTable({
        "ajax": {
            "url": ajaxUrl,
            "dataSrc": ""
        },
        "paging": false,
        "info": true,
        "columns": [
            {
                "data": "dateTime",
                "render": function (dateTime, type, row) {
                    if (type === "display") {
                        dateTime = getDateTime(dateTime);
                    }
                    return dateTime;
                }
            },
            {
                "data": "description"
            },
            {
                "data": "calories"
            },
            {
                "orderable": false,
                "defaultContent": "",
                "render": renderEditBtn
            },
            {
                "orderable": false,
                "defaultContent": "",
                "render": renderDeleteBtn
            }
        ],
        "order": [
            [
                0,
                "desc"
            ]
        ],
        "createdRow": function (row, data, dataIndex) {
            $(row).attr("data-mealExceed", data.exceed);
        },
        "initComplete": makeEditable
    });

    $('#dateTime').datetimepicker({
        format: 'Y-m-d H:i'
    });
    $('#startDate, #endDate').datetimepicker({
        timepicker: false,
        format: 'Y-m-d'
    });
    $('#startTime, #endTime').datetimepicker({
        datepicker: false,
        format: 'H:i'
    });
});