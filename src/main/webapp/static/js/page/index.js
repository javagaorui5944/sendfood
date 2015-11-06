require.config({
    baseUrl :  BEEN.STATIC_ROOT
});
require(['lib/jquery','modules/lazyload','lib/jqueryPlugs/jquery.cookie','util/request'
         ,'util/Headertip','modules/dialog/cChooseSchoolDialog','modules/dialog/cloginDialog','lib/juicer'], 
		function($,lazyload,cookie,request,Headertip,cChooseSchoolDialog,cloginDialog) {
	
	   var index = {
		   init:function(){
			   this._hover();
			   this._isFirst();
			   this._login();
			   this._bookfood();
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
	   					getNextWeekOrder(schoolId);
	   					var a = $("#J-schools  a[data-schoolid='"+schoolId+"']");
	   					$("#J-choose-sch").html(a.html());
	   					//点击弹出框地址或者改变地址
	   					$("#J-schools a").click(function(){
	   						$(".food-wrap").html("奋力加载中....");
	   						var schoolId = $(this).attr("data-schoolid");
	   						getNextWeekOrder(schoolId);
							var schoolName = $(this).html();
							$("#J-choose-sch").html(schoolName);
						});
	   				   }); 
   			   }
   			   
   			   function getNextWeekOrder(schoolId){
				request.post("/order/getNextWeekOrder",{school_id:schoolId},function(ret){
					if(1 == ret.code){
						var foodtpl = '{@each snacks as item,index}'+
								'<div class="food-item {@if index % 5 == 0 && index != 0}food-item-first{@else if index == item.length}food-item-last{@/if} ">'+
								'<img data-src="http://211.155.92.139:9001/${item.snacks_pic}" class="food-img scroll-loading" />'+
								'<span class="food-name"> ${item.snacks_name} × <span>${item.snacks_stock_number}</span></span>'+
								'<span class="food-price"><span>¥${item.snacks_sell_price}</span>/份</span>'+
								'</div>'+
						  '{@/each}';
						var foodtemp = juicer(foodtpl,ret.data);
                        var cost = 0;
                        $(ret.data.snacks).each(function(){
                            cost += parseInt(this.snacks_sell_price) * parseInt(this.snacks_stock_number);
                        });
                        $("#J-cost").html(cost);
						$(".food-wrap").html(foodtemp);
						lazyload();
					}else{
						$(".food-wrap").html(ret.msg);
					}
				});
   			   }
   		   },
   		   //预定美食
   		   _bookfood:function(){
   			 $(".btn-order").click(function(){
   				 request.post("/order/scheduleOrder",{sure:false},function(ret){
   					 //还有未配送订单
   					 if(2 == ret.code){
   						 if(confirm(ret.msg)){
   							request.post("/order/scheduleOrder",{sure:true},function(ret){
   								if(1 == ret.code){
   									alert("预定成功");
   								}
   							});
   						 }
   					 }else if(1 == ret.code){
							alert("预定成功");
					 }else{
						alert(ret.msg);
					 }
   				 });
   			 });
   		   },
   		   //登录
   		   _login:function(){
   			   $("#J-login").click(function(){
   				   cloginDialog();
   			   });
   		   },
   		   //选择学校下拉框
   		   _hover:function(){
	   			$("#J-position").hover(function(){
	           	 $(this).addClass("hover");
	            },function(){
	           	 $(this).removeClass("hover");
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

