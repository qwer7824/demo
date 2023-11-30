function addMapsButton() {
    // DTO 객체 생성
    var dto = {
        name: $("#name").val(),
        category: $("#category").val(),
        tel: $("#tel").val(),
        venue: $("#venue").val(),
        latitude: $("#latitude").val(),
        longitude: $("#longitude").val()
    };

    // AJAX 요청
    $.ajax({
        url: "/admin/db/markerAdd",
        type: "POST",
        contentType: "application/json",
        data: JSON.stringify(dto),
        success: function(response) {
            // 성공 시 처리할 로직
            location.reload();
        },
        error: function(xhr, status, error) {
            // 에러 시 처리할 로직
        }
    });

    // 모달 창 닫기
    $("#addMapsModal").modal("hide");
}

function addCategoryButton() {
    // DTO 객체 생성
    var dto = {
        name: $("#cname").val()
    };

    // AJAX 요청
    $.ajax({
        url: "/admin/db/categoryAdd",
        type: "POST",
        contentType: "application/json",
        data: JSON.stringify(dto),
        success: function(response) {
            // 성공 시 처리할 로직
            location.reload();
        },
        error: function(xhr, status, error) {
            // 에러 시 처리할 로직
        }
    });

    // 모달 창 닫기
    $("#addMapsModal2").modal("hide");
}

function deleteDbMap(id) {
    const confirmDelete = confirm("삭제 하시겠습니까?");

    fetch("/admin/marker/db/" + id, {
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

function deleteDbCategory(id) {
    const confirmDelete = confirm("삭제 하시겠습니까?");

    fetch("/admin/category/db/" + id, {
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

function showModal() {
    $('#addMapsModal').modal('show');
}

function showModal2() {
    $('#addCategoryModal').modal('show');
}

    window.onload = function(){
    document.getElementById("address_kakao").addEventListener("click", function(){
        //카카오 지도 발생
        new daum.Postcode({
            oncomplete: function(data) { //선택시 입력값 세팅
                document.getElementById("address_kakao").value = data.address; // 주소 넣기
                convertAddressToCoordinate(data.address);
            }
        }).open();
    });
}
    // 주소를 경도와 위도 값으로 변환하는 함수
    function convertAddressToCoordinate(address) {
    // 주소-좌표 변환 객체 생성
    var geocoder = new kakao.maps.services.Geocoder();

    // 주소로 좌표 변환
    geocoder.addressSearch(address, function(result, status) {
    if (status === kakao.maps.services.Status.OK) {
    var latitude = result[0].y; // 위도
    var longitude = result[0].x; // 경도

    // 변환된 좌표로 원하는 작업 수행
    document.getElementById("latitude").value = latitude; // 주소 넣기
    document.getElementById("longitude").value = longitude; // 주소 넣기
} else {
    console.log("주소-좌표 변환 실패");
}
});
}