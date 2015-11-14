/**
 * 弹出模态框顶层类，结合bootstrap
 * 注意：一个页面只能有一个模态框，如果想要多个模态框，需要自己定义
 * 主要用于对模态框内容，事件的定义
 * 这个算是把事件和内容解藕，并抽象出顶层模块
 * @author 吉鹏
 *
 *
 */
define(['lib/Class',"lib/basic/bootstrap",'lib/juicer'], function(Class) {
    // 防止页面上同时存在多个弹框的情况
    var dialog;
    var Dialog = Class.extend({
        //最终拼凑的html
        allHtml : "",
        //大模态框
        bigDialogContent : '<div class="modal fade tpl-modal" id="J-Modal" aria-hidden="true" style="display: none;">'+
                            '<div class="modal-dialog">'+
                            '<div class="modal-content J-dialog-content">'+
                            '   <div class="modal-header">'+
                                '    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>'+
                                '    <h4 class="modal-title">{#title#}</h4>'+
                                '</div>'+
                                '<div class="modal-body">{#content#}</div>'+
                            ' <div class="modal-footer">'+
                            '<button type="button" class="btn btn-default btn-modal-close" data-dismiss="modal">取消</button>'+
                            '<button type="button" class="btn btn-success J-modal-save">保存</button>'+
                            '</div>'+
                            '</div>'+
                             '</div>'+
                            '</div>',

        init : function(options) {

            // 如果已经有存在的弹框，则先将其销毁
            dialog && dialog.destroy();

            this.options = options;

            this._setContent();

            this.delegate();

            dialog = this;
            if(this.options.initFunc){
                this.options.initFunc(dialog);
            }
        },
        //把你内容加进来
        _setContent : function(){

             this.allHtml = this.bigDialogContent.replace(/\{\#content\#\}/,this.options.contentHtml);
            this.allHtml = this.allHtml.replace(/\{\#title\#\}/,this.options.modalTitle);
            $("body").append(this.allHtml);
            this._dialog = $("#J-Modal");
            //bootstrap弹框方法
            $("#J-Modal").modal();
        },
        /**
         * 同jQuery.find
         *
         */
        find : function(selector) {
            return this._dialog.find(selector);
        },

        delegate : function(selector, evt, callback) {
            return this._dialog.delegate(selector, evt, callback);
        },
        /**
         * 销毁当前的对象。
         *
         */
        destroy : function(evt) {
            dialog = null;
            $(".tpl-modal").remove();
            // 阻止继续冒泡
            evt && evt.stopPropagation();
        }
    });

    return Dialog;

});