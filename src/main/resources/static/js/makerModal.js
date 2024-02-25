function modalOn(positions) {
    $.ajax({
        url: "/board/marker/" + positions.markerId,
        method: "GET",
        dataType: "json",
        success: function(data) {
            $('.marker-title').empty(); // 기존 목록 초기화
            let title = '<h3>'+ positions.name + ' 의 리뷰 목록'+'</h3>';
            $('.marker-title').append(title);
            $('.marker-body').empty(); // 기존 목록 초기화
            $('.marker-modal').css('display', 'block');
            data.forEach(function(markers) {
                let modal = '<div class="col my-2">' +
                    '<a href="/board/' + markers.id + '">' +
                    '<img src="' + markers.boardImgDtoList[0].imgUrl + '" class="card-img-top rounded" alt="">' +
                    '<div class="card-body">' +
                    '<span class="card-text card-color-gray">' + markers.memberUserid + '</span>' +
                    '<span class="card-l card-color-gray">' + "|" + '</span>' +
                    '<span class="card-text card-color-gray">' + markers.createdAt + '</span>' +
                    '<p class="card-title text-center card-color-black">' + markers.content + '</p>' +
                    '</div>' +
                    '</div>';
                $('.marker-body').append(modal);
            })
        },
        error: function(xhr, status, error) {
            console.error(error);
        }
    });
}
function modalOff() {
    $('.marker-modal').css('display', 'none');
}
// 지도 클릭 이벤트 처리
kakao.maps.event.addListener(map, 'click', function() {
    modalOff();
    for (var j = 0; j < overlays.length; j++) {
        if (overlays[j]) {
            overlays[j].setMap(null);
        }
    }
});