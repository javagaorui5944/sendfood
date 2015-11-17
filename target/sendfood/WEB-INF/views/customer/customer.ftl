    <content tag="title">用户管理－消费者列表</content>
    <content tag="css">/stuff/stuffList</content>
    <content tag="javascript">/customer/customerList</content>
 <div class="padding-md">
  <div class="panel panel-default">
   <div class="panel-heading">
           消费者列表
   <a class="btn btn-sm btn-success event-addUser" id="J-addCustomer" >新增用户</a>
       <div class="input-group pull-right" style="width:200px;position: relative;">
                <input type="search" class="form-control input-sm" placeholder="搜索用户" id=""/>
       </div>
   </div>
   <div class="panel-body">
    <table class="table table-bordered table-condensed table-hover table-striped">
     <thead>
       <tr>
        <th class="text-center">序号</th>
        <th class="text-center">用户名</th>
        <th class="text-center">用户电话</th>
        <th class="text-center">用户学校</th>
        <th class="text-center">用户楼栋</th>
        <th class="text-center">寝室</th>
        <th class="text-center">状态</th>
        <th class="text-center">操作</th>
       </tr>
     </thead>
     <tbody id="userlist">
     </tbody>
    </table>
    <div id="page"></div>
   </div>

   <div class="panel-footer clearfix">
   </div>
  </div>
 </div>
