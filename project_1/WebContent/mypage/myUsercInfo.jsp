<%@page import="java.text.SimpleDateFormat"%>
<%@page import="gongmo.GongmoDAO"%>
<%@page import="java.util.List"%>
<%@page import="web.gongmo.model.signupDTO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>기업회원 정보</title>
</head>
<%
 String strReferer = request.getHeader("referer");
 
 if(strReferer == null){
%>
 <script>
  alert("URL 주소창에 주소를 직접 입력해서 접근하셨습니다. 정상적인 경로를 통해 다시 접근해 주십시오.");
  document.location.href="../main/main.jsp";
 </script>
<%
  return;
 }
%>
<%
	//페이징 처리
	int pageSize = 5;
	
	// 현재 페이지 번호
	String pageNum = request.getParameter("pageNum");
	if(pageNum == null) {
		pageNum = "1";
	}
	
	// 현재 페이지에 보여줄 게시글 시작과 끝
	int currentPage = Integer.parseInt(pageNum);
	int startRow = (currentPage - 1) * pageSize + 1;
	int endRow = currentPage * pageSize;
	
	int number = 1;
%>
<body class="container">
<%@ include file="../common/header.jsp" %>
	<jsp:include page="mypageLeft.jsp" />
	<h2>기업회원</h2>
	<hr />
	<h3>기업회원</h3>
	<table border="1" style="border-collapse:collapse";>
		<tr align="center" width="700" height="50">
			<td>No. </td>
			<td>ID</td>
			<td>가입날짜</td>
		</tr>
	<%
	int count = 0;
	List userclist = null;
	GongmoDAO dao = new GongmoDAO();
	signupDTO sdto = null;
	count = dao.getUsernCount();
	
	SimpleDateFormat sdfm = new SimpleDateFormat("yyyy-MM-dd");
	
	if(count > 0) { 
		userclist = dao.getUsercAll(startRow, endRow);

		for(int i = 0; i < userclist.size(); i++) {
			sdto = (signupDTO)userclist.get(i);
			
		%>
		<tr align="center" width="700" height="50">
			<td><%= number++ %></td>
			<td><%=sdto.getUserc_id()%></td>
			<td><%=sdfm.format(sdto.getUserc_reg())%></td>
		</tr>
	<%	}%>
	</table>
	<%-- 페이지 번호 --%>
		<div align="center">
		<%if(count > 0) {
			// 페이지 번호 몇개 지정
			int pageBlock = 5;
			// 총 페이지
			int pageCount = count / pageSize + (count % pageSize == 0 ? 0 : 1);
			// 현재 페이지에서 보여줄 첫번째 페이지 번호
			int startPage = (int)((currentPage-1)/pageBlock) * pageBlock + 1;
			// 현재 페이지에서 보여줄 마지막 페이지 번호
			int endPage = startPage + pageBlock - 1;
			
			if(endPage > pageCount) endPage = pageCount;
			
			if(startPage > pageBlock) { %>
				<a href="myUsercInfo.jsp?pageNum=<%=startPage-pageBlock %>" class="pageNums"> &lt; &nbsp;</a>
			<%}
			
			// 페이지 번호 뿌리기
			for(int i = startPage; i <= endPage; i++) { %>
				<a href="myUsercInfo.jsp?pageNum=<%=i%>" class="pageNum"> &nbsp; <%=i %> &nbsp; </a>
			<%}
			
			// <
			if(endPage < pageCount) { %>
				&nbsp; <a href="myUsercInfo.jsp?pageNum=<%=startPage+pageBlock%>" class="pageNums"> &gt; </a>
			<%}
		}%>
		</div>
	<%}else{ %>
		<h3>회원이 없습니다.</h3>
	<%} %>	
<%@ include file="../common/footer.jsp" %>
</body>
</html>