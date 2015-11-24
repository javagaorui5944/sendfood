/**
 * 订单管理，当点击管理订单弹出的模态框
 * 订单状态，10表示未配送，20表示配送中，30表示配送完成，
 * 40表示结算完成，50表示返回入库
 * */


define(["lib/jquery","widget/dialog/dialog",'tpl/orderManageDialogTpl','util/request','util/Headertip']
	,function($,dialog,Dialogtpl,request,Headertip){
	var orderDialog = {
			
		init:function(tr){
			this._createHtml(tr);
		},
		//构造弹框内部html
        _createHtml:function(tr){

            var order_query_id = $(tr).parent().attr("order_query_id");
            var order_id = $(tr).parent().attr("order_id");
            var order_status = $(tr).parent().attr("order_status");
            var school = $(tr).parent().attr("school");
			var staff_name = $(tr).parent().attr("staff_name");
			var building = $(tr).parent().attr("building");
			var dormitory = $(tr).parent().attr("dormitory");




        	orderDialog.orderDialog =  new dialog({
                modalTitle:"操作订单",
                contentHtml: Dialogtpl.dialogtpl,
                //初始化事件
                initFunc:function(){
                	
                	//判断订单状态，显示不同的进度条
                	$("#wizardProgress").css({"width":parseInt(order_status)/50*100+"%"})
                	switch(order_status){
	    				case "10": {
	    					$("#wizardTab > li:nth-child(1)").addClass("active");
	    					request.get("/orderManage/orderList",{order_id:order_id},function(ret){
								if(1 == ret.code){
									request.post("/organizationManage/listAllstaff",{},function(ret1){
										 var data = {};
										  data.order_cost_money = 0;
										 for(var i = 0; i < ret.data.orderitemlist.length;i++){
                                             data.order_cost_money += ret.data.orderitemlist[i].snacks_number * ret.data.orderitemlist[i].snacks_sell_price
                                          }
										 data.orderitemlist =  ret.data.orderitemlist;
										 data.personlist = ret1.data;
										 data.dormitory = dormitory;
										 data.order_create_time = ret.data.orderinfo.order_create_time;
										 data.school = school;
										 data.building = building;
										 data.order_query_id = order_query_id;
										 data.staff_name = staff_name;
										 var tpl = juicer(Dialogtpl.order10,data);
										 $("#wizardContent1").html(tpl);

										 //修改配送人员
										 $("#J-revisePerson").click(function(){
											request.get("/orderManage/staff_update",{orderid:order_id,staff_id:$("#J-sendPerson").val()},function(ret){
												if(1 == ret.code){
													Headertip.success("确认配送人员并开始配送",true,3000);
													$(".modal-dialog button.close").click();
													$(".pagination a.active").click();
                                                    $("#pager .active").click();
												}else{
													Headertip.success(ret.msg,true,3000);
												}

											});
										 })

									});
								}
	    					});
							$(".J-modal-save").remove();
							$(".btn-modal-close").html("关闭");
			    			break;
			    		};
	    				case "20": {
	    					$("#wizardTab > li:nth-child(2)").addClass("active");
	    					request.get("/orderManage/orderList",{order_id:order_id},function(ret){
								if(1 == ret.code){
									request.post("/organizationManage/listAllstaff",{},function(ret1){
										 var data = {};
										  data.order_cost_money = 0;
										 for(var i = 0; i < ret.data.orderitemlist.length;i++){
                                             data.order_cost_money += ret.data.orderitemlist[i].snacks_number * ret.data.orderitemlist[i].snacks_sell_price
                                          }
										 data.orderitemlist =  ret.data.orderitemlist;
										 data.personlist = ret1.data;
										 data.dormitory = dormitory;
										  data.order_create_time = ret.data.orderinfo.order_create_time;
										 data.school = school;
										 data.building = building;
										  data.order_query_id = order_query_id;
										 data.staff_name = staff_name;
										 var tpl = juicer(Dialogtpl.order20,data);
										 $("#wizardContent1").html(tpl);
									});
								}
							});

							$(".J-modal-save").html("确认配送已完成").click(function(){
                                request.get("/orderManage/order_sended_update",{
                                    orderid:order_id
                                },function(res){
                                    if(1 == res.code){
                                        Headertip.success("订单配送已完成,请及时完成结算",true,3000);
                                        $("#pager .active").click();
                                        $(".btn-modal-close").click();
                                    }
                                })
                            });
							$(".btn-modal-close").html("关闭");
	    					break;
	    				}
	    				case "30":{
	    					$("#wizardTab > li:nth-child(3)").addClass("active");
	    					request.get("/orderManage/orderList",{order_id:order_id},function(ret){
								if(1 == ret.code){
									request.post("/organizationManage/listAllstaff",{},function(ret1){
										 var data = {};
										  data.order_cost_money = 0;
										  for(var i = 0; i < ret.data.orderitemlist.length;i++){
                                                data.order_cost_money += ret.data.orderitemlist[i].snacks_number * ret.data.orderitemlist[i].snacks_sell_price
                                             }
										 data.orderitemlist =  ret.data.orderitemlist;
										 data.personlist = ret1.data;
										  data.order_create_time = ret.data.orderinfo.order_create_time;
										 data.dormitory = dormitory;
										 data.school = school;
										 data.building = building;
										 data.order_query_id = order_query_id;
										 data.staff_name = staff_name;
										 var tpl = juicer(Dialogtpl.order30,data);
										 $("#wizardContent1").html(tpl);
                                        $("#wizardContent1 .consumeNumber").blur(function(){
                                            var snacks_number = $(this).attr("snacks_number");
                                            var num = $(this).val().trim();
                                            if(/^[0-9]+[0-9]*$/.test(num) && num <= parseInt(snacks_number)){
                                                $(this).removeClass("error");
                                            }else{
                                                $(this).addClass("error");
                                            }


                                        });



                                        $(".J-modal-save").click(function(){
                                            if(confirm("确认结算？请仔细核对消耗数量无误")){
                                                var sell_items = {"foods":[]};
                                                if($("#wizardContent1 .error").length == 0){
                                                    $("#wizardContent1 .consumeNumber").each(function(){
                                                        sell_items.foods.push({
                                                            snacks_id:$(this).attr("snacks_id"),
                                                            snacks_num:$(this).val().trim()
                                                        });
                                                    })
                                                    request.post("/orderManage/order_balance_update",{
                                                        order_id:order_id,
                                                        sell_items:JSON.stringify(sell_items)
                                                    },function(res){
                                                        if(1 == res.code){
                                                            Headertip.success("结算成功,请及时返货入库，每一次快速入库都是减少一次损失",true,5000);
                                                        }else{
                                                            Headertip.error(res.msg,true,3000);
                                                        }
                                                        $("#pager .active").click();
                                                        $(".btn-modal-close").click();
                                                    });

                                                }

                                            }

                                        });
									});
								}
							});
                            $(".J-modal-save").html("结算确认");
							$(".btn-modal-close").html("关闭");

	    					break;
	    				}
	    				case "40": {
	    					$("#wizardTab > li:nth-child(4)").addClass("active");
							request.get("/orderManage/getOrderBack",{order_id:order_id},function(ret){
								if(1 == ret.code){
									request.post("/organizationManage/listAllstaff",{},function(ret1){
										 var data = {};
										   data.view =  ret.data.orderItems;
                                         data.order_create_time = ret.data.order_create_time;
                                          data.order_cost_money = 0;
                                          var orderItems = ret.data.orderItems;
                                         for(var i = 0; i < orderItems.length;i++){
                                            data.order_cost_money += orderItems[i].eat_number * orderItems[i].snacks_sell_price
                                         }
										 data.personlist = ret1.data;
										 data.dormitory = dormitory;
										 data.school = school;
										 data.building = building;
										 data.order_query_id = order_query_id;
										 data.staff_name = staff_name;
										 var tpl = juicer(Dialogtpl.order40,data);
										 $("#wizardContent1").html(tpl);
                                        $(".J-modal-save").click(function(){
                                            if(confirm("确认配色人员已经将货品返回仓库？")){
                                                //返货入库
                                                var order_note = $("#order_note").val().trim()
                                                    if(order_note == ""){
                                                     Headertip.error("备注不能为空",true,5000);
                                                        return;
                                                    }
                                                request.post("/orderManage/updateOrderByPhone",{
                                                    order_id:order_id,
                                                    order_note:order_note,
                                                    order_status:50
                                                },function(ret){
                                                    if(1 == ret.code){
                                                        Headertip.success("返货入库成功",true,5000);
                                                        $("#pager .active").click();
                                                    }
                                                });
                                            }
                                        });
									});
								}
							});
                            $(".J-modal-save").html("确认返货入库");
	    					break;
	    				}
	    				case "50": {
	    					$("#wizardTab > li:nth-child(5)").addClass("active");
                            $(".J-modal-save").remove();
                            $(".btn-modal-close").html("关闭");
                            request.get("/orderManage/getOrderBack",{order_id:order_id},function(ret){
                                if(1 == ret.code){
                                    request.post("/organizationManage/listAllstaff",{},function(ret1){
                                        var data = {};
                                         data.view =  ret.data.orderItems;
                                            data.order_create_time = ret.data.order_create_time;
                                             data.order_cost_money = 0;
                                             var orderItems = ret.data.orderItems;
                                             for(var i = 0; i < orderItems.length;i++){
                                                data.order_cost_money += orderItems[i].eat_number * orderItems[i].snacks_sell_price
                                             }
                                         data.personlist = ret1.data;
                                         data.dormitory = dormitory;
                                         data.school = school;
                                         data.building = building;
                                         data.order_query_id = order_query_id;
                                         data.staff_name = staff_name;
                                        var tpl = juicer(Dialogtpl.order50,data);
                                        $("#wizardContent1").html(tpl);
                                        $(".J-modal-save").click(function(){
                                            if(confirm("确认配色人员已经将货品返回仓库？")){
                                                //返货入库
                                                request.get("/orderManage/order_back_select",{
                                                    order_id:order_id,
                                                    operation:50
                                                },function(ret){
                                                    if(1 == ret.code){
                                                        Headertip.success("返货入库成功",true,5000);
                                                        $("#pager .active").click();
                                                    }
                                                });
                                            }
                                        });
                                    });
                                }
                            });
	    					break;
	    				}
	    				default:return;
                	}
                	
                	
                	
                }
            });
        },
        //保存事件
        save:function(callback){
            Dialog.movedialog.find(".J-modal-save").on("click",function(e){
                callback();
            })
        },
        //关闭事件
        close:function(){
            Dialog.movedialog.find(".btn-modal-close").click();
        }
		
		
	}; 
	
	return orderDialog;
});
