/**
 * 新增权限弹框
 *author:jipeng
 **/

define(["lib/jquery","widget/dialog/dialog"],function($,dialog){
    var Dialog = {
        dialogHtml :
            '<form class="form-horizontal" id="J-limitsDialog">'+
                '<div class="form-group">'+
                    '<label for="inputEmail3" class="col-sm-2 control-label">权限名字</label>'+
                '<div class="col-sm-10">'+
                     '<input type="text" class="form-control J-limits-name"  placeholder=" 请输入权限对应页面名字">'+
                '</div>'+
                '</div>'+
                '<div class="form-group">'+
                    '<label for="inputPassword3" class="col-sm-2 control-label">权限URL</label>'+
                    '<div class="col-sm-10">'+
                        '<input type="text" class="form-control J-limits-url"  placeholder="请输入权限对应页面的链接">'+
                    '</div>'+
                 '</div>'+
                  '<div class="form-group">'+
                          '<label for="inputPassword3" class="col-sm-2 control-label">权限备注</label>'+
                        '<div class="col-sm-10">'+
                                '<input type="text" class="form-control J-limits-note"  placeholder="请输入权限备注">'+
                         '</div>'+
                  '</div>'+
                   '<div class="form-group">'+
                            '<label for="inputPassword3" class="col-sm-2 control-label">父级页面</label>'+
                        '<div class="col-sm-10">'+
                                '<select name="parent"  class="form-control "  id="J-parent-url">'+
                                    '<option value="-1">请选择父级列表</option>'+
                               '</select>'+
                   '</div>'+
                 '</div>'+
             '</form>',
        init:function(data){
            Dialog._createHtml(data);
        },
        movedialog : null,
        //构造弹框内部html
        _createHtml:function(data){
            var contentHtml = Dialog.dialogHtml;
            Dialog.movedialog =  new dialog({
                modalTitle:data.modalTitle,
                contentHtml: Dialog.dialogHtml,
                //初始化事件
                initFunc:data.initFunc || function(){}
            })
        },
        //保存事件
        save:function(callback){
            Dialog.movedialog.find(".J-modal-save").on("click",function(e){
                callback(Dialog.movedialog);
            })
        },
        //关闭事件
        close:function(){
            Dialog.movedialog.find(".btn-modal-close").click();
        }
    };
    return Dialog;
});