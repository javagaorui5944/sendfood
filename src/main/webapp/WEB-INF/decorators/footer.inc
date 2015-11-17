       <div id="footer">
			<img src="/static/image/icon/footer-logo.png" class="footer-logo"/>
			<h1 class="contact-telephone">投诉热线 : 13527474697</h1>
			<ul class="our-links">
				<li><a href="/SettledOnCampus">校园入驻</a></li>
				<li><a href="/contact">联系我们</a></li>
				<li><a href="/aboutUs">关于我们</a></li>
				<li class="last-link"><a href="/joinUs">加入我们</a></li>
			</ul>
       </div>
		<#if page.properties['page.javascript']?exists>
		    <script type="text/javascript" data-main="${staticPath}/js/page${page.properties['page.javascript']}" src="${staticPath}/js/lib/r.js"></script>
		</#if>
</body>
</html>