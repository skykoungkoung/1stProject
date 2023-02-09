<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	// 좋아요 처리 
	// DAO~~~ 
	System.out.println(request.getParameter("number"));
	//Dao 가서 좋아요 처리하고 
	int result = 1;
%>
<%if(result == 1){ 
	out.println(1);
}else {
	out.println(2);
}
%>