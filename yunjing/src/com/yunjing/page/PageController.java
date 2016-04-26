package com.yunjing.page;

public interface PageController {

	//获得首页索引
	int getStartIndex();

	//获得尾页索引
	int getLastIndex();

	// 根据当前索引获得当前页
	int getCurrentPage();

	// 根据数据总数获得分页总数
	int getTotalPage();

	// 当前分页数据的结束索引���������
	int getEndIndex();

	// 下一页开始的索引������
	int getNextIndex();

	// 上一页开始的索引������
	int getPreviousIndex();

	void setCurrentIndex(int currentIndex);
	
	int getCurrentIndex();

	int getIndexSize();

	void setIndexSize(int indexSize);

	int getPageSize();

	void setPageSize(int pageSize);
}