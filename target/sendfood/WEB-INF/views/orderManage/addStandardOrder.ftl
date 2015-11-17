<<<<<<< HEAD
    <content tag="title">新增或修改标准订单</content>
    <content tag="css">/orderManage/addStandardOrder</content>
    <content tag="javascript">/orderManage/addStandardOrder</content>
    <div class="padding-md">
    <div class="row">
    <div class="panel panel-default table-responsive">
					<div class="panel-heading">
						<div class="row">
							<div class="col-md-2 col-sm-2">
								<label for="superOrg" class="control-label">选择学校当前对应的标准订单</label>
								<select class="form-control" id="J-schoolSelect">
									<option value="-1">下拉选择学校</option>
								</select>
							</div>
							<div class="col-md-4 col-sm-4">
                <label for="superOrg" class="control-label">标准订单名</label>
                <input type="text" class="input-sm form-control" id="J-sta-order" placeholder="请输入当前学校对应的标准订单名字"/>
              </div>
              <div class="col-md-2 col-sm-2" i>
               <label for="superOrg" class="control-label"></label>
               <button  class="btn btn-success form-control" id="J-saveOrder">新增或修改标准订单</button>
              </div>
						</div><!-- /.row -->
					</div>
					<div class="panel-body">
					<div class="row">
						<div class="col-md-6 col-sm-6">
							<table class="table table-bordered table-condensed table-hover table-striped">
									<thead>
									<tr>
									  <th>条形码</th>
									  <th>商品名</th>
									   <th>图片</th>
									  <th>成本价</th>
									  <th>售价</th>
									  <th>库存</th>
									  <th>加入订单</th>
									</tr>
								  </thead>
								<tbody id="J-storageList">
								</tbody>
							</table>
							<div id="orderPager">
							</div>
							<div class="panel-footer clearfix">
                		 <button type="submit" class="btn btn-default hide" id="J-addStardOrder">增加标准订单</button>
              		</div>
						</div>
						<div class="col-md-6 col-sm-6">
							<table class="table table-bordered table-condensed table-hover table-striped">
								<thead>
									<tr>
										<th>商品条形码</th>
										<th>商品名</th>
										<th>成本价</th>
										<th>售价</th>
										<th>商品数量</th>
										<th>操作(只能修改本次的数量)</th>
									</tr>
								</thead>
								<tbody id="J-defaultList">

								</tbody>
							</table>

						</div>

					</div>
					</div>

				</div>
    </div>
    </div>
=======
<content tag="title">新增或修改标准订单</content>
<content tag="css">/orderManage/addStandardOrder</content>
<content tag="javascript">/orderManage/addStandardOrder</content>
<div class="padding-md">
    <div class="row">
        <div class="panel panel-default table-responsive">
            <div class="panel-heading">
                <div class="row">
                    <div class="col-md-3 col-sm-3">
                        <label for="J-schoolSelect" class="control-label">选择学校当前对应的标准订单</label>
                        <select class="form-control" id="J-schoolSelect">
                            <option value="-1">下拉选择学校</option>
                        </select>
                    </div>
                    <div class="col-md-3 col-sm-3">
                        <label for="J-orderSelect" class="control-label">选择学校当前对应的标准订单</label>
                        <select class="form-control" id="J-orderSelect">
                            <option value="-1">下拉选择订单(套餐)</option>
                        </select>
                    </div>
                    <div class="col-md-3 col-sm-3">
                        <label for="J-addOrder" class="control-label">&nbsp;</label>
                        <button class="btn btn-success form-control" id="J-addOrder">新增订单</button>
                    </div>
                </div>
                <!-- /.row -->
            </div>
            <div class="panel-body">
                <div class="row">
                    <div class="col-md-6 col-sm-6">
                        <table class="table table-bordered table-condensed table-hover table-striped">
                            <thead>
                            <tr>
                                <th>条形码</th>
                                <th>商品名</th>
                                <th>图片</th>
                                <th>成本价</th>
                                <th>售价</th>
                                <th>库存</th>
                                <th>加入订单</th>
                            </tr>
                            </thead>
                            <tbody id="J-storageList">
                            </tbody>
                        </table>
                        <div id="orderPager">
                        </div>
                        <div class="panel-footer clearfix">
                            <button type="submit" class="btn btn-default hide" id="J-addStardOrder">增加标准订单</button>
                        </div>
                    </div>
                    <div class="col-md-6 col-sm-6">
                        <table class="table table-bordered table-condensed table-hover table-striped">
                            <thead>
                            <tr class="edit-options">
                                <th colspan="6">
                                    <a href="javascript:;" class="btn " id="J-orderSave">保存</a>
                                    <a href="javascript:;" class="btn " id="J-orderDelete">删除</a>
                                    <a href="javascript:;" class="btn " id="J-orderDown">下载</a>
                                </th>
                            </tr>
                            <tr>
                                <th>商品条形码</th>
                                <th>商品名</th>
                                <th>成本价</th>
                                <th>售价</th>
                                <th>商品数量</th>
                                <th>操作(只能修改本次的数量)</th>
                            </tr>
                            </thead>
                            <tbody id="J-defaultList">

                            </tbody>
                        </table>

                    </div>

                </div>
            </div>

        </div>
    </div>
</div>
>>>>>>> df40f490d101bea97b422c1afb37ba69155072c0
