

require.config({
    baseUrl :  BEEN.STATIC_ROOT
});

require(['lib/jquery','modules/baseModule','util/request','modules/bridge','modules/dialog/manageRole','util/Headertip']
,function($,baseModule,request,bridge,manageRole,Headertip){

    var index = {
        init:function(){
            this._manageRole();
            this._saveRoleLimits();
        },
        //保存角色权限
        _saveRoleLimits:function(){
            $("#J-save").click(function(){
            	   var limitId = [],
                   roleId =$(".roleSelected").attr("data-roleid");
               $("#J-Limits tr").find(".chk-item:checked").each(function(){
            	   limitId.push($(this).attr("limitid"));
               });

               if(roleId == null){
                   Headertip.error("选中角色",true,3000);
                   return;
               }

               if(limitId.length == 0){
                   Headertip.error("请选择权限",true,3000);
                   return;
               }
               limitId = limitId.join(",");
               request.post("/limitsManage/saveRoleLimits",{
            	   limitId :limitId,
                   roleId:roleId
               }, function(ret){
                   if(1 == ret.code){
                       Headertip.success(ret.msg,true,3000);
                   }else{
                       Headertip.error(ret.msg,true,3000);
                   }
               });
            });
        },
        //选中角色变色并且请求相对应的权限
        _manageRole:function(){
        	$("#J-roleList > tr").click(function(){
        		$("#J-roleList > tr").removeClass("roleSelected");
        		$(this).addClass("roleSelected");
        		var roleId = $(this).attr("data-roleId");
        		  //取得当前用户对应的角色，并且打勾
                request.post("/limitsManage/getRoleOfLimits",{roleId:roleId},function(ret){
                	
                    if(1 == ret.code){
                        $("#J-Limits").find(".chk-item").each(function(){
                            var limitid = $(this).attr("limitid"),self = this;
                            $(self).prop("checked",false);
                            $(ret.data).each(function(index,value){
                                if(limitid == value.limits_id){
                                    $(self).prop("checked",true);
                                }
                            });
                        });
                    }else{
                        Headertip.error("500服务器错误",true,3000);
                    }
                });
        	});
        }
    };

    index.init();

});