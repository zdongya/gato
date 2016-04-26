package com.yunjing.page;
/**
 * 类名:PageControllerImpl.java
 * @author dongya
 * 2009-11-9
 */
class PageControllerImpl implements PageController {
	// 数据总数
	private int indexSize;

	// 当前数据的索引
	private int currentIndex;

	// 每页所含的数据数量
	private int pageSize;

	//默认pageSize=10
	public PageControllerImpl() {
		this.pageSize = 10;
	}
	
	//设置当前索引和pageSize
	public PageControllerImpl(int currentIndex) {
		this(10, currentIndex);
	}

	public PageControllerImpl(int pageSize, int currentIndex) {
		this.pageSize = pageSize;
		this.setCurrentIndex(currentIndex);
	}

	//获得首页索引
	public int getStartIndex() {
		return 0;
	}
	
	//获得尾页索引
	public int getLastIndex() {
		//如果能完整整除pageSize表示最后一页为完整的一页返回该页的第一条记录,否则返回最后一条记录所能显示的完整索引
		int result = (indexSize % pageSize==0 ? (indexSize==0 ? 0 :indexSize - pageSize) :(indexSize -(indexSize % pageSize)));
		return result;
	}
	
	// 根据当前索引获得当前页
	public int getCurrentPage() {
		long result = currentIndex % pageSize;
		// 超过一页的部分作为一页
		return result == 0 ? (currentIndex / pageSize) : (currentIndex/ pageSize + 1);
	}

	// 根据数据总数获得分页总数
	public int getTotalPage() {
		long result = indexSize % pageSize;
		// 超过一页的部分作为一页
		return result == 0 ? (indexSize / pageSize): (indexSize / pageSize + 1);
	}

	// 当前分页数据的结束索引
	public int getEndIndex() {
		int result = currentIndex + pageSize;
		// 最终索引不能大于数据总数
		// 当前索引本身也算作一条记录
		return (result > indexSize - 1 ? indexSize : result) - 1;
	}

	// 下一页开始的索引
	public int getNextIndex() {
		//下一页开始的索引为分页数据的结束索引的下一条
		//下一页开始的索引不能大于数据总数
		return this.getEndIndex()+1 > this.getIndexSize()-1 ?this.getLastIndex() : this.getEndIndex()+1;
	}

	// 上一页开始的索引
	public int getPreviousIndex() {
		int result = currentIndex - pageSize;
		return result < 0 ? 0 : result;
	}

	public int getCurrentIndex() {
		return currentIndex;
	}
	
	public void setCurrentIndex(int currentIndex) {
		//设置当前索引无法被pageSize整除表示格式不合理,自动转换为小于当前索引的相近最小值
		this.currentIndex = (currentIndex % pageSize == 0 ? currentIndex: currentIndex - (currentIndex % pageSize));
	}

	public int getIndexSize() {
		return indexSize;
	}

	public void setIndexSize(int indexSize) {
		this.indexSize = indexSize;
	}
	
	public int getPageSize() {
		return pageSize;
	}
	
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
}
