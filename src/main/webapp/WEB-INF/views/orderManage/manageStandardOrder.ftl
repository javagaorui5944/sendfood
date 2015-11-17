    <content tag="title">管理标准订单</content>
    <content tag="css">/orderManage/manageStandardOrder</content>
    <content tag="javascript">/orderManage/manageStandardOrder</content>
    <div class="padding-md">
    <div class="row">
    <div class="panel panel-default table-responsive">
					<div class="panel-heading">
						订单管理						
					</div>
					<div class="panel-body">
						<div class="row">
							<div class="col-md-6 col-sm-6">
								<select class="input-sm form-control inline" id="J-orderOrPerseon" style="width:100px;">
									<option value="0">订单号</option>
									<option value="1">配送人员</option>
								</select>
								<select class="input-sm form-control inline" id="J-orderStatus" style="width:110px;"> 
									<option value="0">订单所有状态</option>
									<option value="10">未配送</option>
									<option value="20">配送中</option>
									<option value="30">配送完成</option>
									<option value="40">完成结算</option>
									<option value="50">返货入库</option>
								</select>
								<input type="text" class="input-sm form-control inline" style="width:130px;"  id = "J-value" placeholder="请输入订单关键字"/>
								<a class="btn btn-default btn-sm" id="J-search" >搜索</a>
							</div><!-- /.col -->
						</div><!-- /.row -->
					</div>
					<table class="table table-striped" id="responsiveTable">
						<thead>
							<tr>
								<th>
									序号
								</th>
								<th>订单号</th>
								<th>订单创建时间</th>
								<th>学校</th>
								<th>楼栋</th>
								<th>寝室</th>
								<th>配送人员</th>
								<th>订单状态</th>
								<th>操作订单</th>
							</tr>
						</thead>
						<tbody id="J-orderList">
								
						</tbody>
					</table>
					<div class="panel-footer clearfix">
						<div id="pager">
						</div>
					</div>
				</div>
    </div>
    </div>