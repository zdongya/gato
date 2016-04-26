package com.yunjing.dto;

import com.yunjing.model.Push;

public class PushDto extends Push {
	private String time;
	private String warningId;
	private String CommandState; //指令状态
	private String sign;
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getWarningId() {
		return warningId;
	}
	public void setWarningId(String warningId) {
		this.warningId = warningId;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	public String getCommandState() {
		return CommandState;
	}
	public void setCommandState(String commandState) {
		CommandState = commandState;
	}

}
