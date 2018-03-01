package com.qingniao.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.qingniao.common.EasyUIDataGridResult;
import com.qingniao.common.QingNiaoResult;
import com.qingniao.pojo.TbItem;
import com.qingniao.pojo.TbItemExample;
import com.qingniao.search.service.ItemSearchService;
import com.qingniao.service.ItemService;

@Controller
public class ItemController {
	
	@Autowired
	private ItemService itenService;
	
	@Autowired
	private ItemSearchService itemSearchService;
	
	@ResponseBody
	@RequestMapping("/item/{itemid}")
	public TbItem getItem(@PathVariable long itemid){
		
		return itenService.selectByprimarKey(itemid);
	}
	
	/**
	 * 商品列表
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping("/item/list")
	@ResponseBody
	public EasyUIDataGridResult itemList(@RequestParam(value="page",defaultValue="1")Integer page,
			@RequestParam(value="rows",defaultValue="30")Integer rows){
		
		TbItemExample example=new TbItemExample();
		return itenService.selectByExample(example, page, rows);
	}
	
	
	
	/**
	 * 添加商品
	 * @param item
	 * @param desc
	 * @return
	 */
	@RequestMapping("/item/save")
	@ResponseBody
	public QingNiaoResult save(TbItem item,String desc){
		return itenService.saveItem(item, desc);
	}
	
	/**
	 * 修改商品
	 * @return
	 */
	@RequestMapping("/rest/item/update")
	@ResponseBody
	public QingNiaoResult update(TbItem item,String desc){
		return itenService.updateItem(item, desc);
	}
	
	/**
	 * 删除
	 * @param ids
	 * @return
	 */
	@RequestMapping("//rest/item/delete")
	@ResponseBody
	public QingNiaoResult delete(long[] ids){
		return itenService.deleteItem(ids);
	}
	
	/**
	 * 上架商品
	 * @param ids
	 * @return
	 */
	@RequestMapping("/rest/item/reshelf")
	@ResponseBody
	public QingNiaoResult putaway(long[] ids){
		return itenService.putaway(ids);
	}
	
	
	/**
	 * 下架商品
	 * @param ids
	 * @return
	 */
	@RequestMapping("/rest/item/instock")
	@ResponseBody
	public QingNiaoResult soldOut(long[] ids){
		return itenService.soldOut(ids);
	}
	/**
	 * 添加索引库
	 * @return
	 */
	@RequestMapping("/item/import")
	@ResponseBody
	public QingNiaoResult saveSearch(){
		return itemSearchService.saveItemInSolr();
	}
}
