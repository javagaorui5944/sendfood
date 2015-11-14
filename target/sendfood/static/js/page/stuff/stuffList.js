/**
 * Created by Emory on 15/5/12.
 */

require.config({
    baseUrl :  BEEN.STATIC_ROOT
});

require(['lib/jquery','modules/baseModule','util/request','modules/bridge','modules/dialog/addUser','modules/dialog/ManageUser',
          'util/Headertip','widget/AjaxPager','lib/juicer']
,function($,baseModule,request,bridge,addUser,manageUser,Headertip,AjaxPager){

    var index = {
        init:function(){
            var pager = $("#page"),
                that = this;
            AjaxPager.init({
                elem:pager,
                pagerUrl:'/userManage/getStaffList',
                current:1,
                handle:function(resp){
                    if(resp.code == 1){
                        that._userList($("#userlist"),resp.data);
                    }
                }
            });
        },

        //删除某个管理员
        _deletUser:function(){
           $(".event-delete").click(function(){
        	   var userId = $(this).parent().attr("data-userid");
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
        //新增用户
        _addUser:function(){
              $("#J-addUser").click(function(){
                   addUser.init({
                       modalTitle:"新增用户",
                       contentHtml: "加载中,请稍等",
                       //初始化事件
                       initFunc:function(){}
                   });
                   addUser.save(function(dialog){
                       var userName = $("#J-stuffName").val().trim();
                       var telphone = $("#J-telphone").val().trim();
                       var staff_email = $("#J-email").val().trim();
                       if(userName == ""){
                    	   Headertip.error("员工名不能为空",true,3000);
                    	   return;
                       }
                       if(telphone == ""){
                    	   Headertip.error("员工电话不能为空",true,3000);
                    	   return;
                       }
                       if(staff_email == ""){
                    	   Headertip.error("员工邮箱不能为空",true,3000);
                    	   return;
                       }
                       //判断用户名是否重复
                       index._judgeUserRepeat(telphone,-1,function(){
                    	   request.post('/userManage/addOrReviseStaff',
                                   {
                              	   		id:-1,
                              	   		staff_name:userName,
                              	   		staff_tel:telphone,
                              	   		staff_email:staff_email,
                              	   		staff_status:10
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
            });
        },
        //判断用户名是否重复
        _judgeUserRepeat:function(tel,staff_id,callback){
        	 request.post('/userManage/judgeUserRepeat',{tel:tel,staff_id:staff_id},function(ret){
        		 if(1 == ret.code){
        			 callback();
        		 }else{
        			 Headertip.error(ret.msg,true,3000);
        		 }
        	 });
        },
        //管理员工
        _manageStaff:function(){
        	  $(".event-manage").click(function(){
        		  var userId = $(this).parent().attr("data-userid");
        		  manageUser.init({
                      modalTitle:"修改用户",
                      contentHtml: "加载中,请稍等",
                      //初始化事件
                      initFunc:function(dialog){
                    	 var  dialogHtml ='<form  class="form-horizontal" data-staffId="${data.staff_id}">'+
                          '               <div class="form-group">'+
                          '                   <label for="stuffName" class="col-sm-2 control-label">姓名</label>'+
                          '                   <div class="col-sm-10">'+
                          '                   <input type="text" class="form-control" id="J-stuffName" value="${data.staff_name}" placeholder="请输入姓名"/>'+
                          '                   </div>'+
                          '               </div>'+
                          '               <div class="form-group">'+
                          '                    <label for="telphone" class="col-sm-2 control-label">电话</label>'+
                          '                       <div class="col-sm-10">'+
                          '                            <input type="text" class="form-control" id="J-telphone" value="${data.staff_tel}" placeholder="请输入电话"/>'+
                          '                       </div>'+
                          '                </div>'+
                          '               <div class="form-group">'+
                          '                    <label for="email" class="col-sm-2 control-label">邮箱</label>'+
                          '                       <div class="col-sm-10">'+
                          '                            <input type="text" class="form-control" id="J-email" value="${data.staff_email}"  placeholder="请输入邮箱"/>'+
                          '                       </div>'+
                          '                </div>'+
                          '                <div class="form-group">'+
                          '                    <label for="status" class="col-sm-2 control-label">员工状态</label>'+
                          '                       <div class="col-sm-10">'+
                          '                           <select class="form-control" id="J-status">'+
                          '                                <option value="10" {@if data.staff_status == 10} selected="selected"{@/if}>在职</option>'+
                          '								 <option value="0" {@if data.staff_status == 0} selected="selected"{@/if}>离职</option>'+
                          '                           </select>'+
                          '                       </div>'+
                          '                </div>'+
                          '        </form>';
                    	 request.post('/userManage/getStaffById',{staffId:userId},function(ret){
                    		 if(1 == ret.code){
                    			 var tpl = juicer(dialogHtml,ret);
                    			 dialog.find(".modal-body").html(tpl);
                    		 }else{
                    			 Headertip.error(ret.msg,true,3000);
                    		 }
                    	 });
                      }
                  });
        		  manageUser.save(function(dialog){
                      var userName = $("#J-stuffName").val().trim();
                      var telphone = $("#J-telphone").val().trim();
                      var staff_email = $("#J-email").val().trim();
                      if(userName == ""){
                   	   Headertip.error("员工名不能为空",true,3000);
                   	   return;
                      }
                      if(telphone == ""){
                   	   Headertip.error("员工电话不能为空",true,3000);
                   	   return;
                      }
                      if(staff_email == ""){
                   	   Headertip.error("员工邮箱不能为空",true,3000);
                   	   return;
                      }
                      
                      index._judgeUserRepeat(telphone,userId,function(){
                    	  request.post('/userManage/addOrReviseStaff',
                                  {
                             	   		id:userId,
                             	   		staff_name:userName,
                             	   		staff_tel:telphone,
                             	   		staff_email:staff_email,
                             	   		staff_status:$("#J-status").val()
                                    },function(resp){
                                 	  if(resp.code == 1){
                                 		  Headertip.success("保存成功",true,3000);
                                 		  setTimeout(function(){
                                 			  window.location.reload();
                                 		  },500);
                                 	  }else{
                                 		  Headertip.error(resp.msg,true,3000);
                                 	  }
                          });
                      });
                 
             
             });
           });
        },
        //生成员工列表并且绑定事件
        _userList:function($ele,data){
            var tpl = '{@each staffs as item, index}'+
                       '<tr>' +
                           '<td>${item.staff_id}</td>'+
                           '<td>${item.staff_name}</td>'+
                           '<td>' +
                           '{@if item.staff_rank === 1 }' +
                             '仓库管理员' +
                           '{@else if item.staff_rank === 10}' +
                             '寝室长'+
                           '{@else if item.staff_rank === 20}' +
                             '楼栋长'+
                           '{@else if item.staff_rank === 30}' +
                             '学校经理'+
                           '{@else if item.staff_rank === 40}' +
                             '区县经理'+
                           '{@else}' +
                             '新增用户 ' +
                           '{@/if}' +
                           '</td>' +
                           '<td>${item.staff_tel}</td>' +
                           '<td>${item.staff_email}</td>' +
                           '<td>${item.staff_tel}</td>' +
                           '<td> ${item.staff_join_time}</td>'+
                           '<td>' +
                           '{@if item.staff_status === 10 }' +
                             '在职' +
                           '{@else}' +
                             '<span style="color:red;">离职</span>'+
                           '{@/if}' +
                           '</td>' +
                           '<td data-userid= ${item.staff_id}>'+
                           '<a class="btn btn-sm btn-success event-manage">管理</a>'+
                           '<a class="btn btn-sm btn-success event-delete">删除</a>'+
                           '</td>'+
                       '<tr/>'+
                      '{@/each}';
            var tmp = juicer(tpl,data);
            $ele.html(tmp);
            tpl ='用户人员列表<a class="btn btn-sm btn-success event-addUser" id="J-addUser" >新增用户</a>';
            $(".addUser-palhead").html(tpl);
            this._deletUser();
            this._addUser();
            this._manageStaff();
        }
    };

    index.init();

});