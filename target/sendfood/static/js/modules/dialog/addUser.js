/**
 * 新增用户弹框
 *author:jipeng
 **/

define(["lib/jquery","widget/dialog/dialog"],function($,dialog){
    var Dialog = {
        dialogHtml :'<form  class="form-horizontal">'+
        '               <div class="form-group">'+
        '                   <label for="stuffName" class="col-sm-2 control-label">姓名</label>'+
        '                   <div class="col-sm-10">'+
        '                   <input type="text" class="form-control" id="J-stuffName" placeholder="请输入姓名"/>'+
        '                   </div>'+
        '               </div>'+
        '               <div class="form-group">'+
        '                    <label for="telphone" class="col-sm-2 control-label">电话</label>'+
        '                       <div class="col-sm-10">'+
        '                            <input type="text" class="form-control" id="J-telphone" placeholder="请输入电话"/>'+
        '                       </div>'+
        '                </div>'+
        '               <div class="form-group">'+
        '                    <label for="email" class="col-sm-2 control-label">邮箱</label>'+
        '                       <div class="col-sm-10">'+
        '                            <input type="text" class="form-control" id="J-email" placeholder="请输入邮箱"/>'+
        '                       </div>'+
        '                </div>'+
        '                <div class="form-group">'+
        '                    <label for="status" class="col-sm-2 control-label">员工状态</label>'+
        '                       <div class="col-sm-10">'+
        '                           <select class="form-control" id="J-status">'+
        '                                <option value="10">在职业</option>'+
        '                           </select>'+
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
                modalTitle:"新增加用户",
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