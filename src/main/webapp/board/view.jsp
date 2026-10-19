<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>


	<!-- header.jsp -->
	<%@ include file="/include/header.jsp" %>
	

	
	
	<main class="view-wrap">
		<div class="view-header">
			<h2 class="view-title">${view.title}</h2>
			<p class="view-meta">작성자 ${view.writerNickname} | ${view.regDate} | 조회수  ${view.viewCnt}</p>
		</div>
		<div class="view-image">
			<img src="/imgs/${view.imgPath}" alt="" class="view-thum">
		</div>
		<div class="view-content">
			${view.content}
		</div>
		<div class="view-actions">
			<button type="button" id="likeBtn" class="btn btn-outline like-btn">
				♥ 좋아요 <span id="likeCount"> ${view.likeCnt}</span>
			</button>
			<div class="view-actions-right">
				<a href="${pageContext.request.contextPath}/blog/update?bno=${view.bno}" class="btn btn-outline">수정</a>
				<a href="${pageContext.request.contextPath}/blog/delete?bno=${view.bno}" class="btn btn-outline" onclick="return confirm('삭제 하시겠습니까?')">삭제</a>
				<a href="${pageContext.request.contextPath}/blog/list" class="btn btn-primary">목록</a>
			</div>
		</div>
			<!-- 댓글 영역 -->
			<div class="reply-section">
				<h3 class="reply-title">댓글 <span id="replyCount">${fn:length(replyList)}</span></h3>
				<ul class="reply-list" id="replyList">
				<c:forEach var="r" items="${replyList}">
					<li class="reply-item" data-rno="${r.rno}">
						<div class="reply-info">
							<span class="reply-writer">${r.nickName}</span>
							<span class="reply-data">${r.regDate}</span>
						</div>
						<p class="reply-content">${r.content}</p>
						<div class="reply-btns">
							<button type="button" class="reply-edit-btn">수정</button>
							<button type="button" class="reply-delete-btn">삭제</button>
						</div>
					</li>
					</c:forEach>
				</ul>
				<div class="reply-form">
					<input type="text" id="replyContent" placeholder="댓글을 입력하세요" class="reply-input">
					<button type="button" id="replySubmitBtn" class="btn btn-primary">등록</button>
				</div>
			</div>
		
		
		
			
	</main>
	
	<!-- Modal -->
<div class="modal fade" id="replyModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <h1 class="modal-title fs-5" id="exampleModalLabel">댓글 수정</h1>
        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
      </div>
      <div class="modal-body">
        <input type="hidden" id="modalRno">
        <textarea id="modalContent" class="form-control" row="4"></textarea>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
        <button type="button" class="btn btn-primary" id="modalSaveBtn">Save changes</button>
      </div>
    </div>
  </div>
</div>
	
   <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
   <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/js/bootstrap.bundle.min.js"></script>
   
   <script>
	//jquery
		//------좋아요 버튼 처리-------
		$("#likeBtn").on("click",function(){
			const bno=${view.bno};
		
		$.ajax({
		    url: "${pageContext.request.contextPath}/blog/like",
		    type: "post",
		    data: { bno: bno },
		    dataType: "json",
		    success: function(result) {
		        if (!result.success) {
		            alert(result.message);
		            return;
		        	}

		        	$("#likeCount").text(result.likeCount);
		        	$("#likeBtn").toggleClass("liked", result.liked);
		    	},
		    	error: function() {
		        alert("통신에러");
		    	}
			});
		});
		
		//--------댓글 등록-------
		$("#replySubmitBtn").on("click",function(){
			const content = $("#replyContent").val().trim();
			if(content==''){
				alert("댓글 내용을 입력하세요");
				return;
			}
			
			$.ajax({
				url:"${pageContext.request.contextPath}/reply/write",
				type:'post',
				data:{bno:${view.bno}, content:content},
				dataType:'json',
				success:function(result){
					if(!result.success){
						alert(result.message || '댓글 등록 식패');
						return
					}
					//
					//'혼따옴표'가 아니라 `백틱`이다
					const newItem=`
			   			<li class="reply-item" data-rno="\${result.rno}">
						<div class="reply-info">
							<span class="reply-writer">\${result.writerNickname}</span>
							<span class="reply-data">방금 전</span>
						</div>
						<p class="reply-content">\${result.content}</p>
						<div class="reply-btns">
							<button type="button" class="reply-edit-btn">수정</button>
							<button type="button" class="reply-delete-btn">삭제</button>
						</div>
					</li>
			   		`;
			   		$("#replyList").append(newItem);
			   		$("#replyContent").val('');
			   		//
				},error:function(){
					alert("댓글 등록 중 오류가 발생했습니다");
				}
			})
			
			
	   		/*실제연동 시 비동기식으로 데이터를 처리 할 거임
	   		$.ajax({
	   			
	   		})*/
	   		
		}); 
	
	//------댓글 삭제------
	$("#replyList").on("click",'.reply-delete-btn',function(){
		if(confirm('댓글을 삭제할까요?')){
			//실제 연동 시 rno값을 data-rno에서 꺼내서 비동기식으로 처리한다
			const $item = $(this).closest('.reply-item'); //클릭한 li 요소 
			const rno = $item.data('rno'); //li 태그의 data-rno 속석 값
			
			$.ajax({
				url:"${pageContext.request.contextPath}/reply/delete",
				type:'post',
				data: {rno : rno}, 
				datatype: 'json', //서버에서 받는 데이터는 반드시 json형식으로 방음
				success:function(result){
					if(result.success){
						$item.remove(); //실제로 서버에서 지워졌으면 화면에서 제거
					}else{
						alert(result.message);
					}
				}, error:function(){
					alert("통신 오류")
				}
				
			})
		}
	})
	
	//------댓글 수정------
	const replyModal = new bootstrap.Modal(document.getElementById('replyModal'));
	document.getElementById('replyModal').addEventListener('show.bs.modal',function(){
		$('#modalContent').focus();
	})
	$("#replyList").on("click",'.reply-edit-btn',function(){
		const $item=$(this).closest('.reply-item');
		$('#modalRno').val($item.data('rno'));
		$('#modalContent').val($item.find('.reply-content').text());
		replyModal.show();
	});
	
	$('#modalSaveBtn').on('click',function(){
		const rno = $('#modalRno').val();
		const newContent = $('#modalContent').val().trim();
		if(newContent == ''){
			alert('댓글 내용을 입력하세요');
			return;
		}
		
		$.ajax({
			url:'${pageContext.request.contextPath}/reply/modify',
			type: 'post',
			data:{rno:rno, content:newContent},
			dataType:'json',
			success:function(result){
				if( result.success){
					$('#replyList .reply-item[data-rno="'+rno+'"]')
						.find(".reply-content").text(result.content);
					replyModal.hide();
				}else{
					alert(result.message);
				}
			},error:function(){
				alert("통신실패");
			}
		})
	})
	
		
		
   //자바스크립트
   		/* document.getElementById("likeBtn").addEventListener("click",function(){
   		//document에 있는 get가져와라 Element요소 ById 아이디를 ()안의. 
   		//addEventListener이벤트가 일어나면 click이라는. function실행해라 ()안의.
   			let likeBtn = this; //this:likeBtn
   			likeBtn.classList.toggle("liked");
   			//classList:likeBtn의 구현 부분
   			//toggle:liked 클래스가 없으면 추가하고 있으면 제거한다
   			let count = parseInt(document.getElementById("likeCount").textContent);
   			//likecount 라는 span을 가져오는데, 문자열이니까 parseInt로 바꿔서 숫자열로 가져옴
   			//parseInt:자바스크립트에서 문자열을 숫자열로 바꿀 때 사용
   			if(likeBtn.classList.contains('liked')){
   				count = count + 1;
   				document.getElementById('likeCount').textContent = count;
   			}else{
   				count = count - 1;
   				document.getElementById('likeCount').textContent = count;
   			}
   		}) */
   		
   		//'혼따옴표'가 아니라 `백틱`을 사용
   		
   		
   		
   </script>
   
   <%@ include file="/include/footer.jsp" %>
   
	
    
    
    
    
    