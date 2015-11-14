require.config({
    baseUrl :  BEEN.STATIC_ROOT
});
require(['lib/jquery','lib/jqueryPlugs/jquery.cookie','util/request',
         'modules/dialog/cChooseSchoolDialog','modules/dialog/cloginDialog','lib/juicer'],
		function($,cookie,request,cChooseSchoolDialog,cloginDialog) {
	   var index = {
		   init:function(){
//			   this._hover();
			   this._isFirst();
			   this._login();
			   this._animate();
		   },
		   //是否是第一次访问该页面，如果是会弹出选择地址的框
   		   _isFirst:function(){
   			   var schoolId = $.cookie("schoolId");
   			   //弹出学校选择框
   			   if(schoolId == undefined){
   				cChooseSchoolDialog(function(schoolId){
   					$.cookie("schoolId",schoolId);
   				});
   			   //学校已经确定，根据学校id取得学校本期的标准订单
   			   }else{
   				  request.post("/customer/getAllSchool",{},function(ret){
	   					//替换头部地址
	   					var tpl = '{@each data as item}'+
	   								'<li><a href="#" data-schoolid="${item.school_id}">${item.school_name}</a></li>'+
	   					 			'{@/each}';
	   				    var temp =  juicer(tpl,ret);
	   					$("#J-schools").html(temp);
	   					var a = $("#J-schools  a[data-schoolid='"+schoolId+"']");
	   					$("#J-choose-sch").html(a.html());
	   					//点击弹出框地址或者改变地址
	   					$("#J-schools a").click(function(){
	   						$(".food-wrap").html("奋力加载中....");
	   						var schoolId = $(this).attr("data-schoolid");
							var schoolName = $(this).html();
							$("#J-choose-sch").html(schoolName);
						});
	   				   }); 
   			   }
   			},
   		   //登录
   		   _login:function(){
   			   $("#J-login").click(function(){
   				   cloginDialog();
   			   });
   		   },
		   //动画效果
		   _animate:function(){
			   var ie6 = !-[1,] && !window.XMLHttpRequest;
			   if (ie6) {
				   return false;
			   }
			   var headerHeight = $('.page-header').outerHeight(), flag,
			   topNav = $(".header-top");
			   $(window).scroll(function(){
				   var scrollTop = $(window).scrollTop();
				   if (scrollTop > headerHeight) {
					   if (!flag) {
						   topNav.css({"position":"fixed","height":"0"}).animate({"top" : "0","height":"36px"}, 200);
						   flag = true;
					   }
				   } else {
					   if (flag) {
						   topNav.css({"position":"static"});
						   flag = false;
					   }
				   }

			   });
		   }
	   };
	   index.init();
});  

