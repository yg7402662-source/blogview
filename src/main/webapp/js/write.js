$(function(){ //jquery 시작
	//이미지 선택 시 미리보기
	const defaultImg = $("#imgPreview").attr('src');
	$("#imgFile").on("change", function(){
		const file = this.files[0];
		if(!file) return;
		//브라우저 메모리 안에서 임시 URL을 만들어 미리보기에 반영
		const previewUrl = URL.createObjectURL(file);
		$("#imgPreview").attr("src",previewUrl);
		$("#removeImg").val("false"); //제거 눌렀다가 다시 새 파일 선택하면 제거 요청 취소
	})
	//이미지 제거
	$("#imgRemoveBtn").on("click", function(){
		$("#imgFile").val('');
		$("#imgPreview").attr("src",defaultImg);
		$("#removeImg").val('true'); //modify.jsp에만 있는 hidden, write.jsp에는 없다
//첨부파일은 무조건 배열의 형태로 저장이 된다
//그렇기에 그 값을 가져 올 때도 files라는 배열을 이용한다
//attr(''): 값을 가져옮
//attr("", ):값을 대체함
	})
	
	
	$("#writeForm").on('submit', function(e){
		const title = $("#title").val().trim();
		const content = $("#content").val().trim();
		if(title===''){
			alert("제목을 입력하세요");
			e.preventDefault();
			$("#title").focus();
			return;
		}
		if(content==''){
			alert("내용을 입력하세요");
			e.preventDefault();
			$("#content").focus();
			return;
				}
	})
	
	
})
