/**
 * 新增用户弹框
 *author:jipeng
 **/

define(["lib/jquery","widget/dialog/dialog"],function($,dialog){
    var Dialog = {
        dialogHtml :'<form  class="form-horizontal" data-staffId="<%=staff.staff_id%>">'+
        '               <div class="form-group">'+
        '                   <label for="stuffName" class="col-sm-2 control-label">姓名</label>'+
        '                   <div class="col-sm-10">'+
        '                   <input type="text" class="form-control" id="J-stuffName" value="<%=staff.staff_name%>" placeholder="请输入姓名"/>'+
        '                   </div>'+
        '               </div>'+
        '               <div class="form-group">'+
        '                    <label for="telphone" class="col-sm-2 control-label">电话</label>'+
        '                       <div class="col-sm-10">'+
        '                            <input type="text" class="form-control" id="J-telphone" value="<%=staff.staff_tel%>" placeholder="请输入电话"/>'+
        '                       </div>'+
        '                </div>'+
        '               <div class="form-group">'+
        '                    <label for="email" class="col-sm-2 control-label">邮箱</label>'+
        '                       <div class="col-sm-10">'+
        '                            <input type="text" class="form-control" id="J-email" value="<%=staff.staff_email%>"  placeholder="请输入邮箱"/>'+
        '                       </div>'+
        '                </div>'+
        '                <div class="form-group">'+
        '                    <label for="status" class="col-sm-2 control-label">员工状态</label>'+
        '                       <div class="col-sm-10">'+
        '                           <select class="form-control" id="J-status">'+
        '                                <option value="10" {@if staff.staff_status == 10} selected="selected">在职</option>'+
        '								 <option value="0" {@if staff.staff_status == 0} selected="selected">离职</option>'+
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
                modalTitle:data.modalTitle,
                contentHtml: data.contentHtml,
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