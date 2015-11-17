    <content tag="title">零食预售系统－后台登录</content>
    <content tag="css">/admin/login</content>
    <content tag="javascript">/admin/login</content>
  
<div class="login-wrapper">
		<div class="text-center">
			<h2 class="fadeInUp animation-delay8" style="font-weight:bold">
				<span class="text-success">权限</span> <span style="color:#ccc; text-shadow:0 1px #fff">管理系统</span>
			</h2>
		</div>
		<div class="login-widget animation-delay1">	
			<div class="panel panel-default">
				<div class="panel-heading clearfix">
					<div class="pull-left">
						<i class="fa fa-lock fa-lg"></i> 登录
					</div>
				</div>
				<div class="panel-body">
					<form class="form-login" onsubmit="return false">
						<div class="form-group">
							<label>管理员账号</label>
							<input type="text" placeholder="请输入给你分配的账号" id="J-telephone" class="form-control input-sm bounceIn animation-delay2" />
						</div>
						<div class="form-group">
							<label>密码</label>
							<input type="password" placeholder="请输入密码" id="J-password" class="form-control input-sm bounceIn animation-delay4">
						</div>
						<div class="seperator"></div>
						<hr/>
						<a class="btn btn-success btn-sm bounceIn animation-delay5 login-link pull-right"  id="J-submit"><i class="fa fa-sign-in"></i>登录</a>
					</form>
				</div>
			</div><!-- /panel -->
		</div><!-- /login-widget -->
	</div><!-- /login-wrapper -->