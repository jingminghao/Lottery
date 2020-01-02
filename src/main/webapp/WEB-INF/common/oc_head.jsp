<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<style type="text/css">
	.brandcode{
		position: fixed;
		width: 1200px;
		height: 30px;
		text-align: center;
		padding-top: 10px;
		font-size: 18px;
		color: #44b549;
	}
</style>
<div class="head">
	<div class="head-box">
		<div class="inner clearfix">
			<div class="logo">
				<i class="ico_logo m-r-15"></i>
				<a title="全渠道 CRM平台" href="#"></a>
			</div>
			<div class="brandcode">
				<%-- No.${sessionScope.BRANDCODE }		${sessionScope.BRANDNANE } --%>
			</div>
			<div class="account">
				<div class="account-info">
                    <img src="<%=basePath %>pt/images/11.png" class="fl" />
                    <div class="fl m-r-20">
                    	<span class="account-info-spanblock">
                        	<span class="time_01"></span>
                           	<span>${USERLOGINNAME}</span>
                        </span>
                        <span class="font-reset-12 account-info-spanblock">
                        	<a href="http://118.31.22.218/content" title="登陆ICO" target="_Blank"><span>登陆ICO</span></a>
                        </span>
                        <!--
                        <span class="font-reset-12 account-info-spanblock">
                            	短信：<span>${MESSAGE.currentCount}</span>条
                            <a class="red" style="text-decoration:underline;" title="短信充值" href="../application/flowSetPage.do?msgType=0"><span>(充值)</span></a>
                        </span>
                        -->
                    </div>
                    <div class="fl m-l-20 text-right">
                    	<span class="account-info-spanblock">
                            <!-- <i class="icon-inbox"></i> -->
                            <a><span  class="account-editPsd">修改密码</span></a>
                            <a href="javascript:loginOut();">
                            	<span  class="account-logout">退出</span>
                            </a>
                        </span>

                        <!--
                        <span class="font-reset-12 account-info-spanblock">
                            	邮件：<span>${MAIL.currentCount}</span>封
                            <a class="red" style="text-decoration:underline;" title="邮件充值" href="../application/flowSetPage.do?msgType=1"><span>(充值)</span></a>
                        </span>
                        -->
                    </div>
                </div>
                <script type="text/javascript">
                  	function loginOut(){
                  		if(confirm('是否确定退出，返回登录页？')){

                  			location.href='http://116.62.159.204:9080/cas/logout?service=http://116.62.159.142:18080/';
						}
					}
				</script>
			</div>
		</div>
	</div>
</div>
<!--修改密码-->
<div id="editPsd" class='mask'>
    <div class="dialog_wrp ui-draggable " style="width: 500px; margin-left: -250px;">
        <div id="wxDialog_1" class="dialog">
            <div class="dialog_hd">
                <h3>修改密码</h3>
                <a href="javascript:;" class="pop_closed">关闭</a>
            </div>
            <div class="dialog_bd">
                <div class="row">
                    <form class="text-center">
                        <div class="form-group form-inline">
                            <label class="w80 text-right">原密码</label>
                            <input type="password" class="borderHighlight form-control old-psd">
                        </div>
                        <div class="form-group form-inline">
                            <label class="w80 text-right">新密码</label>
                            <input type="password" class="borderHighlight form-control new-psd">
                        </div>
                        <div class="form-group form-inline">
                            <label class="w80 text-right">确认新密码</label>
                            <input type="password" class="borderHighlight form-control new-psd-again">
                        </div>
                    </form>
                </div>
            </div>
            <div class="dialog_ft">
                <button class="btn btnBlue account-editPsd-save">保存</button>
                <button class="btn dialog_cancel">取消</button>
            </div>
        </div>
    </div>
</div>


<!--商户中心-->
<div id="userCenter" class='mask'>
    <div class="dialog_wrp ui-draggable " style="width: 814px; margin-left: -407px;">
        <div class="dialog">
            <div class="dialog_hd">
                <h3>用户中心</h3>
                <a href="javascript:;" class="pop_closed">关闭</a>
            </div>
            <div class="dialog_bd">
                <div class="title-tab">
                    <ul class="clearfix">
                        <li class="tab-nav selected" data-content="userInfo">
                            <a>商户信息</a>
                        </li>
                        <li class="tab-nav" data-content="userImg">
                            <a>商户Logo</a>
                        </li>
                    </ul>
                </div>
                <div class="userMainbox userInfo">
                	<div class="input-box">
                        <span class="input-text">商户名称:</span>
                        <input type="text" value="1213131231" readonly>
                    </div>
                	<div class="input-box">
                        <span class="input-text">商户昵称:</span>
                        <input type="text" value="gvqe4hg5h" name="userName" readonly>
                        <input type="hidden" value="gvqe4hg5h" id="userName">
                        <span class="name-edit">编辑</span>
                        <span class="name-cancel ng-hide">取消</span>
                    </div>
                    <div class="input-box">
                        <span class="input-text">注册手机:</span>
                        <input type="text" value="15468416545" readonly>
                    </div>
                    <div class="input-box">
                        <span class="input-text">注册邮箱:</span>
                        <input type="text" value="15468416545" readonly>
                    </div>
                    <div class="input-box">
                        <span class="input-text">商户Code:</span>
                        <input type="text" value="1213131231" readonly>
                    </div>
                    <div class="input-box">
                        <span class="input-text">商户KEY:</span>
                        <input type="text" value="1213131231" readonly>
                    </div>

                </div>
                <div class="userMainbox clearfix userImg" style="display: none">
                    <div class="upload-left fl">
                        <p>选择上传方式</p>
                        <button class="btn btnBlue m-t-20 m-b-20 file-btn" >
                            <img src="<%=basePath %>pt/images/user_upload.png" class="m-r-5">本地上传
                            <input type="file" id="file" value="选择图片文件" accept="image/*" />
                        </button>
                        <p class="upload-tip">仅支持jpg，png，gif格式，文件小于5M</p>
                    </div>
                    <div class="upload-right fr">
                        <div class="box">
                            <img src="<%=basePath %>pt/images/user_default_img.png" >
                        </div>
                        <div class="cropBox" style="display: none">
                            <div class="imageBox">
                                <div class="thumbBox"></div>
                                <div class="spinner" style="display: none">Loading...</div>
                            </div>
                            <div class="action clearfix m-t-20">
                                <button class="btn btnBlue" id="btnCrop" >保存</button>
                            </div>
                            <div class="cropped" style="display:none;"></div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<script type="text/javascript">
	now = new Date(), hour = now.getHours();
	var msg = "";
	if (hour < 6) {
		msg = "凌晨好！";
	} else if (hour < 9) {
		msg = "早上好！";
	} else if (hour < 12) {
		msg = "上午好！";
	} else if (hour < 14) {
		msg = "中午好！";
	} else if (hour < 17) {
		msg = "下午好！";
	} else if (hour < 19) {
		msg = "傍晚好！";
	} else if (hour < 22) {
		msg = "晚上好！";
	} else {
		msg = "夜里好！";
	};
	$(".time_01").html(msg);

	//修改密码
	$(".account-editPsd").click(function(){
		$.dialogModal("editPsd")
	})

	//修改密码 保存
	$(".account-editPsd-save").click(function(){
		if(!$.trim($(".old-psd").val())){
			alert("请输入原密码");
			return  false;
		}
		if(!$.trim($(".new-psd").val())){
			alert("请输入新密码");
			return  false;
		}
		if(!$.trim($(".new-psd-again").val())){
			alert("请确认新密码");
			return  false;
		}
		if($(".new-psd").val()!=$(".new-psd-again").val()){
			alert("两次新密码输入不一致，请重新输入！");
			return  false;
		}
		var param="oldPwd="+$(".old-psd").val()+"&newPwd="+$(".new-psd").val();
		$.post("<%=path%>/login/updateUserPassword.do",
				param,
				function(res){
				if(res.status=="SUCCESS"){
					alert("修改成功");
				}else{
					alert(res.msg)
				}
		});

		$.dialogModal("editPsd").hide();
	})

</script>