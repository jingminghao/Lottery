<%@ page isELIgnored="false"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>

<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>


<jsp:include page="../../common/template.jsp"></jsp:include>

<body id="_memberPage" >
    <table id="info_table" lay-filter="lotteryInfoTableLayFilter" style="overflow-x:scroll"></table>
</body>

<script type="text/javascript">
var fPhaseNum="${fPhaseNum}";
    //table
    layui.use(['table','form'], function() {
        var table = layui.table; //表格
        var form = layui.form;
        table.render({
            elem: '#info_table',
            url: '<%=basePath%>doubleColorBall/info/data',
            // cellMinWidth: 80 //全局定义常规单元格的最小宽度，layui 2.2.1 新增
            method:'POST',

            where :{"fPhaseNum":fPhaseNum},
            id: 'infoTableId',
            cols: [ [
                        {field: 'fType', title: '奖级', width: "25%",sort: true,align:"center"},
                        {field: 'fTypeNum', title: '注数', width:"20%",sort: true,align:"center",
                            templet: function(row) {
                                var fTypeNum = Number(row.fTypeNum);
                                return (fTypeNum).toLocaleString({style: 'currency', currency: 'JPY'});
                            }
                        },
                        {field: 'fTypeMoney', title: '金额(元)' ,sort: true,align:"center",
                            templet: function(row){
                                var fTypeMoney= Number( row.fTypeMoney);
                                return (fTypeMoney).toLocaleString('en-US', {style: 'currency', currency: 'JPY'});
                            }
                        }

                    ] ],
        });

    });


</script>


</script>