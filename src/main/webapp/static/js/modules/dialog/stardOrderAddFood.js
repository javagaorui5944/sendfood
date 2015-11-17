/**
 * Created by Emory on 15/6/2.
 * 查看库存系统时候，添加食物的弹窗
 */

define(["lib/jquery","widget/dialog/dialog"],function($,dialog){
    var Dialog = {
        dialogHtml :
        '<div class="panel panel-default">'+
            '<div class="panel-body">'+
                    '<form class="form-horizontal">'+
                        '<div class="form-group col-md-12 ">'+
                            '<label for="foodName" class="col-lg-3 control-label">仓库所在学校</label>'+
                            '<span class="input-sm col-lg-3" id="J-schoolName" data-id=""></span>'+
                            '<label for="foodName" class="col-lg-3 control-label">标准订单名</label>'+
                             '<span class="input-sm col-lg-3" id="J-orderName" ></span>'+
                        '</div><!-- /form-group -->'+
                    '<div class="form-group col-md-10 ">'+
                        '<label for="foodName" class="col-lg-3 control-label">商品关键字</label>'+
                        '<div class="col-lg-7">'+
                            '<input type="type" class="form-control input-sm col-md-9 J-select" id="J-addFoodByName" placeholder="请输入商品关键字查找">'+
                        ' </div>'+
                    '</div><!-- /form-group -->'+
                    '<div class="form-group">'+
                        '<table class="table table-striped">'+
                            '<thead>'+
                            ' <tr>'+

                                '<th>商品条形码</th>'+
                                '<th>商品名</th>'+
                                '<th>成本价</th>'+
                                '<th>售价</th>'+
                                '<th>商品数量</th>'+
                                '<th>操作</th>'+
                            ' </tr>'+
                            '</thead>'+
                            ' <tbody id="J-addFoodList">'+
                            ' </tbody>'+
                        '</table>'+
                        '<div id="J-addFoodPager"></div>'+
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