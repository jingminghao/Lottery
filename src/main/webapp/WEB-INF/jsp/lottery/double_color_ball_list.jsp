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
    <title>会员管理</title>
    <meta charset="utf-8">
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport"
          content="width=device-width, initial-scale=1, maximum-scale=1">
    <style type="text/css">
        .layui-form-pane .layui-form-label {
            width: 120px !important;
        }

        .layui-elem-field{
            border-color: #c2c2c2 !important;
        }
        ._label{
            color:white;
            background: #c2c2c2 !important;
            border-color: 1px soild #e6e6e6 !important;
        }
        .layui-elem-field legend {
            font-size: 18px !important;
        }
        legend{
            color: #c2c2c2;
        }

        .layui-table td, .layui-table th, .layui-table-col-set, .layui-table-fixed-r, .layui-table-grid-down, .layui-table-header, .layui-table-page, .layui-table-tips-main, .layui-table-tool, .layui-table-total, .layui-table-view, .layui-table[lay-skin=line], .layui-table[lay-skin=row]{
            border-color: #c2c2c2 !important;
        }




        /*球*/
        .rq1 {
            background-image: url('<%=basePath%>pt/img/qiuHs.jpg');
        }
        .rq1 {
            overflow: hidden;
            width: 25px;
            height: 25px;
            margin: 0 1px;
            display: inline-block;
            text-align: center;
            font: 12px/25px "Microsoft YaHei", "微软雅黑", Arial;
            color: #fff;
            border-radius: 50%;
        }
    </style>
</head>
<body id="_memberPage" >



<fieldset class="layui-elem-field layui-field-title" style="margin-top: 15px; border: 1px soild red;">
    <legend>双色球列表</legend>
</fieldset>
<div style="margin-top: 15px;">
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
        <%--<div style="margin-left: 10px;">
            <button class="layui-btn addMember_btn"><i class="layui-icon">&#xe654;</i>添加会员</button>
        </div>--%>
        <table id="member_table" lay-filter="memberTableLayFilter" style="overflow-x:scroll"></table>

        <script type="text/html" id="barDemo1">
            <a class="layui-btn layui-btn-xs" lay-event="detail">详情</a>

            <%--<a class="layui-btn layui-btn-normal layui-btn-xs" lay-event="koufei">消费</a>
            <a class="layui-btn layui-btn-xs" lay-event="edit">编辑</a>
            <a class="layui-btn layui-btn-danger layui-btn-xs _delete_btn" lay-event="del">删除</a>--%>
        </script>


    </div>
</div>


<script type="text/html" id="fSales_money">
    <span style="color: #5FB878;">
     <i class="layui-icon">&#xe65e;</i>  {{ d.fSales }}
    </span>
</script>
<script type="text/javascript">
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


    //table
    layui.use(['table','form'], function() {
        var table = layui.table; //表格
        var form = layui.form;
        table.render({
            elem: '#member_table',
            url: '<%=basePath%>doubleColorBall/list',
            method:'POST',
            /*  page: {
                 curr: location.hash.replace('#!data_customer_info_table_page=', ''), //获取起始页
                 hash: 'data_customer_info_table_page' //自定义hash值
             }, */
            request: {
                pageName: 'current' //页码的参数名称，默认：page
                ,limitName: 'pageSize' //每页数据量的参数名，默认：limit
            },

            page:true,
            limit:15,
            limits:[10,20,50],
            id: 'memberTableId',
            cols: [
                [
                {type:"numbers",title: '序号',width: "5%",sort: true,align:"center"},
                {field: 'fPhaseNum', title: '期号', width: "7%",sort: true,align:"center"},
                {field: '', title: '红球1', width:"7%",sort: true,align:"center",
                    templet: function(row){
                        return "<span class=\"rq1\" >"+row.fRedOne+"</span>";
                    }
                },
                {field: 'fRedTwo', title: '红球2', width: "7%",sort: true,align:"center",
                    templet: function(row){
                        return "<span class=\"rq1\" >"+row.fRedTwo+"</span>";
                    }
                },
                {field: 'fRedThree', title: '红球3', width: "7%",sort: true,align:"center",
                    templet: function(row){
                        return "<span class=\"rq1\">"+row.fRedThree+"</span>";
                    }
                },
                {field: 'fRedFour', title: '红球4', width: "7%",sort: true,align:"center",
                    templet: function(row){
                        return "<span class=\"rq1\">"+row.fRedFour+"</span>";
                    }
                },
                {field: 'fRedFive', title: '红球5', width: "7%",sort: true,align:"center",
                    templet: function(row){
                        return "<span class=\"rq1\" >"+row.fRedFive+"</span>";
                    }
                },
                {field: 'fRedSix', title: '红球6', width: "7%",sort: true,align:"center",
                    templet: function(row){
                        return "<span class=\"rq1\" >"+row.fRedSix+"</span>";
                    }
                },
                {field: 'fBlue', title: '蓝球', width: "7%",sort: true,align:"center",
                    templet: function(row){
                        return "<span class=\"rq1\" style=\"background-image: url('<%=basePath%>pt/img/qiuLs.jpg')  \">"+row.fBlue+"</span>";
                    }
                },
                {field: 'fLotteryTimeStr', title: '开奖日期', width: "10%" ,
                    templet: function(row){
                        return row.fLotteryTime+"("+row.fLotteryTimneWeek+")";
                    }
                },
                {field: 'fSalesStr', title: '总销售额（元）', width: "10%",sort: true,
                    templet: function(row){
                        var fSalesNumber= Number( row.fSales);
                        return (fSalesNumber).toLocaleString('en-US', {style: 'currency', currency: 'JPY'});
                    }
                },
                {field: 'fPoolmoneyStr', title: '奖池（元）', width: "10%",sort: true,
                    templet: function(row){
                        var fPoolmoneyNumber= Number( row.fPoolmoney);
                        return (fPoolmoneyNumber).toLocaleString('en-US', {style: 'currency', currency: 'JPY'});
                    }
                 },
                {title: '操作',align: 'center', toolbar: '#barDemo1'}
            ]

            ],
            loading: true
        });
        //查询
        var $ = layui.$, active = {
            reload: function(){
                //userName mobile
                var fPhaseNum=$("input[name=fPhaseNum]").val();
                var fBalls=$("input[name=fBalls]").val();

                var fLotteryTimeStart=$("#fLotteryTimeStart").val();
                var fLotteryTimeEnd=$("#fLotteryTimeEnd").val();

                table.reload('memberTableId', {
                    where: {
                        fPhaseNum:fPhaseNum,fBalls:fBalls,fLotteryTimeStart:fLotteryTimeStart,fLotteryTimeEnd:fLotteryTimeEnd
                    },
                    page: {
                        curr: 1 //重新从第 1 页开始
                    }
                });
            }
        };
        //绑定 查询 click点击事件
        $('._memberSearch_btn').on('click', function(){
            var type = $(this).data('type');
            active[type] ? active[type].call(this) : '';
        });

        //table 里按钮 监听工具条
        table.on('tool(memberTableLayFilter)', function(obj){
            var data = obj.data;
            if(obj.event === 'detail'){
                //layer.msg('ID：'+ data.fPhaseNum + ' 的查看操作');
                detail(obj);
            } else if(obj.event === 'del'){
                layer.confirm("确认要删除吗，删除后不能恢复", { title: "删除确认" }, function (index) {
                    console.log(data);
                    console.log(data.fPhaseNum);
                    $.ajax({
                        url: "<%=basePath%>member/removeMemberByRowId",
                        type: "POST",
                        data:{"rowId":data.fPhaseNum},
                        dataType: "json",
                        success: function(res){
                            if(res.status=="SUCCESS"){
                                //删除这一行
                                obj.del();
                                //关闭弹框
                                layer.close(index);
                                layer.msg(res.msg, {icon: 1});
                                return;
                            }
                            layer.msg(res.msg, {icon: 2});

                        }

                    });
                });
            } else if(obj.event === 'edit'){
                editor(obj);
            }
        });
    });


    //详情
    function detail(obj){
        JSON.stringify(obj.data)
        var fPhaseNum=obj.data.fPhaseNum;
        layer.open({
            btn: ['关闭'],
            type: 2,
            title:"详情("+fPhaseNum+")",
            id: (new Date()).valueOf(), //设定一个id，防止重复弹出 时间戳1280977330748
            moveType: 1, //拖拽模式，0或者1
            area: ['700px', '450px'],
            fixed: false, //不固定
            maxmin: false, //true  可以缩小放大
            content: '<%=basePath%>doubleColorBall/info/page?fPhaseNum='+fPhaseNum,
            btn2: function (index, layero) {

            }
        });

    }







</script>
</body>
</html>