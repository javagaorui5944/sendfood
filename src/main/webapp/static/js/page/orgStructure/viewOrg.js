require.config({
    baseUrl :  BEEN.STATIC_ROOT
});
require(['lib/jquery','modules/baseModule','util/request','lib/jqueryPlugs/jquery.ztree.all-3.5',
        'lib/jqueryPlugs/jquery.organStructrureTree','util/Headertip'
        /**,'modules/dialog/moveDialog',**/,'modules/bridge','lib/juicer'],
    function($,bs,request,tree,jquery_organStructrureTree,Headertip,bridge){

        var Manage = {
            init:function(){

                //构造树形结构数据
                Manage._createTree();
                //新增下级节点，修改，删除功能
                Manage._operateTree();
                //搜索功能
                Manage._searchAuthTree();
            },
            //新增下级节点，修改，转移，删除功能
            _operateTree:function(){
                //本字段用来区别用户的管理权限，1表示仓库管理员，10表示管理寝室，20表示楼栋，
                //30表示学校，40表示区县，再通过管理部分关联自己负责的部分
                //新增下级结构
                var addOrReviseUrl = "/organizationManage/addOrg";
                $("#J-add").click(function(){
                    var node = bridge.get("node");
                    var id = '',type='';
                    if(node == null){
                        var firstNode = $("#J-tree >ul >li > a");
                        id = firstNode.attr("nodeid");
                        type  = firstNode.attr("tlevel");
                    }else{
                        id = node.split(",")[0];
                        type = node.split(",")[1];

                    }
                    if(id == undefined || type == undefined){
                        return;
                    }
                      var url = addOrReviseUrl+"?pid="+id+"&ptype="+type+"&isAdd=true";
                      window.location.href = url;
                });
                //修改
                $("#J-revise").click(function(){
                    var node = bridge.get("node");
                    if(node == null){
                          var firstNode = $("#J-tree >ul >li > a");
                           id = firstNode.attr("nodeid");
                           type  = firstNode.attr("tlevel");
//                        if(type == 40){
//                            Headertip.error("当前节点不",true,3000);
//                           return;
//                        }
                    }else{
                          id = node.split(",")[0];
                          type = node.split(",")[1];
//                          if(type == 40){
//                            return;
//                         }
                    }
                    if(id == undefined || type == undefined){
                        return;
                    }
                      var url = addOrReviseUrl+"?id="+id+"&type="+type+"&isAdd=true";
                      window.location.href = url;
                });
                //删除
                $("#J-delete").click(function(){
                    if(!confirm("确认删除节点？这样会删除当前节点及以下的所有信息")){
                        return;
                    }

                    var node = bridge.get("node");
                    if(node == null){
                        Headertip.error("当前节点不可已删除",true,3000);
                    }else{
                        var id = node.split(",")[0];
                        var type = node.split(",")[1];
                        if(type != 10){
                            Headertip.error("当前节点不可删除,只能删除寝室节点",true,4000);
                            return;
                        }
                        request.post("/organizationManage/deleteOrganization/",{
                            id:id,
                        },function(ret){
                            if(ret.code == 1){
                                Headertip.error("删除成功",true,3000);
                                $("#treeMenu").html("组织树形菜单重新加载中...");
                                $("#J-tree").html("重新获取组织架构中...");
                                //构造树形结构数据
                                Manage._createTree();

                            }else{
                                Headertip.error(ret.msg,true,4000);
                            }
                        });
                    }
                });
            },
            //构造树形结构数据
            _createTree:function(){
                //请求组织架构
                request.post('/organizationManage/listOrganization',
                    {
                    },function(ret){
                        if(ret.code == 1){
                            Manage._initTree(ret.data,$("#treeMenu"));
                            $(".tree").organStructrureTree({
                                data:ret.data,
                                //点击城市节点展示当前下级节点
                                events:[Manage._clickCityNode]
                            });
                        }else{
                            Headertip.error("获取组织架构失败",true,3000);
                        }
                          $("#J-nodeName").html( $("#J-tree >ul >li > a").html());
                    });
            },
            //构造数据结构
            _initTree:function(data,treeNode){



                //重新构造数据结构
                var all = data;
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
                for(var index = 0,lenindex = area.length; index < lenindex; index++) {

                    var school = area[index].children;

                    tree.push({
                        id: "a_" + area[index].id,
                        pId: "all_"+1,
                        name: area[index].name,
                        trueId: area[index].id,
                        tlevel: area[index].type,
                        nodeData: school
                    });
                    for (var i = 0, len = school.length; i < len; i++) {
                        if (school.length > 0) {
                            var building = school[i].children;
                            if (school[i].staff.length > 0) {
                            	
                            	var name  = "负责人:";
                            	for(var n = 0; n < school[i].staff.length; n++){
                            		name += school[i].staff[n].staff_name+" "
                            	}
                            	
                            	
                                tree.push({
                                    id: "sb_" +school[i].staff[0].staff_id,
                                    pId: "a_" + area[index].id,
                                    name: name,
                                    nodeName: school[i].name,
                                    staff: school[i].staff,
                                    icon: "/static/image/tree/treePerson.png",
                                    type: "employNode",
//                                    pos:quyu[i].employNode.pos,
                                    tlevel: school[i].type,
                                    nodeData: building
                                });
                            }
                            tree.push({
                                id: "s_" + school[i].id,
                                pId: "a_" + area[index].id,
                                name: school[i].name,
                                trueId: school[i].id,
                                tlevel: school[i].type,
                                nodeData: building
                            })
                            for (var j = 0, len1 = building.length; j < len1; j++) {
                                var domitory = building[j].children;
                                if (building[j].staff.length > 0) {
                                	
                                	var name  = "负责人:";
                                	for(var n = 0; n < building[j].staff.length; n++){
                                		name += building[j].staff[n].staff_name+" "
                                	}
                                	
                                	
                                    tree.push({
                                        id: "eb_" +building[j].staff[0].staff_id,
                                        pId: "s_" + school[i].id,
                                        name: name,
                                        nodeName: building[j].name,
                                        staff: building[j].staff,
                                        icon: "/static/image/tree/treePerson.png",
//                                      pos:cities[j].employNode.pos,
                                        type: "employNode",
                                        nodeData: domitory,
                                        tlevel: building[j].type

                                    })
                                }
                                tree.push({
                                    id: "b_" + building[j].id,
                                    pId: "s_" + school[i].id,
                                    name: building[j].name,
                                    trueId: building[j].id,
                                    nodeData: domitory,
                                    tlevel: building[j].type

                                })

                                for (var k = 0, len2 = domitory.length; k < len2; k++) {
//                                    var aors = domitory[k].children;
                                    if (domitory[k].staff.length > 0) {
                                    	var name  = "负责人:";
                                    	for(var n = 0; n < domitory[k].staff.length; n++){
                                    		name += domitory[k].staff[n].staff_name+" "
                                    	}
                                    	
                                        tree.push({
                                            id: "db_" + domitory[k].staff[0].staff_id,
                                            pId: "b_" + building[j].id,
                                            name: name,
                                            nodeName: domitory[k].name,
                                            staff: domitory[k].staff,
                                            icon: "/static/image/tree/treePerson.png",
//                                          pos:domitory[k].employNode.pos,
                                            type: "employNode",
                                            nodeData: [],
                                            tlevel: domitory[k].type
                                        });
                                    }
                                    tree.push({
                                        id: "d_" + domitory[k].id,
                                        pId: "b_" + building[j].id,
                                        name: domitory[k].name,
                                        trueId: domitory[k].id,
                                        nodeData: [],
                                        tlevel: domitory[k].type
                                    })
                                }
                            }
                        }//区域
                    }
                }
                var setting = {
                    data: {
                        simpleData: {
                            enable: true
                        }
                    },
                    callback: {
                        onClick: Manage._showTrees
                    }
                };
                $.fn.zTree.init($(treeNode), setting, tree);
            },
            //树形菜单点击事件根据点击的节点，在右边展开相应的树形节点
            _showTrees:function(event, treeId, treeNode){

                //让右边的树形结构滚动条到最左边
                $(".inner-box1").scrollLeft(0);
                //如果是点击的是负责人
                var nodeType = treeNode.type;

                if(nodeType == "employNode"){
                    $("#J-node-msg").hide();
                    $("#J-btn-group").css({"display":"none"});
                    //重新加载节点
                    var nodeData = treeNode.nodeData;
                    var data = {
                        id:treeNode.id,
                        pId:treeNode.pId,
                        staff:treeNode.staff,
                        level: treeNode.tlevel,
                        children:nodeData
                    };
                    $(".tree").organStructrureTree({
                        data:[data],
                        nodeType:"employ",
                        //点击节点的事件,点击节点之后重新加载当前节点的下级节点
                        //原理是：点击当前节点，可以通过保存在当前节点的nodeie和level去找到左侧树形菜单的节点，然后去triggle它
                        events:[function(){
                            $(".btn-node").click(function(){
                                var id = $(this).attr("nodeid");
                                var tlevel =  $(this).attr("tlevel");
                                var nodeId = "";
                                //构造唯一id，用以取得左侧树形结构的节点
                                switch (tlevel){
                                    case '10':nodeId = 'd_' + id;break;
                                    case '20' :nodeId ='b_'+id;break;
                                    case '30' :nodeId ='s_'+id;break;
                                    case '40' :nodeId ='a_'+id;break;
                                    case '50' :nodeId = "all_"+id;break;

                                }
                                var treeObj = $.fn.zTree.getZTreeObj("treeMenu");
                                var treeNode = treeObj.getNodeByParam("id",nodeId, null);
                                treeObj.expandNode(treeNode, false, true, true);
                                var spanId = treeNode.id+"_span";
                                //模拟点击节点
                                $("#"+spanId).click();
                            });
                        }]
                    });
                }else{
                    $("#J-node-msg").show();
                    //设置当前的node信息，以便于修改和新增
                    var id = treeNode.trueId,tlevel = treeNode.tlevel,name = treeNode.name;

                    //重新加载节点
                    var nodeData = treeNode.nodeData;
                    var data = {
                        id:treeNode.trueId,
                        pId:treeNode.pId,
                        name:treeNode.name,
                        type: treeNode.tlevel,
                        children:nodeData
                    };
                    $(".tree").organStructrureTree({
                        data:[data],
                        //当前只有，点击树形node节点的事件
                        events:[Manage._clickCityNode]
                    });

                    if(bridge.has("node")){
                        bridge.set("node",id+","+tlevel+","+name);
                        $("#J-nodeName").html(name);
                    }else{
                        bridge.register("node",id+","+tlevel+","+name);
                        $("#J-nodeName").html(name);
                    }
                    if(tlevel == 10){
                        $("#J-delete").show();
                    }else{
                        $("#J-delete").hide();
                    }

                }
            },
            //点击城市节点，重新加载当前城市下级图形
            _clickCityNode:function(){
                $(".btn-node").click(function() {
                    var id = $(this).attr("nodeid");
                    var tlevel = $(this).attr("tlevel");
                    var name = $(this).text().trim();
                    var nodeId = "";
                    //构造唯一id，用以取得左侧树形结构的节点（根据initTree方法构造的参赛，因为不同类型的id可能相同，所有再前面加了前缀）
                    switch (tlevel) {
                        case '10' :
                            nodeId = 'd_' + id;
                            break;
                        case '20' :
                            nodeId = 'b_' + id;
                            break;
                        case '30' :
                            nodeId = 's_' + id;
                            break;
                        case '40' :
                            nodeId = 'a_' + id;
                            break;
                         case '50' :
                            nodeId = "all_"+id;
                            break;

                    }

                    var treeObj = $.fn.zTree.getZTreeObj("treeMenu");
                    var treeNode = treeObj.getNodeByParam("id", nodeId, null);
                    treeObj.expandNode(treeNode, true, false, true);
                    var spanId = treeNode.tId + "_span";
                    //模拟点击节点
                    $("#" + spanId).click();

                })
            },
            //搜索功能
            _searchAuthTree:function(){
                var nodeList =[];
                var timeFlag ;//给500反应时间
                $("#J-searchAuthTree").on('input',function(e){
                    if(timeFlag){
                        clearTimeout(timeFlag);
                        //展开之前，先把红色的收起来

                        var zTree = $.fn.zTree.getZTreeObj("treeMenu"),
                            nodeList1 = zTree.transformToArray(zTree.getNodes());
                        zTree.expandAll(false);

                        for( var i=0, l=nodeList1.length; i<l; i++) {
                            $("#"+nodeList1[i].tId+"_a").css({"color":"#666"});
                        }
                    }
                    timeFlag = setTimeout(function(){
                        if( $("#J-searchAuthTree").val().trim() != "" ){
                            searchNode(e);
                        }
                    },500)
                });
                function searchNode(e) {
                    var zTree = $.fn.zTree.getZTreeObj("treeMenu");
                    var value = $.trim($("#J-searchAuthTree").val());

                    updateNodes(false);
                    nodeList = zTree.getNodesByParamFuzzy("name", value);
                    updateNodes(true);

                }
                function updateNodes(highlight) {
                    var zTree = $.fn.zTree.getZTreeObj("treeMenu");

                    for( var i=0, l=nodeList.length; i<l; i++) {
                        var flag = zTree.expandNode(nodeList[i], highlight, false, true);
                        //如果是最后一个节点，没有子结点，就展开父节点
                        if(!flag){
                            zTree.expandNode(nodeList[i].getParentNode(), highlight, false, true);
                        }
                        nodeList[i].highlight = highlight;
                        if(highlight){
                            $("#"+nodeList[i].tId+"_a").css({"color":"red"});
                        }
                        zTree.updateNode(nodeList[i]);
                    }
                }

            }

        }

        Manage.init();
    });