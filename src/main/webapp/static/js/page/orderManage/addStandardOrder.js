
require.config({
    baseUrl: BEEN.STATIC_ROOT,
    paths:{
        'xdoc':'http://xdoc.aliapp.com/xdoc'
    },
    shim:{
        'lib/jq-qrcode':['lib/jquery'],
        'xdoc':{exports:'XDoc'}
    }
});
require(['lib/jquery', 'modules/baseModule', 'util/request', 'modules/bridge'
        , 'util/Headertip', 'widget/AjaxPager', 'lib/juicer','lib/jq-qrcode','xdoc'],
    function ($, baseModule, request, bridge, Headertip, AjaxPager,jc,qrcode,xdoc) {
		var orders="";
        var add = {
            init: function () {
                add._initSchoolSelect();//初始化学校下拉框
                add._saveOrder();//保存标准订单
                add._deleteOrder();//删除订单
                add._addNewOrder();//新增订单
                add._orderListDown();//下载订单（请在此扩展下载）
                add._chooseFood();//从库存里面选中食物作为标准订单
            },
            //初始化学校下拉框
            _initSchoolSelect: function () {
                request.post("/OrderItem/getAllSchool", {}, function (ret) {
                    if (1 == ret.code) {
                        var tpl = '{@each data as item,index}' +
                            '<option value="${item.school_id},${item.storage_id}" storage_id="">${item.school_name}</option>' +
                            '{@/each}';
                        var tmp = juicer(tpl, ret);
                        var select = $("#J-schoolSelect");
                        select.append(tmp);
                        //当只有一个学校的时候就直接加载
                        if (1 == ret.data.length) {
                            select.html(tmp);
                            //初始化订单下拉框
                            add._initOrderSelect(ret.data[0].school_id, ret.data[0].storage_id);
                        }
                        //学校下拉框改变事件
                        select.change(function () {
                            var value = $(this).val();
                            var storage_id = (value).split(",")[1];
                            var school_id = (value).split(",")[0];
                            //再这之前把内存的数据清空
                            bridge.register("food", []);
                            //初始化订单下拉框
                            add._initOrderSelect(school_id, storage_id);
                        });
                    }
                });
            },
            //初始化订单下拉框
            _initOrderSelect: function (school_id, storage_id) {
                request.get("/order/getDefaultOrderBySchId", {school_id: school_id}, function (ret) {
                    if (1 == ret.code) {
                        var tpl = '{@each data as item,index}' +
                            '<option value=' + school_id + ',' + storage_id + ',${index},${item.default_order_id} storage_id="">${item.default_order_name}</option>' +
                            '{@/each}';
                        var tmp = juicer(tpl, ret);
                        var select = $("#J-orderSelect");
                        select.append(tmp);
                        //订单下拉框改变事件
                        select.change(function () {
                            var value = $(this).val();
                            var storage_id = (value).split(",")[1];
                            var school_id = (value).split(",")[0];
                            var index = (value).split(",")[2];
                            //再这之前把内存的数据清空
                            bridge.register("food", []);
                            //显示当前学校的标准订单名称
                            add._getDefaultOrderBySchId(school_id, storage_id, index);
                        });
                    }
                });
            },
            //根据学校id取得学校当前有效的标准订单
            _getDefaultOrderBySchId: function (school_id, storage_id, index) {
                request.get("/order/getDefaultOrderBySchId", {school_id: school_id}, function (ret) {
                    if (1 == ret.code) {
                        if (ret.data) {
                            if (!ret.data) {
                                Headertip.error("当前学校暂时没有标准订单,请及时添加", 3000, true);
                                //根据学校id取得当前学校库存
                                add._showStorage(storage_id);
                                return;
                            }
                            /*$("#J-sta-order").val(ret.data.default_order_name);
                             $("#J-sta-order").attr("default_order_id", ret.data.default_order_id);*/
                            var tpl = '{@each orderItems as item,index}' +
                                '<tr snacks_id="${item.snacks_id}" id="tr${item.snacks_id}">' +
                                '<td class="barCode">${item.snacks_bar_code}</td>' +
                                '<td class="foodName">${item.snacks_name} </td>' +
                                '<td class="costPrice">${item.snacks_cost_price} </td>' +
                                '<td class="price">${item.snacks_sell_price} </td>' +
                                '<td class="snacks_number">${item.snacks_number}</td>' +
                                '<td data-foodId=${item.snacks_id} class="foodManage">' +
                                '<a href="javascript:;" class="J-revise btn btn-success">修改</a>' +
                                '<a href="javascript:;" class="J-delete btn ">删除</a>' +
                                '</td>' +
                                '</tr>' +
                                '{@/each}';
                            //重新加入缓存
                            bridge.register("food", ret.data[index].orderItems);
                            var temp = juicer(tpl, ret.data[index]);
                            $("#J-defaultList").html(temp);
                            //根据学校id取得当前学校库存
                            add._showStorage(storage_id);
                        } else {
                            Headertip.error(ret.msg, 3000, true);
                        }
                        $("#J-defaultList").on("click", ".J-revise", function () {
                            $(this).removeClass("J-revise").addClass("J-save").html("保存");
                            var tr = $(this).parents("tr");
                            var orderName = tr.find(".snacks_number").html().trim();
                            tr.find(".snacks_number").html('<input type="text" class="form-control" value="' + orderName + '">');
                        });
                        $("#J-defaultList").on("click", ".J-save", function () {
                            var tr = $(this).parents("tr");
                            var snacks_number = tr.find(".snacks_number > input").val().trim();
                            if (!/^([1-9][0-9]*)$/.test(snacks_number)) {
                                Headertip.error("请输入大于0的正整数", true, 3000);
                                return;
                            }
                            $(this).removeClass("J-save").addClass("J-revise").html("修改");
                            tr.find(".snacks_number").html(snacks_number);
                            var snacks_id = tr.attr("snacks_id");
                            var obj = add._orderOperate().get(snacks_id);
                            obj.snacks_number = snacks_number;
                            add._orderOperate().update(obj);

                        });
                        //删除当前订单
                        $("#J-defaultList").on("click", ".J-delete ", function () {
                            var tr = $(this).parents("tr"),
                                snacks_id = tr.attr("snacks_id");
                            //删除缓存
                            add._orderOperate().deleteFood(snacks_id);
                            tr.remove();
                            //左边库存也删除
                            $("#foodtr" + snacks_id).find("input").prop("checked", false);
                            $("#foodtr" + snacks_id).removeClass("foodListSecleted");
                        });
                    }
                });
            },
            //根据学校id加载当前学校的库存
            _showStorage: function (storage_id) {
                var pager = $("#orderPager");
                AjaxPager.init({
                    elem: pager,
                    pagerUrl: '/food/getInventoryFood',
                    current: 1,
                    handle: function (resp) {
                        if (resp.code == 1) {
                            if (resp.data.foods.length == 0) {
                                tpl = '<tr class="stopedFood"><td colspan="7" style="text-align:center;color: red;">仓库为空,请尽快录入数据</td></tr>';
                                $("#J-storageList").html(tpl);
                                return;
                            }
                            var tpl = '{@each foods as item,index}' +
                                '<tr snacks_id="${item.snacks_id}" id="foodtr${item.snacks_id}" >' +
                                '<td class="snacks_bar_code">${item.snacks_bar_code}</td>' +
                                '<td class="snacks_name">${item.snacks_name} </td>' +
                                '<td class="pic" snacksid="${item.snacks_id}">' +
                                '{@if item.snacks_pic == null}' +
                                '<img src="/static/image/icon/nopic.png"  style="width: 100px;height: 100px;">' +
                                '{@else}' +
                                '<img src="http://211.155.92.139:9001/${item.snacks_pic}" style="width: 100px;height: 100px;">' +
                                '{@/if}</td>' +
                                '<td class="snacks_cost_price">${item.snacks_cost_price} </td>' +
                                '<td class="snacks_sell_price">${item.snacks_sell_price} </td>' +
                                '<td class="snacks_stock_number">${item.snacks_stock_number}</td>' +
                                '<td data-foodId=${item.snacks_id} class="foodManage">' +
                                '<div class="checkbox">' +
                                '<label class="label-checkbox">' +
                                '<input type="checkbox" class="btn-choose" id="checkbox${item.snacks_id}">' +
                                '<span class="custom-checkbox" ></span>' +
                                '点击加入订单' +
                                '</label>' +
                                '</div>' +
                                '</td>' +
                                '</tr>' +
                                '{@/each}';
                            var tmp = juicer(tpl, resp.data);
                            $("#J-storageList").html(tmp);
                            var stardFood = add._orderOperate().get();
                            $(stardFood).each(function () {
                                var snackid = this.snacks_id;
                                $("#checkbox" + snackid).prop("checked", true);
                                $("#checkbox" + snackid).parents("tr").addClass("foodListSecleted");
                            })
                        }
                        else {
                            Headertip.error(resp.msg);
                        }
                    },
                    val: {'storage_id': storage_id}
                });
            },
            //从库存里面选中食物作为标准订单
            _chooseFood: function () {
                $("#J-storageList").on("click", ".btn-choose", function () {
                    var tr = $(this).parents("tr");
                    if (tr.hasClass("foodListSecleted")) {
                        var snacks_id = $(tr).attr("snacks_id");
                        //删除右侧的标准订单
                        $("#tr" + snacks_id).remove();
                        //删除缓存
                        add._orderOperate().deleteFood(snacks_id);
                        tr.removeClass("foodListSecleted");
                    } else {

                        tr.addClass("foodListSecleted");
                        //添加到右边去
                        var data = {};
                        data.snacks_bar_code = $(tr).find(".snacks_bar_code").html().trim();
                        data.snacks_id = $(tr).attr("snacks_id");
                        data.snacks_name = $(tr).find(".snacks_name").html().trim();
                        data.snacks_cost_price = $(tr).find(".snacks_cost_price").html().trim();
                        data.snacks_sell_price = $(tr).find(".snacks_sell_price").html().trim();
                        data.snacks_pic = $(tr).find("img").attr("src");
                        data.snacks_number = 1;
                        //添加缓存
                        add._orderOperate().add(data);
                        var tpl = '<tr snacks_id=' + data.snacks_id + ' id="tr' + data.snacks_id + '" >' +
                            '<td class="snacks_bar_code">' + data.snacks_bar_code + '</td>' +
                            '<td class="snacks_name">' + data.snacks_name + '</td>' +
                            '<td class="snacks_cost_price">' + data.snacks_cost_price + '</td>' +
                            '<td class="snacks_sell_price">' + data.snacks_sell_price + '</td>' +
                            '<td class="snacks_number"><input type="text" class="form-control" value="1"></td>' +
                            '<td><a href="javascript:;" class="J-save btn btn-success">保存</a>' +
                            '<a class="btn btn-success J-delete">删除</a></td>' +
                            '</tr>';

                        $("#J-defaultList").append(tpl);
                    }
                });
            },
            //对当前订单的缓存操作
            _orderOperate: function () {
                return {
                    //isRegester:boolean true
                    add: function (value, isReset) {
                        var food = bridge.get("food");
                        var flag = false;
                        if (food) {
                            food = add._orderOperate().get();
                            for (var i = 0; i < food.length; i++) {
                                if (food[i].snacks_id == value.snacks_id) {
                                    flag = true;
                                    break;
                                }
                            }
                            if (flag == false) {
                                food.push(value);
                            }
                            if (isReset) {
                                bridge.register("food", value);
                            } else {
                                bridge.register("food", food);
                            }
                        } else {
                            //第一次进入
                            if (value instanceof Array) {
                                bridge.register("food", value);
                            } else {
                                bridge.register("food", [value]);
                            }

                        }
                    },
                    update: function (value) {
                        var food = bridge.get("food");
                        if (food) {
                            food = add._orderOperate().get();
                            for (var i = 0; i < food.length; i++) {
                                if (food[i].snacks_id == value.snacks_id) {
                                    food[i] = value;
                                }
                            }
                            bridge.register("food", food);
                        }
                    },
                    deleteFood: function (value) {
                        var food = bridge.get("food");
                        if (food) {
                            food = add._orderOperate().get();
                            for (var i = 0; i < food.length; i++) {
                                if (food[i].snacks_id == value) {
                                    delete food[i];
                                }
                            }
                            bridge.register("food", food);
                        }
                    },
                    get: function () {
                        var food = bridge.get("food");
                        if (!food)return;
                        var newFood = [];
                        for (var i = 0; i < food.length; i++) {
                            if (food[i] != undefined) {
                                newFood.push(food[i]);
                            }
                        }
                        return newFood;
                    }
                }
            },
            //新增标准订单
            _addNewOrder: function () {
                $("#J-addOrder").click(function () {
                    var name = prompt("请输入订单名称");
                    if(!!name){
                        var choosedSchoolId = $("#J-schoolSelect").val().split(",")[0];
                        if (choosedSchoolId == "-1") {
                            Headertip.error("请选择学校", true, 3000);
                            return;
                        }
                        var school_id = choosedSchoolId;
                        request.post("/OrderItem/saveNewDefaultOrder", {
                            newOrderItem: JSON.stringify({
                                school_id: school_id,
                                default_order_name: name,
                                Default_order_id: 0,
                                OrderItem: []
                            })
                        }, function (res) {
                            if (1 == res.code) {
                                Headertip.success("新增订单成功!正在刷新...", true, 4000);
                                setTimeout(function () {
                                    window.location.reload();
                                },1000);
                            } else {
                                Headertip.error(res.msg, true, 4000);
                            }
                        });
                    }
                });
            },
            //保存标准订单
            _saveOrder: function () {
                $("#J-orderSave").click(function () {
                    var choosedSchool = $("#J-schoolSelect > option:selected").text(),
                        choosedSchoolId = $("#J-schoolSelect").val().split(",")[0];
                    if (choosedSchoolId == "-1") {
                        Headertip.error("请选择学校", true, 3000);
                        return;
                    }

                    if ($("#J-orderSelect").val().split(",")[0] == "-1") {
                        Headertip.error("请选择订单号或新增订单", true, 3000);
                        return;
                    }
                    if (!add._orderOperate().get() || add._orderOperate().get().length == 0) {
                        Headertip.error("请选择食物", true, 3000);
                        return;
                    }
                    var input = $("#J-defaultList input");
                    if (input.length > 0) {
                        Headertip.error("请确认商品数量", true, 3000);
                        $(input).each(function () {
                            $(this).addClass("border-red");
                        });
                        return;
                    }


                    var school_id = choosedSchoolId,
                        default_order_name = $("#J-orderSelect > option:selected").text(),
                        default_order_id = $("#J-orderSelect").val().split(",")[3],
                        OrderItem = add._orderOperate().get();
                    request.post("/OrderItem/saveNewDefaultOrder", {
                        newOrderItem: JSON.stringify({
                            school_id: school_id,
                            default_order_name: default_order_name,
                            Default_order_id: default_order_id,
                            OrderItem: OrderItem
                        })
                    }, function (res) {
                        if (1 == res.code) {
                            Headertip.success("保存标准订单成功", true, 4000);
                            window.location.reload();
                        } else {
                            Headertip.error(res.msg, true, 4000);
                        }
                    });
                    
                })
            },
            //删除标准订单
            _deleteOrder: function () {
                $("#J-orderDelete").click(function () {
                    var choosedSchool = $("#J-schoolSelect > option:selected").text(),
                        choosedSchoolId = $("#J-schoolSelect").val().split(",")[0];
                    if (choosedSchoolId == "-1") {
                        Headertip.error("请选择学校", true, 3000);
                        return;
                    }

                    if ($("#J-orderSelect").val().split(",")[0] == "-1") {
                        Headertip.error("请选择订单号", true, 3000);
                        return;
                    }
                    var default_order_id = $("#J-orderSelect").val().split(",")[3];
                    console.log("订单号");
                    console.log(default_order_id);
                    if(confirm("确认删除吗？")){
                        request.post("/OrderItem/deleteDefaultOrder", {
                            default_order_id: default_order_id
                        }, function (res) {
                            if (1 == res.code) {
                                Headertip.success("删除订单成功!正在刷新...", true, 4000);
                                setTimeout(function () {
                                    window.location.reload();
                                },1000);
                            } else {
                                Headertip.error(res.msg, true, 4000);
                            }
                        });
                    }
                });
            },
            //下载订单
            _orderListDown: function () {
                var self = this;
                $("#J-orderDown").click(function () {
                    //检查是否输入下载份数
                    var downNum = $("#J_downOrderNum").val();
                    var isNum = /^[1-9][0-9]*$/g.test(downNum);
                    if(!isNum){return}

                    //先检查当前页面的订单是否已经保存到数据库
                    var orderId = $("#J-orderSelect").val().split(",")[3];
                    var schoolId = $("#J-schoolSelect").val().split(",")[0];
    
                    if(!orderId&&!schoolId){ return ;}
                    request.get("/order/getDefaultOrderBySchId",
                        {school_id: schoolId},function (resp) {
                             if(resp.code == 1){
                                 var currentFood = []; //存储当前页面上的食物清单
                                 var i = 0;    
                                 var orderList = resp.data;
                                 var orderFood;//从后台获取订单食物列表
                                 var orderFoodItem = [];
 
                                 $("#J-defaultList tr").each(function (i) {
                                     var foodId = $(this).attr("snacks_id");
                                     if(foodId){currentFood.push(foodId)}
                                 });

                                 for(;i<orderList.length;i++){
                                     if(orderList[i].default_order_id == orderId){
                                         orderFood = orderList[i].orderItems;
                                     }
                                 } 
                                 $.each(orderFood,function (index,value) {
                                     orderFoodItem.push(value["snacks_id"]);
                                 }) 
                                 
                                 if( currentFood.sort().toString() === orderFoodItem.sort().toString() ){
                                     
                                     self._downOrderLocal(orderId,downNum);
                                 }else{
                                     Headertip.error("请先保存订单才能下载", true, 3000);
                                 }
                             }      
                    });
                });
            },
            //下载订单到本地
            _downOrderLocal:function  (id,num) {
                var opt = {};
                    opt.defaultorder_id = 2;
                    opt.ordercount = num;
                var self = this;
                var that = {};
                    that._downOrder = this._downOrder;
                    that.count = -1;
                    that.orderId = [];
                    that.orderinfo = [];
                request.get('/order/addOrdersBy',opt,function(resp){
                    if(resp.orderids){
                        $.each(resp.orderids,function(key,value){
                            that.orderId.push(value);
                            that.orderinfo.push(key);
                        });
                        that.num = that.orderId.length;
                        that.itvalId = setInterval(function(){
                             that.count++;
                             if(that.count === that.num){
                            	 
                                clearInterval(that.itvalId)
                                 orders = orders;
                                xdoc.run(orders, "docx", {}, "_blank");
                                setTimeout(function(){
                                    location.reload();
                                },5000);
                             }
                             that._downOrder(that.orderinfo[that.count],that.orderId[that.count]);
                        },3000)
                    }
                });              
            },
            //拼接二维码及订单并且下载一份
            _downOrder: function(info,str){
                 var canvas,
                     order,
                     img = new Image();
                
                 //先移除编辑栏
                 $("#J_edit_options").remove();
                 $("#J-defaultList .foodManage").html("");
                 $("#J-defaultList .J_foodManage").html("吃货大名");
                 $('#J_qrcode').qrcode({width:60,height:60,text:str+""}); 
                 canvas = $("#J_qrcode > canvas")[0];
                 img.src = canvas.toDataURL("image/png");
                 $("#J_qrcode").html(img);
                 $("#J_info").html(info);
                 order = '<html height=\'100%\'><table>'+$("#J_downOrder").html()+'</table></html>';
                 orders+=order;
               //  xdoc.run(order, "docx", {}, "_blank");

            }
           
        };

        //构造函数
        add.init();
    });