var ajaxUrl = "ajax/admin/users/";
var datatableApi;


function changeEnable(object, id) {
    $.ajax({
        url: ajaxUrl + 'changed',
        type: 'POST',
        data: {strID: id, strState: object.checked}
    }).done(function () {
        updateTable();
        successNoty("changed");
    });
}

// $(document).ready(function () {
$(function () {
    datatableApi = $("#datatable").DataTable({
        "paging": false,
        "info": true,
        "columns": [
            {
                "data": "name"
            },
            {
                "data": "email"
            },
            {
                "data": "roles"
            },
            {
                "data": "enabled"
            },
            {
                "data": "registered"
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
                "asc"
            ]
        ]
    });
    makeEditable();
});
