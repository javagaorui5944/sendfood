  <content tag="title">管理用户角色</content>
   <content tag="css">/role/ManageUserRole</content>
   <content tag="javascript">/role/ManageUserRole</content>

<div class="row">
<div class="col-md-12">
<div class="panel panel-default inbox-panel">
<div class="panel-heading">
    <div class="input-group">
        <input type="hidden" id="J-userId" value=""/>
        <input type="text" class="form-control input-sm J-select" id="J-userName" placeholder="搜索用户" real-value="-1">
    </div><!-- /input-group -->
</div>
<div class="panel-body">
    <label class="label-checkbox inline">
        <input type="checkbox" id="chk-all">
        <span class="custom-checkbox"></span>
    </label>
    <a class="btn btn-sm btn-danger" id="J-save"><i class="fa fa-plus"></i>保存</a>
    <!--<a class="btn btn-sm btn-default"><i class="fa fa-trash-o"></i>删除</a>-->
    <div class="pull-right">
        <a class="btn btn-default btn-sm dropdown-toggle" data-toggle="dropdown"><i class="fa fa-refresh"></i></a>
        <div class="btn-group" id="inboxFilter">
            <button type="button" class="btn btn-default btn-sm dropdown-toggle" data-toggle="dropdown">
                All
                <span class="caret"></span>
            </button>
            <ul class="dropdown-menu pull-right">
                <li><a href="#">Read</a></li>
                <li><a href="#">Unread</a></li>
                <li><a href="#">Starred</a></li>
                <li><a href="#">Unstarred</a></li>
            </ul>
        </div><!-- /btn-group -->
    </div>
</div>
<ul class="list-group" id="J-roles">
<!--<li class="list-group-item clearfix inbox-item">-->
    <!--<label class="label-checkbox inline">-->
        <!--<input type="checkbox" class="chk-item" roleId="1">-->
        <!--<span class="custom-checkbox"></span>-->
    <!--</label>-->
    <!--<span class="from">超级管理员</span>-->
    <!--<span class="detail">-->
        <!--<span class="label label-danger">Important</span>-->
        <!--拥有整个系统所有权限，慎用-->
    <!--</span>-->
<!--</li>-->
<!--<li class="list-group-item clearfix inbox-item">-->
    <!--<label class="label-checkbox inline">-->
        <!--<input type="checkbox" class="chk-item">-->
        <!--<span class="custom-checkbox"></span>-->
    <!--</label>-->
    <!--<span class="from">配送人员</span>-->
    <!--<span class="detail">-->
        <!--<span class="label label-info">Work</span>-->
        <!--拥有接受配送信息，管理自己配送订单的人员-->
    <!--</span>-->
<!--</li>-->
<!--</li>-->
<!--<li class="list-group-item clearfix inbox-item">-->
    <!--<label class="label-checkbox inline">-->
        <!--<input type="checkbox" class="chk-item">-->
        <!--<span class="custom-checkbox"></span>-->
    <!--</label>-->
    <!--<span class="from">仓库管理员</span>-->
    <!--<span class="detail">-->
        <!--<span class="label label-info">Work</span>-->
        <!--记录管理仓库初入货-->
    <!--</span>-->
<!--</li>-->
</ul><!-- /list-group -->
<!--<div class="panel-footer clearfix">-->
    <!--<div class="pull-left">112 messages</div>-->
    <!--<div class="pull-right">-->
        <!--<span class="middle">Page 1 of 8 </span>-->
        <!--<ul class="pagination middle">-->
            <!--<li class="disabled"><a href="#"><i class="fa fa-step-backward"></i></a></li>-->
            <!--<li class="disabled"><a href="#"><i class="fa fa-caret-left large"></i></a></li>-->
            <!--<li><a href="#"><i class="fa fa-caret-right large"></i></a></li>-->
            <!--<li><a href="#"><i class="fa fa-step-forward"></i></a></li>-->
        <!--</ul>-->
    <!--</div>-->
<!--</div>-->
</div><!-- /panel -->
</div>
</div>
