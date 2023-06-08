home();

function home() {
	$('.slider').bxSlider();
};

// 좋아요 기능 구현을 위한 js
const toast = new bootstrap.Toast(document.querySelector("#liveToast"));

$("#likeIcon").click(function() {
	// 게시물 번호 request body에 추가
	const feedId = $("#feedIdText").text().trim();
	const data = {feedId};
	
	$.ajax("/like", {
		method: "post",
		contentType: "application/json",
		data: JSON.stringify(data),
		
		success: function(data) {
			if (data.like) {
				$("#likeIcon").html(`<img src="/images/homebone.png">`);
			} else {
				$("#likeIcon").html(`<img src="/images/olhomebone.png">`);
			}
			// 좋아요 수 업데이트
			$("#likeNumber").text(data.count);
		},
		error: function(jqXHR) {
			$(".toast-body").text(jqXHR.responseJSON.message);
			toast.show();
		},
	});
});