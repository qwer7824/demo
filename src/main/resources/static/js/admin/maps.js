
function addMapsButton() {
    // DTO 객체 생성
    const dto = {
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

function editMapButton() {

    const dto = {
        id : $('#id').val(),
        name: $("#name").val(),
        category: $("#category").val(),
        tel: $("#tel").val(),
        venue: $("#venue").val(),
        latitude: $("#latitude").val(),
        longitude: $("#longitude").val()
    };

    // 서버로 Ajax 요청을 보내어 카테고리 업데이트 처리
    $.ajax({
        type: 'PUT',
        url: '/admin/marker/db/' + dto.id,
        contentType: "application/json",
        data: JSON.stringify(dto),
        success: function(response) {
            // 업데이트 성공 시 모달 창 닫기
            $('#addMapsModal').modal('hide');
            location.reload();
        },
        error: function(xhr, status, error) {
            // 업데이트 실패 시 에러 처리
            console.error(error);
        }
    });
}

function addCategoryButton() {
    // DTO 객체 생성
    const dto = {
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

    if (confirmDelete) {
        $.ajax({
            url: "/admin/marker/db/" + id,
            type: "DELETE",
            contentType: "application/json",
            success: function(response) {
                location.reload();
            },
            error: function(xhr, status, error) {
                console.error("Error: " + error);
            }
        });
    }
}

function deleteDbCategory(id) {
    const confirmDelete = confirm("삭제 하시겠습니까?");

    if (confirmDelete) {
        $.ajax({
            url: "/admin/category/db/" + id,
            type: "DELETE",
            contentType: "application/json",
            success: function(response) {
                location.reload();
            },
            error: function(xhr, status, error) {
                alert('카테고리에 연결된 지역이 있습니다.');
                console.error("Error: " + error);
            }
        });
    }
}

function editCategoryButton() {

    const dto = {
        id: $('#cid').val(),
        name: $("#cname").val()
    };

    // 서버로 Ajax 요청을 보내어 카테고리 업데이트 처리
    $.ajax({
        type: 'PUT',
        url: '/admin/category/db/' + dto.id,
        contentType: "application/json",
        data: JSON.stringify(dto),
        success: function(response) {
            // 업데이트 성공 시 모달 창 닫기
            $('#addCategoryModal').modal('hide');
            location.reload();
        },
        error: function(xhr, status, error) {
            // 업데이트 실패 시 에러 처리
            console.error(error);
        }
    });
}

function CategoryModal(categoryId, categoryName) {
    const $cname = $('#cname');
    const $cid = $('#cid');
    const $addButton = $('#addCategoryButton');
    const $editButton = $('#editCategoryButton');
    const $addLabel = $('#addCategoryModalLabel');
    const $editLabel = $('#editCategoryModalLabel');
    const $addCategoryModal = $('#addCategoryModal');

    $cname.val(categoryName);
    $cid.val(categoryId);

    const cidInput = document.getElementById("cid");

    if (cidInput.value === "") {
        $addButton.show();
        $editButton.hide();
        $addLabel.show();
        $editLabel.hide();
    } else {
        $addButton.hide();
        $editButton.show();
        $addLabel.hide();
        $editLabel.show();
    }

    $addCategoryModal.modal('show');

    $addCategoryModal.on('hidden.bs.modal', function() {
        $cname.val('');
        $cid.val('');
        cidInput.value = null;
    });

    $addCategoryModal.on('click', '[data-dismiss="modal"]', function() {
        $cname.val('');
        $cid.val('');
        cidInput.value = null;
    });
}

function editMapModal(id) {
    const $addMapsModal = $('#addMapsModal');

    $.ajax({
        url: "/admin/marker/db/" + id,
        type: "GET",
        success: function(response) {
            var marker = response;

            // 모달 창 열기
            $('#addMapsModal').modal('show');

            // 데이터 자동 채우기
            $('#id').val(marker.id);
            $('#name').val(marker.name);
            $('#tel').val(marker.tel);
            $('#venue').val(marker.venue);
            $('#latitude').val(marker.latitude);
            $('#longitude').val(marker.longitude);

            // 카테고리 선택
            const categoryId = marker.category.id; // 선택된 카테고리 ID
            const categoryName = marker.category.name; // 선택된 카테고리 이름

            // select 요소의 option을 탐색하여 선택
            $('#category option').each(function() {
                if ($(this).val() == categoryId) {
                    $(this).prop('selected', true);
                    return false; // 선택된 option을 찾았으므로 반복문 종료
                }
            });

            // 선택된 카테고리 이름을 표시
            $('#selectedCategory').text(categoryName);

            // 추가 버튼과 레이블 처리
            if (marker.id === "") {
                $('#addMapButton').show();
                $('#editMapButton').hide();
                $('#addMapsModalLabel').show();
                $('#editMapsModalLabel').hide();
            } else {
                $('#addMapButton').hide();
                $('#editMapButton').show();
                $('#addMapsModalLabel').hide();
                $('#editMapsModalLabel').show();
            }
        },
        error: function() {
            // 에러 시 처리할 로직
        }
    });

    $addMapsModal.on('hidden.bs.modal', function() {
        $('#id').val('');
        $('#name').val('');
        $('#tel').val('');
        $('#venue').val('');
        $('#latitude').val('');
        $('#longitude').val('');
    });

    $addMapsModal.on('click', '[data-dismiss="modal"]', function() {
        $('#id').val('');
        $('#name').val('');
        $('#tel').val('');
        $('#venue').val('');
        $('#latitude').val('');
        $('#longitude').val('');
    });
}

function AddMapModal() {

    $('#addMapButton').show();
    $('#editMapButton').hide();
    $('#addMapsModalLabel').show();
    $('#editMapsModalLabel').hide();

    $('#addMapsModal').modal('show');
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