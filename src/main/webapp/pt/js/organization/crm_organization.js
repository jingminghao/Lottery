//初始化ztree控件  
var treeObjEver;
var treeObjEver_setShop;
var treeObjEver_setRegion;
var treeObjEver_setSale;

var currentTreeNode;//当前选中的TreeNode
var selectCheckBoxList=[];//userId
var organizationDeptCode=[];//机构deptCode



var checkUserIds=[];//分配员工，勾选的
var cancelUserIds=[];//分配员工,取消的
var _pdUpdateAdd="";
$(function () {  
	$(".loading").show();
	treeObjEver= $.fn.zTree.init($("#organization_ztree"), setting); 
	//iCheck插件
	$(document).ready(function(){
	  $('input').iCheck({
	    checkboxClass: 'icheckbox_square-blue',
	    radioClass: 'iradio_square-blue',
	    increaseArea: '20%' // optional
	  });
	});
	
});  

function initTable(_strDeptCode){
	
	window.table = $.MMOTable("#organizationTbaleId",{
		url:"../organization/searchPageOrganization.do",
		height: 500,
		//queryParams: initNodeClick(_strDeptCode),
		queryParams:function(res){
        	if(this.pagination){
        		this.args.currentPage = (res.offset/res.limit+1);
        		this.args.current= (res.offset/res.limit+1);
        		this.args.pageSize = res.limit;
        	}
        	this.args.deptCode= _strDeptCode;
        	return this.args;
        },
		columns:[
		    {checkbox: true},
			{field:'user.loginName', title:'用户名',width:'80',align: 'center',
		    	formatter:function(value,row,index){  
	              return row.user.loginName;  
	            } 
			},
			{field:'user.mobile', title:'手机号',width:'120',align: 'center',
		    	formatter:function(value,row,index){  
	              return row.user.mobile;  
	            } 
			},
			{field:'user.realName', title:'真实姓名',width:'120',align: 'center',
		    	formatter:function(value,row,index){  
	              return row.user.realName;  
	            } 
			},
			{field:'roleName', title:'角色',width:'80',align: 'center'},
			{field:'user.positionStatus', title:'岗位',width:'80',align: 'center',
				formatter:function(value,row,index){ 
					if(row.user.positionStatus==0){
						return "销售岗";
					}else if(row.user.positionStatus==1){
						return "管理岗";
					}else if(row.user.positionStatus==2){
						return "超级岗";
					}
				}
			},
			{field:'deptName', title:'当前机构',width:'80',align: 'center'}
			 
			 
			 
		],
		 onLoadSuccess:function(data){
		    	//console.info(data);
		},
		onCheck: function (row) {//单选
			selectCheckBoxList.push(row.user.rowId);
			organizationDeptCode.push(row.deptCode);
        },
        onUncheck: function (row) {//单不选
        	if(selectCheckBoxList.length>0){
        		for(var i=0;i<selectCheckBoxList.length;i++){
        			 if(row.user.rowId==selectCheckBoxList[i]){
        				 selectCheckBoxList.splice(i,1);
						 organizationDeptCode.splice(i,1);
        			 }
        		}
        	}
        },
        onCheckAll:function( rows){//全选
        	selectCheckBoxList=[];
        	for(var i=0;i<rows.length;i++){
        		selectCheckBoxList.push(rows[i].user.rowId);
				organizationDeptCode.push(rows[i].deptCode);
    		}
        },
        onUncheckAll:function( rows){//全不选
        	selectCheckBoxList=[];
			organizationDeptCode=[];
        }
	});
}
//参数传递
/*function initNodeClick(_strDeptCode) {
    var temp = {   
    		deptCode: _strDeptCode,
    };
    return temp;
}*/


  function onAsyncSuccess(){
	  treeObjEver.expandAll(true);
	  var zTree = $.fn.zTree.getZTreeObj("organization_ztree"); 
	//调用默认展开第一个结点    
      var selectedNode = zTree.getSelectedNodes();    
      var nodes = zTree.getNodes(); 
      var intdept='1';
	  if(nodes.length>0){
		  zTree.selectNode(nodes[0]);
		  zTree.expandNode(nodes[0], true);  
		  intdept=nodes[0].deptCode;
		  currentTreeNode=nodes[0];
	  }    
       
	  initTable(intdept);
	  $(".loading").hide();
  }
  function onAsyncError_org(){
	  $(".loading").hide();
  }
	/**
	 * 单机机构
	 * @param event
	 * @param treeId
	 * @param treeNode
	 * @param clickFlag
	 */
	function onClick(event, treeId, treeNode, clickFlag) {
		//重新加载table后，清空selectCheckBoxList
		selectCheckBoxList=[];
		organizationDeptCode=[];
		currentTreeNode=treeNode;
		$("#organizationTbaleId").bootstrapTable('destroy'); 
		initTable(treeNode.deptCode)
		/*table.query({
			deptCode:treeNode.deptCode
		})*/
		//return false;
	}		
	  
  
var setting={  
  
    async: {  
        enable: true,  
        type:'post',  
        url:"../organization/getOrganizationZtree.do",  
        dataType : "json"  
    },  
    data: {  
        key : {  
            name : "deptName"  ,
            deptCode:"deptCode"
        },  
        simpleData : {   //简单的数据源  
            enable : true,  
            idKey : "deptCode",  // 树的结构信息  
            pIdKey : "parentCode",  
            rootPId : 0  , //根节点  
        }  
    },  
    callback: {  
		onClick: onClick,
    	onAsyncSuccess: onAsyncSuccess  ,
    	onAsyncError: onAsyncError_org  ,
    	beforeEditName: zTreeBeforeEditName,//用于捕获节点编辑按钮的 click 事件，并且根据返回值确定是否允许进入名称编辑状态
    	beforeRename: zTreeBeforeRename, //用于捕获节点编辑名称结束（Input 失去焦点 或 按下 Enter 键）之后
    	beforeRemove: zTreeBeforeRemove //用于捕获节点被删除之前的事件回调函数
    },  
    view:{  
        showIcon: false,  
        showLine: true 
    } 
    
}; 
//删除回调
function zTreeBeforeRemove(treeId, treeNode){
	var params={
			deptCode:treeNode.deptCode
		}
	$.post("../organization/removeOrganization.do",params,function(res){
		if(res.status=="SUCCESS"){
			var zTree = $.fn.zTree.getZTreeObj("organization_ztree"); 
			var nodes = zTree.getNodes(); 
			if(nodes.length>0){
				zTree.selectNode(nodes[0]);
				zTree.setting.callback.onClick(null,"organization_ztree", nodes[0]); 
			 }
		}else{
			alert(res.msg);
		}
		      
		       
	});
}
function zTreeBeforeEditName(treeId, treeNode){
	//alert(1);
}
function zTreeBeforeRename(treeId, treeNode, newName, isCancel){
	if(newName==""){
		alert("机构名称不能为空");
		return;
	}
	if(_pdUpdateAdd=="update"){
		var params={
				deptName:newName,
				deptCode:treeNode.deptCode
			}
		$.post("../organization/updateOrganization.do",params,function(res){
			if(res.status!="SUCCESS"){
				alert(res.msg);
			}
			
		});
	}else{//新增
		//修改节点完节点名称保存事件
		var params={
				levelId:treeNode.levelId,
				parentCode:treeNode.parentCode,
				deptName:newName,
				deptCode:treeNode.deptCode,
				rowId:treeNode.rowId
				
			}
			//保存
			$.post("../organization/addOrganization.do",params,function(res){
				alert(res.msg);//防止用户快速点击
			});
		var zTree = $.fn.zTree.getZTreeObj("organization_ztree"); 
		zTree.selectNode(treeNode);
		zTree.setting.callback.onClick(null,"organization_ztree", treeNode); 

		 
	}
	
}
/**
 * 分配员工查询参数
 */
/*function distributionUser(_strDeptCode){
	var strUserName=$("#user_realName").val();
	var temp = {   
    	deptCode: _strDeptCode,
    	realName:strUserName
    };
    return temp;
}*/
/**
 * 分配员工事件
 */
 $("#yg_btn").click(function(){
	 $("#user_realName").val("");
	//当前如果是跟节点不让分配
	 if(currentTreeNode.tId=="organization_ztree_1"){
		alert("根节点无法分配");
		return;
	 }
	checkUserIds=[];
 	cancelUserIds=[];
	 $('#matchyg').show();
	 var realNamePrms=$("#user_realName").val();
	 init_table2(currentTreeNode.deptCode);
 });
 function init_table2(prams_table2deptCode){
	 $(".distributionUser").bootstrapTable('destroy'); 
	 var user_realName=$("#user_realName").val();
	 window.table2= $.MMOTable(".distributionUser",{
		url:"../organization/searchPageUser.do",
		height: 350,
		//queryParams: distributionUser(currentTreeNode.deptCode),
		queryParams:function(res){
        	if(this.pagination){
        		this.args.currentPage = (res.offset/res.limit+1);
        		this.args.current= (res.offset/res.limit+1);
        		this.args.pageSize = res.limit;
        	}
        	this.args.deptCode= prams_table2deptCode;
        	this.args.realName = user_realName;
        	return this.args;
        },
		columns:[
		    {checkbox: true,field:'user.rowId',
		    	formatter:function(value,row,index){ 
		    		if(row.checkChoose==1){
		    			return {
		    	            checked : true//设置选中
		    	        };
		    		}
		             
		         } 
		    },
			{field:'user.loginName', title:'用户名',width:'80',align: 'center',
		    	formatter:function(value,row,index){  
	              return row.user.loginName;  
	            } 
			},
			{field:'user.mobile', title:'手机号',width:'120',align: 'center',
		    	formatter:function(value,row,index){  
	              return row.user.mobile;  
	            } 
			},
			{field:'user.realName', title:'真实姓名',width:'120',align: 'center',
		    	formatter:function(value,row,index){  
	              return row.user.realName;  
	            } 
			},
			{field:'roleName', title:'角色',width:'80',align: 'center'},
			{field:'user.positionStatus', title:'岗位',width:'80',align: 'center',
				formatter:function(value,row,index){    
					if(row.user.positionStatus==0){
						return "销售岗";
					}else if(row.user.positionStatus==1){
						return "管理岗";
					}else if(row.user.positionStatus==2){
						return "超级岗";
					}
				}
			},
			 
		],
		 onLoadSuccess:function(data){
		    	//console.info(data);
		},
		onCheck: function (row) {//单选
			checkUserIds.push(row.user.rowId);
        },
        onUncheck: function (row) {//单不选
        	if(checkUserIds.length>0){
        		for(var i=0;i<checkUserIds.length;i++){
        			 if(row.user.rowId==checkUserIds[i]){
        				 checkUserIds.splice(i,1);
        			 }
        		}
        	}
        	if(cancelUserIds.length>0){
        		var pd=0;
        		for(var i=0;i<cancelUserIds.length;i++){
        			if(row.user.rowId==cancelUserIds[i]){
        				pd=1;
        			}
        		}
        		if(pd==0){
        			cancelUserIds.push(row.user.rowId);
        		}
        	}else{//取消已经分配的员工
        		cancelUserIds.push(row.user.rowId);
        	}
        },
        onCheckAll:function( rows){//全选
        	checkUserIds=[];
        	cancelUserIds=[];
        	for(var i=0;i<rows.length;i++){
        		checkUserIds.push(rows[i].user.rowId);
        		cancelUserIds.push(rows[i].user.rowId);
    		}
        },
        onUncheckAll:function( rows){//全不选
        	checkUserIds=[];
        	cancelUserIds=[];
        	for(var i=0;i<rows.length;i++){
        		cancelUserIds.push(rows[i].user.rowId);
    		}
        }
	});
 }
 
 /**
  * 删除员工
  */
 $("#deleteUser_but").click(function(){
	 if(selectCheckBoxList.length>0){
		 if (confirm("确定删除数据？")){
				var params = {
						deptCodeList:organizationDeptCode.toString(),
						userIdList:selectCheckBoxList.toString()
				};
				
				$.post("../organization/removeBatchOrgUser.do",params,function(res){
					if(res.status=="SUCCESS"){
						selectCheckBoxList=[];
						organizationDeptCode=[];
						table.query({
							deptCode:currentTreeNode.deptCode
						})
					}else{
						alert(res.msg);
					}
					 
					 
				});	
			}
	 }else{
		 alert("请先勾选要删除的数据！");
	 }
 });
 /**
  * 查询分配员工
  */
 $("#relationSearch").click(function(){
	 checkUserIds=[];
	 cancelUserIds=[];
	 
	 init_table2(currentTreeNode.deptCode);
	/* var realName=$("#user_realName").val();
	 table2.query({
		 	realName:realName
		})*/
	 
 });
 
 /**
  * 保存分配员工
  */
 $("#dialog_save").click(function(){
	 if(checkUserIds.length>0 || cancelUserIds.length>0){
		 var params = {
				deptCode : currentTreeNode.deptCode,
				checkUserIds:checkUserIds.toString(),
				cancelUserIds:cancelUserIds.toString()
			};
		 $.post("../organization/addBatchOrgUser.do",params,function(res){
			 if(res.status=="SUCCESS"){
				 $('#matchyg').hide();
				 selectCheckBoxList=[];
				 organizationDeptCode=[];
				 table.query({
					 deptCode:currentTreeNode.deptCode
				 })
			 }
			 if(res.status!="SUCCESS"){
				 alert(res.msg);
			 }

			 
		 });
	 }else{
		 alert("您没有做任何更改");
	 }
	 
 });
 
 
 //新增节点
 $("#tree_add_btn").on("click",function(e){
	 	_pdUpdateAdd="add";
		var zTree = $.fn.zTree.getZTreeObj("organization_ztree");
		nodes = zTree.getSelectedNodes();
		if(nodes.length>0){
			treeNode = nodes[0];
			if(treeNode.levelId==10){
				alert("节点超出限制，不能超过10级");
			}else{
				//查询选中节点下的子节点Max(deptCode)
				var params={
						parentCode:treeNode.deptCode
				}
				
				if ( treeNode) {
					$.post("../organization/maxDeptCodeByParentId.do",params,function(res){
						treeNode = zTree.addNodes(treeNode, 
								{
									id:null, 
									levelId:(parseInt(treeNode.levelId)+1).toString(),
									parentCode:treeNode.deptCode,
									pId:treeNode.rowId, 
									deptName:"新增节点",
									deptCode:res.deptCode,
									rowId:res.rowId
									
								}
							);
						zTree.editName(treeNode[0]);
					});
					
				}else {
					alert("子节点被锁定，无法增加子节点");
				}		
			}
			 
		}else{
			alert("请选择要添加的位置");
		}
	});
 
 
//修改节点
 $("#tree_modify_btn").on("click",function(){
	 _pdUpdateAdd="update";
 	var zTree = $.fn.zTree.getZTreeObj("organization_ztree"),
		nodes = zTree.getSelectedNodes();
		treeNode = nodes[0];
		if (nodes.length == 0) {
			alert("请先选择一个节点");
			return;
		}
		zTree.editName(treeNode);
	});
 //删除节点
 $("#tree_remove_btn").on("click",function(){
 	var zTree = $.fn.zTree.getZTreeObj("organization_ztree");
		nodes = zTree.getSelectedNodes(),
		treeNode = nodes[0];
		if (nodes.length == 0) {
			alert("请先选择一个节点");
			return;
		}
		if(treeNode.tId=="organization_ztree_1"){
			alert("根节点无法删除");
			return;
		}
		 if (confirm("确定删除该节点及子节点？")){
			 zTree.removeNode(treeNode, true);
		 }
	});
 
 /**
 *Start 设置门店============================================================================== 
 */
 var deletDeptCodes=[];//删除的Codes
 var deptCodes=[];//新增的parentCodes
 var parentCodes=[];//新增的Codes
 
 var areaCodes=[];//新增大区Codes
 var provinceCodes=[];//新增省Codes
 var cityCodes=[];//新增市Codes
 var areaTypes=[];//新增区域类型areaType
 
 var deptNames=[];//新增的deptNames
 
 var initCheckNodelList=[];//初始化绑定已选中的组织结构节点 
 
 var setting_setShop={ 
		 	check: {
				enable: true,
				chkStyle: "checkbox"
			},
		    async: {  
		        enable: true,  
		        type:'post',  
		        url:"../organization/getOrganizationZtree.do",  
		        dataType : "json"  
		    },  
		    data: {  
		        key : {  
		            name : "deptName"  ,
		            deptCode:"deptCode"
		        },  
		        simpleData : {   //简单的数据源  
		            enable : true,  
		            idKey : "deptCode",  // 树的结构信息  
		            pIdKey : "parentCode",  
		            rootPId : 0  , //根节点  
		        }  
		    },  
		    callback: {  
		    	onAsyncSuccess: onAsyncSuccess_setShop ,
		    	onAsyncError:onAsyncError_setShop,
		    	beforeCheck: zTreeBeforeCheck_setShop
		    },  
		    view:{  
		        showIcon: false,  
		        showLine: true 
		    } 
		    
		}; 
 $("#setShop_btn").click(function(){
	 $("#setShop_dialog").show();
	 $(".loading").show();
	 treeObjEver_setShop= $.fn.zTree.init($("#setShop_ztree"), setting_setShop); 
 });
 //oncheck 回调 
 function zTreeBeforeCheck_setShop(treeId, treeNode){
	 if(treeNode.isParent){
		 alert("父级不可选，请选择子节点！");
		 return false;
	 }
	 //console.log(treeNode)
	 if(treeNode.checked){//取消√
		 var pdqxpush=0;
		 for(var i=0;i<deptCodes.length;i++){
			 if(deptCodes[i]==treeNode.deptCode){
				 deptCodes.splice(i,1);
				 parentCodes.splice(i,1);
				 deptNames.splice(i,1);
				 
				 areaCodes.splice(i,1);
				 provinceCodes.splice(i,1);
				 cityCodes.splice(i,1);
				 areaTypes.splice(i,1);
				 
				 
				 pdqxpush=1;
			 }
		 }
		if(pdqxpush==0){
			 deletDeptCodes.push(treeNode.deptCode);
		} 
	 }else{//√
		 
		 for(var i=0;i<deletDeptCodes.length;i++){
			 if(deletDeptCodes[i]==treeNode.deptCode){
				 deletDeptCodes.splice(i,1);
			 }
		 }
		 var pdpush=0;
		 for(var k=0;k<initCheckNodelList.length;k++){
			 if(treeNode.deptCode==initCheckNodelList[k].deptCode){	
				 pdpush=1;
			 }
		 }
		 if(pdpush==0){
			 deptCodes.push(treeNode.deptCode);
			 parentCodes.push(treeNode.parentCode);
			 deptNames.push(treeNode.deptName);
			 
			 if(treeNode.areaCode==null || treeNode.areaCode==""){
				 areaCodes.push(" ");
			 }else{
				 areaCodes.push(treeNode.areaCode);
			 }
			 if(treeNode.provinceCode==null || treeNode.provinceCode==""){
				 provinceCodes.push(" ");
			 }else{
				 provinceCodes.push(treeNode.provinceCode);
			 }
			 if(treeNode.cityCode==null || treeNode.cityCode==""){
				 cityCodes.push(" ");
			 }else{
				 cityCodes.push(treeNode.cityCode);
			 }
			 if(treeNode.areaType==null || treeNode.areaType==""){
				 areaTypes.push(" ");
			 }else{
				 areaTypes.push(treeNode.areaType);
			 }
			 
		 }
		 
		 
	 }
	 
	 return true;
	 
 }
 //保存
 $("#setShopDialog_save").click(function(){
	 var params={
			 	"deptCodes":deptCodes.toString(),
			 	"parentCodes":parentCodes.toString(),
			 	"deptNames":deptNames.toString(),
			 	"deletDeptCodes":deletDeptCodes.toString(),
			 	
			 	"areaCodes":areaCodes.toString(),
			 	"provinceCodes":provinceCodes.toString(),
			 	"cityCodes":cityCodes.toString(),
			 	"areaTypes":areaTypes.toString()
			 	
			 }
	 $.post("../channel/addBatchChannels.do",params,function(res){
		 if(res.status=="SUCCESS"){
			 $("#setShop_dialog").hide();
		 }
		 alert(res.msg);
		 
	 })
	 
 });
 
 //绑定选中

 function getChildreByParent(parentNodel){
	 var zTree_shop = $.fn.zTree.getZTreeObj("setShop_ztree");
	 for(var i=0;i<parentNodel.length;i++){
		 if(parentNodel[i].isParent){
			 getChildreByParent(parentNodel[i].children);
		 }else{
			 //initCheckShopList.push(parentNodel[i]);
			 
			 for(var k=0;k<initCheckNodelList.length;k++){
				 if(parentNodel[i].deptCode==initCheckNodelList[k].deptCode){	
					 //console.log("找到--------:"+parentNodel[i].deptName);
					 parentNodel[i].checked=true;
					 parentNodel[i].checkTypeFlag =true;
					 zTree_shop.checkNode(parentNodel[i], true, true);
				 }
			 }
			 
		 }
	 }
	
 }
 //加载ztree后触发
 function onAsyncSuccess_setShop(data){
	 deletDeptCodes=[];//清空删除的Codes
	 deptCodes=[];//清空新增的parentCodes
	 parentCodes=[];//清空新增的Codes
	 deptNames=[];//清空新增的deptNames
	 
	 areaCodes=[];//清空新增的areaCodes
	 provinceCodes=[];//清空新增的provinceCodes
	 cityCodes=[];//清空新增的cityCodes
	 areaTypes=[];//清空新增的areaTypes
	 
	 initCheckNodelList=[];//清空初始化绑定已选中的组织结构节点
	 
	 treeObjEver_setShop.expandAll(true);
	 
	 /**
	  * 查询与组织管理绑定的门店
	  */
	 $.post("../channel/getAllChannelByOrganization.do",function(res){
		 if(res.status=="SUCCESS" && res.data.length>0){
			 initCheckNodelList=res.data;
			 var allNodels=treeObjEver_setShop.getNodes();
			 if(allNodels.length>0){
				 getChildreByParent(allNodels);
				 
			 }
		 }
	 });
	 
	 
	 
	 $(".loading").hide();
	 
	 
	 //console.log(data);
 }
 function onAsyncError_setShop(){
	 $(".loading").hide();
 }
 /**
  *End 设置门店============================================================================== 
  */

 /**
  * Start 设置区域===========================================================================
  */
 var _curreClickNodel_shop;
 var setting_setRegion={  
	    async: {  
	        enable: true,  
	        type:'post',  
	        url:"../organization/getOrganizationZtree.do",  
	        dataType : "json"  
	    },  
	    data: {  
	        key : {  
	            name : "deptName"  ,
	            deptCode:"deptCode"
	        },  
	        simpleData : {   //简单的数据源  
	            enable : true,  
	            idKey : "deptCode",  // 树的结构信息  
	            pIdKey : "parentCode",  
	            rootPId : 0  , //根节点  
	        }  
	    },  
	    callback: {  
			onClick:onClick_setRegion,
			beforeClick: beforeClick_setRegion,
	    	onAsyncSuccess: onAsyncSuccess_setRegion,
	    	onAsyncError:onAsyncError_setRegion
	    },  
	    view:{  
	        showIcon: false,  
	        showLine: true 
	    } 
	    
	}; 
 	//
 	function onAsyncSuccess_setRegion(){
		treeObjEver_setRegion.expandAll(true);//加载以后全部展示
		$(".loading").hide();
	}
 	function onAsyncError_setRegion(){
 		$(".loading").hide();
 	}
 	//nodel 点击之前事件
 	function beforeClick_setRegion(treeId, treeNode, clickFlag){
 		if(treeNode.tId=="setRegion_ztree_1"){
 			alert("根节点不可以设置区域!");
 			return false;
 		}
 		$('.setReg_dialog').addClass("setRegion_dialog_bd");
 	}
 	//nodel 点击事件
 	function onClick_setRegion(event, treeId, treeNode){
 		_curreClickNodel_shop=treeNode;
 		var curryareaType="0";
 		if(treeNode.areaType!="" && treeNode.areaType!=null){
 			curryareaType=treeNode.areaType;
 		}
 		
 		
 		$(".areaTypeRadio_"+curryareaType).iCheck('uncheck');
 		$(".areaTypeRadio_"+curryareaType).iCheck('check');
 		
 		
 		
 		$("#setRegionDeptCode").val(treeNode.deptCode);
 		$("#setRegionParentCode").val(treeNode.parentCode);
 		$("#setRegionDertName").val(treeNode.deptName);
 		
 		$('#setRegionDialog_right').show();
 		
 	}
 	//加载下拉
 	function loadSelect(areaType){
 		$(".areaType_"+areaType).empty();
 		var params={
 				areaType:areaType
 		}
 		// 加载大区
	$.ajax({
		  type: "POST",
 		  url:'../channel/getAllArea.do',
 		  data:params,
 		  async:false,
 		  success:function(res){
			if(res.status=="SUCCESS"){
				if(res.data.length>0){
					for(var i=0;i<res.data.length;i++){
						var _html="<option value='"+res.data[i].areaCode+"' ";
						if(_curreClickNodel_shop.areaCode==res.data[i].areaCode){
							_html+=" selected='selected' ";
						}
						_html+=" > "+res.data[i].areaName+"</option>";
						$(".areaType_"+areaType).append(_html);
					}
				}
			}else{
				alert(res.msg);
			}
 		  }
		})
 		
 	}
 	//加载联动下拉(省、市)
 	function loadProvinceOrCitySelect(parentId,areaType){
 		$(".areaType_"+areaType).empty();
 		if(parentId=="" || parentId==null){
 			return;
 		}
 		var params={
 				parentId:parentId
 		}
 		$.ajax({
 			  type: "POST",
 	 		  url:'../channel/getProvinceOrCityByArea.do',
 	 		  data:params,
 	 		  async:false,
 	 		  success:function(res){
 	 			if(res.status=="SUCCESS"){
 					var curreCode;
 					if(res.data.length>0){
 						if(areaType=="1"){
 							curreCode=_curreClickNodel_shop.provinceCode;
 						}else{
 							curreCode=_curreClickNodel_shop.cityCode;
 						}
 						for(var i=0;i<res.data.length;i++){
 							var _html="<option value='"+res.data[i].areaCode+"' ";
 							if(curreCode==res.data[i].areaCode){
 								_html+=" selected='selected' ";
 							}
 							_html+=" > "+res.data[i].areaName+"</option>";
 							
 							$(".areaType_"+areaType).append(_html);
 						}
 					}
 				}else{
 					alert(res.msg);
 				}
 	 		  }
 		
			
		})
 		
 	}
 	
 	$("#setRegion_btn").click(function(){
 		$('.setReg_dialog').removeClass("setRegion_dialog_bd");
 		$('#setRegionDialog_right').hide();
 		$('#setRegion_dialog').show();
 		$(".loading").show();
 		treeObjEver_setRegion= $.fn.zTree.init($("#setRegion_ztree"), setting_setRegion); 
 		
 		
 	});
 	$('input').on('ifChecked', function(event){
 		
 		loadSelect(0);//加载区
 		
 		var value=event.currentTarget.value;
 		if(value=="0"){
 			$(".province_area").hide();
 			$(".city_area").hide();
 			$(".large_area").show();
 			
 			var parentAreaCode_0=$(".areaType_0").val();
 	 		loadProvinceOrCitySelect(parentAreaCode_0,"1");//联动省
 	 		var parentAreaCode_1=$(".areaType_1").val();
 	 		if(parentAreaCode_1 !==""){
 	 			loadProvinceOrCitySelect(parentAreaCode_1,"2");//联动市
 	 		}
 		}else if(value=="1"){
 			$(".city_area").hide();
 			$(".large_area").show()
 			$(".province_area").show();
 			var parentAreaCode_0=$(".areaType_0").val();
 	 		loadProvinceOrCitySelect(parentAreaCode_0,"1");//联动省
 	 		var parentAreaCode_1=$(".areaType_1").val();
 	 		if(parentAreaCode_1 !==""){
 	 			loadProvinceOrCitySelect(parentAreaCode_1,"2");//联动市
 	 		}
 		}else{
 			$(".large_area").show()
 			$(".province_area").show();
 			$(".city_area").show();
 			
 			var parentAreaCode_0=$(".areaType_0").val();
 	 		loadProvinceOrCitySelect(parentAreaCode_0,"1");//联动省
 	 		
 			var parentAreaCode_1=$(".areaType_1").val();
 	 		if(parentAreaCode_1 !==""){
 	 			loadProvinceOrCitySelect(parentAreaCode_1,"2");//联动市
 	 		}
 		}
 		
 	 });
 	 
 	//选中1、2 select 下拉切换
 	$(".areaType_0").change(function(){
 		var parentAreaCode_0=$(".areaType_0").val();
 		loadProvinceOrCitySelect(parentAreaCode_0,"1");//联动省
 		var parentAreaCode_1=$(".areaType_1").val();
 		if(parentAreaCode_1 !==""){
 			loadProvinceOrCitySelect(parentAreaCode_1,"2");//联动市
 		}
 		
 		
 	});
 	$(".areaType_1").change(function(){
 		var parentAreaCode_1=$(".areaType_1").val();
 		if(parentAreaCode_1 !==""){
 			loadProvinceOrCitySelect(parentAreaCode_1,"2");//联动市
 		}
 		
 	});
 	 
 	
 	//setRegion_save 点击保存
 	$("#setRegion_save").click(function(){
 		var zTree = $.fn.zTree.getZTreeObj("setRegion_ztree"),
 		setRegionNodes = zTree.getSelectedNodes();
		if (setRegionNodes.length == 0) {
			alert("您还没有操作！");
			return;
		}
		//areaType、areaCode、provinceCode、cityCode、deptCode
		//_curreClickNodel_shop
		
		//修改组织表的信息、如果门店表 有这个组织信息的话也一并修改
		var areaType="0";
		var areaTypeIchecks=$("input[name=areaType]");
		for(var i in areaTypeIchecks){
			if(areaTypeIchecks[i].checked){
				areaType=$("input[name=areaType]")[i].value;
			}
		}
		var areaCode=$(".areaType_0").val();
		var provinceCode=$(".areaType_1").val();
		var cityCode=$(".areaType_2").val();
		var deptCode=_curreClickNodel_shop.deptCode;
		var params={
			areaType:areaType,
			areaCode:areaCode,
			provinceCode:provinceCode,
			cityCode:cityCode,
			deptCode:deptCode
		};
		console.log(deptCode);
		$.post("../organization/updateChannelInfoBySetArea.do",params,function(res){
			if(res.status=="SUCCESS"){
				$('#setRegion_dialog').hide();
			}
			alert(res.msg);
		})
		
 	});
 	
 /**
  * End 设置区域===========================================================================
  */
 
 /**
  * Start 设置销售部门===========================================================================
  */
 	
 	$("#setSale_btn").click(function(){
 		$("#setSale_dialog").show();
 		 $(".loading").show();
 		treeObjEver_setSale= $.fn.zTree.init($("#setSale_ztree"), setting_setSale); 
 		
 	});

 	 var setting_setSale={ 
 			 	check: {
 					enable: true,
 					chkStyle: "checkbox"
 				},
 			    async: {  
 			        enable: true,  
 			        type:'post',  
 			        url:"../organization/getOrganizationZtree.do",  
 			        dataType : "json"  
 			    },  
 			    data: {  
 			        key : {  
 			            name : "deptName"  ,
 			            deptCode:"deptCode"
 			        },  
 			        simpleData : {   //简单的数据源  
 			            enable : true,  //树的层次结构
 			            idKey : "deptCode",  // 树的结构信息  
 			            pIdKey : "parentCode",  
 			            rootPId : 0  , //根节点  
 			        }  
 			    },  
 			    callback: {  
 			    	onAsyncSuccess: onAsyncSuccess_setSale ,
 			    	onAsyncError:onAsyncError_setSale,
 			    	beforeCheck: zTreeBeforeCheck_setSale
 			    },  
 			    view:{  
 			        showIcon: false,  
 			        showLine: true 
 			    } 
 			    
 	 }; 
 	function getChildreByParent_Sale(parentNodel){
 		 var zTree_Sale = $.fn.zTree.getZTreeObj("setSale_ztree");
 		 for(var i=0;i<parentNodel.length;i++){
 			 if(parentNodel[i].officeType=="2"){
				parentNodel[i].checked=true;
	 			zTree_Sale.updateNode(parentNodel[i],false);
			 }
 			 if(parentNodel[i].isParent ){
 				getChildreByParent_Sale(parentNodel[i].children); 
 			 }
 		 }
 	 }
 	
 	 function onAsyncSuccess_setSale(){
 		var zTree = $.fn.zTree.getZTreeObj("setSale_ztree"); 
 		treeObjEver_setSale.expandAll(true);
 		var allNodels=zTree.getNodes();
		 if(allNodels.length>0 && allNodels[0].isParent){
			 getChildreByParent_Sale(allNodels);
		 }
		 $(".loading").hide();
 		
 	 }
 	 function onAsyncError_setSale(){
 		 $(".loading").hide();
 	 }
 	 function zTreeBeforeCheck_setSale(treeId, treeNode){
 		 if(treeNode.tId=="setSale_ztree_1"){
 			alert("根节点无法设置！");
 			return false;
 		 }else if(treeNode.isParent==false){//最后一级（门店）不可以设置销售部
 			 alert("最后一级节点不可设置销售部！");
 			 return false;
 		 }else{
 			if(treeNode.checked){
 				treeNode.checked=false;
 			}else{
 				treeNode.checked=true;
 			}
 			var zTree = $.fn.zTree.getZTreeObj("setSale_ztree"); 
 			zTree.updateNode(treeNode,false);//但修改选中状态不触发联动勾选
 			return false;
 		 }
  	 }
 	 
 	 //保存
 	 $("#setSaleDialog_save").click(function(){
 		var zTree = $.fn.zTree.getZTreeObj("setSale_ztree"); 
 		var checkNodes = zTree.getCheckedNodes();
 		var deptCodeStr="";
 		var deptName="";
 		for(var i=0;i<checkNodes.length;i++){
 			if(i==0){
 				deptCodeStr=checkNodes[i].deptCode;
 			}else{
 				deptCodeStr+=","+checkNodes[i].deptCode;
 			}
 		}
 		//console.log(deptCodeStr);
 		var params={"deptCodeStr":deptCodeStr}
 		$.post("../organization/updateOrgSetSale.do",params,function(res){
 			if(res.status="SUCCESS"){
 				$("#setSale_dialog").hide();
 			}
 			alert(res.msg);
 		})
 		 
 	 });
 	
 /**
  * End 设置销售部门===========================================================================
  */
 
 	