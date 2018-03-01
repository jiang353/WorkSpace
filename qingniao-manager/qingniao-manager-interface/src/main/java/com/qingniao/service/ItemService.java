package com.qingniao.service;


import com.qingniao.common.EasyUIDataGridResult;
import com.qingniao.common.QingNiaoResult;
import com.qingniao.pojo.TbItem;
import com.qingniao.pojo.TbItemDesc;
import com.qingniao.pojo.TbItemExample;

public interface ItemService {
	
	TbItem selectByprimarKey(long itenid);
	
	EasyUIDataGridResult selectByExample(TbItemExample example,int page, int rows);
	
	QingNiaoResult saveItem(TbItem item,String desc);
	
	QingNiaoResult updateItem(TbItem item,String desc);
	
	QingNiaoResult deleteItem(long[] itemID);
	
	QingNiaoResult putaway(long[] itemID);
	
	QingNiaoResult soldOut(long[] itemID);
	
	TbItemDesc selectItemDescByKey(long itenid);
}
