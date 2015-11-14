require.config({
    baseUrl :  BEEN.STATIC_ROOT
});
require(['lib/jquery','modules/baseModule','util/request','modules/bridge','modules/select/select','util/Headertip','lib/juicer'],
    function($,baseModule,request,bridge,select,Headertip) {

    var index = {
        init:function(){
            this._searchUser(); //搜素用户信息
            this._getUsersAndRoles();
            this._saveUserAndRole(); //保存用户对应的角色
        },
        //搜素用户信息
        _searchUser:function(){
            select.searchUserByuserName("#J-userName",function(staffId){
            	//选中之后的回调函数
            	//取得当前用户对应的角色，并且打勾
                request.post("/roleManage/getUserRolesById",{staff_id:staffId},function(ret){
                    if(1 == ret.code){
                        $("#J-roles .list-group-item").find(".chk-item").each(function(){
                            var roleId = $(this).attr("roleId"),self = this;
                            $(self).prop("checked",false);
                            $(ret.data).each(function(index,value){
                            	
                                if(roleId == value.role_id){
                                    $(self).prop("checked",true);
                                }
                            });
                        });
                    }else{
                        Headertip.error("500服务器错误",true,3000);
                    }
                });
            });
        },
        //如果是修改用户角色，就是同时取得用户的信息和角色的信息
        _getUsersAndRoles:function(){
            var userId = $("#J-userId").val().trim();
            //取得当前所有的角色
            request.post("/roleManage/getAllRolesNoPage",{},function(ret){
                if(1 == ret.code){
                    var tpl =  '{@each data as roleItem,index}'+
                                '<li class="list-group-item clearfix inbox-item">'+
                                    '<label class="label-checkbox inline">'+
                                        '<input type="checkbox" class="chk-item" roleId="<%=roleItem.role_id%>">'+
                                        ' <span class="custom-checkbox"></span>'+
                                    '</label>'+
                                    ' <span class="from"><%=roleItem.role_name%></span>'+
                                     '<span class="detail">'+
                                    '<%=roleItem.role_note%>'+
                                    '</span>'+
                                 ' </li>'+
                               '{@/each}';
                       var html = juicer(tpl,ret);
                       $("#J-roles").html(html);
                }else{
                    Headertip.error(ret.msg,true,3000);
                }
            });
            if($("#J-userName").val().trim() == ""){
                return;
            }
            //取得当前用户对应的角色，并且打勾
            request.post("/roleManage/getUserRolesById",{userId:userId},function(ret){
                if(1 == ret.code){
                    $("#J-roles .list-group-item").find(".chk-item").each(function(){
                        var roleId = $(this).attr("roleId"),self = this;
                        $(self).prop("checked",false);
                        $(ret.data).each(function(index,value){
                        	
                            if(roleId == value.role_id){
                                $(self).prop("checked",true);
                            }
                        });
                    });
                }else{
                    Headertip.error(ret.msg,true,3000);
                }
            });
        },
        //保存用户对应的角色
        _saveUserAndRole:function(){
            $("#J-save").click(function(){
                var roleId = [],
                    userId =$("#J-userName").attr("real-value");
                $("#J-roles .list-group-item").find(".chk-item:checked").each(function(){
                    roleId.push($(this).attr("roleid"));
                });

                if($("#J-userName").val().trim() == ""){
                    Headertip.error("请输入用户",true,3000);
                    return;
                }

                if(roleId.length == 0){
                    Headertip.error("请选择角色",true,3000);
                    return;
                }
                roleId = roleId.join(",");
                request.post("/roleManage/saveUserRoles",{
                    userId :userId,
                    roleId:roleId
                }, function(ret){
                    if(1 == ret.code){
                        Headertip.success(ret.msg,true,3000);
                    }else{
                        Headertip.error(ret.msg,true,3000);
                    }
                });
            });
        }
    };

    index.init();
});