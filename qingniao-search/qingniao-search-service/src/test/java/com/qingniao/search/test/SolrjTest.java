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
package com.qingniao.search.test;



import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;

/** 
* @author 作者: Jiang Song
* @version 创建时间：2017年12月18日 下午4:32:51 
* 类说明  Solrj测试
*/
public class SolrjTest {
	
	/**
	 * 添加
	 * @throws Exception
	 */
	@Test
	public void solrj() throws Exception{
		//1.创建solrService
		SolrServer service =new HttpSolrServer("http://192.168.128.128:8080/solr");
		SolrInputDocument document=new SolrInputDocument();
		document.addField("id", "10");
		document.addField("item_title", "测试一下");
		document.addField("item_image", "测试一下");
		service.add(document);
		service.commit();
	}
	
	@Test
	public void deleteSolr() throws Exception{
		SolrServer service =new HttpSolrServer("http://192.168.128.128:8080/solr");
		//service.deleteById("10");
		//查询删除
		service.deleteByQuery("item_title:测试一下");
		service.commit();
	}
	
	/**
	 * 简单查询
	 * @throws Exception 
	 */
	@Test
	public void selectSolr() throws Exception{
		SolrServer service =new HttpSolrServer("http://192.168.128.128:8080/solr");
		SolrQuery query=new SolrQuery();
		query.setQuery("*:*");
		QueryResponse query2 = service.query(query);
		SolrDocumentList results = query2.getResults();
		for (SolrDocument solrDocument : results) {
			System.out.println(solrDocument.get("item_title"));
		}
	}
}
 