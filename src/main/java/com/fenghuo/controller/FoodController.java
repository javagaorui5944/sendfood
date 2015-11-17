package com.fenghuo.controller;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.alibaba.fastjson.JSONObject;
import com.fenghuo.domain.Default_order;
import com.fenghuo.domain.Snacks;
import com.fenghuo.domain.Storage;
import com.fenghuo.domain.order_item;
import com.fenghuo.domain.staff;
import com.fenghuo.service.Default_OrderService;
import com.fenghuo.service.Order_ItemService;
import com.fenghuo.service.SnackService;
import com.fenghuo.service.StorageService;
import com.fenghuo.service.UserService;
import com.fenghuo.util.CommonUtil;
import com.fenghuo.util.PropertiesUtils;
import com.fenghuo.util.SftpUtils;

/**
 * 对商品管理
 * 
 * @author family
 *
 */
@Controller
@RequestMapping("/food")
public class FoodController {

	@Autowired
	private SnackService snackService;
	@Autowired
	private UserService userService;
	@Autowired
	private StorageService storageService;
	@Autowired
	private Order_ItemService order_ItemService;
	@Autowired
	private Default_OrderService default_OrderService;

	// 库存页面主页跳转
	@RequestMapping("/storageIndex")
	public String foodIndex(HttpSession httpSession, Model model) {
		return "/food/storageIndex";
	}

	/**
	 * 获取库存详情
	 * 
	 * @param storage_id
	 *            仓库编号、员工切换他所能查看的仓库时需要此参数、为空时默认获取所能查看的仓库中的第一个
	 * @param page
	 *            页码、为空时或小于等于0时为默认值1
	 * @param key
	 *            模糊查询的类型（snacks_bar_code--条形码，snacks_name--食品名称）
	 * @param value
	 *            模糊查询内容、关键字 然后根据用户查看的具体仓库、分页、模糊查询查出具体内容
	 */
	@RequestMapping(value = "/getInventoryFood", method = RequestMethod.GET)
	@ResponseBody
	public JSONObject getAllDefaultFood(
			HttpSession httpSession,
			@RequestParam(value = "storage_id", required = false) Long storage_id,
			@RequestParam(value = "page", required = false) Long page,
			@RequestParam(value = "key", required = false) String key,
			@RequestParam(value = "value", required = false) String value) {
		staff staff = (staff) httpSession.getAttribute("staff"); // 获取员工
		if (staff == null) {
			return CommonUtil.constructResponse(0, "员工不存在", null);
		}
		JSONObject jo = new JSONObject();
		List<Storage> storages = new ArrayList<Storage>(); // 添加员工所能管理的仓库集合
		if (staff.getStaff_rank() == 10) {
			return CommonUtil.constructResponse(0, "没有该员工所能查看的仓库", null);
		} else if (staff.getStaff_rank() == 20) {
			return CommonUtil.constructResponse(0, "没有该员工所能查看的仓库", null);
		} else if (staff.getStaff_rank() == 30) {
			storages = (storageService.getStorageBySchool(staff.getStaff_id()));
		} else if (staff.getStaff_rank() == 40) {
			storages = storageService.getStorageByRegion(staff.getStaff_id());
		}
		if (storages == null || storages.size() == 0) {
			return CommonUtil.constructResponse(0, "没有该员工所能查看的仓库", null);
		}
		if (storage_id == null) {
			storage_id = storages.get(0).getStorage_id();
		} else {
			int i = 0;
			for (Storage s : storages) {
				if (s.getStorage_id() == storage_id) {
					i = 1;
					break;
				}
			}
			if (i == 0) {
				return CommonUtil.constructResponse(0, "该员工不能查看该仓库详情", null);
			}
		}
		if (page == null || page < 1) {
			page = (long) 1;
		}
		jo.put("storages", storages);
		jo.put("nowStorage", storage_id);
		jo.put("nowPage", page);
		jo.put("total", snackService.getSnacksCountByStorageAndKey(storage_id,
				key, value));
		jo.put("foods", snackService.getSnacksByStorageAndKey(storage_id, page,
				10, key, value));

		return CommonUtil.constructResponse(1, "success", jo);
	}

	/**
	 * 添加食品、默认将食品状态设置为10--正常 包含的食品信息如下： snacks_bar_code--条形码，不能为空
	 * snacks_name--食品名称，不能为空 snacks_cost_price--成本价，不能为空
	 * snacks_sell_price--售价、不能为空 snacks_stock_number--库存数量、不能为空
	 * storage_id--仓库编号、不能为空
	 */
	@RequestMapping(value = "/addFood", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject addFood(Snacks snack) {
		if (snack.getSnacks_bar_code() == null
				|| "".equals(snack.getSnacks_bar_code())) {
			return CommonUtil.constructResponse(0, "条形编码不能为空", null);
		}
		if (snack.getSnacks_name() == null || "".equals(snack.getSnacks_name())) {
			return CommonUtil.constructResponse(0, "食品名称不能为空", null);
		}
		if ((Float) snack.getSnacks_cost_price() == null) {
			return CommonUtil.constructResponse(0, "成本价不能为空", null);
		}
		if ((Float) snack.getSnacks_sell_price() == null) {
			return CommonUtil.constructResponse(0, "售价不能为空", null);
		}
		if ((Integer) snack.getSnacks_stock_number() == null) {
			return CommonUtil.constructResponse(0, "食品数量不能为空", null);
		}
		if ((Long) snack.getStorage_id() == null) {
			return CommonUtil.constructResponse(0, "仓库不能为空", null);
		}
		snack.setSnacks_status(10);
		snackService.addSnack(snack);
		return CommonUtil.constructResponse(1, "添加成功", null);
	}

	/**
	 * 修改食品状态 10为正常状态、0为冻结状态 需要获取参数：snacks_id--食品编号，snacks_status--修改后的食品状态
	 */
	@RequestMapping(value = "/changeFood", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject changeFood(Snacks snack) {
		if ((Long) snack.getSnacks_id() == null) {
			return CommonUtil.constructResponse(0, "食品编号获取失败为空", null);
		}
		if ((Integer) snack.getSnacks_status() == null) {
			return CommonUtil.constructResponse(0, "食品状态不能为空", null);
		}
		List<Default_order> default_orders = order_ItemService
				.getDefaultOrderIdBySnack(snack.getSnacks_id());
		if (default_orders != null && default_orders.size() > 0) { // 查找含有原食物的标准订单
			snackService.changeSnack(1, snack.getSnacks_id()); // 如果有食物含有，则将原食物状态设置为0
			for (Default_order d : default_orders) { // 将现在正在用的标准，重新生成
				if (d.getDefault_order_status() == 10) {
					default_OrderService.updateStatus_1(
							d.getDefault_order_id(), 1);
					long newDefault_order_id = default_OrderService
							.addDefaultOrder(d.getSchool_id(),
									d.getDefault_order_name());
					List<order_item> order_items = order_ItemService
							.getOrderitemByDefaultOrderId_5(d
									.getDefault_order_id());
					for (order_item o : order_items) {
						if (o.getSnacks_id() != snack.getSnacks_id()) {
							order_ItemService.saveOrderItem(
									newDefault_order_id, o.getSnacks_id(),
									o.getSnacks_number());
						}
					}
					default_OrderService.updateRedisOrder(d.getSchool_id()); // 更新redis数据
				}
			}
		} else {
			snackService.deleteSnacks(snack.getSnacks_id()); // 如果没有订单含有该食物，就删除
		}
		return CommonUtil.constructResponse(1, "修改成功", null);
	}

	/**
	 * 修改食品 当食品价格改变时，将以前的食品对象更改为冻结，然后添加一条新的数据 需要获取的参数同添加食品（加上食品编号、减去食品状态以及仓库编号）
	 */
	@RequestMapping(value = "/updateFood", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject updateFood(Snacks snack) {
		if (snack.getSnacks_bar_code() == null
				|| "".equals(snack.getSnacks_bar_code())) {
			return CommonUtil.constructResponse(0, "条形编码不能为空", null);
		}
		if (snack.getSnacks_name() == null || "".equals(snack.getSnacks_name())) {
			return CommonUtil.constructResponse(0, "食品名称不能为空", null);
		}
		if ((Float) snack.getSnacks_cost_price() == null) {
			return CommonUtil.constructResponse(0, "成本价不能为空", null);
		}
		if ((Float) snack.getSnacks_sell_price() == null) {
			return CommonUtil.constructResponse(0, "售价不能为空", null);
		}
		if ((Integer) snack.getSnacks_stock_number() == null) {
			return CommonUtil.constructResponse(0, "食品数量不能为空", null);
		}
		Snacks s = snackService.getSnack(snack.getSnacks_id());
		if (s == null) {
			return CommonUtil.constructResponse(0, "你修改的食品不存在", null);
		}
		if (!s.getSnacks_cost_price().toString()
				.equals(snack.getSnacks_cost_price().toString())
				|| !s.getSnacks_sell_price().toString()
						.equals(snack.getSnacks_sell_price().toString())) {
			List<Default_order> default_orders = order_ItemService
					.getDefaultOrderIdBySnack(snack.getSnacks_id());
			snack.setSnacks_status(10); // 将新数据的状态设置为原数据的状态
			snack.setStorage_id(s.getStorage_id());
			snack.setSnacks_pic(s.getSnacks_pic());
			snack.setSnacks_id(snackService.addSnack(snack)); // 将新数据存入数据库,并设置返回的id
			if (default_orders != null && default_orders.size() > 0) { // 查找含有原食物的标准订单
				snackService.changeSnack(0, s.getSnacks_id()); // 如果有食物含有，则将原食物状态设置为0
				for (Default_order d : default_orders) { // 将现在正在用的标准，重新生成
					if (d.getDefault_order_status() == 10) {
						default_OrderService.updateStatus_1(
								d.getDefault_order_id(), 0);
						long newDefault_order_id = default_OrderService
								.addDefaultOrder(d.getSchool_id(),
										d.getDefault_order_name());
						List<order_item> order_items = order_ItemService
								.getOrderitemByDefaultOrderId_5(d
										.getDefault_order_id());
						for (order_item o : order_items) {
							if (o.getSnacks_id() == s.getSnacks_id()) {
								order_ItemService.saveOrderItem(
										newDefault_order_id,
										snack.getSnacks_id(),
										o.getSnacks_number());
							} else {
								order_ItemService.saveOrderItem(
										newDefault_order_id, o.getSnacks_id(),
										o.getSnacks_number());
							}
						}
						default_OrderService.updateRedisOrder(d.getSchool_id()); // 更新redis数据
					}
				}
			} else {
				snackService.deleteSnacks(s.getSnacks_id()); // 如果没有订单含有该食物，就删除
			}
			order_ItemService.updateOrderItem(s.getSnacks_id(),
					snack.getSnacks_id());
		} else {
			snackService.updateSnack(snack);
		}
		return CommonUtil.constructResponse(1, "修改成功", null);
	}

	/***
	 * 上传食物图片(ajax异步操作)
	 * 
	 */
	@RequestMapping(value = "/foodPicUploadfile", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject foodPicUploadfile(HttpServletRequest request,
			long snacks_id, long storage_id) {
		SimpleDateFormat myFmt2 = new SimpleDateFormat("yyyyMMdd");// 等价于now.toLocaleString()
		Date date = new Date();
		// 时间
		String uploadDate = myFmt2.format(date);
		// storage_id = 11;
		// 仓库名
		String storage_idName = "storage_" + storage_id;

		String result = null;// 上传后返回情况说明
		String path = null;// 上传图片路径
		// //创建一个通用的多部分解析器
		CommonsMultipartResolver cmr = new CommonsMultipartResolver(request
				.getSession().getServletContext());
		// //判断 request 是否有文件上传,即多部分请求
		if (cmr.isMultipart(request)) {
			// 转换成多部分request
			MultipartHttpServletRequest mhsr = (MultipartHttpServletRequest) request;
			// //取得request中的所有文件名
			Iterator<String> iter = mhsr.getFileNames();
			while (iter.hasNext()) {
				// //取得上传文件
				MultipartFile file = mhsr.getFile((String) iter.next());

				// //取得当前上传文件的文件名称
				String filename = file.getOriginalFilename();
				// 获得文件后缀
				String fileSuffixName = filename.substring(filename
						.indexOf("."));
				/**
				 * 上传文件大小,类型判断
				 */
				if (file.getSize() > 1048576) {
					result = "上传失败：上传文件大小大于1M";

					return CommonUtil.constructResponse(0, result, null);

				} else if (!fileSuffixName.equals(".jpg")
						&& !fileSuffixName.equals(".png")
						&& !fileSuffixName.equals(".gif")) {
					result = "上传失败：上传文件类型不正确";

					return CommonUtil.constructResponse(0, result, null);

				}

				// 生成的GUID为一串32位字符组成的128位数据上传文件重命名filename1
				CommonUtil cu = new CommonUtil();
				String UUID = cu.GUID();

				String filename1 = UUID + fileSuffixName;
				// 验证当前操作系统，构建上传路径
				String os = System.getProperty("os.name").toLowerCase();
				if (os.indexOf("win") >= 0)
					path = PropertiesUtils.getProp("path.win") + "/"
							+ storage_idName + "/" + uploadDate;
				else
					path = PropertiesUtils.getProp("path.linux") + "/"
							+ storage_idName + "/" + uploadDate;

				// 定义上传路径
				// path =
				// "http://103.249.252.139:9001/usr/sendfoodpic/"+filename1;
				// String path2 =
				// path = "/static/image/icon/"+filename1;

				/*
				 * File file2 = new File(path2);
				 * 
				 * String snacks_pic1 = path2;
				 */

				File file2 = new File(path);
				if (!file2.exists()) {
					file2.mkdirs();
				}

				File file3 = new File(path + "/" + filename1);
				// String snacks_pic1 = filename1;
				try {
					// transfer方法是MultipartFile包中提供的方法，直接可以写入文件到指定目录
					file.transferTo(file3);

					// 复制Web服务器文件到文件服务器
					boolean status = SftpUtils.uploadFile(path + "/"
							+ filename1);
					if (status) {
						// 存入数据库
						snackService.foodPicUploadfile(storage_idName + "/"
								+ uploadDate + "/" + filename1, snacks_id);
						result = "上传成功";
						System.out.println("上传成功***");
						// 删除WEB服务器文件
						file3.delete();
						// TODO effine 文件上传成功返回URL需要讨论确定
						List<Default_order> default_orders = order_ItemService
								.getDefaultOrderIdBySnack(snacks_id);
						if (default_orders != null && default_orders.size() > 0) { // 查找含有原食物的标准订单
							for (Default_order d : default_orders) { // 将现在正在用的标准，重新生成
								if (d.getDefault_order_status() == 10) {
									default_OrderService.updateRedisOrder(d
											.getSchool_id()); // 更新redis数据
								}
							}
						}
						return CommonUtil.constructResponse(1, result,
								storage_idName + "/" + uploadDate + "/"
										+ filename1);
					}

				} catch (Exception e) {
					result = e.getMessage();

					e.printStackTrace();
					return CommonUtil.constructResponse(0, result, null);
				}

			}

		}
		return CommonUtil.constructResponse(0, null, null);
	}

}
