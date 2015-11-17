require.config({
    baseUrl :  BEEN.STATIC_ROOT
});
require(['lib/jquery','lib/basic/bootstrap','util/request','lib/jqueryPlugs/validform'], function($,bs,request) {

   var welcome = {
     init:function(){
    	//点击预定
    	 this._book();
         

     },
     //点击预定
     _book:function(){
    	 //判断是否已经登陆过系统，如登录过，就直接提交，如果没有就弹出登录框
    	 $("#J-submit").Validform({
			   //提交之前判断是否自动登录
			   beforeSubmit:function(form){
		      		$("#J-Validform_loading").removeClass().addClass("Validform_checktip Validform_loading").html("登录中，请稍候...");
		      		
				},
				//后台回调
				callback:function(data){
					console.log(data);
				},
				tiptype:function(msg,o,cssctl){
					var objtip=o.obj.siblings(".Validform_checktip");
					if(o.type==2)
						{
						  objtip.removeClass().addClass("Validform_checktip");//for ignore;
						  objtip.text("");
						}else
						{
						 cssctl(objtip,o.type);
						 objtip.text(msg);
						}
				},
				ajaxPost:true
			});
     },
     //点击加载模版
     _showTemplate:function(){
    	 
     }
     
   };

     welcome.init();
});