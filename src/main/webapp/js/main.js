$(document).ready(function () {
    let showAll = false;
    buildTable(showAll);
    hello();
    getCategory();

    $('#add').click(function () {
        validateAndAdd();
        setTimeout(function () {
            buildTable(showAll);
        }, 1000);
    });

    $('#flexSwitchCheckDefault').click(function () {
        showAll = !showAll;
        buildTable(showAll);
    });
});

function getCategory() {
    let categorys = [];
    categorys.push('<option selected>Select ...</option>');
    $.getJSON("http://localhost:8080/todo/categorys"
    ).done(function (response) {
        $.each(response, function (key, val) {
            categorys.push('<option value="' + val.id + '">' + val.name + '</option>');
        });
        $('#category').html(categorys);
    }).fail(function (err) {
        alert('Get category Failed!');
        console.log("Get category error: " + err);
    });
}

function validateAndAdd() {
    if ($('#overview').val() == '' || $('#category').val() == 'Select ...') {
        $('#overview').val() == '' ? alert('Enter overview') : alert('Enter category');
        return false;
    }
    addTask();
    return false;
}

function addTask() {
    $.get("http://localhost:8080/todo/add", $('form').serialize()
    ).done(function (response) {
        console.log("Response Data: " + response);
    }).fail(function (err) {
        alert('Request Failed!');
        console.log("Request Failed: " + err);
    });
}

function buildTable(showAll) {
    $.getJSON("http://localhost:8080/todo/build"
    ).done(function (response) {
        let rows = [];
        $.each(response, function (key, val) {
            if (showAll == false) {
                if (val.done == false) {
                    rows.push('<tr><td>' + val.description + '</td><td>' +
                        '<div class="form-check">' +
                        '<input class="form-check-input" type="checkbox" value="" id="' + val.id + '">' +
                        '</div></td>' +
                        '<td>' + val.user.name + '</td>' +
                        '<td>' + print(val.categories) + '</td></tr>');
                }
            } else {
                if (val.done == false) {
                    rows.push('<tr><td>' + val.description + '</td><td>' +
                        '<div class="form-check">' +
                        '<input class="form-check-input" type="checkbox" value="" id="' + val.id + '">' +
                        '</div></td>' +
                        '<td>' + val.user.name + '</td>' +
                        '<td>' + print(val.categories) + '</td></tr>');
                } else {
                    rows.push('<tr><td>' + val.description + '</td><td>' +
                        '<div class="form-check">' +
                        '<input class="form-check-input" type="checkbox" value="" id="' + val.id + '" checked>' +
                        '</div></td>' +
                        '<td>' + val.user.name + '</td>' +
                        '<td>' + print(val.categories) + '</td></tr>');
                }
            }
        });
        $('#table').html(rows);
        $('table').find('input').click(function () {
            update($(this).attr("id"), showAll);
        });
    }).fail(function (err) {
        alert('buildTable Failed!');
        console.log("Request Failed: " + err);
    });
}

function print(category) {
    let result = [];
    category.forEach(c => result.push(c.name))
    return result;
}

function update(id, showAll) {
    $.get("http://localhost:8080/todo/update", {
        id: id
    }).done(function (response) {
        buildTable(showAll);
        console.log("Response Data: " + response);
    }).fail(function (err) {
        alert('Request Failed!');
        console.log("Request Failed: " + err);
    });
}

function hello() {
    $.getJSON("http://localhost:8080/todo/hello.do"
    ).done(function (response) {
        $('#hello').text('Hello, ' + response.name + ' ! ');
        console.log("Response Data: " + response);
    }).fail(function (err) {
        alert('Request Failed!');
        console.log("Request Failed: " + err);
    });
}