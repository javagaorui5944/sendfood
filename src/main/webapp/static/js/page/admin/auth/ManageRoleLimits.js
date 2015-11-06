require.config({
    baseUrl :  BEEN.STATIC_ROOT
});
require(['lib/jquery','modules/baseModule','util/request','modules/bridge','modules/select/select','util/Headertip','lib/juicer'],
    function($,baseModule,request,bridge,select,Headertip) {

        var index = {
            init:function(){
                //this._searchUser(); //搜素用户信息
                //this._getUsersAndRoles();
                //this._saveUserAndRole(); //保存用户对应的角色
            },
            //搜素用户信息
            _searchUser:function(){
                select.searchUserByuserName("#J-userName");
            },
            //如果是修改用户角色，就是同时取得用户的信息和角色的信息
            _getUsersAndRoles:function(){
                var userId = $("#J-userId").val().trim();

                //取得当前所有的角色
                request.post("/user/getRoles",{},function(ret){
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
                        var html = juicer(tpl,ret.data);
                        $("#J-roles").html(html);
                    }else{
                        Headertip.error(ret.msg,true,3000);
                    }
                });

                //取得当前用户对应的角色，并且打勾
                request.post("/user/getUserRoles",{userId:userId},function(ret){
                    if(1 == ret.code){
                        $("#J-roles .list-group-item").find(".chk-item").each(function(){
                            var roleId = $(this).attr("roleId"),self = this;
                            $(ret.data).each(function(index,value){
                                if(value == roleId){
                                    $(self).attr("checked",'true');
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
                        roleId.push($(this).attr("roleId"));
                    });

                    if($("#J-userName").val().trim() == ""){
                        Headertip.error("请输入用户",true,3000);
                        return;
                    }

                    if(roleId.length == 0){
                        Headertip.error("请选择角色",true,3000);
                        return;
                    }



                    request.post("/user/saveUserAndRole",{
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