/**
 * 新增标准订单
 *
 **/

define(["lib/jquery","widget/dialog/dialog"],function($,dialog){
    var Dialog = {
        dialogHtml :'<form  class="form-horizontal">'+
        '               <div class="form-group">'+
        '                   <label for="school" class="col-sm-2 control-label">标准订单关联学校</label>'+
        '                   <div class="col-sm-10">'+
        '                       <span class="form-control" id="J-school" data-id=""></span>'+
        '                   </div>'+
        '               </div>'+
        '               <div class="form-group">'+
        '                    <label for="telphone" class="col-sm-2 control-label">标准订单名</label>'+
        '                       <div class="col-sm-10">'+
        '                            <input type="text" class="form-control" id="J-orderName" value="" placeholder="输入标准订单名,简洁明了，容易区分"/>'+
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
                contentHtml: data.contentHtml || Dialog.dialogHtml,
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