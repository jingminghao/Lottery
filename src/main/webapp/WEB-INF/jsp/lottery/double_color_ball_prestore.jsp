<%@ page isELIgnored="false" %>
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
    <title>双色预存</title>
    <meta charset="utf-8">
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport"
          content="width=device-width, initial-scale=1, maximum-scale=1">
<style type="text/css">
    .prestoreTable > thead >tr > th{ text-align: center; !important;}
</style>
</head>
<body id="_memberPage" >

<fieldset class="layui-elem-field layui-field-title" style="margin-top: 20px;">
    <legend>双色预测</legend>
</fieldset>

<div style="padding: 20px; background-color: #F2F2F2;">
    <div class="layui-row layui-col-space15">

        <div class="layui-col-md12">
            <div class="layui-card">
                <div class="layui-card-header">最近一期</div>
                <div class="layui-card-body">

                    <div class="layui-form">
                        <table class="layui-table">
                            <thead>
                            <tr>
                                <th>期号</th>
                                <th>红1</th>
                                <th>红2</th>
                                <th>红3</th>
                                <th>红4</th>
                                <th>红5</th>
                                <th>红6</th>
                                <th>蓝</th>
                                <th>时间</th>
                            </tr>
                            </thead>
                            <tbody class="last_data">

                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>

        <div class="layui-col-md12">
            <div class="layui-card">
                <div class="layui-card-header">预存</div>
                <div class="layui-card-body">

                    历史：xxxxx<br/>
                    最新/指定xxxx

                    <div class="layui-form-item layui-form-pane ">
                        <div class="layui-form-item">
                            <label class="layui-form-label _label" >期号</label>
                            <div class="layui-input-inline">
                                <input type="text" name="fPhaseNum" lay-verify="required"
                                       placeholder="请输入期号" autocomplete="off" class="layui-input">
                            </div>
                            <!-- 手机号 -->
                            <label class="layui-form-label _label" >中奖号码</label>
                            <div class="layui-input-inline">
                                <input type="text" name="fBalls" lay-verify="required"
                                       placeholder="如:01,05,19,25,25,33,07" autocomplete="off" class="layui-input">
                            </div>

                            <label class="layui-form-label _label" >开始日期</label>
                            <div class="layui-input-inline">
                                <input type="text" class="layui-input"  placeholder="开奖开始日期" id="fLotteryTimeStart">
                            </div>

                            <label class="layui-form-label _label" >结束日期</label>
                            <div class="layui-input-inline">
                                <input type="text" class="layui-input" placeholder="开奖结束日期" id="fLotteryTimeEnd">
                            </div>


                            <button class="layui-btn _memberSearch_btn" data-type="reload">搜索</button>
                        </div>



                    <div class="layui-form">




                       <%-- <table class="layui-table">
                            <table id="prestore_table" lay-filter="prestoreTableLayFilter" style="overflow-x:scroll"></table>
                        </table>--%>


                           <table class="layui-table prestoreTable" >
                               <thead>
                               <tr>
                                   <th colspan="2" >红1</th>
                                   <th colspan="2" >红2</th>
                                   <th colspan="2" >红3</th>
                                   <th colspan="2" >红4</th>
                                   <th colspan="2" >红5</th>
                                   <th colspan="2" >红6</th>
                                   <th colspan="2" >蓝</th>
                               </tr>
                               <tr>
                                   <th >历史</th>
                                   <th >最新/指定</th>

                                   <th >历史</th>
                                   <th >最新/指定</th>

                                   <th >历史</th>
                                   <th >最新/指定</th>

                                   <th >历史</th>
                                   <th >最新/指定</th>

                                   <th >历史</th>
                                   <th >最新/指定</th>

                                   <th >历史</th>
                                   <th >最新/指定</th>

                                   <th >历史</th>
                                   <th >最新/指定</th>
                               </tr>
                               </thead>
                               <tbody class="prestore_data">

                               </tbody>
                           </table>

                    </div>

                </div>
            </div>
        </div>
    </div>
</div>




<script type="text/javascript">

    //最新开奖数据
    $.post("<%=basePath%>doubleColorBall/last/data",function (data) {
        if(!!data){
            var html="";
            html+="<tr> ";
            html+="<td>"+data.fPhaseNum+"</td>";
            html+=" <td>"+data.fRedOne+"</td>";
            html+=" <td>"+data.fRedTwo+"</td>";
            html+=" <td>"+data.fRedThree+"</td>";
            html+=" <td>"+data.fRedFour+"</td>";
            html+=" <td>"+data.fRedFive+"</td>";
            html+=" <td>"+data.fRedSix+"</td>";
            html+=" <td>"+data.fBlue+"</td>";
            html+=" <td>"+data.fLotteryTime+" ("+data.fLotteryTimneWeek+") </td>";
            html+=" </tr>";
            $(".last_data").append(html);
        }
    });

    //日期
    layui.use('laydate', function(){
        var laydate = layui.laydate;
        //执行一个laydate实例
        laydate.render({
            elem: '#fLotteryTimeStart' //指定元素
        });

        //执行一个laydate实例
        laydate.render({
            elem: '#fLotteryTimeEnd' //指定元素
        });
    });


   //prestore_data

    $(".prestore_data")






</script>
</body>
</html>