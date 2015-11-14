/**
 *  动态图片的加载，用于懒加载页面中一些非必要的图片，节省带宽流量。
 *
 *  @author jipeng
 */

define(['lib/jquery'], function($) {

  return function(){
	  var container = $(window);
	  var scrollEls = $('.scroll-loading');
	  //动态显示数据
	  var timer;

	  // 预加载图片，防止页面上短暂的抖动
	  var preload = function(el, callback) {
	    var src = el.data('src');

	    var i = new Image();
	    //创建一个Image对象，实现图片的预下载

	    i.onload = function() {
	      i.onload = null;
	      if (callback) {
	        callback(src);
	      }
	    };
	    //找不到就替换默认图片
	    i.onerror = function(){
	    	i.onerror = null;
	    	el.attr("src","/static/image/icon/nopic.png");
	    	el.attr("data-src","/static/image/icon/nopic.png");
	    }
	    i.src = src;
	  };

	  var loading = function() {
	    var contHeight = container.height(), contop = container.scrollTop();
	    	scrollEls = $('.scroll-loading');
	    $.each(scrollEls, function(i, el) {
	      el = $(el);

	      var post = el.offset().top - contop;

	      if (post >= 0 && post < contHeight) {

	        preload(el, function(src) {
	        	el.attr("src",src);
	        });
	      }
	    });
	  };

	  var startTimer = function() {
	    timer && clearTimeout(timer);
	    timer = setTimeout(loading, 100);
	  };

	  //事件触发
	  //加载完毕即执行
	  loading();

	  //滚动执行
	  container.bind('scroll', startTimer);
  }
});