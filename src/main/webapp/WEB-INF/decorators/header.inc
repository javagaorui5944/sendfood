<#include "../views/config.ftl">
<!DOCTYPE html>
<!--[if IE 8]> <html lang="en" class="ie8 no-js"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9 no-js"> <![endif]-->
<!--[if !IE]><!--> <html lang="en" class="no-js"> <!--<![endif]-->
<head>
	<meta charset="utf-8" />
	<title>
	 <#if page.properties['page.title']?exists>
          ${page.properties['page.title']}
    <#else>
        零食蜂_零食预定_校园微超市_勤奋蜂科技
     </#if>
     </title>
	<meta content="keywords" name="零食蜂,零食预定,校园微超市,勤奋蜂科技" />
	<meta name="description" content="零食蜂专业品质大学生零食预售，饿了么，饿了订零食箱子。零食峰覆盖全国各城市高校，拥有最先进的网上预定平台和专业配送团队，提供优质的零食配送服务。">
    <link rel="stylesheet" href="${staticPath}/css/globalUser.css"/>
    <#if page.properties['page.css']?exists>
     <link href="${staticPath}/css/page${page.properties['page.css']}.css" rel="stylesheet" />
    </#if>
    	<link rel="shortcut icon" href="${staticPath}/image/favicon.ico" />
    <script type="text/javascript">
        var BEEN = {};
        BEEN.BOOTSTAMP = new Date().getTime();
        BEEN.STATIC_ROOT = "/static/js";
    </script>
</head>
 <div id="header">
        <div class="header-top">
            <div class="header-address">
                <div class="header-left">
                    <i class="icon-address fl"></i>
                    <a class="city fl ml5" href="#">重庆</a>
                    <a href="#" class="school fl ml5" id="J-choose-sch">请选择学校</a>
                    <div class="position fl ml5" id="J-position">
                        <a class="change-link" href="#">
                            [切换地址]
                            <i></i>
                        </a>
                        <ul id="J-schools">
                        </ul>
                    </div>
                </div>
                <#if customer??>
                 <div class="header-right fr">
                    <a class="login" href="#">欢迎您&nbsp;${customer.customer_name!''}</a>
                     <a class="login" id="J-login" href="javascript:;">更换账号</a>
                      <a class="login" id="J-changePwd" href="javascript:;">修改密码</a>
                </div>
                <#else>
	                <div class="header-right fr">
	                    <a class="login" id="J-login" href="javascript:;">登录</a>
	                </div>
               </#if>
            </div>
        </div>
        <div class="header-nav">
              <div id="header-inner">
                 <a href="/" class="index-logo"></a>
                 <h1 class="tips">一家专门做“微超市”的平台</h1>
                 <ul class="introduce">
                    <li>
                        <span class="clock icon"></span>
                        <span class="text">24小时营业</span>
                    </li>
                    <li>
                        <span class="truck icon"></span>
                        <span class="text">0分钟享受美食</span>
                    </li>
                    <li>
                        <span class="gallery icon"></span>
                        <span class="text">无缝随取随吃</span>
                    </li>
                 </ul>
              </div>
        </div>
    </div>
<body>
