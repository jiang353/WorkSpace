//                            _ooOoo_  
//                           o8888888o  
//                           88" . "88  
//                           (| -_- |)  
//                            O\ = /O  
//                        ____/`---'\____  
//                      .   ' \\| |// `.  
//                       / \\||| : |||// \  
//                     / _||||| -:- |||||- \  
//                       | | \\\ - /// | |  
//                     | \_| ''\---/'' | |  
//                      \ .-\__ `-` ___/-. /  
//                   ___`. .' /--.--\ `. . __  
//                ."" '< `.___\_<|>_/___.' >'"".  
//               | | : `- \`.;`\ _ /`;.`/ - ` : | |  
//                 \ \ `-. \_ __\ /__ _/ .-` / /  
//         ======`-.____`-.___\_____/___.-`____.-'======  
//                            `=---='  
//  
//         .............................................  
//                  佛祖保佑             永无BUG 

/**
 * 
 */ 
package com.qingniao.search.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.qingniao.common.QingNiaoResult;
import com.qingniao.common.pojo.ItemSearch;
import com.qingniao.common.pojo.SearchResult;
import com.qingniao.search.mapper.ItemSearchMapper;
import com.qingniao.search.service.ItemSearchService;

/** 
* @author 作者: Jiang Song
* @version 创建时间：2017年12月19日 下午2:36:51 
* 类说明 
*/

@Service
@Transactional
public class ItemSearchServiceImpl implements ItemSearchService {
	
	@Autowired
	private ItemSearchMapper itemSearchMapper;

	@Autowired
	private SolrServer solrServer;

	/* (non-Javadoc)
	 * @see com.qingniao.search.service.ItemSearchService#saveItemInSolr()
	 * 添加所有商品至solr服务器
	 */
	@Override
	public QingNiaoResult saveItemInSolr() {
		//获取数据
		List<ItemSearch> itemSearch = itemSearchMapper.getItemSearch();
		try {
			//添加至solr服务器
			for (ItemSearch item : itemSearch) {
				SolrInputDocument document=new SolrInputDocument();
				document.addField("id", item.getId());
				document.addField("item_sell_point", item.getSell_point());
				document.addField("item_price", item.getPrice());
				document.addField("item_image", item.getImage());
				document.addField("item_category_name", item.getCategory_name());
				document.addField("item_desc", item.getItem_desc()==null?"":item.getItem_desc());
				document.addField("item_title", item.getTitle());
				solrServer.add(document);
			}
			//提交
			solrServer.commit();
		} catch (Exception e) {
			e.printStackTrace();
			return QingNiaoResult.build(500, "添加错误");
		} 
		return QingNiaoResult.ok();
	}

	/* (non-Javadoc)
	 * 从solr服务器查询数据  企业级搜索服务器
	 * 及分页 高亮显示
	 * @see com.qingniao.search.service.ItemSearchService#selectSearch(java.lang.String, long, long)
	 */
	@Override
	public SearchResult selectSearch(String search, int page, int rows) {
		SearchResult resoult=new SearchResult();
		List<ItemSearch> item=new ArrayList<>();
		//创建solrquery对象
		SolrQuery query=new SolrQuery();
		try {
			//设置查询条件
			query.setQuery(search);
			//设置搜索域
			query.set("df", "item_keywords");
			//设置分页
			query.setStart(page);
			query.setRows(rows);
			//开启高亮显示
			query.setHighlight(true);
			//设置高亮显示域
			query.addHighlightField("item_title");
			query.setHighlightSimplePre("<font color='red'>");
			query.setHighlightSimplePost("</font>");
			QueryResponse response = solrServer.query(query);
			//获取数据
			SolrDocumentList results = response.getResults();
			long totalPages=0;
			for (SolrDocument solrDocument : results) {
				ItemSearch sea=new ItemSearch();
				String title="";
				sea.setId(solrDocument.get("id").toString());
				sea.setImage(solrDocument.get("item_image")==null?"":solrDocument.get("item_image").toString());
				sea.setItem_desc(solrDocument.get("item_desc")==null?"":solrDocument.get("item_desc").toString());
				sea.setPrice(Long.parseLong(solrDocument.get("item_price")==null?"":solrDocument.get("item_price").toString()));
				sea.setSell_point(solrDocument.get("item_sell_point")==null?"":solrDocument.get("item_sell_point").toString());
				sea.setCategory_name(solrDocument.get("item_category_name")==null?"":solrDocument.get("item_category_name").toString());
				
				//取高亮
				Map<String, Map<String, List<String>>> highlighting = response.getHighlighting();
				Map<String, List<String>> map = highlighting.get(solrDocument.get("id"));
				List<String> list = map.get("item_title");
				title = list==null?solrDocument.get("item_title").toString():list.get(0);
				sea.setTitle(title);
				//////
				item.add(sea);
			}
			//获取总页数
			totalPages=results.getNumFound()%rows==0?results.getNumFound()/rows:results.getNumFound()/rows+1;
			resoult.setItemList(item);
			resoult.setTotalPages((int)totalPages);
		} catch (SolrServerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return resoult;
	}

	/* (non-Javadoc)
	 * @see com.qingniao.search.service.ItemSearchService#saveByItem(long)
	 * 将商品添加至solr索引
	 */
	@Override
	public QingNiaoResult saveByItem(long id) {
		// TODO Auto-generated method stub
		try {
			ItemSearch item = itemSearchMapper.getByItemSearch(id);
			//System.out.println(item.getId());
			SolrInputDocument document=new SolrInputDocument();
			document.addField("id", item.getId());
			document.addField("item_sell_point", item.getSell_point());
			document.addField("item_price", item.getPrice());
			document.addField("item_image", item.getImage());
			document.addField("item_category_name", item.getCategory_name());
			document.addField("item_desc", item.getItem_desc()==null?"":item.getItem_desc());
			document.addField("item_title", item.getTitle());
			solrServer.add(document);
			solrServer.commit();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return QingNiaoResult.ok();
	}
	
	
	
	

}
 