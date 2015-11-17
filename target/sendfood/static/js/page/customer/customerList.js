require.config({
    baseUrl :  BEEN.STATIC_ROOT
});

require(['lib/jquery','modules/baseModule','util/request','modules/bridge','modules/dialog/addCustomer','modules/dialog/ManageUser',
    'util/Headertip','widget/AjaxPager','lib/juicer']
    ,function($,baseModule,request,bridge,addCustomer,manageUser,Headertip,AjaxPager){
        var index = {
            init:function(){
                var that = this;
                AjaxPager.init({
                    elem:$("#page"),
                    pagerUrl:'/userManage/getAllCustomer',
                    current:1,
                    handle:function(resp){
                      that._customerList($("#userlist"),resp.data);
                    }
                });
                this._addCst();
            },

            //生成消费者列表
            _customerList:function($ele,data){
                var tpl = '{@each customers as item, index}'+
                             '<tr>' +
                               '<td>${index}</td>'+
                               '<td class="name"><input type="text" value=${item.customer_name} class="form form-control"/></td>'+
                               '<td class="tel"><input type="text" value=${item.customer_tel} class="form form-control"/></td>' +
                               '<td class="school-name">${item.school_name}</td>' +
                               '<td class="building-name">${item.building_name}</td>' +
                               '<td class="dormitory-name">${item.dormitory_name}</td>' +
                               '<td class="status">'+
                                   '{@if item.customer_status === 0 }' +
                                    '<span style="color:red;">已删除</span>'+
                                   '{@else}' +
                                     '<span>正常</span>'+
                                   '{@/if}' +
                               '</td>' +
                               '<td data-userid= ${item.customer_id}>'+
                                   '<a class="btn btn-sm btn-success event-manage">修改</a>'+
                                   '{@if item.customer_status === 0 }' +
                                      '<a class="btn btn-sm btn-success event-delete" data-status=${item.customer_status}>恢复账号</a>'+
                                   '{@else}' +
                                      '<a class="btn btn-sm btn-success event-delete" data-status=${item.customer_status}>删除账号</a>'+
                                   '{@/if}' +
                               '</td>'+
                             '<tr/>'+
                           '{@/each}';
                var tmp = juicer(tpl,data);
                $ele.html(tmp);
                this._changeCst();
                this._deletCst();
            },
            //修改消费者
            _changeCst:function(){
              $(".event-manage").click(function(){
                  var customer = {};
                  customer.customer_id = $(this).parent().attr("data-userid");
                  customer.customer_name = $(this).parent().prevAll(".name").find("input").val().trim();
                  customer.customer_tel = $(this).parent().prevAll(".tel").find("input").val().trim();
                  if(customer.customer_name == ""){
                      Headertip.error("名字不为空",false,3000);
                      return;
                  }
                  /*if(/[\u4e00-\u9fa5]\w\d{0,5}/.test(customer.customer_name) == false){
                      Headertip.error("鍚嶅瓧鍙兘杈撳叆浜斾釜瀛楃浠ュ唴鐨勪腑鏂�,false,3000);
                      return;
                  }*/
                  if(customer.customer_tel == ""){
                      Headertip.error("电话号码不为空",false,3000);
                      return;
                  }
                  if(/\d{11}/.test(customer.customer_tel) == false){
                      Headertip.error("请输入11位电话号码",false,3000);
                      return;
                  }
                  request.post('/userManage/updateCustomer',customer,function(resp){
                      if(resp.code == 1){
                         Headertip.success("修改成功",false,3000);
                      }
                      else{
                         Headertip.error(resp.msg,false,3000);
                      }
                  });
              });
            },
            //冻结账号
            _deletCst:function(){
                $(".event-delete").click(function(){
                    var customer = {};
                    var that = this;
                    customer.customer_id = $(this).parent().attr("data-userid");
                    customer.customer_status = $(this).attr("data-status") == 10 ? 0 :10;
                    if(customer.customer_id == undefined){
                        Headertip.error("服务器在休息",false,3000);
                        return;
                    }
                    if(customer.customer_status == undefined){
                        Headertip.error("服务器在休息",false,3000);
                        return;
                    }
                    if(!confirm("确认删除账号？")){
                        return;
                    }
                    request.post('/userManage/changeCustomer',customer,function(resp){
                        if(resp.code ==1 ){
                           Headertip.success("修改成功",false,3000);
                           if(customer.customer_status == 0 ){
                              $(that).attr("data-status","10");
                              $(that).html("删除");
                              $(that).parent().prevAll(".status").html("正常");
                           }else{
                              $(that).attr("data-status","0");
                              $(that).html("恢复账号");
                              $(that).parent().prevAll(".status").html("已删除");
                           }
                        }
                        else{
                           Headertip.error(resp.msg,false,3000);
                        }
                    });
                });
            },
            //添加消费者
            _addCst:function(){
                $("#J-addCustomer").click(function(){
                    addCustomer.init({
                        initFunc:function(){
                            var tpl,
                                tmp;
                            request.post('/area/getSchool',function(resp){
                                if(resp.code == 1){
                                   tpl = '<option>请选择学校</option>'+
                                         '{@each schools as item}' +
                                         '  <option value="${item.school_id}">${item.school_name}</option>' +
                                         '{@/each}';
                                   tmp = juicer(tpl,resp.data);
                                   $("#school").html(tmp);
                                }else{
                                    Headertip.error(resp.msg,false,3000);
                                }
                            });
                            $("#school").bind("change",function(){
                                  var schoolId = $(this).find("option:selected").val();
                                  if(isNaN(schoolId) == true){return}
                                  request.post('/area/getBuilding',{'school_id':schoolId},function(resp){
                                      if(resp.code == 1){
                                          tpl = '<option>请选择楼栋</option>'+
                                                '{@each buildings as item}' +
                                                '  <option value="${item.building_id}">${item.building_name}</option>' +
                                                '{@/each}';
                                          tmp = juicer(tpl,resp.data);
                                          $("#building").html(tmp);
                                      }else{
                                          Headertip.error(resp.msg,false,3000);
                                      }
                                  });
                            });
                            $("#building").bind("change",function(){
                                var  buildingId = $(this).find("option:selected").val();
                                if(isNaN(buildingId) == true){return}
                                request.post('/area/getDormitory',{'building_id':buildingId},function(resp){
                                    if(resp.code == 1){
                                        tpl = '<option>请选择寝室</option>'+
                                              '{@each dormitories as item}' +
                                              '<option value="${item.dormitory_id}">${item.dormitory_name}</option>' +
                                              '{@/each}';
                                        tmp = juicer(tpl,resp.data);
                                        $("#dormitory").html(tmp);
                                    }else{
                                        Headertip.error(resp.msg,false,3000);
                                    }
                                });
                            });

                        }
                    });
                    addCustomer.save(function(){
                        var customer = {};
                        customer.customer_name = $("#name").val().trim();
                        customer.customer_tel = $("#tel").val().trim();
                        customer.school_id = $("#school option:selected").val().trim();
                        customer.building_id = $("#building option:selected").val().trim();
                        customer.dormitory_id = $("#dormitory option:selected").val().trim();
                        if(customer.customer_name == "" || /[\u4e00-\u9fa5]{0,5}/.test(customer.customer_name) == false){
                            Headertip.error("名字不为空且长度不超过5个字符");
                            return;
                        }
                        if(customer.customer_tel == "" || /\d{11}/.test(customer.customer_tel) == false){
                            Headertip.error("电话不为空且为11位数字");
                            return;
                        }
                        if(isNaN(customer.school_id) == true){
                            Headertip.error("请选择学校");
                            return;
                        }
                        if(isNaN(customer.building_id) == true){
                            Headertip.error("请选择楼栋");
                            return;
                        }
                        if(isNaN(customer.dormitory_id) == true){
                            Headertip.error("请选择寝室");
                            return;
                        }
                        request.get('/userManage/addCustomer',customer,function(resp){
                            if(resp.code == 1){
                               Headertip.success("添加成功");
                                setTimeout(function(){
                                    window.location.reload();
                                },500);
                            }
                            else{
                                Headertip.error("添加失败");
                            }
                        });
                    });
                });
            }
        };
        index.init();
    });