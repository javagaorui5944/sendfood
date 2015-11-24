/**
 * Created by Emory on 15/6/2.
 */

require.config({
    baseUrl :  BEEN.STATIC_ROOT,
    paths:{
        'jquery':"lib/jquery",
        'ajaxfileupload':'lib/jqueryPlugs/ajaxfileupload'
    },
    shim:{
        'ajaxfileupload':{
            'deps': ['lib/jquery'],
            'exports': 'jQuery.fn.ajaxfileupload'
        }
    }
});

require(['lib/jquery','modules/baseModule','util/request','modules/dialog/showResult',
         'modules/dialog/addfood','util/Headertip','widget/AjaxPager','lib/jqueryPlugs/ajaxfileupload','lib/juicer',],
    function($,baseModule,request,showResult,addfood,Headertip,ajaxpager){
	
    var index = {
       init:function(){
    	   this.foodindex();
           this.addfood();
           this.stopfood();
           this.checkfoodByStorage();
           this.savefood();
           this._dealPic();
       },
       //库存首页,找到用户的仓库
       foodindex:function(){
           var that = this;
           var pager = $("#pager");
        request.get("/food/getInventoryFood/",{},function(resp){
            if(resp.code == 1){
                tpl = '{@each storages as item}'+
                          '<option  value=${item.storage_id} class="storage">${item.storage_name}</option>'+
                    '{@/each}';
                tmp = juicer(tpl,resp.data);
                $(".storagelist").html(tmp);
                //找到第一个学校去取数据
                if($("#foodStorage > option").length == 0){
                    return;
                }
                ajaxpager.init({
                    elem:pager,
                    pagerUrl:'/food/getInventoryFood/',
                    current:1,
                    val:{
                        storage_id:$("#foodStorage").val()
                    },
                    handle:function(resp){
                        if(resp.code == 1){
                            that.initFoodlist(resp.data);
                        }
                        else{
                            Headertip.error(resp.msg);
                        }
                    }
                });
            }
            else{
                Headertip.error(resp.msg);
            }
        });

       },
       //查看对应的仓库食物
       checkfoodByStorage:function(){
    	   var that = this;
    	   $("#foodStorage").change(function(){
    		   var storageId = $(this).val();
               var pager = $("#pager");
               ajaxpager.init({
                   elem:pager,
                   pagerUrl:'/food/getInventoryFood/',
                   current:1,
                   handle:function(resp){
                       if(resp.code == 1){
                           that.initFoodlist(resp.data);
                       }
                       else{
                           Headertip.error(resp.msg);
                       }
                   },
                   val:{'storage_id':storageId}
               });
    	   });
       },
       //修改食物信息并且保存
       savefood:function(){
           $("#foodlist").on("click",".saveFood",function(){
               var food = {};
               food.snacks_id = $(this).parent().attr("data-foodId");
               food.snacks_bar_code =  $(this).parent().prevAll(".barCode").find("input").val().trim();
               food.snacks_name =  $(this).parent().prevAll(".foodName").find("input").val().trim();
               food.snacks_cost_price =  $(this).parent().prevAll(".costPrice").find("input").val().trim();
               food.snacks_sell_price =  $(this).parent().prevAll(".price").find("input").val().trim();
               food.snacks_stock_number =  $(this).parent().prevAll(".inventoryNum").find("input").val().trim();
               //food.storage_id = $(this).attr("data-storageId");
               
               if(food.snacks_bar_code == ""){
                   Headertip.error("食物条形码不能为空");
                   return;
               }
               if(food.snacks_name == ""){
                   Headertip.error("食物名不能为空");
                   return;
               }
               if(food.snacks_cost_price == ""){
                   Headertip.error("成本价不能为空");
                   return;
               }
               if(/^[0-9]+\.[1-9]$/.test(food.snacks_cost_price)==false  &&  /^[1-9][0-9]*$/.test(food.snacks_cost_price) ==false ){
            	   Headertip.error("成本价请填写正确格式，小数点后只能保留一位，且不能为0",false,3000);                	   
                   return;   
               }    
               if(food.snacks_sell_price == ""){
                   Headertip.error("售价不能为空");
                   return;
               }
               if(/^[0-9]+\.[1-9]$/.test(food.snacks_sell_price) == false &&  /^[1-9][0-9]*$/.test(food.snacks_sell_price) == false){
            	   Headertip.error("售价填写正确格式，小数点后只能保留一位，且不能为0",false,3000);                      	 
                   return;   
               }  
               if(food.snacks_stock_number == ""){
                   Headertip.error("库存数量不能为空");
                   return;
               }
               if(/^[1-9][0-9]*$/.test(food.snacks_stock_number) == false){
            	   Headertip.error("库存填写正确格式，只能输入整数",false,3000);      
                   return;   
               }  
               
               request.post('/food/updateFood',food,function(resp){
                   if(resp.code == 1){
                	   Headertip.success(resp.msg,false,3000);                       
                   }
                   else{
                	   Headertip.error(resp.msg,false,3000); 
                   }
               });
           });
       },
       //停售一款食物
       stopfood:function(){
           $("#foodlist").on("click",".stopFood",function(){
               if(!confirm("确认删除食品？删除食品之后，标准订单里面不可现")){
                   return;
               }
               var food = {};
               food.snacks_id = $(this).parent().attr("data-foodId");
               food.snacks_status= $(this).attr("data-status");
               if(food.snacks_status == 10){
            	   food.snacks_status = 0;
               }else{
            	   food.snacks_status = 10;
               }
               var that = this;
               request.post('/food/changeFood',food,function(resp){
                   if(resp.code == 1){
                	   if(food.snacks_status == 0){
                		   Headertip.success("删除成功",false,3000);
                		   $(that).attr("data-status","0");
                           $("#pager .active").trigger("click");
                	   }else{
                		   Headertip.success("取消停售",false,3000); 
                		   $(that).attr("data-status","10");
                           $(that).html("停售"); 
                	   }
                   }
                   else{
                	   Headertip.error(resp.msg,false,3000); 
                   }
               });
              
           });
       },
       //添加食物种类
       addfood:function(){
           var that = this;
    	   $("#addfood").click(function(){
               addfood.init({});
               addfood.save(function(dialog){
            	   var food = {};
            	   var regPrice = new RegExp('^[0-9]+\.{0,1}[0-9]{0,1}$','g');
            	   var regNum = new RegExp('^[1-9][0-9]*$','g');
            	   food.snacks_name = $("#foodName").val().trim();
            	   food.snacks_cost_price = $("#costPrice").val().trim();
            	   food.snacks_sell_price = $("#price").val().trim();
            	   food.snacks_stock_number = $("#inventoryNum").val().trim();
            	   food.snacks_bar_code = $("#barCode").val().trim();
            	   var storage = $("#foodStorage");
                   food.storage_id = storage.val();
            	   if(food.snacks_name == ""){
            		   Headertip.error("placeholder","食物名不能为空",true,3000);
                       return;
            	   }                   
                   if(food.snacks_cost_price== ""){
                	   Headertip.error("placeholder","原价不能为空",true,3000);
                       return;                       
                   }
                   if(/^[0-9]+\.[1-9]$/.test(food.snacks_cost_price) == false && /^[1-9][0-9]*$/.test(food.snacks_cost_price) == false){
                	   Headertip.error("成本价请填写正确格式，小数点后只能保留一位，且不能为0",true,3000);
                       return;   
                   }                    
                   if(food.snacks_sell_price == ""){
                	   Headertip.error("placeholder","食物名不能为空",true,3000);
                       return;
                   }   
                   if(/^[0-9]+\.[1-9]$/.test(food.snacks_sell_price) == false&&/^[1-9][0-9]*$/.test(food.snacks_sell_price) == false){
                	   Headertip.error("售价填写正确格式，小数点后只能保留一位，且不能为0",false,3000);                      	 
                       return;   
                   } 
                   if(food.snacks_stock_number == ""){
                	   Headertip.error("售价不能为空",true,3000);
                       return;
                   }   
                   if(/^[1-9][0-9]*$/.test(food.snacks_stock_number) == false){
                	   Headertip.error("库存填写正确格式，只能输入整数",false,3000);      
                       return;   
                   }   
                   if(food.snacks_bar_code == ""){
                	   $("#barCode").attr("placeholder","不能为空");
                       return;
                   }


                   request.post('/food/addFood',food,function(resp){
                	  if(resp.code == 1){
                		  Headertip.success("添加食物成功",true,3000);
                          ajaxpager.init({
                              elem:$("#pager"),
                              pagerUrl:'/food/getInventoryFood/',
                              current:1,
                              val:{
                                  storage_id:$("#foodStorage").val()
                              },
                              handle:function(resp){
                                  if(resp.code == 1){
                                      that.initFoodlist(resp.data);
                                  }
                                  else{
                                      Headertip.error(resp.msg);
                                  }
                              }
                          });
                	  }
                	  else{
                		  Headertip.error("添加失败",false,3000);
                	  }
                	  $("#J-Modal").remove();
                   });
               });
    	   });                          
       },
       //模糊搜索食物
       searchfood:function(){    
    	   var lastTime ;
    	   $("#searchfood").on("keyup",function(event){
    		    lastTime = event.timeStamp;   		    
    		    var timer = setTimeout(function(){		    	
    		    	 if( lastTime-event.timeStamp  == 0){
    		    		var food = {};
     	                food.key = 'snacks_name';
     	                food.name = $("#searchfood").val().trim();
     	                food.storageId=$("#foodStorage").val()
     	                if(food.name == ""){
     	                	$(".searchfood").hide().html("");
     	                	return;
     	                	}     
     	                if(food.storageId == null){
     	                	Headertip.error("出错",false,3000);
     	                	return;
     	                }
     	                request.get('/food/getInventoryFood',{key:"snacks_name",value:food.name,storage_id:food.storageId},function(resp){
     	                 	if(resp.code == 1 && resp.data.foods.length != 0){
     	                 		var tpl = '{@each foods as item,index}'+
								    			 '{@if item.snacks_status == 10}'+
												   '<tr class="stopedFood">'+
							                        '<td>'+
                                                    '{@each storages as itStorage}'+
                                                            '{@if item.storage_id === itStorage.storage_id}'+
                                                                '${itStorage.storage_name}'+
                                                             '{@/if}'+
                                                        '{@/each}'+
                                                    '</td>'+
                                                    '<td class="barCode"><input type="text" value=${item.snacks_bar_code} class="form"/></td>'+
                                                    '<td class="foodName"><input type="text" value=${item.snacks_name} class="form"/></td>'+
                                                    '<td class="costPrice"><input type="text" value=${item.snacks_cost_price} class="form"/></td>'+
                                                    '<td class="price"><input type="text" value=${item.snacks_sell_price} class="form"/></td>'+
                                                    '<td class="inventoryNum"><input type="text" value=${item.snacks_stock_number} class="form"/></td>'+
                                                    '<td data-foodId=${item.snacks_id} class="foodManage">'+
                                                        '<a class="saveFood btn btn-sm btn-success" data-status=${item.snacks_status} >保存</a>'+
                                                       '<a class="stopFood btn btn-sm btn-warning" data-status=${item.snacks_status}>删除</a>'+
                                                    '</td>'+
                                                '</tr>'+
                                                '{@/if}'+
				                         '{@/each}';
							  var tmp = juicer(tpl,resp.data);
				              $("#foodlist").html(tmp);               
     	                 	}
     	                });
    		        }
    		    },500);              
    	   }); 
       },
       //点击被搜索出来的食物
       clickFood:function(){
    	       $(".searchfood p").click(function(){
        	   $(".searchfood").hide();       	  
        	   var food = {};
        	   var thisfood =$(this);
        	   food.name = thisfood.html().trim();
        	   food.storageId= thisfood.attr("data-sId");
        	   food.key = 'snacks_name';
        	   food.value =food.name;        
        	   if(food.name == ""){
        		   Headertip.error("请输入食物名称",false,3000);  
        		   return;
        	   }
        	   if(food.storageId == ""){
        		   Headertip.error("输入有误",false,3000);  
        		   return;
        	   }
               request.get('/food/getInventoryFood',{storage_id:food.storageId,key:"snacks_name",value:food.name},function(resp){            	
            	   var food = resp.data.foods[0];
            	   if(resp.code == 1){
            		   addfood.init({
            			   modalTitle:"修改食物",
                           initFunc:function(dialog){
                        	   $("#foodName").val(food.snacks_name);
                        	   $("#foodName").attr("data-id",food.snacks_id );
                        	   $("#costPrice").val(food.snacks_cost_price);
                        	   $("#price").val(food.snacks_sell_price);
                        	   $("#inventoryNum").val(food.snacks_stock_number);
                        	   $("#barCode").val(food.snacks_bar_code);                        	   
                           }
                       }); 
            		   addfood.save(function(dialog){
                    	   var food = {};
                    	   food.snacks_id = $("#foodName").attr("data-id");
                    	   food.snacks_name = $("#foodName").val().trim();
                    	   food.snacks_cost_price = $("#costPrice").val().trim();
                    	   food.snacks_sell_price = $("#price").val().trim();
                    	   food.snacks_stock_number = $("#inventoryNum").val().trim();
                    	   food.snacks_bar_code = $("#barCode").val().trim();   
                    	   
                    	   if(food.snacks_name == ""){
                    		   $("#foodName").attr("placeholder","不能为空");
                               return;
                    	   }                   
                           if(food.snacks_cost_price== ""){
                        	   $("#costPrice").attr("placeholder","不能为空");
                               return;                       
                           }
                           if(/^[0-9]+\.[1-9]$/.test(food.snacks_cost_price) == false && /^[1-9][0-9]*$/.test(food.snacks_cost_price) == false){
                        	   Headertip.error("成本价请填写正确格式，小数点后只能保留一位，且不能为0",false,3000);                	   
                               return;   
                           }                    
                           if(food.snacks_sell_price == ""){
                        	   $("#price").attr("placeholder","不能为空");
                               return;
                           }   
                           if(/^[0-9]+\.[1-9]$/.test(food.snacks_sell_price) == false&&/^[1-9][0-9]*$/.test(food.snacks_sell_price) == false){
                        	   Headertip.error("售价填写正确格式，小数点后只能保留一位，且不能为0",false,3000);                      	 
                               return;   
                           } 
                           if(food.snacks_stock_number == ""){
                        	   $("#inventoryNum").attr("placeholder","不能为空");
                               return;
                           }   
                           if(/^[1-9][0-9]*$/.test(food.snacks_stock_number) == false){
                        	   Headertip.error("库存填写正确格式，只能输入整数",false,3000);      
                               return;   
                           }   
                           if(food.snacks_bar_code == ""){
                        	   $("#barCode").attr("placeholder","不能为空");
                               return;
                           }
                           request.post('/food/updateFood',food,function(resp){
                               if(resp.code == 1){
                            	   Headertip.success("修改成功",false,3000);  
                            	   $("#J-Modal").remove();
                               }
                               else{
                            	   Headertip.error(resp.msg,false,3000);                                  
                               }
                           });
            		   });     
            	   }
               });
           });
           
       },
       //根据json生成食物列表，并且食物的所有绑定事件
       initFoodlist:function(data){
           var tpl = '{@each foods as item,index}'+
                        '{@if item.snacks_status == 10}'+
                           '<tr class="stopedFood">'+
                           '<td>'+
                           '{@each storages as itStorage}'+
                           '{@if item.storage_id === itStorage.storage_id}'+
                           '${itStorage.storage_name}'+
                           '{@/if}'+
                           '{@/each}'+
                           '</td>'+
                            '<td class="foodName"><input type="text" value=${item.snacks_name} class="form"/></td>'+
                           '<td class="pic" snacksid="${item.snacks_id}"><input type="file" style="display: none" name="file${item.snacks_id}" id="myfile${item.snacks_id}">' +
                            '{@if item.snacks_pic == null}' +
                             '<img src="/static/image/icon/nopic.png"  style="width: 100px;height: 100px;">'+
                            '<a href="javascript:;" class="btn-choose-file btn">选择并上传</a>'+
                             '{@else}' +
                               '<img src="http://211.155.92.139:9001/${item.snacks_pic}" style="width: 100px;height: 100px;">'+
                               '<a href="javascript:;" class="btn-choose-file btn">重新上传</a>'+
                            '{@/if}</td>'+
                           '<td class="barCode"><input type="text" value=${item.snacks_bar_code} class="form"/></td>'+
                               '<td class="costPrice"><input type="text" value=${item.snacks_cost_price} class="form"/></td>'+
                           '<td class="price"><input type="text" value=${item.snacks_sell_price} class="form"/></td>'+
                           '<td class="inventoryNum"><input type="text" value=${item.snacks_stock_number} class="form"/></td>'+
                           '<td data-foodId=${item.snacks_id} class="foodManage">'+
                           '<a class="saveFood btn btn-sm btn-success" data-status=${item.snacks_status} >保存</a>'+
                           '<a class="stopFood btn btn-sm btn-warning" data-status=${item.snacks_status}>删除</a>'+
                           '</td>'+
                           '</tr>'+
                           '{@/if}'+
                      '{@/each}';
           if(data.foods.length == 0){
              tpl = '<tr class="stopedFood"><td colspan="7" style="text-align:center;color: red;">仓库为空,请尽快录入数据</td></tr>';
           }
           var tmp = juicer(tpl,data);
           $("#foodlist").html(tmp);
       },
        //图片处理
        _dealPic:function(){
            //上传图片
            $("#foodlist").on("click",".btn-choose-file",function(){

                var self = $(this);
                var snacks_id = $(this).parents(".pic").attr("snacksid");
                var fileElementId = $(this).parents(".pic").find("input");
                $(fileElementId).click();
                var fileid = $(fileElementId).attr("id");
                $(fileElementId).change(function(){
                    if( $(fileElementId).val() != ""){
                        Headertip.info("上传图片中....")
                        $.ajaxFileUpload({
                            url:'/food/foodPicUploadfile?snacks_id='+snacks_id+"&storage_id="+$("#foodStorage").val(),
                            secureuri:false,
                            fileElementId:fileid,
                            dataType: 'json',
                            success: function (data, status)
                            {
                                if(data.code == 0){
                                    Headertip.error("错误信息："+data.msg);
                                    return;
                                }
                                if(data.code == 1){
                                    Headertip.success("上传成功");
                                    $(self).prev().attr("src","http://211.155.92.139:9001/"+data.data);
                                }
                                else{
                                    Headertip.error("上传失败");
                                }
                            },
                            error: function (data, status, e)            //相当于java中catch语句块的用法
                            {
                                Headertip.error("500服务器异常");
                            }
                        });

                    }else{

                    }
                });

            })
        }
   };
        index.init();
});