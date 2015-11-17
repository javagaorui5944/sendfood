require.config({
    baseUrl :  BEEN.STATIC_ROOT
});
require(['lib/jquery','util/request','util/Headertip'], function($,request,Headertip) {

   var index = {
     init:function(){
    	 this._login();
     },
     _login:function(){
         $("#J-submit").click(function(){
           var telephone = $("#J-telephone").val().trim();
           var password = $("#J-password").val().trim();
           if("" == telephone){
               Headertip.error("电话号码不能为空",true,3000);
               return;
           }
           if("" == password){
             Headertip.error("密码不能为空",true,3000);
             return;
           }
           Headertip.info("正在登录中,请稍候...",true,30000);
           request.post("/userManage/staffLogin",{
        	   telephone:telephone,
               password:password
           },function(ret){
    		 if(1 == ret.code){
                window.location.href="/admin";
             }else{
            	  Headertip.error(ret.msg,true,3000);
             }
    	 });
        });
     }
   };

     index.init();
});