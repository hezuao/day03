package com.xiaoshu.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import com.xiaoshu.config.util.ConfigUtil;
import com.xiaoshu.entity.Device;
import com.xiaoshu.entity.Operation;
import com.xiaoshu.entity.Type;
import com.xiaoshu.service.DeviceService;
import com.xiaoshu.service.OperationService;
import com.xiaoshu.util.StringUtil;
import com.xiaoshu.util.WriterUtil;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;

@Controller
@RequestMapping("sb")
public class SbController extends LogController{
	static Logger logger = Logger.getLogger(SbController.class);

	@Autowired
	private DeviceService deviceService;
	
	@Autowired
	private OperationService operationService;
	
	
	@RequestMapping("sbIndex")
	public String index(HttpServletRequest request,Integer menuid) throws Exception{
		List<Type> typeList=deviceService.findType();
		request.setAttribute("roleList", typeList);
		List<Operation> operationList = operationService.findOperationIdsByMenuid(menuid);
		request.setAttribute("operationList", operationList);
		return "sb";
	}
	
	
	@RequestMapping(value="sbList",method=RequestMethod.POST)
	public void userList(Device device,HttpServletRequest request,HttpServletResponse response,String offset,String limit) throws Exception{
		try {
			
			Integer pageSize = StringUtil.isEmpty(limit)?ConfigUtil.getPageSize():Integer.parseInt(limit);
			Integer pageNum =  (Integer.parseInt(offset)/pageSize)+1;
			PageInfo<Device> userList= deviceService.findDevicePage(device,pageNum,pageSize);
			
			JSONObject jsonObj = new JSONObject();
			jsonObj.put("total",userList.getTotal());
			jsonObj.put("rows", userList.getList());
	        WriterUtil.write(response,jsonObj.toString());
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("用户展示错误",e);
			throw e;
		}
	}
	
	// 新增或修改
	@RequestMapping("reserveSb")
	public void reserveUser(HttpServletRequest request,Device device,HttpServletResponse response){
		Integer deviceid = device.getDeviceid();
		JSONObject result=new JSONObject();
		try {
			if (deviceid != null) {   // userId不为空 说明是修改
				if(deviceService.existDeviceWithDeviceName(device.getDeviceName())==null){  // 没有重复可以添加
					deviceService.updateDevice(device);
					result.put("success", true);
				}else{
					result.put("success", true);
					result.put("errorMsg", "该用户名被使用");
				}
				
			}else {   // 添加
				if(deviceService.existDeviceWithDeviceName(device.getDeviceName())==null){  // 没有重复可以添加
					device.setCreatetime(new Date());
					deviceService.addDevice(device);
					result.put("success", true);
				} else {
					result.put("success", true);
					result.put("errorMsg", "该用户名被使用");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("保存用户信息错误",e);
			result.put("success", true);
			result.put("errorMsg", "对不起，操作失败");
		}
		WriterUtil.write(response, result.toString());
	}
	
	@RequestMapping("deleteSb")
	public void delUser(HttpServletRequest request,HttpServletResponse response){
		JSONObject result=new JSONObject();
		try {
			String[] ids=request.getParameter("ids").split(",");
			for (String id : ids) {
				deviceService.deleteDevice(Integer.parseInt(id));
			}
			result.put("success", true);
			result.put("delNums", ids.length);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("删除用户信息错误",e);
			result.put("errorMsg", "对不起，删除失败");
		}
		WriterUtil.write(response, result.toString());
	}
	
	@RequestMapping("echarts")
	public void echarts(HttpServletRequest request, HttpServletResponse response, MultipartFile file) {
		JSONObject result = new JSONObject();
		try {
			//查询数据
			List<Device> list = deviceService.echarts();
			
			result.put("data",list);
		} catch (Exception e) {
			e.printStackTrace();
		}
		WriterUtil.write(response, result.toString());
	}
}
