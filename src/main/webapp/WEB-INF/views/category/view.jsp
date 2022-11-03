<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="../common/aheader.jsp" %>
<link rel="stylesheet" href="/resources/css/category/category.css">
<script type="text/javascript">
$(document).ready(function(e) {
	var csrfHeaderName ="${_csrf.headerName}"; 
	var csrfTokenValue="${_csrf.token}";
	window.localStorage.setItem("csrfHeaderName", csrfHeaderName);
	window.localStorage.setItem("csrfTokenValue", csrfTokenValue);
});
</script>
<script type="text/javascript" src="/resources/js/category.js"></script>
<div id="main-wrap">
	<aside>
		<h4>상품 관리</h4>
		<ul>
			<li><a href="1">상품 등록</a></li>
			<li><a href="2">상품 리스트</a></li>
			<li><a href="3">카테고리 관리</a></li>
		</ul>
	</aside>
	<section id="main-sec">
		<p>| 상품 관리 > 카테고리 관리</p>
		<h2>| 스타일 숍 상품 카테고리 관리</h2>
		<h3>| 카테고리 등록/수정/삭제</h3>
		<div>
			<div id="cat_aside">
				<button id="firstBtn">1차 카테고리 생성</button>
			<c:if test="${!empty list}">
				<ul>
				<c:forEach var="first" items="${list}" varStatus="status">
					<li data-name="${first.name}" data-code="${first.code}"
						data-size="${first.list.size() + 1}" data-index="${status.count}">
						<div>
							<a class="expand" href="#">-</a>
							<a class="firstName" href="#">${first.name}</a>
							<span class="upArrow 1st">올리기</span>
							<span class="downArrow 1st">내리기</span>
						</div>
						<hr>
						<ul>
					<c:if test="${!empty first.list}">
						<c:forEach var="second" items="${first.list}" varStatus="sStatus">
							<li data-name="${second.name}" data-code="${second.code}"
								data-parent="${first.code}" data-index="${sStatus.index}">
								<span>- <a href="#" class="secondName">${second.name}</a></span>
								<span class="upArrow 2nd">올리기</span>
								<span class="downArrow 2nd">내리기</span>
							</li>
						</c:forEach>
					</c:if>
						</ul>
						<Button class="secondBtn">2차 카테고리 생성</Button>
					</li>
				</c:forEach>
				</ul>
			</c:if>
				<form action="move" method="post" name="move">
					<input type="hidden" name="step">
					<input type="hidden" name="direction"><!-- up, down -->
					<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
				</form>
			</div>
			<div id="cat_sec">
				<form id="secondId" action="#" method="post" name="secondId">
					<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
					<input type="hidden" name="parent">
					<input type="hidden" name="code">
					<table>
						<colgroup>
							<col style="background-color: #eee">
							<col>
						</colgroup>
						<tr>
							<th>1차 카테고리명</th>
							<td id="fName"></td>
						</tr>
						<tr>
							<th>1차 카테고리 코드</th>
							<td id="fCode"></td>
						</tr>
						<tr>
							<th>2차 카테고리명</th>
							<td><input type="text" name="name">&nbsp;특수문자 입력 불가</td>
						</tr>
						<tr>
							<th>2차 카테고리 코드</th>
							<td>생성 시 자동 부여 됩니다.</td>
						</tr>
						<tr>
							<th>카테고리 노출</th>
							<td>
								<input id="sey" type="radio" name="expose" value="yes" checked="checked">노출&nbsp;&nbsp;
								<input id="sen" type="radio" name="expose" value="no">숨김
							</td>
						</tr>
					</table>
					<div>
						<span>
							<input type="submit" data-oper="add" value="저장">
							<input type="submit" data-oper="modify" value="수정">
							<input type="submit" data-oper="cancel" value="취소">
						</span>
						<span><input type="submit" data-oper="remove" value="삭제"></span>
					</div>
				</form>
				<form id="firstId" action="add" method="post" name="firstId">
					<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
					<table>
						<colgroup>
							<col style="background-color: #eee">
							<col>
						</colgroup>
						<tr>
							<th>카테고리명</th>
							<td><input type="text" name="name">&nbsp; 특수문자 입력 불가</td>
						</tr>
						<tr>
							<th>카테고리 코드</th>
							<td>
								<input type="text" name="code">&nbsp; <input type="button" id="duplicateBtn" value="중복 확인">&nbsp; 영문, 숫자만 입력 가능<br>
								(<span>중복확인 결과 메시지 노출</span>)
							</td>
						</tr>
						<tr>
							<th>카테고리 노출</th>
							<td>
								<input id="fey" type="radio" name="expose" value="yes" checked="checked">노출&nbsp;&nbsp;
								<input id="fen" type="radio" name="expose" value="no">숨김
							</td>
						</tr>
						<tr>
							<th>GNB 노출</th>
							<td>
								<input id="fgy" type="radio" name="gnb" value="yes" checked="checked">진열&nbsp;&nbsp;
								<input id="fgn" type="radio" name="gnb" value="no">미진열
							</td>
					</table>
					<div>
						<span>
							<input type="submit" data-oper="add" value="추가">
							<input type="submit" data-oper="modify" value="수정">
							<input type="submit" data-oper="cancel" value="취소">
						</span>
						<span><input type="submit" data-oper="remove" value="삭제"></span>
					</div>
				</form>
			</div>
		</div>
		<div id="guideP">
			<h3>| 카테고리 등록/수정/삭제 안내</h3>
			<p>
				<b>[1차 카테고리 생성]</b> 왼쪽 상단의 '1차 카테고리 생성' 버튼을 클릭하시면 1차 카테고리명, 카테고리 노출 여부를 설정할 수 있습니다.<br>
				<b>[2차 카테고리 생성]</b> 생성된 1차 카테고리 리스트 하단의 '하위 카테고리 생성' 버튼을 클릭하시면 2차 카테고리명, 카테고리 노출 여뷰를 설정할 수 있습니다.<br>
				<b>[카테고리 수정]</b> 수정하려는 카테고리 선택 시 카테고리를 카테고리명, 카테고리 노출 여부를 수정할 수 있습니다.
			</p>			
		</div>
	</section>	
</div>
<%@ include file="../common/afooter.jsp" %>