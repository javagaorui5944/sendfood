	</div><!-- /main-container -->
	</div><!-- /wrapper -->

	<a href="" id="scroll-to-top" class="hidden-print"><i class="fa fa-chevron-up"></i></a>
	<!-- Logout confirmation -->
	<div class="custom-popup width-100" id="logoutConfirm">
		<div class="padding-md">
			<h4 class="m-top-none"> 确认退出系统？</h4>
		</div>

		<div class="text-center">
			<a class="btn btn-success m-right-sm" href="/logOut">确定</a>
			<a class="btn btn-danger logoutConfirm_close">取消</a>
		</div>
	</div>
	
	<!-- 页面的启动脚本文件 start-->
		<#if page.properties['page.javascript']?exists>
		    <script type="text/javascript" data-main="${staticPath}/js/page${page.properties['page.javascript']}" src="${staticPath}/js/lib/r.js"></script>
		</#if>
    <!-- 页面的启动脚本文件 end-->
</body>
</html>