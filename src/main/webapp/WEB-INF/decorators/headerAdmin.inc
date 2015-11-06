<#include "../views/config.ftl">
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="">
	<title></title>
    <link rel="stylesheet" href="${staticPath}/css/global.css"/>

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
   <body class="overflow-hidden">
  <!-- Overlay Div -->
  <div id="overlay" class="transparent"></div>
  <a href="" id="theme-setting-icon"><i class="fa fa-cog fa-lg"></i></a>
  <div id="theme-setting">
      <div class="title">
          <strong class="no-margin">Skin Color</strong>
      </div>
      <div class="theme-box">
          <a class="theme-color" style="background:#323447" id="default"></a>
          <a class="theme-color" style="background:#efefef" id="skin-1"></a>
          <a class="theme-color" style="background:#a93922" id="skin-2"></a>
          <a class="theme-color" style="background:#3e6b96" id="skin-3"></a>
          <a class="theme-color" style="background:#635247" id="skin-4"></a>
          <a class="theme-color" style="background:#3a3a3a" id="skin-5"></a>
          <a class="theme-color" style="background:#495B6C" id="skin-6"></a>
      </div>
      <div class="title">
          <strong class="no-margin">Sidebar Menu</strong>
      </div>
      <div class="theme-box">
          <label class="label-checkbox">
              <input type="checkbox" checked id="fixedSidebar">
              <span class="custom-checkbox"></span>
              Fixed Sidebar
          </label>
      </div>
  </div><!-- /theme-setting -->

  <div id="wrapper" class="preload">
  <div id="top-nav" class="skin-6 fixed">
  <div class="brand">
      <span>零食蜂</span>
      <span class="text-toggle">系统管理</span>
  </div><!-- /brand -->
  <button type="button" class="navbar-toggle pull-left" id="sidebarToggle">
      <span class="icon-bar"></span>
      <span class="icon-bar"></span>
      <span class="icon-bar"></span>
  </button>
  <button type="button" class="navbar-toggle pull-left hide-menu" id="menuToggle">
      <span class="icon-bar"></span>
      <span class="icon-bar"></span>
      <span class="icon-bar"></span>
  </button>
  <ul class="nav-notification clearfix">
      <!--<li class="dropdown">
          <a class="dropdown-toggle" data-toggle="dropdown" href="#">
              <i class="fa fa-envelope fa-lg"></i>
              <span class="notification-label bounceIn animation-delay4">4</span>
          </a>
      </li>-->
      <li class="profile dropdown">
          <a class="dropdown-toggle" data-toggle="dropdown" href="#">
              <strong><#if staff??>${staff.staff_name!''}</#if></strong>
              <span><i class="fa fa-chevron-down"></i></span>
          </a>
          <ul class="dropdown-menu">
              <li>
                  <a class="clearfix" href="#">
                      </static/image/template src="/static/image/template/user.jpg" alt="User Avatar">
                      <div class="detail">
                          <strong><#if staff??>${staff.staff_name!''}</#if></strong>
                          <!--<p class="grey">jipeng@email.com</p>-->
                      </div>
                  </a>
              </li>
               <li class="divider"></li>
              <li><a tabindex="-1" class="main-link logoutConfirm_open" href="#logoutConfirm"><i class="fa fa-lock fa-lg"></i> Log out</a></li>
          </ul>
      </li>
  </ul>
  </div><!-- /top-nav-->

  <aside class="fixed skin-6">
      <div class="sidebar-inner scrollable-sidebars">
          <div class="size-toggle">
              <a class="btn btn-sm" id="sizeToggle">
                  <span class="icon-bar"></span>
                  <span class="icon-bar"></span>
                  <span class="icon-bar"></span>
              </a>
              <a class="btn btn-sm pull-right logoutConfirm_open"  href="#logoutConfirm">
                  <i class="fa fa-power-off"></i>
              </a>
          </div><!-- /size-toggle -->
          <div class="user-block clearfix">
              </static/image/template src="/static/image/template/user.jpg" alt="User Avatar">
              <div class="detail">
                  <strong>欢迎您，<#if staff??>${staff.staff_name!''}</#if></strong><!--<span class="badge badge-danger bounceIn animation-delay4 m-left-xs">4</span>-->

              </div>
          </div><!--
          <div class="search-block">
              <div class="input-group">
                  <input type="text" class="form-control input-sm" placeholder="search here...">
						<span class="input-group-btn">
							<button class="btn btn-default btn-sm" type="button"><i class="fa fa-search"></i></button>
						</span>
              </div>
          </div>-->
          <div class="main-menu">
              <ul id="J-pages" data-url="<#if staff??>${staff.staff_password!''}</#if>" >
                  <li>
                      <a href="/admin">
								<span class="menu-icon">
									<i class="fa fa-desktop fa-lg"></i>
								</span>
								<span class="text">
								    首页
								</span>
                          <span class="menu-hover"></span>
                      </a>
                  </li>
                   
                 

              </ul>

              <div class="alert alert-info">
                  欢迎来到零食蜂系统
              </div>
          </div><!-- /main-menu -->
      </div><!-- /sidebar-inner -->
  </aside>
  <div id="main-container">


