<%@page import="gongmo.GongmoDAO"%>
<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.text.DateFormat"%>
<%@page import="java.sql.Timestamp"%>
<%@page import="com.oreilly.servlet.MultipartRequest"%>
<%@page import="com.oreilly.servlet.multipart.DefaultFileRenamePolicy"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<jsp:useBean id="gmboard" class="gongmo.GongmoWriteDTO"/>
<%
	request.setCharacterEncoding("UTF-8");
	String id = (String)session.getAttribute("cid");

	// file은 자바빈으로 불러오기 불가능해서 다 리쿼스토 받아와야한다.
	// 이미지 경로 설정
	String path = request.getRealPath("gongmoFile");
	// 용량 설정
	int max = 1024*1024*5; // 5M
	String enc ="UTF-8";
	DefaultFileRenamePolicy dp = new DefaultFileRenamePolicy();
	MultipartRequest mr = new MultipartRequest(request, path, max, enc, dp);
	
	
	// 관심 목록 문자열로 넣기
	String favor[] = mr.getParameterValues("gmboard_favor");
	String fstr = "";
		for(int i=0;i<favor.length;i++){
			if(i==favor.length-1){
				fstr += favor[i];
			}else{
			fstr+=favor[i]+",";
			}
		}
	System.out.println("favor = "+fstr);
	
	// 참여대상 문자열로 넣기
	String target[] = mr.getParameterValues("gmboard_target");
	String tstr = "";
	for(int i=0;i<target.length;i++){
			if (i==target.length-1){
				tstr += target[i];
			}else{
				tstr+=target[i]+",";
			}
		}
		System.out.println("target = "+tstr);
	
	// 수상혜택 문자열로 넣기
	String benefit[] = mr.getParameterValues("gmboard_benefit");
	String bstr = "";
		for(int i=0;i<benefit.length;i++){
			if(i==benefit.length-1){
				bstr += benefit[i];
			}else{
				bstr+=benefit[i]+",";
			}
		}
	System.out.println("benefit = "+bstr);
		
		gmboard.setGmboard_title(mr.getParameter("gmboard_title"));
		gmboard.setGmboard_favor(fstr);
		gmboard.setGmboard_target(tstr);
		gmboard.setGmboard_winner(mr.getParameter("gmboard_winner"));
		gmboard.setGmboard_benefit(bstr);
		gmboard.setGmboard_money(mr.getParameter("gmboard_money"));
		gmboard.setGmboard_sday(mr.getParameter("gmboard_sday"));
		gmboard.setGmboard_eday(mr.getParameter("gmboard_eday"));
		
		// 0716 Content textarea에서 wrap="hard" 사용으로 널포인트오류잡기위한 코드 
		String changeContent = mr.getParameter("gmboard_content");
		changeContent = changeContent.replace("\r", "<br/>");
		gmboard.setGmboard_content(changeContent);
		//gmboard.setGmboard_content(mr.getParameter("gmboard_content"));
		
		
		
		gmboard.setGmboard_poster(mr.getFilesystemName("gmboard_poster"));
		gmboard.setGmboard_file(mr.getFilesystemName("gmboard_file"));
			
		//공모전 insert
		GongmoDAO dao = new GongmoDAO();
		gmboard = dao.setGmboard(id, gmboard);
		int result = dao.insertGmBoard(gmboard);
		System.out.println("공모전 등록  = "+result);
		if(result==1){
			response.sendRedirect("gongmoList.jsp");
		}else{%>
			<script>
				alert("등록 실패 다시시도해주세요");
				history.go(-1);
			</script>
			
		<%}%>

<body>

</body>
</html>