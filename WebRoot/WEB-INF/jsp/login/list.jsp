<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html>
<HTML>
<HEAD>
	<TITLE> ZTREE DEMO - beforeEditName / beforeRemove / onRemove / beforeRename / onRename</TITLE>
	<meta http-equiv="content-type" content="text/html; charset=UTF-8">
	
	<script type="text/javascript" src="${ctx}/UI/js/jquery-1.9.1.js"></script>

</HEAD>

<BODY>

	<c:forEach var="list" items="${notList}">
		<div><h3>${list.name }</h3></div>
		
		<div>
			<a href="${list.url}">${list.url}</a>
		</div>
	</c:forEach>
	
	
	<%-- ===============================1========================================
	
	<c:forEach var="map" items="${mapList}">
		<div><h3>${map.value }</h3></div>
		
		<div>
			<a href="${map.key}">${map.key}</a>
		</div>
	</c:forEach> --%>
	
	
	

	
	
	
	
	
	
	
	
	
	
	
	
	
	
</BODY>
</HTML>