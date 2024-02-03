function saveActivities() {
    $.ajax({
        url: "/admin/activity/redis/save",
        method: "GET",
        success: function(data) {
            console.log(data); // 성공적으로 요청이 완료되면 콘솔에 출력
            location.reload();
        },
        error: function(jqXHR, textStatus, errorThrown) {
            console.error("Error: " + textStatus); // 요청이 실패했을 경우 에러 메시지 출력
        }
    });
}

function deleteRedisActivity(id) {
    const confirmDelete = confirm("삭제하시겠습니까?");

    $.ajax({
        url: "/admin/activity/redis/" + id,
        method: "DELETE",
        headers: {
            "Content-Type": "application/json"
        },
        success: function(data) {
            location.reload();
        },
        error: function(jqXHR, textStatus, errorThrown) {
            console.error("Error: " + textStatus);
        }
    });
}

function deleteDbActivity(id) {
    const confirmDelete = confirm("삭제 하시겠습니까?");

    fetch("/admin/activity/db/" + id, {
        method: "DELETE",
        headers: {
            "Content-Type": "application/json"
        }
    })
        .then(response => {
            if (response.ok) {
                location.reload();
            } else {
                throw new Error("Error: " + response.status);
            }
        })
        .catch(error => {
            console.error(error);
        });
}

function deleteAllActivities() {
    const confirmDelete = confirm("전체 삭제 하시겠습니까?");

    $.ajax({
        url: "/admin/activity/redis/deleteAll",
        method: "DELETE",
        headers: {
            "Content-Type": "application/json"
        },
        success: function(data) {
            location.reload();
        },
        error: function(jqXHR, textStatus, errorThrown) {
            console.error("Error: " + textStatus);
        }
    });
}

function addActivityButton() {

    const activityDTO = {
        name: $("#name").val(),
        venue: $("input[name='venue']:checked").val(),
        capacity: $("input[name='capacity']:checked").val()
    };

    $.ajax({
        url: "/admin/activity/db/add",
        type: "POST",
        contentType: "application/json",
        data: JSON.stringify(activityDTO),
        success: function(response) {
            console.log("활동이 추가되었습니다:", response);
            location.reload();
        },
        error: function(xhr, status, error) {
            alert("유효성 검사에 실패하였습니다.");
        }
    });


    // 모달 창 닫기
    $("#addActivityModal").modal("hide");
}

function editActivityButton() {

    const dto = {
        id : $('#id').val(),
        name : $("#name").val(),
        venue : $("input[name='venue']:checked").val(),
        capacity : $("input[name='capacity']:checked").val()
    };

    $.ajax({
        type: 'PUT',
        url: '/admin/activity/db/' + dto.id,
        contentType: "application/json",
        data: JSON.stringify(dto),
        success: function(response) {
            // 업데이트 성공 시 모달 창 닫기
            $('#addCategoryModal').modal('hide');
            location.reload();
        },
        error: function(xhr, status, error) {
            alert("유효성 검사에 실패하였습니다.");
            console.error(error);
        }
    });
}

function AddActivityModal() {

    $('#addActivityButton').show();
    $('#editActivityButton').hide();
    $('#addActivityModalLabel').show();
    $('#editActivityModalLabel').hide();

    $('#addActivityModal').modal('show');
}


function editActivityModal(id) {
    const $addActivityModal = $('#addActivityModal');

    $.ajax({
        url: "/admin/activity/db/" + id,
        type: "GET",
        success: function(response) {
            const activity = response;

            // 모달 창 열기
            $('#addActivityModal').modal('show');

            // 데이터 자동 채우기
            $('#id').val(activity.id);
            $('#name').val(activity.name);
            $(`input[name="venue"][value="${activity.venue}"]`).prop("checked", true);
            $(`input[name="capacity"][value="${activity.capacity}"]`).prop("checked", true);

            // 추가 버튼과 레이블 처리
            if (activity.id === "") {
                $('#addActivityButton').show();
                $('#editActivityButton').hide();
                $('#addActivityModalLabel').show();
                $('#editActivityModalLabel').hide();
            } else {
                $('#addActivityButton').hide();
                $('#editActivityButton').show();
                $('#addActivityModalLabel').hide();
                $('#editActivityModalLabel').show();
            }
        },
        error: function() {
            // 에러 시 처리할 로직
        }
    });

    $addActivityModal.on('hidden.bs.modal', function() {
        $('#id').val('');
        $('#name').val('');
        $(`input[name="venue"][value="0"]`).prop("checked", true);
        $(`input[name="capacity"][value="0"]`).prop("checked", true);
    });

    $addActivityModal.on('click', '[data-dismiss="modal"]', function() {
        $('#id').val('');
        $('#name').val('');
        $(`input[name="venue"][value="0"]`).prop("checked", true);
        $(`input[name="capacity"][value="0"]`).prop("checked", true);
    });
}