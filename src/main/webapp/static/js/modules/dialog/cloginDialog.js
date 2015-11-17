/**
 * 学生端登录弹出框
 * author：吉鹏
 */
define(["lib/jquery","widget/dialog/cdialog",'util/request'],function($,dialog,request){
	var LoginDialog = {
	
			"Dailog":function(){
				return  ['<form class="win-form" id="J_login_form"  method="post" action="" onsubmit="return false;">',
				         '<p class="" id="J-valid"></p>',
				         '<div><input type="text" name="username" id="J-telephone"  autocomplete="off"  placeholder="请输入自己的用户名" class="win-email J-validate-defalut" /></div>',
		        
		          '<div><input type="password" name="password" id="J-password" autocomplete="off"   placeholder="密码"  class="win-pw J-validate-defalut" datatype="*" nullmsg="请输入密码！"/>',
		          '<p class="Validform_checktip" id="J-Validform_loading"></p></div>',
		          '<input type="hidden" name="type" id="J-ipt-type"> ',
		           '<button class="btn-u-login" id="J_btn-u-login" id="J-Validform_loading">登录</button>',
		        '</form>'].join('');
			},
			"init":function(){
					var optdef={
							 dialog_id:'J_flay-win'	 
							,dialog_tit:'用户登录'
							,dialog_body:LoginDialog.Dailog() //jQuery
							,dialog_footer:""
							,dialog_style:null
							,click_mask_fire:true
							,callback:function () {
								$("#J_btn-u-login").click(function(){
									 var telephone = $("#J-telephone").val().trim();
							           var password = $("#J-password").val().trim();
							           $("#J-valid").removeClass("v-error").addClass("v-loading").html("用户登录中,请稍等...");;
							           if("" == telephone){
							        	   $("#J-valid").removeClass("v-loading").addClass("v-error").html("电话号码不能为空");
							        	   
							               return;
							           }
							           if("" == password){
							        	   $("#J-valid").removeClass("v-loading").addClass("v-error").html("密码不能为空");
								        	 
							             return;
							           }
							           request.post("/userManage/customerLogin",{
							        	   telephone:telephone,
							               password:password
							           },function(ret){
							    		 if(1 == ret.code){
							                window.location.href="/welcome";
							             }else{
							            	 $("#J-valid").removeClass("v-loading").addClass("v-error").html(ret.msg);
							             }
							    	 });
								});
							}
						 };
					   dialog.init(optdef);
				
			}
			
	};
	return LoginDialog.init;
		
});