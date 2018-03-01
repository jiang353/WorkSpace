package com.qingniao.search.mapper;


import java.util.List;

import com.qingniao.common.pojo.ItemSearch;

public interface ItemSearchMapper {
	
	public List<ItemSearch> getItemSearch();
	
	public ItemSearch getByItemSearch(long id);
	
}
