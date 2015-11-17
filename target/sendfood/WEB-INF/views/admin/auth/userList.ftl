
  <content tag="title">用户管理－用户列表</content>
    <content tag="css">admin/auth/userList</content>
    <content tag="javascript">admin/auth/userList</content>

<% include ../../components/header.ejs%>
 <div class="padding-md">
  <div class="panel panel-default">
   <div class="panel-heading">
    用户人员列表
       <a class="btn btn-sm btn-success event-addUser" data-toggle="modal" href="#addModal" >新增用户</a>
   </div>

   <div class="panel-body">

    <table class="table table-bordered table-condensed table-hover table-striped">
     <thead>
       <tr>
        <th class="text-center">ID</th>
        <th class="text-center">姓名</th>
        <th class="text-center">操作</th>
       </tr>
     </thead>
     <tbody>
      <%for(var i =0;i<userList.length;i++){ %>
       <tr>
          <td class="text-center">
           <%=i+1%>
          </td>
          <td class="text-center">
              <%= userList[i].user_name %>
          </td>
          <td class="text-center" data-userid= <%= userList[i].user_id %> >
           <a class="btn btn-sm btn-success event-manage">管理</a>
           <a class="btn btn-sm btn-success event-delete" data-toggle="modal" href="#deleteModal">删除</a>
          </td>
       </tr>
       <tr>
      <%}%>>
     </tbody>
    </table>
   </div>

   <div class="panel-footer clearfix">
    <ul class="pagination pagination-xs m-top-none pull-right">
     <li class="disabled"><a href="#">Previous</a></li>
     <li class="active"><a href="#">1</a></li>
     <li><a href="#">2</a></li>
     <li><a href="#">3</a></li>
     <li><a href="#">4</a></li>
     <li><a href="#">5</a></li>
     <li><a href="#">Next</a></li>
    </ul>
   </div>
  </div>
 </div>

<!--删除管理员的弹出框-->
<div id="deleteModal" class="modal fade  deleteM" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true"
            style="display: none">
        <div class="modal-header text-center">
            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
            <h3 id="myModalLabel">确认删除</h3>
        </div>
        <div class="modal-body text-center">
            <p>是否删除管理员</p>
        </div>
        <div class="modal-footer">
            <button class="btn" data-dismiss="modal" aria-hidden="true">关闭</button>
            <button class="btn btn-primary" id="deleteButton">确定</button>
        </div>
</div>
<!--新增用户的弹出框-->
    <div id="addModal" class="modal fade  deleteM addM" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true"
         style="display: none">
        <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
            <h3 id="myModalLabel">添加用户</h3>
        </div>
        <div class="modal-body">
            <form class="form-horizontal">
                <div class="form-group">
                    <label for="userName" class="col-lg-2 control-label">用户名</label>
                    <div class="col-lg-10">
                        <span class="errorTip">不能为空</span>
                        <input type="text" class="form-control input-sm notEmpty" id="userName" placeholder="userName">

                    </div><!-- /.col -->
                </div><!-- /form-group -->
                <div class="form-group">
                    <label for="userLoginname" class="col-lg-2 control-label">昵称</label>
                    <div class="col-lg-10">
                        <span class="errorTip"></span>
                        <input type="text" class="form-control input-sm notEmpty" id="userLoginname" placeholder="userLoginname">
                    </div><!-- /.col -->
                </div><!-- /form-group -->
                <div class="form-group">
                    <label for="password" class="col-lg-2 control-label">密码</label>
                    <div class="col-lg-10">
                        <span class="errorTip"></span>
                        <input type="password" class="form-control input-sm notEmpty" id="password" placeholder="password">
                    </div><!-- /.col -->
                </div><!-- /form-group -->
                <div class="form-group">
                    <label for="phone" class="col-lg-2 control-label">电话</label>
                    <div class="col-lg-10">
                        <span class="errorTip"></span>
                        <input type="text" class="form-control input-sm notEmpty" id="phone" placeholder="phone">
                    </div><!-- /.col -->
                </div><!-- /form-group -->
                <div class="form-group">
                    <label for="email" class="col-lg-2 control-label">邮箱</label>
                    <div class="col-lg-10">
                        <span class="errorTip"></span>
                        <input type="text" class="form-control input-sm notEmpty" id="email" placeholder="email">
                    </div><!-- /.col -->
                </div><!-- /form-group -->
            </form>
            <div class="errorTip">

            </div>
        </div>
        <div class="modal-footer">
            <button class="btn" data-dismiss="modal" aria-hidden="true">关闭</button>
            <button class="btn btn-primary" id="addButton">确定</button>
        </div>
    </div>
<!--确认成功新增用户-->
<div id="successedM" class="modal fade  deleteM" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true"
     style="display: none">
    <div class="modal-header text-center">
        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
        <h3 id="myModalLabel">提示</h3>
    </div>
    <div class="modal-body text-center">
        <p>添加成功</p>
    </div>
    <div class="modal-footer">
        <button class="btn" data-dismiss="modal" aria-hidden="true">关闭</button>
    </div>
</div>
