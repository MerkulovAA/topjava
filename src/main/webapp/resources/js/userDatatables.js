var ajaxUrl = "ajax/admin/users/";
var datatableApi;


function changeEnable() {
    var object = event.target;
    var id = getIdTr();
    $.ajax({
        url: ajaxUrl + 'changed',
        type: 'POST',
        data: {userId: id, enabled: object.checked}
    }).done(function () {
        $('#' + id).attr('data-userEnable', object.checked);
        successNoty("changed");
    });
}


function saveUser(){
    save().done(function () {
        updateUserTable();
    })
}

function deleteUser() {
    deleteRow(getIdTr()).done(function () {
        updateUserTable();
    });

}

function updateUserTable(){
    $.get(ajaxUrl, function (data) {
        updateTableWithData(data)
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
