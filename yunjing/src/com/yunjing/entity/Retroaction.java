package com.yunjing.entity;

import java.sql.Timestamp;

/**
 * 用户反馈
 * @author DONGYA
 *
 */
public class Retroaction {
	private String id;
	private Member user;
	private String contents;
	private String contact;
	private Timestamp addDate;
	private String memo; //备注
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Member getUser() {
		return user;
	}
	public void setUser(Member user) {
		this.user = user;
	}
	public String getContents() {
		return contents;
	}
	public void setContents(String contents) {
		this.contents = contents;
	}
	public String getContact() {
		return contact;
	}
	public void setContact(String contact) {
		this.contact = contact;
	}
	public Timestamp getAddDate() {
		return addDate;
	}
	public void setAddDate(Timestamp addDate) {
		this.addDate = addDate;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	
	

}
