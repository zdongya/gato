package com.lanyuan.entity;

import com.lanyuan.annotation.TableSeg;
import com.lanyuan.util.FormMap;
/**
 * 系统用户
 * @author DONGYA
 *
 */
@TableSeg(tableName = "tb_user", id="USERID")
public class SysUserFormMap extends FormMap<String,Object>{
	private static final long serialVersionUID = 1L;
}
