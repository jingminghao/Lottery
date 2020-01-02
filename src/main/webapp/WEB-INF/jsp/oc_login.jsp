<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<title>登录页</title>
	<meta name="description" content="particles.js is a lightweight JavaScript library for creating particles.">
	<meta name="author" content="Vincent Garreau" />
	<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">
	<link rel="stylesheet" media="screen" href="<%=basePath%>pt/login/style.css">
	<link rel="stylesheet" type="text/css" href="<%=basePath%>pt/login/reset.css"/>
</head>
<body>

<div id="particles-js">
	<div class="login">
		<div class="login-top">
			登录
		</div>
		<div class="login-center clearfix">
			<div class="login-center-img"><img src="<%=basePath%>pt/login/img/name.png"/></div>
			<div class="login-center-input">
				<input id="userName" type="text"  name="" value="" placeholder="请输入您的用户名" onfocus="this.placeholder=''" onblur="this.placeholder='请输入您的用户名'"/>
				<div class="login-center-input-text">用户名</div>
			</div>
		</div>
		<div class="login-center clearfix">
			<div class="login-center-img"><img src="<%=basePath%>/pt/login/img/password.png"/></div>
			<div class="login-center-input">
				<input id="password" type="password" name=""value="" placeholder="请输入您的密码" onfocus="this.placeholder=''" onblur="this.placeholder='请输入您的密码'"/>
				<div class="login-center-input-text">密码</div>
			</div>
		</div>
		<div class="login-center clearfix">
			<span class="_error"></span>
		</div>
		<div class="login-button">
			登陆
		</div>
	</div>
	<div class="sk-rotating-plane"></div>
</div>

<!-- scripts -->
<script src="<%=basePath%>pt/login/particles.min.js"></script>
<script src="<%=basePath%>/pt/login/app.js"></script>
<script src="<%=basePath%>/pt/plugin/jquery/jquery.min.js" type="text/javascript"></script>
<script type="text/javascript">
    $("._error").text("");
    function hasClass(elem, cls) {
        cls = cls || '';
        if (cls.replace(/\s/g, '').length == 0) return false; //当cls没有参数时，返回false
        return new RegExp(' ' + cls + ' ').test(' ' + elem.className + ' ');
    }

    function addClass(ele, cls) {
        if (!hasClass(ele, cls)) {
            ele.className = ele.className == '' ? cls : ele.className + ' ' + cls;
        }
    }

    function removeClass(ele, cls) {
        if (hasClass(ele, cls)) {
            var newClass = ' ' + ele.className.replace(/[\t\r\n]/g, '') + ' ';
            while (newClass.indexOf(' ' + cls + ' ') >= 0) {
                newClass = newClass.replace(' ' + cls + ' ', ' ');
            }
            ele.className = newClass.replace(/^\s+|\s+$/g, '');
        }
    }
    document.querySelector(".login-button").onclick = function(){
        var userName=$("#userName").val();
        var password=$("#password").val();
        if(userName==""){
            $("._error").text("账号不能为空！");
            return;
        }
        if(password==""){
            $("._error").text("密码不能为空！");
            return;
        }
        $.post("<%=basePath%>login/loginAuthorization",{"userName":userName,"password":password},function(res){
            if(res.status=="SUCCESS"){
                window.location.href="<%=basePath%>login/loginPage";
                return;
            }
            $("._error").text(res.msg);

        });

    }
</script>
</body>
</html>