require.config({
    baseUrl :  BEEN.STATIC_ROOT
});
require(['lib/jquery','modules/baseModule','util/request'], function($,baseModule,request) {

   var index = {
     init:function(){
    	 this._login();

     },

     _login:function(){
//    	 request.post("/guest/placeAnOrder",{},function(ret){
//    		 console.log(ret);
//    	 });
    	
     },
   };

     index.init();
});