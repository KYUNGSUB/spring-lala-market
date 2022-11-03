<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ include file="../common/aheader.jsp" %>
<link rel="stylesheet" href="/resources/css/notice/list.css">
<script type="text/javascript">
$(document).ready(function(e) {
	var csrfHeaderName ="${_csrf.headerName}"; 
	var csrfTokenValue="${_csrf.token}";
	window.localStorage.setItem("csrfHeaderName", csrfHeaderName);
	window.localStorage.setItem("csrfTokenValue", csrfTokenValue);
});
</script>
<script type="text/javascript" src="/resources/js/product/list.js"></script>
<div id="main-wrap">
	<aside>
		<h4>상품 관리</h4>
		<ul>
			<li><a href="1
			">공지사항</a>
				<ul>
					<li><a href="#">스타일 숍 공지</a></li>
					<li><a href="#">고객센터 공지</a></li>
				</ul>
			</li>
			<li><a href="2">커뮤니티</a></li>
			<li><a href="3">상품문의</a></li>
			<li><a href="4">1:1 문의</a></li>
			<li><a href="5">상품평</a></li>
			<li><a href="6">자주 하는 질문</a></li>
			<li><a href="7">이벤트</a></li>
			<li><a href="8">허위 상품 접수</a></li>
			<li><a href="9">게시판 카테고리 관리</a></li>
		</ul>
	</aside>
	<section id="main-sec">
		<p class="p1">| 게시판 관리 > 공지사항</p>
		<h2>| 공지사항 리스트</h2>
		<div>
		  <form id="searchForm" action="/notice/list" method="get">
		  	<select name="type">
				<option value="" <c:out value="${pageMaker.cri.type == null?'selected':''}"/>>--</option>
				<option value="T" <c:out value="${pageMaker.cri.type eq 'T'?'selected':''}"/>>제목</option>
				<option value="C" <c:out value="${pageMaker.cri.type eq 'C'?'selected':''}"/>>내용</option>
				<option value="TC" <c:out value="${pageMaker.cri.type eq 'TC'?'selected':''}"/>>제목 + 내용</option>
			</select>
			<input type="text" name="keyword" size="40" placeholder="내용을 입력하세요.">
			<input type="submit" value="검색">
		  </form>
		</div>
		<div><input type="button" name="write" value="글쓰기"></div>
		<div id="pArea">
		<c:if test="${empty list}">
			<div>
				<p>등록된 공지사항이 없습니다.</p>
			</div>
		</c:if>
		<c:if test="${!empty list}">
			<div>
				<table>
					<tr>
						<th>번호</th>
						<th>제목</th>
						<th>작성일</th>
					</tr>
				<c:forEach var="notice" items="${list}">
					<tr>
						<td>${notice.nid}</td>
						<td>${notice.title}</td>
						<td><fmt:formatDate value="${notice.regDate}" type="date"></fmt:formatDate></td>
					</tr>
				</c:forEach>
				</table>
			</div>
			<div>
				<p id="pagingArea">
				<c:if test="${pageMaker.prev}">
					<a href="${pageMaker.startPage - 1}">&lt;이전</a>&nbsp;|
				</c:if>
			<c:forEach begin="${pageMaker.startPage}" end="${pageMaker.endPage}" var="num">
				<c:if test="${num == pageMaker.cri.pageNum}">
					<a class="on" href="${num}">[${num}]</a>
				</c:if>
				<c:if test="${num != pageMaker.cri.pageNum}">
					<a href="${num}">[${num}]</a>
				</c:if>
			</c:forEach>
				<c:if test="${pageMaker.next}">
					| <a href="${pageMaker.endPage + 1}">다음&gt;</a>
				</c:if>
				</p>
			</div>
		</c:if>
		</div>
	</section>
</div>
<%@ include file="../common/afooter.jsp" %>