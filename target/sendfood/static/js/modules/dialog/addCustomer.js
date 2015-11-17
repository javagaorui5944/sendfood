/**
 * Created by Emory on 15/6/2.
 * 查看库存系统时候，添加食物的弹窗中文
 */

define(["lib/jquery","widget/dialog/dialog"],function($,dialog){
    var Dialog = {
        dialogHtml :'<form  class="form-horizontal">'+
            '               <div class="form-group">'+
            '                   <label for="stuffName" class="col-sm-2 control-label">姓名</label>'+
            '                   <div class="col-sm-10">'+
            '                   <input type="text" class="form-control" id="name" placeholder="请输入姓名"/>'+
            '                   </div>'+
            '               </div>'+
            '               <div class="form-group">'+
            '                    <label for="telphone" class="col-sm-2 control-label">电话</label>'+
            '                       <div class="col-sm-10">'+
            '                            <input type="text" class="form-control" id="tel" placeholder="请输入电话"/>'+
            '                       </div>'+
            '                </div>'+
            '                <div class="form-group">'+
            '                    <label for="status" class="col-sm-2 control-label">学校</label>'+
            '                       <div class="col-sm-10">'+
            '                           <select class="form-control" id="school">'+
            '                           </select>'+
            '                       </div>'+
            '                </div>'+
            '                <div class="form-group">'+
            '                    <label for="status" class="col-sm-2 control-label">楼栋</label>'+
            '                       <div class="col-sm-10" >'+
            '                           <select class="form-control" id="building" ><option>请先选择学校</option>'+
            '                           </select>'+
            '                       </div>'+
            '                </div>'+
            '                <div class="form-group">'+
            '                    <label for="status" class="col-sm-2 control-label">寝室号</label>'+
            '                       <div class="col-sm-10">'+
            '                           <select class="form-control" id="dormitory" ><option>请先选择楼栋</option>'+
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
            Dialog.movedialog =  new dialog({
                modalTitle:data.modalTitle||"添加消费者",
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
})