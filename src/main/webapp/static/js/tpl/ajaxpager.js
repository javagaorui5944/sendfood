/**
 * 翻页组件widget/AjaxPager其中用到的模板。
 */

define(function() {
  'use strict';  
  
  return '<div class="dataTables_paginate paging_bootstrap pagination" id="pager">' +
         '<ul>'+
         '<% if(pageIndex == 1) { %>' +
         '<li class="first-page disabled"><a href="javascript:;"><span class="hidden-480">首页</span></a></li>'+
         '<li class="prev disabled"><a href="javascript:;"><span class="hidden-480">上一页</span></a></li>'+
         '<% } else { %>' +
         '<li class="first-page disabled"><a href="javascript:;"data-page="1"> <span class="hidden-480">首页</span></a></li>'+
         '<li class="prev disabled"><a href="javascript:;" data-page="<%= pageIndex - 1 %>"><span class="hidden-480">上一页</span></a></li>'+

         '<% } %>' +
         '<% for(var i = startIndex; i <= endIndex; i++) { %>' +
         '  <% if(i == pageIndex) { %>' +
         '<li class="active"><a href="javascript:;"  data-page="<%= i %>"><span class="hidden-480"><%= i %></span></a></li>'+
         '  <% } else { %>' +
         '<li><a href="javascript:;"  data-page="<%= i %>"><span class="hidden-480"><%= i %></span></a></li>'+
         '  <% } %>' +
         '<% } %>' +
         '<% if(pageCount == pageIndex) { %>' +
         '<li class="next"><a href="javascript:;"><span class="hidden-480">下一页</span></a></li>'+
         '<li class="last-page"><a href="javascript:;"><span class="hidden-480">尾页</span></a></li>'+
         '<% } else { %>'+
         '<li class="next"><a href="javascript:;" data-page="<%= pageIndex + 1 %>"><span class="hidden-480">下一页</span></a></li>'+
         '<li class="last-page"><a href="javascript:;" data-page="<%= pageCount %>"><span class="hidden-480">尾页</span></a></li>'+

         '<% } %>' +
         '</ul>'+
         '</div>';
});

