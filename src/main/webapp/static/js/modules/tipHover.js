/**
 *  点击把需要加载的东西展示到上下左右四个方向。
 *  jipeng
 */

define(['lib/jquery','modules/bridge'],function($,bridge){
    $.fn.tipHover = function(data){
        var  position = data.position ? data.position : "bottom", //展示框是基于触发这个事件的节点的位置，默认再下面
            callback = data.callback ? data.callback : function(){};//回调函数
            isabsolute = data.isabsolute == false ? data.isabsolute : true;
            var self = $(this),
                parent = self.parent();

            var top = self.offset().top,
                left = self.offset().left;

            var width =  self.outerWidth()-2,
                height = self.outerHeight();

            if(position == "bottom"){
                top = top+height;
            }

            //定义唯一标识，以便于删除
            var id = "J-"+new Date().getTime();
            bridge.register("tipHover",id);
            var box = "";
            if(isabsolute){
            	box = '<div class="tiphover" id="'+id+'" style="width:'+width+'px;position:absolute;left:'+left+'px;top:'+top+'px"></div>';
            }else{
            	box = '<div class="tiphover" id="'+id+'" style="width:'+width+'px;position:absolute;"></div>';
                
            }
             parent.append(box);
            callback(id);

            //隐藏事件
            $("body").mousedown(function(e){
                var target = e.target;
                var tarPar = $(target).parents(".tiphover");
                if(tarPar.length == 0){
                    $("#"+id).hide();
                }
            });

    }
});