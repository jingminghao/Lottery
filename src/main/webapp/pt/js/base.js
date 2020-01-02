;(function ($, window, document, undefiend) {
  Array.prototype.insert = function (index, item) {
    this.splice(index, 0, item);
  };
  // 对Date的扩展，将 Date 转化为指定格式的String
  // 月(M)、日(d)、小时(h)、分(m)、秒(s)、季度(q) 可以用 1-2 个占位符，
  // 年(y)可以用 1-4 个占位符，毫秒(S)只能用 1 个占位符(是 1-3 位的数字)
  // 例子：
  // (new Date()).Format("yyyy-MM-dd hh:mm:ss.S") ==> 2006-07-02 08:09:04.423
  // (new Date()).Format("yyyy-M-d h:m:s.S")      ==> 2006-7-2 8:9:4.18
  Date.prototype.Format = function (fmt) { //author: meizz
    var o = {
      "M+": this.getMonth() + 1, //月份
      "d+": this.getDate(), //日
      "h+": this.getHours(), //小时
      "m+": this.getMinutes(), //分
      "s+": this.getSeconds(), //秒
      "q+": Math.floor((this.getMonth() + 3) / 3), //季度
      "S": this.getMilliseconds() //毫秒
    };
    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o)
      if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
    return fmt;
  }


  //异步执行工具 window 下的对象
  $(function () {
    (function () {
      // 弹窗显示事件
      $.extend({
        dialogModal: function (id) {
          var target = $("#" + id);
          target.show();
          $('body').css('overflow', 'hidden');
          return {
            show: function (arg) {
              target.show(arg);
            },
            hide: function (arg) {
              target.hide(arg);
              $('body').css('overflow', 'auto');
            }

          }
        },
        //保留两位小数
        //功能：将浮点数四舍五入，取小数点后2位
        toDecimal: function (x) {
          var f = parseFloat(x);
          if (isNaN(f)) {
            return;
          }
          f = Math.round(x * 100) / 100;
          return f;
        },
        confirm: function (msg, handler) {
          this.isOk = true;
          this.returnMsg = null;
          var _confirm = $(".wx-alert");
          if (_confirm) {
            _confirm.remove();
          }
          var _model = $(".yunat_maskLayer");
          if (_model) {
            _model.remove();
          } else {
            _model = $('<div class="yunat_maskLayer"></div>');
          }
          _confirm = $('<div class="wx-alert ng-hide">' +
            '<div class="alert-title" style="cursor: auto;">' +
            '    <span>操作注意</span>' +
            '    <a href="javascript:void(0);" class="close alert-close wx-alert-close"></a>' +
            '</div>' +
            '<div class="alert-content">' + msg + '</div>' +
            '<div class="alert-btn">' +
            '    <button id="wx-alert-save" class="btn btnBlue">确定</button>' +
            '    <button id="wx-alert-cancel" class="btn alert-cancel">取消</button>' +
            '</div>' +
            '</div>');
          $(window.document.body).append(_confirm);
          $(window.document.body).append(_model);
          $(".wx-alert-close").on("click", function () {
            handler(this, false);
            $(".wx-alert").addClass("ng-hide");
            $(".yunat_maskLayer").hide();
          });
          $("#wx-alert-save").on("click", function () {
            handler(this, true);
            $(".wx-alert").addClass("ng-hide");
            $(".yunat_maskLayer").hide();
          });
          $("#wx-alert-cancel").on("click", function () {
            handler(this, false);
            $(".wx-alert").addClass("ng-hide");
            $(".yunat_maskLayer").hide();
          });
          $(".yunat_maskLayer").show();
          $(".wx-alert").removeClass("ng-hide");


          return this;
        },
        //异步执行
        syncRun: function (name) {
          setTimeout(function () {
            var objs = name.split(".");
            if (objs.length > 1) {
              var obj = window[objs[0]];
              var method = objs[objs.length - 1];
              for (var i = 1; i < objs.length - 1; i++) {
                obj = obj[objs[i]];
              }
              obj[method]();
            } else {
              window[name]();
            }
          }, 0);
        },
        MMOTable: function (selector) {
          var _default = {
            height: 505,
            striped: true,
            cache: true,
            pagination: true,
            sidePagination: 'server',
            locales: "zh-CN",
            pageSize: 10,
//                		pageList:[10,25,50],
            method: "post",
            contentType: "application/x-www-form-urlencoded",//默认表单形式发送
            args: {
              currentPage: 1,
              current: 1,
              pageSize: this.pageSize
            },
            handler: function (res) {
            },
            formatLoadingMessage: function () {
              return "数据正在加载中...";
            },
            formatNoMatches: function () {
              return '无符合条件的记录';
            },
            onLoadSuccess: function (data) {
              //console.info(data);
            },
            onLoadError: function (data) {
              $(selector).bootstrapTable('removeAll');
            },
            queryParams: function (res) {
              if (this.pagination) {
                this.args.currentPage = (res.offset / res.limit + 1);
                this.args.current = (res.offset / res.limit + 1);
                this.args.pageSize = res.limit;
              }
              if(this.args.current!=1){
            	  this.args.isSearch='0';
              }
              return this.args;
            },
            responseHandler: function (res) {
              this.handler(res);
              if (this.pagination) {
                if (res.list) {
                  $(res.list).each(function (i, data) {
                    data.index = (res.current - 1) * res.pageSize + i + 1;
                  });
                } else {
                  res.list = [];
                }
                return {
                  pageSize: res.pageSize,
                  rows: res.list,
                  total: res.totalRecord
                };
              } else {
                if (res) {
                  $(res).each(function (i, data) {
                    data.index = i + 1;
                  });
                } else {
                  res = [];
                }
                return {
                  rows: res
                };
              }

            }
          };
          if (arguments.length > 1) {
            for (var key in arguments[1]) {
              _default[key] = arguments[1][key];
            }
          }
          //
          $(selector).bootstrapTable(_default);
          //
          var $this = this;
          return {
            //查询方法
            query: function (data) {
              /*if ($this.pagination) {
                data.currentPage = 1;
                data.current = 1;
              }*/
        	 data.currentPage = 1;
             data.current = 1;
              $(selector).bootstrapTable('refresh', {
                query: data
              });
              //将页码返回第一页
              $(selector).bootstrapTable('refreshOptions',{pageNumber:1});
            },
            method: function (me, params) {
              return $(selector).bootstrapTable(me, params);
            }

          }
        }
      });

      // 弹窗关闭事件
      $(document).on('click', '.dialog_cancel, .pop_closed', function () {
        $(this).closest(".mask").hide();
        $('body').css('overflow', 'auto');
      });

      function checkIEVersion() {
        var ua = navigator.userAgent;
        var s = "MSIE";
        var i = ua.indexOf(s)
        if (i >= 0) {
          //获取IE版本号
          var ver = parseFloat(ua.substr(i + s.length));
          if (ver < 9) {
            $("body").append("<div class='versionTest'>" +
              "<div class='clearfix text-center versionTest-inner'>" +
              "<span>您的浏览器版本太低了！请使用IE9以上版本！</span>" +
              "<span class='close-box text-right'>" +
              "<a class='m-r-20 versionTest-close' >关闭</a>" +
              "</span></div></div>")
          }
        }
      }

      var browser = checkIEVersion();

      $(".versionTest-close").click(function () {
        $(".versionTest").hide();
      });
    })();

    // 主动营销节点窗口收缩&&展开
    $(document).on("click", ".sanjiaoPosition", function () {
      $(".bannerCommStyle").toggleClass("areaHeightAuto")
      $(".sanjiaoPosition").toggleClass("upSanjiao").toggleClass("downSanjiao");
    });

    //菜单折叠
    $(".menu-title").click(function () {
      $(this).siblings().toggle();
      $(this).children(".menu-jt").toggleClass("menu-jt-up");
    })
    //tab  切换
    $(".title-tab .tab-nav").click(function () {
      $(this).addClass("selected").siblings().removeClass("selected")
      $("." + $(this).data("id")).show().siblings(".main-bd").hide()
    })


    $(".account-info img").click(function () {
      //$.dialogModal("userCenter");
    })

    //用户中心  修改昵称
    var edit_flag = false;
    $(".name-edit").click(function () {

      if (!edit_flag) {
        edit_flag = true;
        $(".name-edit").text("保存");
        $(".name-cancel").removeClass("ng-hide");
        $("input[name='userName']").prop("readonly", false).val("").focus();
      } else {
        edit_flag = false;
        $(".name-cancel").addClass("ng-hide");
        $(".name-edit").text("编辑");
        $("input[name='userName']").prop("readonly", true);
        $("#userName").val($("input[name='userName']").val())
      }
    })
    //用户中心  取消修改
    $(".name-cancel").click(function () {
      edit_flag = false;
      $(this).addClass("ng-hide");
      $(".name-edit").text("编辑");
      $("input[name='userName']").val($("#userName").val())
    })

    //用户中心 tab  切换
    $("#userCenter .title-tab .tab-nav").click(function () {
      $(this).addClass("selected").siblings().removeClass("selected");
      $("." + $(this).data("content")).show().siblings(".userMainbox").hide()
    })

  });
})(jQuery, window, document);

$(window).load(function () {
  $('.loading').fadeOut(1000);
//
//
//	var options =
//	{
//		imageBox: '.imageBox',
//		thumbBox: '.thumbBox',
//		spinner: '.spinner',
//		imgSrc: 'avatar.png'
//	}
//	var cropper;
//	document.querySelector('#file').addEventListener('change', function(){
//		var reader = new FileReader();
//		reader.onload = function(e) {
//			options.imgSrc = e.target.result;
//			cropper = new cropbox(options);
//		}
//		reader.readAsDataURL(this.files[0]);
//		this.files = [];
//		if(this.files[0].size > 1024*1024*5){
//			alert("上传的文件不得大于5M!");
//			return false;
//		}
//		$(".box").hide();
//		$(".cropBox").show();
//	})
//	document.querySelector('#btnCrop').addEventListener('click', function(){
//		var img = cropper.getDataURL()
//		document.querySelector('.cropped').innerHTML += '<img src="'+img+'">';
//		$.dialogModal("userCenter").hide();
//		$(".account-info img").attr("src",img)
//	})

});

/**
 * 单价、金额  格式化转换
 * @param s
 * @param n
 * @returns {String}
 */
function fmoney(s, n) {
  if (s != null && s != '') {
    n = n > 0 && n <= 20 ? n : 2;
    s = parseFloat((s + "").replace(/[^\d\.-]/g, "")).toFixed(n) + "";
    var l = s.split(".")[0].split("").reverse(),
      r = s.split(".")[1];
    t = "";
    for (i = 0; i < l.length; i++) {
      t += l[i] + ((i + 1) % 3 == 0 && (i + 1) != l.length ? "," : "");
    }
    return t.split("").reverse().join("") + "." + r;
  } else {
    var result = "0";
    if (n > 0) {
      for (i = 0; i < n; i++) {
        if (i == 0) {
          result += ".0";
        } else {
          result += "0";
        }
      }
    }
    return result;
  }
};

/**
 * 判断 str 字符串中是否含有字符串 subStr
 * @param {} str 原字符串
 * @param {} subStr 要查找的字符串
 * @param {} isIgnoreCase 是否忽略大小写
 * @return {Boolean}
 */
function contains(str, subStr, isIgnoreCase) {
  if (isIgnoreCase) {
    // 忽略大小写
    str = str.toLowerCase();
    subStr = subStr.toLowerCase();
  }
  var startChar = subStr.substring(0, 1);
  var strLen = subStr.length;
  for (var j = 0; j < str.length - strLen + 1; j++) {
    if (str.charAt(j) == startChar) {
      /* 如果匹配起始字符,开始查找 */
      if (str.substring(j, j + strLen) == subStr) {
        /*如果从j开始的字符与 str 匹配 */
        return true;
      }
    }
  }
  return false;
};

//JMH 菜单单机事件
function menuLink(parentId,url,rowId,buttonIds,buttonTypes){
	$.post("../login/getMenuLink.do",{parentId:parentId,rowId:rowId,buttonIds:buttonIds,buttonTypes:buttonTypes}
	,function(res){
		window.location.href=url;
	})
}
//返回上一页(URL)
function goback(url){
	$.post("../login/gobackByThreeLevel.do",function(res){
		//window.history.go(-1); 
		window.location.href=url;
	})
}
//通过超链接进入其他菜单里面的页面,返回上一页(URL)
function goback2(url){
	$.post("../login/gobackByThreeLevelTwo.do",{url:url},function(res){
		window.location.href=url;
	})
}



//初始化按钮隐藏
initButton();

function initButton(){
	//获取当前用户的rowId、buttonIds、buttonTypes
	$.post("../login/getMenuLinkMessage.do",function(res){
		roleButtons(res.buttonTypes);
	}); 
}
//按钮处理
function roleButtons(buttonTypes){
	if(buttonTypes!='' && typeof(buttonTypes)!="undefined"){
		var buttonList=buttonTypes.split(',')
		for(var i=0;i<buttonList.length;i++){
			if(buttonList[i]=="insert_public"){//公用-新增
				$(".insert_public").css("display","inline-block");
			}else if(buttonList[i]=="update_public"){//公用-修改（编辑）
				$(".update_public").css("display","inline-block");
				//等级专区-等级变更-table 等级修改
				$('.gradeChange-table').bootstrapTable('showColumn', 'option');
				
			}else if(buttonList[i]=="delete_public"){//公用-删除
				$(".delete_public").css("display","inline-block");
			}else if(buttonList[i]=="enabled_public"){//公用-开启（启用）
				$(".enabled_public").css("display","inline-block");
			}else if(buttonList[i]=="disable_public"){//公用-关闭（禁用）
				$(".disable_public").css("display","inline-block");
			}else if(buttonList[i]=="application_store"){//应用到所有店铺
				$(".application_store").css("display","inline-block");
			}else if(buttonList[i]=="export_public"){//公用-导出
				$(".export_public").css("display","inline-block");
			}else if(buttonList[i]=="import_public"){//公用-导入
				$(".import_public").css("display","inline-block");
			}else if(buttonList[i]=="exchange_public"){//公用-兑换
				$(".exchange_public").css("display","inline-block");
			}else if(buttonList[i]=="download_template"){//公用-下载模板
				$(".download_template").css("display","inline-block");
			}else if(buttonList[i]=="create_automatic_label"){//创建自动标签
				$(".create_automatic_label").css("display","inline-block");
			}else if(buttonList[i]=="create_manual_label"){//创建手动标签
				$(".create_manual_label").css("display","inline-block");
			}else if(buttonList[i]=="update_label"){//编辑标签
				$(".update_label").css("display","inline-block");
			}else if(buttonList[i]=="delete_label"){//删除标签
				$(".delete_label").css("display","inline-block");
			}else if(buttonList[i]=="assign_permissions"){//分配权限
				$(".assign_permissions").css("display","inline-block");
			}else if(buttonList[i]=="insert_organization"){//新增组织
				$(".insert_organization").css("display","inline-block");
			}else if(buttonList[i]=="update_organization"){//修改组织
				$(".update_organization").css("display","inline-block");
			}else if(buttonList[i]=="delete_organization"){//删除组织
				$(".delete_organization").css("display","inline-block");
			}else if(buttonList[i]=="setting_sales_department"){//设置销售部
				$(".setting_sales_department").css("display","inline-block");
			}else if(buttonList[i]=="setting_channel"){//设置门店
				$(".setting_channel").css("display","inline-block");
			}else if(buttonList[i]=="setting_area"){//设置区域
				$(".setting_area").css("display","inline-block");
			}else if(buttonList[i]=="distribution_member"){//分配员工
				$(".distribution_member").css("display","inline-block");
			}else if(buttonList[i]=="delete_member"){//删除员工
				$(".delete_member").css("display","inline-block");
			}else if(buttonList[i]=="create_task"){//创建(新建)任务
				$(".create_task").css("display","inline-block");
			}else if(buttonList[i]=="delete_task"){//删除(取消)任务
				$(".delete_task").css("display","inline-block");
			}else if(buttonList[i]=="create_clue"){//创建线索
				$(".create_clue").css("display","inline-block");
			}else if(buttonList[i]=="update_clue"){//修改线索
				$(".update_clue").css("display","inline-block");
			}
			else if(buttonList[i]=="create_activity"){//创建活动
				$(".create_activity").css("display","inline-block");
			}else if(buttonList[i]=="update_activity"){//编辑活动
				$(".update_activity").css("display","inline-block");
			}else if(buttonList[i]=="delete_activity"){//删除活动
				$(".delete_activity").css("display","inline-block");
			}
			else if(buttonList[i]=="create_group"){//创建分组
				$(".create_group").css("display","inline-block");
			}else if(buttonList[i]=="update_group"){//编辑分组
				$(".update_group").css("display","inline-block");
			}else if(buttonList[i]=="delete_group"){//创建分组
				$(".delete_group").css("display","inline-block");
			}
		}
	
	}
}
//为了让按钮权限显示正常,禁掉浏览器的前进后退按钮。
jQuery(document).ready(function () {
    if (window.history && window.history.pushState) {
        $(window).on('popstate', function () {
        	// 当点击浏览器的 后退和前进按钮 时才会被触发， 
            window.history.pushState('forward', null, ''); 
            window.history.forward(1);
        });
    }
//
    window.history.pushState('forward', null, '');  //在IE中必须得有这两行
    window.history.forward(1);
});