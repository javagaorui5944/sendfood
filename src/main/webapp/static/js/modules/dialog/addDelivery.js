/**
 *查看物流列表时候，分配配送弹框
 *author:jipeng
 **/

define(["lib/jquery","widget/dialog/dialog"],function($,dialog){
    var Dialog = {
        dialogHtml :'<form>'+
        '               <div class="form-group">'+
        '                   <label for="inputPassword3" class="col-sm-2 control-label">配送到达时间</label>'+
        '                   <div class="col-sm-10">'+
        '                   <select class="form-control">'+
        '                       <option value="-1">请选择楼栋</option>'+
        '                   </select>'+
        '                   </div>'+
        '               </div>'+
        '               <div class="form-group">'+
        '                    <label for="inputPassword3" class="col-sm-2 control-label">配送单内容审核通过</label>'+
        '                       <div class="col-sm-10">'+
        '                           <select class="form-control">'+
        '                               <option value="-1">请选择配送人员</option>'+
        '                           </select>'+
        '                       </div>'+
        '                </div>'+
        '                <div class="form-group">'+
        '                    <label for="inputPassword3" class="col-sm-2 control-label">配送单内容审核通过</label>'+
        '                       <div class="col-sm-10">'+
        '                           <select class="form-control">'+
        '                                <option value="-1">请选择配送人员</option>'+
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
                contentHtml: "加载中,请稍等",
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