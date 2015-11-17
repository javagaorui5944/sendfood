

require.config({
    baseUrl :  BEEN.STATIC_ROOT
});

require(['lib/jquery','modules/baseModule','util/request','modules/bridge','modules/dialog/manageRole'
        ,'util/Headertip','widget/AjaxPager']
,function($,baseModule,request,bridge,manageRole,Headertip,ajaxpager){

    var index = {
        init:function(){
            this._deletRole();
            this._addRole();
            this._getAllRoles();
            this._manageRole();
            this._deleteRole();
        },
        //取得所有角色
        _getAllRoles:function(){
            var pager = $(".pagination");
            ajaxpager.init({
                elem:pager,
                pagerUrl:'/roleManage/getRoleList',
                current:1,
                handle:function(resp){
                    if(resp.code == 1){

           var  tpl =  '{@each roles as role}' +
                        '<tr role-id="${role.role_id}">'+
                        '<td> ${role.role_id}</td>'+
                        '<td class="role-name"> ${role.role_name}</td>'+
                        '<td class="role-note"> ${role.role_note}</td>'+
                        '<td>'+
                        '{@if role.role_status == 10}'+
                        '有效'+
                        '{@else}'+
                        '无效'+
                        '{@/if}'+
                        '</td>'+
                        '<td data-userid= "${role.role_id}" >'+
                        '<a class="btn btn-sm btn-success J-manageRole" data-action="manage">管理</a> '+
                        '<a class="btn btn-sm btn-success  J-delete" data-id="${role.role_id}">删除</a>'+
                        '</td>'+
                        '</tr>'+
                       '{@/each}';
                        tmp = juicer(tpl,resp.data);
                        $("#J-roleList").html(tmp);
                    }
                    else{
                        Headertip.error(resp.msg);
                    }
                }
            });
        },
        //删除
        _deletRole:function(){
            var userId = 0;
            $(".event-delete").click(function(){
            	var userId = $(this).attr("data-id");
         	   if(confirm("确定删除吗？")){
         		   request.post("/userManage/deleteUser",{id:userId},function(ret){
         			   if(1 == ret.code){
         				   Headertip.success("删除成功",true,3000);
                  		  setTimeout(function(){
                  			  window.location.reload();
                  		  },500);
         			   }else{
         				   Headertip.error(resp.msg,true,3000);
         			   }
         		   });
         	   }
            });
        },
        //管理角色
        _manageRole:function(){
            $("#J-roleList").on("click",".J-manageRole",function(){
                $(this).removeClass("J-manageRole").addClass("J-save").html("保存");
                var tr = $(this).parents("tr");
                var roleName = tr.find(".role-name").html().trim(),
                    roleId = tr.attr("role-id"),
                    roleNote = tr.find(".role-note").html().trim();
                tr.find(".role-name").html('<input type="text" class="form-control" value="'+roleName+'">');
                tr.find(".role-note").html('<input type="text" class="form-control" value="'+roleNote+'">');
            });
            $("#J-roleList").on("click",".J-save",function(){
                $(this).removeClass("J-save").addClass("J-manageRole").html("管理");
                var tr = $(this).parents("tr");
                var roleName = tr.find(".role-name > input").val().trim(),
                    roleId = tr.attr("role-id"),
                    roleNote = tr.find(".role-note> input").val().trim();
                tr.find(".role-name").html(roleName);
                tr.find(".role-note").html(roleNote);

                request.post("/roleManage/updateRole",{
                    role_id:roleId,
                    role_name:roleName,
                    role_note:roleNote
                },function(res){
                    if(1 == res.code){
                        Headertip.success("角色更新成功成功",true,3000);
                    }
                })
            });
        },
        //删除角色
        _deleteRole:function(){
            $("#J-roleList").on("click",".J-delete",function(){
                if(confirm("确认删除角色？所有拥有该角色的用户都不可用")){
                    var tr = $(this).parents("tr");
                    var roleId = tr.attr("role-id");
                    request.get("/roleManage/deleteRoleByid",{
                        id:roleId
                    },function(ret){
                        if(ret == true){
                            Headertip.success("删除成功",true,3000);
                            tr.remove();
                        }else{
                            Headertip.error("删除失败",true,3000);
                        }
                    });
                }
            });
        },
        //新增
        _addRole:function(){
        	 $("#J-addRole").click(function(){
        		 manageRole.init({
                     modalTitle:"新增角色",
                     contentHtml: "加载中,请稍等",
                     //初始化事件
                     initFunc:function(){}
                 });
        		 manageRole.save(function(dialog){
                     var roleName = $("#J-roleName").val().trim();
                     var roleNote = $("#J-roleNote").val().trim();
                    
                     if(roleName == ""){
                  	   Headertip.error("角色名不能为空",true,3000);
                  	   return;
                     }
                     if(roleNote == ""){
                    	   Headertip.error("角色备注不能为空",true,3000);
                    	   return;
                       }
                 request.post('/roleManage/addOrReviseRole',
                   {
              	   		role_id:-1,
              	   		role_name:roleName,
              	   	    role_note:roleNote,
              	   	    role_status:10
                     },function(resp){
                  	  if(resp.code == 1){
                  		  Headertip.success("增加成功",true,3000);
                  		  setTimeout(function(){
                  			  window.location.reload();
                  		  },500);
                  	  }else{
                  		  Headertip.error(resp.msg,true,3000);
                  	  }
                     });
            });
          });
        }
    };

    index.init();

});