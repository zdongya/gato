package com.yunjing.action;

import java.util.ArrayList;
import java.util.List;

import com.yunjing.service.TestService;


public class TestAction  extends BaseAction{
	
	private List<Object> testList;
	
	private TestService testService;
	
	public void setTestService(TestService testService) {
		this.testService = testService;
	}

	public String index(){
		testList = new ArrayList<Object>();
		testList.add("1");
		testList.add(2);
		testList.add("3");
		return "success";
	}
	

	public List<Object> getTestList() {
		return testList;
	}

	public void setTestList(List<Object> testList) {
		this.testList = testList;
	}


}
