    <content tag="title">新增组织</content>
    <content tag="css">/orgStructure/addOrg</content>
    <content tag="javascript">/orgStructure/addOrg</content>
<div class="padding-md">
    <div class="panel panel-default">
        <div class="panel-heading">
            新增组织架构
        </div>
        <div class="panel-body">
            <div class="row">
            <div class="col-md-7">
               <input type="hidden" name="level" id="J-level" value="${(or[0].type)!"0"}"><!--本层次的level-->
                  <input type="hidden" name="id" id="J-id" value="${(or[0].id)?default("")}"><!--本层次的id-->
                  <input type="hidden" name="isAdd" id="J-isAdd" value="${(or[0].id)?default("0")}"><!--是否是新增操作-->
                  <input type="hidden" name="parentId" id="J-parentId" value="${(por[0].id)?default("")}"><!--父层次的id-->
                  <input type="hidden" name="parentLevel" id="J-parentLevel" value="${(por[0].type)?default("")}"><!--父层次的level-->
                  <input type="hidden" name="parentName" id="J-parentName" value="${(por[0].name)?default("")}"><!--父层次的name-->
	            <form class="form-horizontal" id="J-form" onsubmit="return false;">
	                <div class="form-group">
	                    <label for="superOrg" class="col-sm-2 control-label">上级组织</label>
	                    <div class="col-sm-10">
	                        <input type="text" class="form-control" real-value="${(por[0].id)?default("")}" value="${(por[0].name)?default("")}" id="J-ParentOrganization" placeholder="请输入上级组织">
	                    </div>
	                </div>
	                <div class="form-group">
	                    <label for="orgName" class="col-sm-2 control-label">组织名称</label>
	                    <div class="col-sm-10">
	                        <input type="text" class="form-control" id="orgName" real-value="${(or[0].id)?default("")}" value="${(or[0].name)?default("")}" placeholder="请输入组织名称">
	                    </div>
	                </div>
	                <div class="form-group">
	                    <label for="orgCode" class="col-sm-2 control-label">组织代码</label>
	                    <div class="col-sm-10">
	                        <input type="text" class="form-control" id="orgCode" value="${(or[0].code)?default("")}" placeholder="如：重庆邮电大学代码为 cqupt">
	                    </div>
	                </div>
	                <div class="form-group" id="J-time-div" style="display:none;">
	                    <label for="time" class="col-sm-2 control-label">定时配送时间</label>
	                    <div class="col-sm-10">
	                    	<select id="J-time" class="form-control  staff" time="${(or[0].date)?default("")}" >
								<option value="-1">请选择时间</option>
								<option value="1">星期一</option>
								<option value="2">星期二</option>
								<option value="3">星期三</option>
								<option value="4">星期四</option>
								<option value="5">星期五</option>
								<option value="6">星期六</option>
								<option value="7">星期日</option>
							</select>
	                    </div>
	                </div>
	                <div class="form-group">
	                    <label for="orgCode" class="col-sm-2 control-label">负责人</label>
	                    <div class="col-sm-10">
	                    	<select id="J-Manager" class="form-control staff">
	                    		<option value="-1">请选择负责人</option>
	                    	</select>
	                    </div>
	                </div>
	                <div class="form-group">
						<label for="orgCode" class="col-sm-2 control-label">已选负责人</label>
						<div class="col-sm-10">
							<div id="J-staff" style="padding-left:0px;">
							<#if (or[0].staff)?exists>
								<#list (or[0].staff) as staff>
									 <button class="btn btn-info staff mr5" real-value="${staff.staff_id}">${staff.staff_name}&nbsp;&nbsp;x</button>
								</#list>
							</#if>
							</div>
						</div>
					</div>
	                <div class="form-group">
	                    <div class="col-sm-offset-2 col-sm-10">
	                        <button type="submit" class="btn btn-default" id="J-save">确定</button>
	                    </div>
	                </div>
	            </form>
            </div>
             <div class="col-md-5" style="color:red;">
             	使用指南：
             	<ul>
           		<li>1,组织和组织机构代码务必认真填写。</li>
           		<li>2,学校有拼音作为简写组织机构就简写，如果楼栋是27栋，组织代码就是27。如果寝室是607A，组织代码就是607A</li>
             	<li>3,负责人可以有一个或多个，点击负责人快就可以删除</li>
             	</ul>
             </div>
           </div>
        </div>
    </div>
</div>
