package com.lanyuan.entity;

import com.lanyuan.annotation.TableSeg;
import com.lanyuan.util.FormMap;
/**
 * 设备信息
 * @author DONGYA
 *
 */
@TableSeg(tableName = "tb_device", id="deviceNo")
public class DeviceFormMap extends FormMap<String,Object>{
	private static final long serialVersionUID = 1L;
}
