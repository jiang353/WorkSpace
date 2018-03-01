package com.qingniao.common.pojo;


import java.io.Serializable;
import java.util.List;

public class SearchResult implements Serializable{
	
	private int totalPages;
	private List<ItemSearch> itemList;
	private int recordCount;
	
	
	public int getTotalPages() {
		return totalPages;
	}
	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}
	public List<ItemSearch> getItemList() {
		return itemList;
	}
	public void setItemList(List<ItemSearch> itemList) {
		this.itemList = itemList;
	}
	public int getRecordCount() {
		return recordCount;
	}
	public void setRecordCount(int recordCount) {
		this.recordCount = recordCount;
	}
	
	
	
}
