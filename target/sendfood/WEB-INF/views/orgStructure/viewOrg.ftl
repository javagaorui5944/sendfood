    <content tag="title">查看组织架构</content>
    <content tag="css">/orgStructure/viewOrg</content>
    <content tag="javascript">/orgStructure/viewOrg</content>
<div class="padding-md">
    <div class="panel panel-default">
        <div class="panel-heading">
           查看架构管理
           </div>
        <div class="panel-body" id="J-tree-panel">
             <div class="row">
             	<div class="col-md-4">
	             <div class="form-group">
	                <label for="superOrg" class="control-label">组织名称</label>
	                <div class="">
	                    <input type="text" class="form-control"  value="" id="J-searchAuthTree" placeholder="请输入组织查看">
	                	<ul class="ztree" id="treeMenu">
                       	组织树形菜单加载中...
                 		</ul>
	                </div>
	            </div>
	            
	            </div>
	            <div class="col-md-8 clearfix right-panel" >
	                  <div id="J-node-msg">
	                  <h3  style="display:inline-block;margin-right:10px;">当前节点<span id="J-nodeName" style="margin-left:10px;color:red;"></span></h3>
	                  <button type="submit" class="btn btn-default" id="J-add">新增下级节点</button>
                      <button type="submit" class="btn btn-default" id="J-revise">修改节点</button>
                        <button type="submit" class="btn btn-danger" style="display:none;" id="J-delete">删除节点</button>
                       </div>
	            	 <div class="tree" id="J-tree">
                               获取组织架构中...
                      </div>
	            </div>
            </div>
           </div>
        </div>
        <div class="panel-footer clearfix">
        </div>
    </div>
</div>