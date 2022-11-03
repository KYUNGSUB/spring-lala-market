<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<%@ include file="../common/aheader.jsp" %>
<link rel="stylesheet" href="/resources/css/product/register.css">
<script type="text/javascript" src="/resources/smarteditor/js/service/HuskyEZCreator.js" charset="utf-8"></script>
<script type="text/javascript" src="/resources/js/product/register.js"></script>
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
		<p class="p1">| 상품 관리 > 상품 등록</p>
		<h2>| 상품 등록</h2>
	<form action="register" method="post" enctype="multipart/form-data">
		<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
		<input type="hidden" name="category1" value="스타일 숍">
		<input type="hidden" name="userid" value='<sec:authentication property="principal.username"/>'>
		<h3>| 상품 정보 입력</h3>
		<div>
			<select name="category2">
			<c:forEach var="category" items="${cList}">
				<option value="${category.name}">${category.name}</option>
			</c:forEach>
			</select>
			<input type="text" name="name" size="80" placeholder="상품명을 입력하세요.">
		</div>
		<table>
			<tr>
				<th>판매 가격</th>
				<td><input type="text" size="12" name="salePrice">&nbsp;원</td>
				<th>정상 가격</th>
				<td><input type="text" size="12" name="price">&nbsp;원</td>
				<th>최대 구매 개수</th>
				<td><input type="text" size="6" name="maxPurchase">&nbsp;개</td>
			</tr>
			<tr>
				<th>적립 포인트</th>
				<td colspan="5">
					<label><input type="radio" name="point" value="basic" checked="checked">기본 포인트 적용&nbsp;</label>
					<label style="margin-left: 15px;"><input type="radio" name="point" value="apart">별도 포인트 적용&nbsp;</label>
					<span style="margin-right: 15px;">: 판매 가격의 <input type="text" size="10" name="deposit" disabled="disabled">&nbsp;%&nbsp;</span>
					<label><input type="radio" name="point" value="none">포인트 없음</label>
				</td>
			</tr>
			<tr>
				<th>배송비</th>
				<td colspan="5">
					<label><input type="radio" name="fee" value="basic" checked="checked">기본 배송비 적용&nbsp;</label>
					<label style="margin-left: 15px;"><input type="radio" name="fee" value="apart">별도 배송비 적용&nbsp;</label>
					<span style="margin-right: 15px;">: <input type="text" size="10" name="delivery" disabled="disabled">&nbsp;원&nbsp;</span>
					<label style="margin-left: 89px;"><input type="radio" name="fee" value="none">무료 배송</label>
				</td>
			</tr>
			<tr>
				<th>상품 특성</th>
				<td colspan="5">
					<label style="margin-right: 25px;"><input type="checkbox" name="feature" value="newp" checked="checked">&nbsp;신상품</label>
					<label style="margin-right: 25px;"><input type="checkbox" name="feature" value="best">&nbsp;BEST</label>
					<label><input type="checkbox" name="feature" value="discount">&nbsp;할인</label>
				</td>
			</tr>
			<tr>
				<th>
					<div style="text-align: left;">
						<p style="text-align: center;">상품 정보</p>
						<label style="text-align: left;"><input type="radio" name="infoBtn" value="no" checked="checked">미사용</label><br>
						<label><input type="radio" name="infoBtn" value="yes">사용</label>
					</div>
				</th>
				<td colspan="5">
					<input style="width: 50px;" type="button" name="infoadd" value="추가">&nbsp;
					<input style="width: 50px;" type="button" name="infodel" value="삭제"><br>
					<div id="infoArea"></div>
				</td>
			</tr>
			<tr>
				<th>
					<div style="text-align: left;">
						<p style="text-align: center;">옵션</p>
						<label style="text-align: left;"><input type="radio" name="optionBtn" value="no" checked="checked">미사용</label><br>
						<label><input type="radio" name="optionBtn" value="yes">사용</label>
					</div>
				</th>
				<td colspan="5">
					<input style="width: 50px;" type="button" name="optadd" value="추가">&nbsp;
					<input style="width: 50px;" type="button" name="optdel" value="삭제"><br>
					<div id="optionArea"></div>
				</td>
			</tr>
		</table>
		<h3>| 상품 소개글</h3>
		<p style="margin-top: 5px;"><input type="text" size="114" maxlength="25" name="introduction" placeholder="25자 이내로 소개글을 입력하세요."></p>
		<!-- Tab links -->
		<div class="tab tab1">
			<input type="button" class="tablinks1" data-oper="t1PC" value="PC">
			<input type="button" class="tablinks1" data-oper="t1Mobile" value="Mobile">
		</div>
		<!-- Tab content -->
		<div id="t1PC" class="tabcontent tabcontent1">
			<table>
				<tr>
					<td style="width: 30%; text-align: right;">
						리스트 이미지<br>
						000*000
					</td>
					<td style="width: 70%">
						<input type="file" name="imgList[0]">
					</td>
				</tr>
				<tr>
					<td style="width: 30%; text-align: right;">
						상품 대표 이미지<br>
						000*000
					</td>
					<td style="width: 70%">
						<input type="file" name="imgList[1]"><br>
						<input type="file" name="imgList[2]"><br>
						<input type="file" name="imgList[3]"><br>
						<input type="file" name="imgList[4]">
					</td>
				</tr>
				<tr>
					<td style="width: 30%; text-align: right;">
						메인 노출 이미지<br>
						000*000
					</td>
					<td style="width: 70%">
						<input type="file" name="imgList[5]">
					</td>
				</tr>
			</table>
		</div>
		<div id="t1Mobile" class="tabcontent tabcontent1">
			<table>
				<tr>
					<td style="width: 30%; text-align: right;">
						리스트 이미지<br>
						000*000
					</td>
					<td style="width: 70%">
						<input type="file" name="imgList[6]">
					</td>
				</tr>
				<tr>
					<td style="width: 30%; text-align: right;">
						상품 대표 이미지<br>
						000*000
					</td>
					<td style="width: 70%">
						<input type="file" name="imgList[7]"><br>
						<input type="file" name="imgList[8]"><br>
						<input type="file" name="imgList[9]"><br>
						<input type="file" name="imgList[10]">
					</td>
				</tr>
				<tr>
					<td style="width: 30%; text-align: right;">
						메인 노출 이미지<br>
						000*000
					</td>
					<td style="width: 70%">
						<input type="file" name="imgList[11]">
					</td>
				</tr>
			</table>
		</div>
		<h3>| 상품 상세 정보</h3>
		<!-- Tab links -->
		<div class="tab tab2">
			<input type="button" class="tablinks2" data-oper="ePC" value="PC">
			<input type="button" class="tablinks2" data-oper="eMobile" value="Mobile">
		</div>
		<!-- Tab content -->
		<div id="ePC" class="tabcontent tabcontent2">
			<textarea name="pc_detail" id="pc_detail" rows="10" cols="100" style="width: 820px; height: 200px; display: none"></textarea>
		</div>
		<div id="eMobile" class="tabcontent tabcontent2">
			<textarea name="mobile_detail" id="mobile_detail" rows="10" cols="100" style="width: 820px; height: 200px; display: none"></textarea>
		</div>
		<h3>| 배송 안내</h3>
		<p style="margin-top: 5px;">안내 선택 : <label><input type="radio" name="dguide" value="common" checked="checked">공통 배송 안내 노출</label>&nbsp;
			<label><input type="radio" name="dguide" value="indivisual">개별 배송 안내 작성</label></p>
		<!-- Tab links -->
		<div class="tab tab3">
			<input type="button" class="tablinks3" data-oper="fPC" value="PC">
			<input type="button" class="tablinks3" data-oper="fMobile" value="Mobile">
		</div>
		<!-- Tab content -->
		<div id="fPC" class="tabcontent tabcontent3">
			<textarea name="pc_delivery" id="pc_delivery" rows="10" cols="100" style="width: 820px; height: 200px; display: none"></textarea>
		</div>
		<div id="fMobile" class="tabcontent tabcontent3">
			<textarea name="mobile_delivery" id="mobile_delivery" rows="10" cols="100" style="width: 820px; height: 200px; display: none"></textarea>
		</div>
		<h3>| 교환 및 반품 안내</h3>
		<p style="margin-top: 5px;">안내 선택 : <label><input type="radio" name="exchange" value="common" checked="checked">공통 교환 및 반품 안내 노출</label>&nbsp;
			<label><input type="radio" name="exchange" value="indivisual">개별 교환 및 반품 안내 작성</label></p>
		<!-- Tab links -->
		<div class="tab tab4">
			<input type="button" class="tablinks4" data-oper="gPC" value="PC">
			<input type="button" class="tablinks4" data-oper="gMobile" value="Mobile">
		</div>
		<!-- Tab content -->
		<div id="gPC" class="tabcontent tabcontent4">
			<textarea name="pc_exchange" id="pc_exchange" rows="10" cols="100" style="width: 820px; height: 200px; display: none"></textarea>
		</div>
		<div id="gMobile" class="tabcontent tabcontent4">
			<textarea name="mobile_exchange" id="mobile_exchange" rows="10" cols="100" style="width: 820px; height: 200px; display: none"></textarea>
		</div>
		<div>
			<div class="box" style="float:left;">
				<h3>| 상품 이력 관리</h3>
				<textarea name="history" rows="6" cols="54" disabled="disabled"></textarea>
			</div>
			<div class="box" style="float: right;">
				<h3>| 상품 메모</h3>
				<textarea name="memo" rows="6" cols="54"></textarea>
			</div>
		</div>
		<div style="clear: both">
			상품 노출 여부 : 
			<label><input type="radio" name="expose" value="진열" checked="checked">진열</label>&nbsp;
			<label><input type="radio" name="expose" value="숨김">숨김</label>&nbsp;
			<label><input type="radio" name="expose" value="품절">품절</label>
		</div>
		<div id="submitBtn">
			<input type="submit" value="등록" data-oper="register">&nbsp;
			<input type="submit" value="취소" data-oper="cancel">
		</div>
	</form>
	</section>
</div>
<%@ include file="../common/afooter.jsp" %>