$(function(){
	let idChecked = false;
	$("#idCheckBtn").on("click", function(){
		const id = $("#id").val().trim();
		if(id===''){
			alert("아이디를 입력하세요");
			return;
		}
		//실제연동 시 비동기식 방식으로 id존재여부 확인 방법
		$.ajax({
			url: '${pageContext.request.contextPath}/member/idCheck',
			//url: 비동기식으로 데이터를 요청하는 주소
			type:'get',
			//type: 전송 방식
			data:{id:id},
			//data: 전송하는 데이터(키:값) -> 자바 스크립트 객체
			//{"id":"abcd"}
			dataType:'json',
			//되돌아 오는 값은 json형식으로 받음
			success:function(result){
				alert(result.isDupilcate);
				//success: 서버와 통신을 성공하면 함수를 실행
				if(result.isDupilcate){
					$("#idCheckMsg").text('이미 사용중인 아이디 입니다').css('color','#ff3b5c')
				}else{
					$("#idCheckMsg").text('사용가능한 아이디 입니다').css('color','#3bcff2')
				}
			}
			
		})//ajax 
	
	})//idCheck
	//아이디를 수정하면 중복확인 재실시
	$("#id").on('input',function(){
		idChecked=false;
		$("#idCheckMsg").text('');
	})
	//비밀번호 확인 일치 검사
	$("#pwdCheck").on('input',function(){
		const pwd = $("#pwd").val();
		const pwdCheck = $(this).val();
		if(pwdCheck===''){
			$("#pwdCheckMsg").text('');
		}else if(pwd===pwdCheck){
			$("#pwdCheckMsg")
			.text("비밀번호가 일치합니다").css('color','#3b6ff2');
		}else{
			$("#pwdCheckMsg")
			.text("비밀번호가 일치하지 않습니다").css('color','#ff3b5c');
		}
	}) //pwdcheck
	
	//가입하기 전 최종 점검
	$("#joinForm").on('submit',function(e){
		if(!idChecked){
			alert("아이디 중복확인을 하십시오");
			e.preventDefault();
			return;
		}
		if($('#pwd').val()!==$("#pwdCheck").val()){
					alert("비밀번호가 일치하지 않습니다");
					e.preventDefault();
					return;
		}
	})
	
	
	
	
	
	
	
	
	
})//끝