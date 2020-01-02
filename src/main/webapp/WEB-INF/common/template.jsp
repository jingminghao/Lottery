<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<link href="<%=basePath%>pt/plugin/layui/css/layui.css" type="text/css" rel="stylesheet" />
<script src="<%=basePath%>pt/plugin/layui/layui.all.js" type="text/javascript"></script>
<script src="<%=basePath%>pt/plugin/jquery/jquery.min.js" type="text/javascript"></script>

<script type="text/javascript">
<%-- function simulateForm(url,tagArray){
	
    var temp = document.createElement("form");        
    temp.action = "<%=basePath %>"+url; 
    temp.method = "post";    
    temp.style.display = "none";
    var opt;
    for(var key in tagArray){
    	 opt = document.createElement("input"); 
    	 opt.name = key;        
	     opt.value = tagArray[key]; 
	     temp.appendChild(opt);
    }
    document.body.appendChild(temp); 
    temp.submit();        
 
}
	 --%>

</script>