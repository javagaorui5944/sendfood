require.config({
    baseUrl :  BEEN.STATIC_ROOT
});
require(['lib/jquery','modules/baseModule','util/request','modules/tipHover','modules/bridge','modules/select/select',
        'util/Headertip',
        'lib/jqueryPlugs/jquery.ztree.all-3.5'],
    function($,bs,request,tipHover,bridge,select,Headertip){
        var save = {
            init:function(){
            	//判断是否是新增还是修改,如果是修改，就得去取得数据
                save._isAdd();
                //构造上级组织树形结构数
                save._parentOrgTree();
                //保存验证
                $("#J-save").click(save._saveValidForm);
                //人员删选器
                save._selectPerson();

            },
            
            //判断是否是新增还是修改,如果是修改，就得去取得数据
            _isAdd:function(){
                var isAdd = $("#J-isAdd").val(),
                    level = $("#J-level").val(),//本层次level
                    id = $("#J-id").val(),
                    parentId = $("#J-parentId").val(),//父层次的id
                    parentLevel = $("#J-parentLevel").val(),//父亲层次level
                    parentName = $("#J-parentName").val();//父亲层次name
                //不是新增
                if(isAdd != "0"){
                        if( parentLevel == 20){
                            $("#J-time-div").show();
                            var time = $("#J-time").attr("time");
                            $("#J-time > option[value='"+time+"']").prop("selected",true);
                        }
                        $("#J-ParentOrganization").attr({"disabled":"disabled"});
                }else{
                     if( parentLevel == 20){
                       $("#J-time-div").show();
                     }
                     if(parentLevel)
                     if(parentId != ""){
                         $("#J-ParentOrganization").attr({"disabled":"disabled"});
                     }
                }
            },
            //负责人
            _selectPerson:function(){
               request.post("/organizationManage/listAllstaff",{},function(ret){
            	   if(1 == ret.code){
            		   var tpl ='<option value="-1">---新增工作人员---</option>'+
            			   		'{@each data.weifenpei as staff}'+			   		   				
			   		   				'<option value="${staff.staff_id}">${staff.staff_name}</option>'+
					   	        '{@/each}'+
        		   				'<option value="-1" align="center">---已分配职位人员---</option>'+
            			   		'{@each data.fenpei as staff}'+
            		   				'<option value="${staff.staff_id}">${staff.staff_name}</option>'+
            		   			'{@/each}';
            		 var tmp = juicer(tpl,ret);
            		 $("#J-Manager").append(tmp);
            		  //下拉框选择事件
                        $("#J-Manager").change(function(){
                            var val = $(this).val();
                            var name = $(this).find("option:selected").text();
                            if(val == '-1'){
                                return;
                            }
                            //判断重复
                            var staffs = $("#J-staff > .staff");
                                for(var i = 0; i < staffs.length; i++){
                                    if($(staffs[i]).attr("real-value") == val){
                                        return;
                                    }
                                }
                                bridge.register('staffs',staffs);


                            var staff = '<button class="btn btn-info staff mr5" real-value="'+val+'">'+name+'&nbsp;&nbsp;x</button>';
                            $("#J-staff").append(staff);
                        })
            	   }else{
            		   Headertip.error("加载员工出错",true,3000);
            	   }
               });
               //点击负责人删除
               $("#J-staff").on("click",".staff",function(){
                    $(this).remove();
               });
            },
            //构造树形结构数据
            _parentOrgTree:function(data){
                $("#J-ParentOrganization").focus(function(){
                    var tipHoverAdd = bridge.get("tipHoverAdd");
                   
                    //判断先前是否已经加载
                    if(tipHoverAdd == null && bridge.get("tree") == null){
                        $("#J-ParentOrganization").tipHover({
                            position:"bottom",
                            isabsolute:false,
                            callback:function(boxobj){
                                bridge.register("boxobj",boxobj);
                                $("#"+boxobj).html('<ul id="J-tree" class="ztree">加载组织中...</ul>');
                                request.post("/organizationManage/listOrganization",{}
                                ,function(ret){
                                    if(ret.code == 1){
                                        save._initTree(ret,"#J-tree");
                                        bridge.register("tipHoverAdd","add");
                                    }else{
                                        Headertip.error("加载上级组织树形菜单出错",true,3000);
                                    }
                                })
                            }
                        })
                    }else{
                        var tipHoverId = bridge.get("tipHover");
                        $("#"+tipHoverId).show();
                    }
                    bridge.register("tree",true);
                    if($(this).val().trim() == ""){
                        $("#J-ParentOrganizationId").val("");
                    }
                });
                $("#J-ParentOrganization").blur(function(){
                    if($(this).val().trim() == ""){
                        $("#J-ParentOrganizationId").val("");
                    }
                });

            },
            //构造树形结构通用方法
            _initTree:function(data,treeNode){
                //重新构造数据结构
                 var all = data.data;
                var tree=[];
                var area = [];
                if(all.length == 0){
                    tree.push({
                        id: "all_",
                        pId: 0,
                        name: "你没有数据权限，请确保你在组织架构中",
                        trueId: 0,
                        tlevel: 0,
                        nodeData:0
                    });
                    var setting = {
                        data: {
                            simpleData: {
                                enable: true
                            }
                        }
                    };
                    $.fn.zTree.init($(treeNode), setting, tree);
                    return;
                }
                if(all[0].type == 50){
                    area = all[0].children;
                    tree.push({
                        id: "all_" + all[0].id,
                        pId: 0,
                        name: all[0].name,
                        trueId: all[0].id,
                        tlevel: all[0].type,
                        nodeData: area
                    });
                }else{
                    area = all;
                }
                for(var i = 0,len = area.length; i < len; i++){
                    if(area.length > 0){
                        var school = area[i].children;
                        tree.push({
                            id:"a_"+area[i].id,
                            pId:"all_"+all[0].id,
                            name:area[i].name,
                            trueId:area[i].id,
                            tlevel:area[i].type//leve需要重命名，因为原插件有这个变量
                        })
                        for(var j = 0,len1 = school.length; j < len1; j++){
                            tree.push({
                                id:"s_"+school[j].id,
                                pId:"a_"+area[i].id,
                                name: school[j].name,
                                trueId:school[j].id,
                                tlevel:school[j].type
                            });
                            var building = school[j].children;
                            for(var k = 0,len2 = building.length; k < len2; k++){
                                tree.push({
                                    id:"b_"+building[k].id,
                                    pId:"s_"+school[j].id,
                                    name: building[k].name,
                                    trueId:building[k].id,
                                    tlevel:building[k].type
                                })
                                var domitory = building[k].children;
                                for(var h = 0,len3 = domitory.length; h < len3; h++){
                                    tree.push({
                                        id:"d_"+domitory[h].id,
                                        pId:"b_"+building[k].id,
                                        name: domitory[h].name,
                                        trueId:domitory[h].id,
                                        tlevel:domitory[h].type
                                    });

                                }
                            }
                        }
                    }
                }
                var setting = {
                    data: {
                        simpleData: {
                            enable: true
                        }
                    },
                    callback: {
                        onClick: save._addParentNode
                    }
                };
                $.fn.zTree.init($(treeNode), setting, tree);
            },
            //对上级组织的增加
            _addParentNode:function(event, treeId, treeNode){
            	//本字段用来区别用户的管理权限，1表示仓库管理员，10表示管理寝室，20表示楼栋，
            	//30表示学校，40表示区县，再通过管理部分关联自己负责的部分
                $("#J-parentId").val(treeNode.trueId);
                $("#J-ParentOrganization").val(treeNode.name);
                $("#J-parentLevel").val(treeNode.tlevel);
                //根据组织类别修改运营人员或者次负责人
                if(treeNode.tlevel == 20){
                    $("#J-time-div").show();
                }else{
                	  $("#J-time-div").hide();
                }
               
                //点击之后隐藏
                $("#"+bridge.get("boxobj")).hide();
            },
            //保存验证
            _saveValidForm:function(){
                //绑定验证事件
                var name = $("#orgName").val().trim(),//组织名字
                    id = $("#J-id").val().trim(),//当前层次id
                    Manager = $("#J-staff .staff"),
                    code = $("#orgCode").val().trim(),
                    type=$("#J-parentLevel").val(),
                    pid = $("#J-parentId").val(),
                    staff_id = [],
                    date = "";

                //根据是新增还是修改来修改id
                var isAdd = $("#J-isAdd").val();
                if(isAdd == "0"){
                    id = -1;
                }
                
                if(name == ""){
                    Headertip.error("组织名不能为空",true,3000)
                    return;
                }
                
                if(code == ""){
                    Headertip.error("组织代码不能为空",true,3000)
                    return;
                }


                if(Manager.length == 0){
                    Headertip.error("负责人不能为空",true,3000)
                    return;
                }
                $(Manager).each(function(){
                    staff_id.push($(this).attr("real-value"));
                });

                if(type == 20){
                	date = $("#J-time").val();
                	 if(date == "-1"){
                        Headertip.error("寝室默认配送时间不能为空",true,3000)
                        return;
                    }

                }

                Headertip.info("保存中...请稍等",true);
                request.post("/organizationManage/addOrganization",
                    {
                        type:type,
                        pid:pid,
                        id:id,
                        Organization_name:name,
                        staff_id:staff_id.join(","),
                        code:code,
                        date:date
                    },function(ret){
                        if(ret.code != 0){
                            Headertip.success("保存成功",true,3000);
                        }else{
                            Headertip.error(ret.msg,true,3000)
                        }
                    })                               
                
            	},

        }

        save.init();
    });