package com.yunjing.util;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class TestServlet extends HttpServlet{

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Test  test = new Test();
		boolean flag = UploadUtil.uploadImage(req, test, "D:\\", "aaa");
		System.out.println(test.getApplyId());
		resp.getWriter().write(flag == true?"true":"false");
	}
}
