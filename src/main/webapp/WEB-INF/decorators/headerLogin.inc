<#include "../views/config.ftl">
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
	<title>
	 <#if page.properties['page.title']?exists>
        ${page.properties['page.title']}
    <#else>
        零食预售后台管理系统
     </#if>
     </title>
	

    <link rel="stylesheet" href="${staticPath}/css/global.css"/>

    <!--当前页面的引入的样式 start-->
    <#if page.properties['page.css']?exists>
     <link href="${staticPath}/css/page${page.properties['page.css']}.css" rel="stylesheet" />
    </#if>
	<link rel="shortcut icon" href="${staticPath}/image/favicon.ico" />

    <!-- 页面的启动脚本配置文件，需要给定个变量判断是否开发环境-->
    <script type="text/javascript">
        var BEEN = {};
        BEEN.BOOTSTAMP = new Date().getTime();
        BEEN.STATIC_ROOT = "/static/js";
    </script>
    <!-- 页面的启动脚本配置文件 -->
</head>

<body>