package com.qingniao.service;

import java.util.List;

import com.qingniao.common.EasyUITreeNode;

public interface ItemCatService {
	
	List<EasyUITreeNode> getItemCatList(long parentId);

}
