/**
 * Created by Emory on 15/6/2.
 * 查看库存系统时候，添加食物的弹窗
 */

define(["lib/jquery","widget/dialog/dialog"],function($,dialog){
    var Dialog = {
        dialogHtml :
                 '<div class="panel panel-default">'+
							'<div class="panel-body">'+
								'<form>'+
									'<div class="form-group">'+
										'<label for="foodName">食物名</label>'+
										'<input type="type" class="form-control input-sm" id="foodName" placeholder="foodName">'+
									'</div><!-- /form-group -->'+
									'<div class="form-group">'+
										'<label for="costPrice">成本价</label>'+
										'<input type="type" class="form-control input-sm" id="costPrice" placeholder="costPrice">'+
									'</div><!-- /form-group -->'+
									'<div class="form-group">'+
										'<label for="price">售价</label>'+
										'<input type="type" class="form-control input-sm" id="price" placeholder="price">'+
									'</div><!-- /form-group -->'+
                                    '<div class="form-group">'+
										'<label for="inventoryNum">库存数量</label>'+
										'<input type="type" class="form-control input-sm" id="inventoryNum" placeholder="num">'+
									'</div><!-- /form-group -->'+
                                     '<div class="form-group">'+
										'<label for="barCode">条形编码</label>'+
										'<input type="type" class="form-control input-sm" id="barCode" placeholder="barCode">'+
									'</div><!-- /form-group -->'+
								'</form>'+
							'</div>'+
						'</div>'
        ,
        init:function(data){
            Dialog._createHtml(data);
        },
        movedialog : null,
        //构造弹框内部html
        _createHtml:function(data){
            Dialog.movedialog =  new dialog({
                modalTitle:data.modalTitle||"添加食物",
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