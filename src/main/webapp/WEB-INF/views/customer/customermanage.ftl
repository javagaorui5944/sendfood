<content tag="title" >编辑个人信息</content>
<content tag="javascript">customer/modifyinfo</content>
<content tag="css">customer/modifyinfo</content>


<div class="padding-md">
   <div class="panel panel-default info" data-id="1">
      <div class="panel-heading">修改个人信息</div>
      <div class="panel-body">
         <form class="form-horizontal" onsubmit="return false;">
            <div class="form-group">
               <label for="userName" class="col-lg-2 control-label">姓名</label>
               <div class="col-lg-6">
                  <input type="text" class="form-control input-sm" id="userName" placeholder=<%=customer.Customer_name%>>
               </div>
               <span class="errorTip"></span>
            </div>
            <!-- /form-group -->
            <div class="form-group">
               <label for="phone" class="col-lg-2 control-label">电话</label>
               <div class="col-lg-6">
                  <input type="text" class="form-control input-sm" id="phone" placeholder=<%=customer.Customer_tel%>>
               </div>
               <span class="errorTip"></span>
            </div>
            <!-- /form-group -->
            <div class="form-group">
               <label for="phone" class="col-lg-2 control-label">邮箱</label>
               <div class="col-lg-6">
                  <input type="text" class="form-control input-sm" id="email" placeholder=<%=customer.Customer_email%>>
               </div>
               <span class="errorTip"></span>
            </div>
            <!-- /form-group -->
            <div class="form-group">
               <div class="col-lg-offset-2 col-lg-10">
                  <a class="changePass" >修改密码</a>
               </div>
            </div>
            <!-- /form-group -->
            <div class="form-group">
               <div class="col-lg-offset-2 col-lg-10">
                  <button type="submit" class="btn btn-success btn-sm" id="savePassButton">保存</button>
               </div><!-- /.col -->
            </div><!-- /form-group -->
         </form>
      </div>
   </div>
</div>