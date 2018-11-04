package javabean;

import java.util.List;

public class Page {

	private List records;// 要显示的分页记录
	private int currentPageNum; // 当前页码 : 由用户指定
	private int pageSize = 10; // 每页显示的记录
	private int totalPageNum; // 总页数
	private int prePageNum; // 上一页的页码
	private int nextPageNum; // 下一页的页码

	private int startIndex; // 数据库每页开始记录的索引
	private int totalRecords; // 总记录的条数

	// currentPageNum : 用户要看的页码
	// totalRecords : 总记录条数
	public Page(int currentPageNum, int totalRecords) {
		this.currentPageNum = currentPageNum;
		this.totalRecords = totalRecords;

		// 计算总页数
		totalPageNum = totalRecords % pageSize == 0 ? (totalRecords / pageSize) : (totalRecords / pageSize + 1);
		// 计算每页开始的索引
		startIndex = (currentPageNum - 1) * pageSize;
	}
	
	public Page(int currentPageNum, int totalRecords,int pageSize){
		this.pageSize = pageSize;
		this.currentPageNum = currentPageNum;
		this.totalRecords = totalRecords;

		// 计算总页数
		totalPageNum = totalRecords % pageSize == 0 ? (totalRecords / pageSize) : (totalRecords / pageSize + 1);
		// 计算每页开始的索引
		startIndex = (currentPageNum - 1) * pageSize;
	}

	public List getRecords() {
		return records;
	}

	public void setRecords(List records) {
		this.records = records;
	}

	public int getCurrentPageNum() {
		return currentPageNum;
	}

	public void setCurrentPageNum(int currentPageNum) {
		this.currentPageNum = currentPageNum;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getTotalPageNum() {
		return totalPageNum;
	}

	public void setTotalPageNum(int totalPageNum) {
		this.totalPageNum = totalPageNum;
	}

	public int getPrePageNum() {

		prePageNum = currentPageNum - 1;
		if (prePageNum < 1)
			prePageNum = 1;
		return prePageNum;
	}

	public void setPrePageNum(int prePageNum) {
		this.prePageNum = prePageNum;
	}

	public int getNextPageNum() {
		nextPageNum = currentPageNum + 1;
		if (nextPageNum > totalPageNum)
			nextPageNum = totalPageNum;
		return nextPageNum;
	}

	public void setNextPageNum(int nextPageNum) {
		this.nextPageNum = nextPageNum;
	}

	public int getStartIndex() {
		return startIndex;
	}

	public void setStartIndex(int startIndex) {
		this.startIndex = startIndex;
	}

	public int getTotalRecords() {
		return totalRecords;
	}

	public void setTotalRecords(int totalRecords) {
		this.totalRecords = totalRecords;
	}

}
