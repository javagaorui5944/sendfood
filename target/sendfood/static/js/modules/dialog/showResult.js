/**
 * Created by Emory on 15/5/26.
 */

define(["lib/jquery","widget/dialog/dialog"],function($,dialog){
    var Dialog = {
        dialoghtml:"",
        init:function(data){
            this._creatHtml(data);
        },
        movedialog:null,
        //构造弹框内部html
        _creatHtml:function(data){
            Dialog.movedialog = new dialog({
                modalTitle:data.modalTitle,
                contentHtml:data.contentHtml,
                //初始化事件
                initFunc:data.initFunc || function(){}
            });
        },
        //关闭事件
        close:function(){
            Dialog.movedialog.find(".btn-modal-close").click();
        }
    };
    return Dialog;
});