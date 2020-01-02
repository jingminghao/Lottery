<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<jsp:include page="../common/template.jsp"></jsp:include>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="utf-8">
	<title>Lottry</title>
	<meta name="renderer" content="webkit">
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
	<meta name="viewport"
		  content="width=device-width, initial-scale=1, maximum-scale=1">

<body>


<span id='div1'>把要打印的内容放这里</span>
<p>所有内容</p>
<div id="div2">div2的内容</div>
<a href="javascript:printme()" rel="external nofollow" target="_self">打印</a>

<script language="javascript">
    function printme()
    { document.body.innerHTML=document.getElementByIdx_x_x('div1').innerHTML+'<br/>'+document.getElementByIdx_x_x('div2').innerHTML;
        window.print();
    }
</script>

</body>
</html>