var ajaxUrl = "ajax/admin/users/";
var datatableApi;


function changeEnable(object, id) {
    $.ajax({
        url: ajaxUrl + 'changed',
        type: 'POST',
        data: {userId: id, enabled: object.checked}
    }).done(function () {
        $('#' + id).attr('data-userEnable', object.checked);
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
