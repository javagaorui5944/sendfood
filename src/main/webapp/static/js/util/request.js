/**
 * 对向后台发起的ajax请求做一层的数据封装。
 * 用以以后统一的进行错误码的处理，异常的统一处理等等需求。
 *
 * @author jipeng
 */

define(['lib/jquery', 'util/Headertip'], function($, Headertip) {
  'use strict';

  var convert = function(obj) {
    return Array.prototype.slice.call(obj);
  };

  return {

    /**
     * 向后台发起请求，方法参数可详见 $.post，jQuery.ajax文档
     */
    post : function() {
      return $.post.apply(this, convert(arguments)).fail(function(data, textStatus) {
        if (textStatus != "abort") {
        	if(data.status == 401){
        		Headertip.error('由于您长时间不操作,为防止信息泄露,请重新登录',true,3000);
        		return;
        	}
            Headertip.error('服务器连接不稳定，请刷新页面重试,或者把具体问题纪录下来反馈给我们的开发人员！',true,3000);
        }
      });
    },

    /**
     * 向后台发起请求，方法参数可详见 $.get，jQuery.ajax文档
     */
    get : function() {
      return $.get.apply(this, convert(arguments)).fail(function(data, textStatus) {
        if (textStatus != "abort") {
        	if(data.status == 401){
        		Headertip.error('由于您长时间不操作,为防止信息泄露,请重新登录',true,3000);
        		return;
        	}
            Headertip.error('服务器连接不稳定，请刷新页面重试,或者把具体问题纪录下来反馈给我们的开发人员！',true,5000);
        }
      });
    },

    /**
     * 向后台发起请求，方法参数可详见 $.ajax，jQuery文档
     */
    ajax : function() {
      return $.ajax.apply(this, convert(arguments));
    }
  };
});
