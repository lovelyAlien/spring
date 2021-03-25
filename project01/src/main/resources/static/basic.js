
// url 파라미터 정보 가져오기
function getParameterByName(name) {
    name = name.replace(/[\[]/, "\\[").replace(/[\]]/, "\\]");
    var regex = new RegExp("[\\?&]" + name + "=([^&#]*)"),
        results = regex.exec(location.search);
    return results == null ? "" : decodeURIComponent(results[1].replace(/\+/g, " "));
}

//format date
function formatDate(before){
    let date = before.split('.');
    return date[0].replace('T',' ');
}

// 사용자가 내용을 올바르게 입력하였는지 확인합니다.
function isValidContents(contents) {
    if (contents === '') {
        alert('내용을 입력해주세요');
        return false;
    }
    if (contents.trim().length > 3000) {
        alert('공백 포함 3000자 이하로 입력해주세요');
        return false;
    }
    return true;
}

function isValidTitle(title) {
    if (title === '') {
        alert('제목을 입력해주세요');
        return false;
    }
    if (title.trim().length > 50) {
        alert('공백 포함 50자 이하로 입력해주세요');
        return false;
    }
    return true;
}

function isValidUsername(username) {
    if (username === '') {
        alert('이름을 입력해주세요');
        return false;
    }
    if (username.trim().length > 30) {
        alert('공백 포함 30자 이하로 입력해주세요');
        return false;
    }
    return true;
}


// 수정 버튼을 눌렀을 때, 기존 작성 내용을 textarea 에 전달합니다.
// 숨길 버튼을 숨기고, 나타낼 버튼을 나타냅니다.
function editPost(id) {
    showEdits(id);
    let contents = $(`#${id}-contents`).text().trim();
    $(`#${id}-textarea`).val(contents);
}

function showEdits(id) {
    $(`#${id}-editarea`).show();
    $(`#${id}-submit`).show();
    $(`#${id}-delete`).show();

    $(`#${id}-contents`).hide();
    $(`#${id}-edit`).hide();
}

function hideEdits(id) {
    $(`#${id}-editarea`).hide();
    $(`#${id}-submit`).hide();
    $(`#${id}-delete`).hide();

    $(`#${id}-contents`).show();
    $(`#${id}-edit`).show();
}

////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// 여기서부터 코드를 작성해주시면 됩니다.

function showPost(id){
    window.location. href="show.html?id="+ id;

}

// 메모를 불러와서 보여줍니다.
function showPosts() {
    // 1. 기존 메모 내용을 지웁니다.
    $('#posts-box').empty();
    // 2. 메모 목록을 불러와서 HTML로 붙입니다.
    $.ajax({
        type: 'GET',
        url: '/api/posts',
        success: function (response) {
            for (let i = 0; i < response.length; i++) {
                let post = response[i];
                let id = post['id'];
                let title = post['title'];
                let username = post['username'];
                let contents = post['contents'];
                let modifiedAt = formatDate(post['modifiedAt']);
                addHTML(id, title, username, contents, modifiedAt);
            }
        }
    })
}

// 메모 하나를 HTML로 만들어서 body 태그 내 원하는 곳에 붙입니다.
function addHTML(id, title, username, contents, modifiedAt) {
    // 1. HTML 태그를 만듭니다.
    let tempHtml = `<tr id="${id}" class="post" style="cursor:pointer" onclick="showPost(${id})">
        <td id="${id}-title">${title}</td>
        <td id="${id}-username">${username}</td>
        <td>${modifiedAt}</td>
    </tr>`;
    // 2. #cards-box 에 HTML을 붙인다.
    $('#posts-box').append(tempHtml);
}

// 메모를 생성합니다.
function writePost() {
    // 1. 작성한 메모를 불러옵니다.
    let title = $('#title').val()
    let username = $('#username').val()
    let contents = $('#contents').val()

    // 2. 작성한 메모가 올바른지 isValidContents 함수를 통해 확인합니다.

    if (isValidTitle(title) == false) {
        return;
    }
    if (isValidUsername(username) == false) {
        return;
    }
    if (isValidContents(contents) == false) {
        return;
    }


    // 3. 전달할 data JSON으로 만듭니다.
    let data = {'title': title, 'username': username, 'contents': contents};
    // 5. POST /api/posts 에 data를 전달합니다.
    $.ajax({
        type: "POST",
        url: "/api/posts",
        contentType: "application/json",
        data: JSON.stringify(data),
        success: function (response) {
            alert("제출 성공");
            window.location.href = '/'
        }
    })
}

// 메모를 수정합니다.
function submitEdit(id) {
    // 1. 작성 대상 메모의 username과 contents 를 확인합니다.
    let title = $('#title').val().trim();
    let username = $('#username').val().trim();
    let contents = $('#contents').val().trim();
    // 2. 작성한 메모가 올바른지 isValidContents 함수를 통해 확인합니다.

    if (isValidTitle(title) === false)  {
        return;
    }
    if (isValidUsername(username) === false) {
        return;
    }
    if (isValidContents(contents) === false) {
        return;
    }
    // 3. 전달할 data JSON으로 만듭니다.
    let data = {'title': title, 'username': username, 'contents': contents};
    // 4. PUT /api/memos/{id} 에 data를 전달합니다.
    $.ajax({
        type: "PUT",
        url: `/api/memos/${id}`,
        contentType: "application/json",
        data: JSON.stringify(data),
        success: function (response) {
            alert('게시글이 수정되었습니다.');
            window.location.reload();
        }
    });
}

// 메모를 삭제합니다.
function deleteOne(id) {
    // 1. DELETE /api/memos/{id}
    $.ajax({
        type: 'DELETE',
        url: `/api/memos/${id}`,
        success: function () {
            alert("삭제 완료");
            window.location.reload();
        }
    })
}

