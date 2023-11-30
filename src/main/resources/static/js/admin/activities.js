function saveActivities() {
    fetch("/admin/activity/redis/save")
        .then(response => {
            if (response.ok) {
                location.reload();
            } else {
                throw new Error("Error: " + response.status);
            }
        })
        .then(data => {
            console.log(data); // 성공적으로 요청이 완료되면 콘솔에 출력
        })
        .catch(error => {
            console.error(error); // 요청이 실패했을 경우 에러 메시지 출력
        });
}

function deleteRedisActivity(id) {
    const confirmDelete = confirm("삭제하시겠습니까?");

    fetch("/admin/activity/redis/" + id, {
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

    fetch('/admin/activity/redis/deleteAll', {
        method: 'DELETE'
    })
        .then(response => {
            if (response.ok) {
                location.reload();
            } else {
                console.error('Error:', response.status);
            }
        })
        .catch(error => {
            console.error('Error:', error);
        });
}

function addActivityButton() {
    var name = $("#name").val();
    var venue = $("input[name='venue']:checked").val();
    var capacity = $("input[name='capacity']:checked").val();

    // 활동 추가 AJAX 요청
    $.ajax({
        type: "POST",
        url: "/admin/activity/db/add",
        data: {
            name: name,
            venue: venue,
            capacity: capacity
        },
        success: function(response) {
            console.log("활동이 추가되었습니다:", response);
            location.reload();
            // 추가된 활동에 대한 처리 로직을 작성합니다.
        },
        error: function(error) {
            console.error("활동 추가 실패:", error);
            // 실패한 경우에 대한 처리 로직을 작성합니다.
        }
    });

    // 모달 창 닫기
    $("#addActivityModal").modal("hide");
}