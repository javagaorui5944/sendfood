/**
 * 新增用户弹框
 *author:jipeng
 **/

define(["lib/jquery","widget/dialog/dialog"],function($,dialog){
    var Dialog = {
        dialogHtml :'<form  class="form-horizontal">'+
        '               <div class="form-group">'+
        '                   <label for="stuffName" class="col-sm-2 control-label">角色名字</label>'+
        '                   <div class="col-sm-10">'+
        '                   <input type="text" class="form-control" id="J-roleName" placeholder="请输入角色名"/>'+
        '                   </div>'+
        '               </div>'+
        '               <div class="form-group">'+
        '                    <label for="telphone" class="col-sm-2 control-label">角色备注</label>'+
        '                       <div class="col-sm-10">'+
        '                            <input type="text" class="form-control" id="J-roleNote" placeholder="请输入角色备注"/>'+
        '                       </div>'+
        '                </div>'+
        '        </form>',
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