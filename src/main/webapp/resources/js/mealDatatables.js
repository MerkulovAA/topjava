var ajaxUrl = "ajax/meals/";
var datatableApi;

function filters() {
    var formFilter = $("#filter");
    $.ajax({
        type: "POST",
        url: ajaxUrl + 'filter',
        data: formFilter.serialize()
    }).done(function (data) {
        updateTableWithData(data);
        successNoty("Filter");
    });
}

function saveMeal() {
    var form = $("#detailsForm");
    $.ajax({
        type: "POST",
        url: ajaxUrl,
        data: form.serialize()
    }).done(function () {
        $("#editRow").modal("hide");
        filters();
        successNoty("Saved");
    });
}

function clearForm() {
    var form = $("#filter");
    form[0].reset();
    updateTable();
}

// $(document).ready(function () {
$(function () {
    datatableApi = $("#datatable").DataTable({
        "paging": false,
        "info": true,
        "columns": [
            {
                "data": "dateTime"
            },
            {
                "data": "description"
            },
            {
                "data": "calories"
            },
            {
                "defaultContent": "Edit",
                "orderable": false
            },
            {
                "defaultContent": "Delete",
                "orderable": false
            }
        ],
        "order": [
            [
                0,
                "desc"
            ]
        ]
    });
    makeEditable();

    $('#datetimepicker').datetimepicker({
        format: 'Y-m-d H:i'
    });
    $('#startDate, #endDate').datetimepicker({
        timepicker: false,
        format: 'Y-m-d'
    });
    $('#startTime, #endTime').datetimepicker({
        datepicker:false,
        format: 'H:i'
    });

});




