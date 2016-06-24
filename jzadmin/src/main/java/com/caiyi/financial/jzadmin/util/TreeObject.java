package com.caiyi.financial.jzadmin.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

/**
 * 这个是列表树形式显示的实体,
 * 这里的字段是在前台显示所有的,可修改
 * @author lanyuan
 * Email：mmm333zzz520@163.com
 * date：2014-11-20
 */
public class TreeObject {
	private String id;
	private String parentId;
	private String name;
	private String parentName;
	private String resKey;
	private String resUrl;
	private Integer leve;
	private String type;
	private String description;
	private String icon;
	private Integer ishide;
	private List<TreeObject> children = new ArrayList<TreeObject>();
	public List<TreeObject> getChildren() {
		return children;
	}
	public void setChildren(List<TreeObject> children) {
		this.children = children;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getParentName() {
		return parentName;
	}
	public void setParentName(String parentName) {
		this.parentName = parentName;
	}
	public String getResKey() {
		return resKey;
	}
	public void setResKey(String resKey) {
		this.resKey = resKey;
	}
	public String getResUrl() {
		return resUrl;
	}
	public void setResUrl(String resUrl) {
		this.resUrl = resUrl;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getParentId() {
		return parentId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	public Integer getLeve() {
		return leve;
	}
	public void setLeve(Integer leve) {
		this.leve = leve;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Integer getIshide() {
		return ishide;
	}
	public void setIshide(Integer ishide) {
		this.ishide = ishide;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	
	public String toString(){
		return ReflectionToStringBuilder.toString(this);
	}
}
