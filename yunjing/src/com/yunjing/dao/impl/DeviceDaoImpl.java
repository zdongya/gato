package com.yunjing.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.yunjing.dao.BaseDao;
import com.yunjing.dao.DeviceDao;
import com.yunjing.entity.Device;
import com.yunjing.entity.DeviceDto;
import com.yunjing.entity.Member;
import com.yunjing.page.PageController;
import com.yunjing.page.PageLocal;
import com.yunjing.util.CheckUtil;

public class DeviceDaoImpl extends BaseDao implements DeviceDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<Device> getByDeviceName(String deviceName) {
		String sql = "from Device as d where d.deviceName=?";
		return super.find(sql, deviceName);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Device getByDeviceNo(String deviceNo) {
		String hql = "from Device as d where d.deviceNo=?";
		List<Device> deviceList = super.find(hql, deviceNo);
		return deviceList.isEmpty()?null:deviceList.get(0);
	}

	@Override
	public void saveDevice(Device device) {
		super.saveOrUpdate(device);
		
	}
	
	
	@Override 
	public List<?> getAllPaging(DeviceDto device) {
		if (null == device){
			device = new DeviceDto();
		}
		StringBuilder builder = new StringBuilder("FROM Device as model where 1=1 ");
		if (!CheckUtil.isNullString(device.getDeviceNo())){
			builder.append(" and model.deviceNo='").append(device.getDeviceNo()).append("'");
		}
		if (!CheckUtil.isNullString(device.getDeviceName())){
			builder.append(" and model.deviceName like '%").append(device.getDeviceName()).append("%'");
		}
		
		if (!CheckUtil.isNullString(device.getBeginDate())){
			builder.append(" and DATE_FORMAT(model.addDate,'%Y-%m-%d')>= '").append(device.getBeginDate()).append("'");
		}
		if (!CheckUtil.isNullString(device.getEndDate())){
			builder.append(" and DATE_FORMAT(model.addDate,'%Y-%m-%d')<= '").append(device.getEndDate()).append("'");
		}
		builder.append(" order by model.addDate desc");
		return super.getAllPaging(builder.toString());
	}

	@Override
	public List<?> getAll(DeviceDto device) {
		if (null == device){
			device = new DeviceDto();
		}
		StringBuilder builder = new StringBuilder("FROM Device as model where 1=1 ");
		if (!CheckUtil.isNullString(device.getDeviceNo())){
			builder.append(" and model.deviceNo='").append(device.getDeviceNo()).append("'");
		}
		if (!CheckUtil.isNullString(device.getDeviceName())){
			builder.append(" and model.deviceName like '%").append(device.getDeviceName()).append("%'");
		}
		
		if (!CheckUtil.isNullString(device.getBeginDate())){
			builder.append(" and DATE_FORMAT(model.addDate,'%Y-%m-%d')>= '").append(device.getBeginDate()).append("'");
		}
		if (!CheckUtil.isNullString(device.getEndDate())){
			builder.append(" and DATE_FORMAT(model.addDate,'%Y-%m-%d')<= '").append(device.getEndDate()).append("'");
		}
		builder.append(" order by model.addDate desc");
		return super.find(builder.toString());
	}

}
