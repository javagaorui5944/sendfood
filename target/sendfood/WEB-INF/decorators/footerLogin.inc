
	<!-- 页面的启动脚本文件 start-->
		<#if page.properties['page.javascript']?exists>
		    <script type="text/javascript" data-main="${staticPath}/js/page${page.properties['page.javascript']}" src="${staticPath}/js/lib/r.js"></script>
		</#if>
    <!-- 页面的启动脚本文件 end-->
</body>
</html>