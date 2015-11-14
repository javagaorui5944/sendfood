/**
 *  筛选联动框
 */
define(['lib/jquery','modules/bridge','modules/select/jqueryAutoComplete'],function($,bridge){
     var select = {
          //构造每一个选择器的html
          init:function(){
              this._initHtml();
          },
         _initHtml:function(){
             var select = $(".J-select");
             select.each(function(){
                var muti = $(this).attr("muti");
                   //多选择的筛选器具不需要构造
                   if(muti != undefined){
                       return;
                   }
                var selcetOut = $(this).parent(),
                    width = $(this).width()+30,
                    span = "",
                    selectId = $(this).attr("id"),
                    real_value= $(this).attr("real-value");
                if(real_value != "-1" && real_value != undefined  ){
                    span = '<span id="J-select-span_'+selectId+'" class="J-select-span" style="width:auto;position: absolute; top: 4px;left:0; display: inline-block;">'+$(this).val()+'<em class="J-em-delete"></em></span>';
                }
                selcetOut.append('<div class="J-select-parent" style="width:'+width+'px"></div>');
                var  selectParent = selcetOut.find(".J-select-parent");
                $(this).appendTo(selectParent);
                $(span).appendTo(selectParent);
                $('<input type="hidden" name="'+selectId+'" value="'+real_value+'"/>').appendTo(selcetOut);
             });
          },
         searchUserByuserName:function(userId,callback) {
            $(userId).selectEmploy(callback);
         },
         searchUserBybystaffRank:function(userId,callback) {
             $(userId).selectEmployByRank(callback);
         },
         searchFoodByFoodName:function(foodId,choosedSchoolId,callback){
                $(foodId).searchFoodByFoodName(choosedSchoolId,function(relId){
                    var foodObj = bridge.get("foodObject");
                        $(foodObj).each(function(index,value){
                            if(relId == value.snacks_id){
                                callback(value)
                               return;
                            }
                        });
                });
         }



    }
    select.init();
    return select;
});
