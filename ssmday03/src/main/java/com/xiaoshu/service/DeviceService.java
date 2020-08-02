package com.xiaoshu.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xiaoshu.dao.DeviceMapper;
import com.xiaoshu.dao.TypeMapper;
import com.xiaoshu.entity.Device;
import com.xiaoshu.entity.Type;

@Service
@Transactional
public class DeviceService {

	@Autowired
	DeviceMapper deviceMapper;
	
	@Autowired
	TypeMapper typeMapper;

	public List<Type> findType() {
		// TODO Auto-generated method stub
		return typeMapper.selectAll();
	}
	
	public PageInfo<Device> findDevicePage(Device device, Integer pageNum, Integer pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		List<Device> userList = deviceMapper.findAll(device);
		PageInfo<Device> pageInfo = new PageInfo<Device>(userList);
		return pageInfo;
	}
	
	public Device existDeviceWithDeviceName(String deviceName) {
		List<Device> deviceList = deviceMapper.findByName(deviceName);
		return deviceList.isEmpty()?null:deviceList.get(0);
	}
	public void addDevice(Device device) {
		// TODO Auto-generated method stub
		deviceMapper.insert(device);
	}

	public void updateDevice(Device device) {
		// TODO Auto-generated method stub
		deviceMapper.updateByPrimaryKeySelective(device);
	}

	public void deleteDevice(int parseInt) {
		// TODO Auto-generated method stub
		deviceMapper.deleteByPrimaryKey(parseInt);
	}

	public List<Device> echarts() {
		// TODO Auto-generated method stub
		return deviceMapper.echarts();

	}

	

}
