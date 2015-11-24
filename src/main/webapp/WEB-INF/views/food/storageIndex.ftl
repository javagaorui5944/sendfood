    <content tag="title">零食预售系统－后台管理</content>
    <content tag="css">/food/storage</content>
    <content tag="javascript">/food/index</content>
    
   <div class="padding-md" id="user">
    <div class="panel panel-default table-responsive">
					<div class="panel-heading clearfix">
						<div class="col-md-3 ">
						    <select  name="storage" class="input-select storagelist form-control " id="foodStorage">
						    </select>
						</div>
					</div>
					<div class="padding-md clearfix">
                        <div class="table-head">
						  <div class="pull-left">
							  <a class="btn btn-success" id="addfood">添加食物</a>
						  </div>
                          <!--<div class="input-group pull-right" style="width:200px;position: relative;">
							  <input type="search" class="form-control input-sm" placeholder="搜索食物" id="searchfood"/>
							 
							  <div class="searchfood">
							  </div>
                          </div>-->
                        </div>
						<table class="table table-bordered table-condensed table-hover table-striped" id="dataTable">
							<thead>
								<tr class="th">
									<th>所属仓库</th>
									<th>食物名</th>
									<th>图片</th>
									<th>条形编码</th>
									<th>成本价</th>
									<th>售价</th>
									<th>库存数量</th>
									<th>操作</th>
								</tr>
							</thead>
							<tbody id="foodlist">									
							</tbody>
						</table>

						<div id="pager">

						</div>
					</div><!-- /.padding-md -->
				</div><!-- /panel -->
  </div>
    