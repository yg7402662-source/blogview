<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ include file="/include/header.jsp" %>

<main class="write-wrap">

	<h2 class="write-page-title">글쓰기</h2>
	<form name="my" id="writeForm" class="write-form"
	method="post" 
	enctype="multipart/form-data"
	action="${pageContext.request.contextPath}/blog/write">
	
		<div class="form-row">
			<label for="writer">작성자</label>
			<p class="write-author">
			<input type="text" id="writer" 
				class="form-input" value="${sessionScope.loginMember.nickname}" readonly></p>
		</div>
	
		<div class="form-row">
			<label for="title">제목</label>
			<input type="text" id="title" name="title"
				placeholder="제목을 입력하세요" class="form-input">
		</div>
		
		<!-- 파일 입력 -->
		<div class="form-row">
			<label>대표이미지<span class="form-option">(선택)</span></label>
			<div class="image-upload-box">
				<img id="imgPreview" 
				src="${pageContext.request.contextPath}/images/default-thumb.jpg" 
				alt="이미지 미리보기" class="img-preview">
				<div class="image-upload-btns">
					<label for="imgFile" class="btn btn-outline file-ladel">
						이미지 선택
					</label>
					<input type="file" id="imgFile" name="imgFile" accept="image/*" class="file-input">
					<button type="button" id="imgRemovebtn" class="btn btn-outline">
						이미지 제거
					</button>
				</div>
			</div>
		</div>
		
		<!-- 내용 입력 -->
		<div class="form-row">
			<label for="content">내용</label>
			<textarea rows="12" id="content" name="content" 
			placeholder="내용을 입력하세요" class="form-textarea"></textarea>
		</div>
		
		<!-- 등록 버튼 -->
		<div class="write-btns">
			<a href="${pageContext.request.contextPath}/board/list" 
			class="btn btn-outline">취소</a>
			<button type="submit" class=" btn btn-primary">등록</button>
		</div>
	</form>

</main>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
<script src="${pageContext.request.contextPath}/js/write.js"></script>
<%@ include file="/include/footer.jsp" %>
