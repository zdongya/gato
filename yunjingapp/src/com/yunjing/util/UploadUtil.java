package com.yunjing.util;


import java.io.File;
import java.lang.reflect.Method;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

public class UploadUtil {
	
	@SuppressWarnings("unchecked")
	public static boolean uploadImage(HttpServletRequest request, Object obj, String uploadDir, String fileName) {
    	boolean flag = false;
		try {
			request.setCharacterEncoding("utf-8");
			// 为解析类提供配置信息
			DiskFileItemFactory factory = new DiskFileItemFactory();
			// 创建解析类的实例
			ServletFileUpload sfu = new ServletFileUpload(factory);
			// 开始解析
			sfu.setFileSizeMax(1024 * 1024 * 5); //最多支持5M
			List<FileItem> items = sfu.parseRequest(request);
			int imageCount = 0;
			for (int i = 0; i < items.size(); i++) {
				FileItem item = items.get(i);
				
				if (!item.isFormField()) {
					String name = item.getName();
					System.out.println("原图文件名称：" + name);
					String fileType = name.split("\\.")[1].toUpperCase();
					if ("JPG,JPEG,PNG,BPM".indexOf(fileType) == -1){
						System.out.println("图片格式不正确");
						flag = false;
						break;
					} else {
						if (fileName.split("\\.").length<2){ //无后缀名
							fileName = fileName + "." + fileType;
						}
					}
					imageCount ++;
					if (imageCount == 1){ //单图片上传
						String filePath = uploadDir + fileName;
						File dir = new File(uploadDir);
						if (!dir.exists()){
							dir.mkdirs();
						}
						File file = new File(filePath);
						item.write(file);
						flag = true;
						break;
					}
				} else { //表单域
					Class clazz = obj.getClass();
					Method[] methods = clazz.getMethods();
					String fieldName = item.getFieldName();
		            String firstChar = fieldName.substring(0, 1).toUpperCase();
		            fieldName = firstChar + fieldName.substring(1);
		            String setter = "set" + fieldName;
	                Object arg = item.getString("UTF-8");
	                for(Method method : methods) {
	                    if(setter.equalsIgnoreCase(method.getName())) {
	                    	String classtype = method.getParameterTypes()[0].getName();
	                        if ("int".equals(classtype)) {
	                            arg = Integer.parseInt(item.getString());;
	                        }
	                        method.invoke(obj, arg);
	                        break;
	                    }
	                }
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("上传图片失败。。。");
			
		}
		return flag;
	}

}
