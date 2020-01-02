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


</head>
<body class="layui-layout-body">
<div class="layui-layout layui-layout-admin">
	<div class="layui-header">
		<div class="layui-logo">JingMingHao</div>
		<ul class="layui-nav layui-layout-left">
			<li class="layui-nav-item"><a href="javascript:;" class="_main">首页</a></li>
		</ul>
		<%--<ul class="layui-nav layui-layout-right">
			<li class="layui-nav-item">
				<a href="javascript:;">
					<img src="<%=basePath%>pt/img/150x150/01.jpg" class="layui-nav-img">
					<span class="userNick"></span>
				</a>
				<!-- <dl class="layui-nav-child"></dl> -->
			</li>
			<li class="layui-nav-item"><a href="javascript:;"
										  class="safety_out">安全退出</a></li>
		</ul>--%>
	</div>

	<div class="layui-side layui-bg-black">
		<div class="layui-side-scroll">
			<!-- 左侧导航区域（可配合layui已有的垂直导航） -->
			<ul class="layui-nav layui-nav-tree" lay-filter="test">
				<li class="layui-nav-item layui-nav-itemed"><a class="" href="javascript:;">双色</a>
					<dl class="layui-nav-child">
						<dd>
							<a href="javascript:;" class="doubleList">开奖列表</a>
						</dd>
						<dd>
							<a href="javascript:;" class="double_prestore">预测</a>
						</dd>
					</dl></li>
				<li class="layui-nav-item"><a href="javascript:;">菜单2</a>
					<dl class="layui-nav-child">
						<dd>
							<a href="javascript:;" class="menu_xftj">功能1</a>
						</dd>
						<dd>
							<a href="javascript:;" class="menu_cztj">功能2</a>
						</dd>
					</dl></li>
			</ul>
		</div>
	</div>

	<div class="layui-body">
		<!-- 内容主体区域 -->
		<div style="padding: 15px;">主页面板</div>

		<div class="layui-fluid" style="padding: 20px; background-color: #F2F2F2;">
			<div class="layui-row layui-col-space15">

				<div class="layui-col-sm6 layui-col-md3 ">
					<div class="layui-card">
						<div class="layui-card-header">
							总记录数
						</div>
						<div class="layui-card-body layuiadmin-card-list" style="height: 75px;line-height:60px;">
							<p class="layuiadmin-big-font" style="font-size: 22px">
								<i class="layui-icon "  style=" color:#01AAED;font-size: 20px">&#xe613;</i>
								${mainMember.memberCount }	人
							</p>
						</div>
					</div>
				</div>

				<div class="layui-col-sm6 layui-col-md3 ">
					<div class="layui-card">
						<div class="layui-card-header">
							账户总金额
						</div>
						<div class="layui-card-body layuiadmin-card-list" style="height: 75px;line-height:60px;">
							<p class="layuiadmin-big-font" style="font-size: 22px">
								<i class="layui-icon" style=" color:#5FB878;font-size: 20px">&#xe65e;</i>
								${mainMember.sumMoney }	元
							</p>
						</div>
					</div>
				</div>


				<div class="layui-col-sm6 layui-col-md3 ">
					<div class="layui-card">
						<div class="layui-card-header">
							会员男女比例
						</div>
						<div class="layui-card-body layuiadmin-card-list" style="height: 75px;line-height:32px; font-size: 20px">
							<p class="layuiadmin-big-font" >
								<i class="layui-icon" style="color:#01AAED;font-size: 20px">&#xe662;</i>
								${mainMember.boyCount }	人
							</p>
							<p>
								<i class="layui-icon" style="color:#FF5722;font-size: 20px;">&#xe661;</i>
								${mainMember.grilCount }	人
							</p>
						</div>
					</div>
				</div>

				<div class="layui-col-sm6 layui-col-md3 ">
					<div class="layui-card">
						<div class="layui-card-header">
							账户金额最高
						</div>
						<div class="layui-card-body layuiadmin-card-list" style="height: 75px;line-height:24px; ">

							<p>金额：<span style="color:#5FB878">
						             	 	<i class="layui-icon" >&#xe65e;</i>	${mainMember.maxMoney }	元</span>
							</p>
							<p>用户名：${mainMember.userName }</p>
							<p>手机号：${mainMember.mobile }</p>
						</div>
					</div>
				</div>

				<div class="layui-col-md12">
					<div class="layui-card">
						<div class="layui-card-header">服务升级</div>




						<div class="layui-card-body" style="line-height: 30px;">
							<p><i class="layui-icon" style="color:#5FB878;font-size: 20px;">&#xe678;</i>
								<span>18205149404</span>
							</p>
							<p><i class="layui-icon" style="color:#5FB878;font-size: 20px;">&#xe677;</i>
								<span>kanannijiukui</span>
							</p>
							<p><i class="layui-icon" style="color:#FF5722;font-size: 20px;">&#xe676;</i>
								<span>1138606085</span>
							</p>

						</div>
					</div>
				</div>
			</div>
		</div>

	</div>



	<div class="layui-footer">
		<!-- 底部固定区域 -->
		© jingmh
	</div>
</div>

<jsp:include page="../common/template.jsp"></jsp:include>

<script language="javascript">
	$("#windowPrint").click(function(){
	    var bodyhc=$("body").html();
        //document.body.innerHTML=document.getElementByIdx_x_x('div1').innerHTML+'<br/>'+document.getElementByIdx_x_x('div2').innerHTML;
        document.body.innerHTML= $("#div2").html();

        $("body").css("height","60px");

        window.print();
        document.body.innerHTM = bodyhc ;
	});

</script>

<script>
    //JavaScript代码区域
    layui.use('element', function(){
        var element = layui.element;
    });

    //控制台
    $("._main").click(function(){
        window.location.href="<%=basePath%>";
    });
    //双色球列表
    $(".doubleList").click(function(){
        $(".layui-body").load("<%=basePath%>doubleColorBall/list/page");
    });
    //预存
    $(".double_prestore").click(function(){
        $(".layui-body").load("<%=basePath%>doubleColorBall/prestore/page");
    });
    $(".menu_xftj").click(function(){
        wkf("");
    });
    $(".menu_cztj").click(function(){
        wkf("");
    });
    function wkf(obj){
        $(".layui-body").html("<div style='font-size:25px;color: #c2c2c2;padding-left: 40%;padding-top: 10%;'>"+obj+"功能暂未开放</div>");
    }

</script>

</body>
</html>