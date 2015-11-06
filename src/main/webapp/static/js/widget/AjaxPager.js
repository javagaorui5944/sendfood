/**
 *
 * 翻页组件模块。
 * Ex:默认全是get请求
 *   AjaxPager.init({
 *     elem: $elem, 分页组件最外层的Dom
 *     pagerUrl:分页请求的url,
 *     current:请求页面，
 *     handle(json):处理分页返回的json，
 *     val:向后台传递的参数/可以为空
 *   });
 *
 */

define(['lib/jquery', 'util/Headertip', 'util/request'], function($, Headertip, request) {
    'use strict';

  var AjaxPager = {
    /**
     * 初始化界面上的各种元素。
     *
     * @param {Object} config.elem pager的装载容器
     * @param {Object} config.elem pager结构渲染完成后触发的回调
     */
    init : function(config) {

        this.$elem = config.elem;
        this.current = config.current;
        this.pagerUrl=config.pagerUrl;
        this.handle = config.handle;
        this.method = config.method || "get";
        if(config.val){
              this.val =config.val;
          }else{
              this.val = {};
          }
          this.initPage();
          this._bindEvt();
    },
     /*首次生成分页
     */
      initPage:function(){
          var that = this;
          this.val.page = this.current;
          request.get(this.pagerUrl,this.val,function(resp){
              //首页刷新处理数据
              that.handle(resp);

              that.pageCount = Math.ceil(resp.data.total/10);
              var tpl = '<div class="pull-right pageSkip">'+
                  '<span>共'+that.pageCount+'页</span>'+
                  '<span>到第<input type="text" id="pageInpt" value='+that.current+'>页</span>'+
                  '<a class="btn btn-sm btn-success skip">确定</a>'+
                  '</div>'+
                  '<ul class="pagination pagination-split pull-right">'+
                  '<li><a class="pre"> <上一页</a></li>';
              var endPage = that.pageCount;
              if(endPage>5){endPage=5}
              endPage++;
              for(var i = 1;i<endPage;i++){
                  if(i == that.current){
                      tpl += '<li><a class="active">'+i+'</a></li>';
                  }else{
                      tpl += '<li><a>'+i+'</a></li>';
                  }
              }
              tpl += '<li><a class="next">下一页></a></li></ul>';
              that.$elem.html(tpl);
          });
      },
     /**
     * 事件的绑定
     */
    _bindEvt : function() {
    	
      
      this.$elem.off('click', 'a');//如果页面有多个事件
      this.$elem.on('click', 'a', $.proxy(this._anyPage, this));
    },

    /**
     * 点击首页或者上一页，或者其中某页的时候触发的事件出发的事件
     *
     * @param {Number} number 需要跳转的页
     */
    _anyPage : function(evt) {
      var $target = $(evt.target);
      var page = parseInt($target.html(), 10);

      if (isNaN(page)) {
            if ($target.hasClass('next')) {
              if(this.current < this.pageCount){
                  this.current++;
              }
              else{return;}
            } else if ($target.hasClass('pre')) {
               if(this.current != 1){
                   this.current--;
               }
               else{return;}
            } else if ($target.hasClass('first-page')) {
              this.current = 1;
            } else if ($target.hasClass('last-page')) {
              ;
            } else if($target.hasClass('skip')){
              page = parseInt($("#pageInpt").val(),10);
              if(page < 1 || page > this.pageCount){
                  return;
              }
              else{
                  this.current = page;
              }
            }
      } else {
        this.current = page;
      }
      this._sendRequest();
    },

    _sendRequest : function() {
      var self = this,
          val=self.val;
      if(isNaN(this.current)){return};

      var pagerDom = function(){
          var current = self.current;
          var pageCount = self.pageCount;
          var temp ,
              indexPage,
              endPage=6;
          if(current <= 5){
              temp = '<li><a class="pre"><上一页</a></li>';
              if(pageCount < 5 ){
                  endPage = pageCount+1;
              }
              for(var i = 1 ;i<endPage;i++){
                  if(i == current){
                      temp += '<li><a class="active">'+i+'</a></li>';
                  }
                  else{
                      temp += '<li><a>'+i+'</a></li>';
                  }
              }
              temp += '<li><a class="next">下一页></a></li>';
          }
          else{
              temp ='<li><a class="pre"><上一页</a></li><li>...</li>';
              indexPage = current-2;//45678 678910
              if( (current+5) >= pageCount){
                  current = pageCount - 2;
              }
              for(var i = 0;i < pageCount;i++){
                if(indexPage == current){
                   temp += '<li><a class="active">'+indexPage+'</a></li>';
                }
                else{
                   temp += '<li><a>'+indexPage+'</a></li>';
                }
                indexPage++;
              }
              temp += '<li>...</li><li><a class="next">下一页></a></li>';
          }
          $(self.$elem).find(".pagination-split").html(temp);

      };
      pagerDom();
      val.page = this.current;
      request.get(this.pagerUrl,val,this.handle);
    }
  };

  return AjaxPager;
});
