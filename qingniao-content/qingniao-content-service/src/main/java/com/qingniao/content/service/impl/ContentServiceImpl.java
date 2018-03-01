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
package com.qingniao.content.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.qingniao.common.EasyUIDataGridResult;
import com.qingniao.common.QingNiaoResult;
import com.qingniao.common.utils.Ad1;
import com.qingniao.common.utils.JsonUtils;
import com.qingniao.content.service.ContentService;
import com.qingniao.jedis.JedisClient;
import com.qingniao.jedis.JedisClientPool;
import com.qingniao.mapper.TbContentMapper;
import com.qingniao.pojo.TbContent;
import com.qingniao.pojo.TbContentExample;
import com.qingniao.pojo.TbContentExample.Criteria;

/** 
* @author 作者: Jiang Song
* @version 创建时间：2017年12月13日 下午10:17:40 
* 类说明 
*/
@Service
@Transactional
public class ContentServiceImpl implements ContentService {
	
	
	@Value("${AD1_WIDTH}")
	private Integer AD1_WIDTH;
	@Value("${AD1_WIDTH_B}")
	private Integer AD1_WIDTH_B;
	@Value("${AD1_HEIGHT}")
	private Integer AD1_HEIGHT;
	@Value("${AD1_HEIGHT_B}")
	private Integer AD1_HEIGHT_B;
	@Value("${INDEX_CONTENT}")
	private String INDEX_CONTENT;
	
	@Autowired
	private JedisClient jedisClientPool;
	@Autowired
	private TbContentMapper tbContentMapper;

	/* (non-Javadoc)
	 * @see com.qingniao.content.service.ContentService#getContentList(long, int, int)
	 * 分页查询
	 */
	@Override
	public EasyUIDataGridResult getContentList(long categoryId, int page, int rows) {
		// TODO Auto-generated method stub
		//开始分页查询
		PageHelper.startPage(page, rows);
		TbContentExample example=new TbContentExample();
		Criteria create = example.createCriteria();
		create.andCategoryIdEqualTo(categoryId);
		List<TbContent> list = tbContentMapper.selectByExample(example);
		EasyUIDataGridResult resou=new EasyUIDataGridResult();
		PageInfo<TbContent> info=new PageInfo<>(list);
		resou.setRows(list);
		resou.setTotal((int)info.getTotal());
		return resou;
	}

	/* (non-Javadoc)
	 * @see com.qingniao.content.service.ContentService#save(com.qingniao.pojo.TbContent)
	 */
	@Override
	public QingNiaoResult save(TbContent content) {
		// TODO Auto-generated method stub
		content.setUpdated(new Date());
		content.setCreated(new Date());
		tbContentMapper.insert(content);
		jedisClientPool.hdel(INDEX_CONTENT, content.getCategoryId()+"");
		return QingNiaoResult.ok();
	}

	/* (non-Javadoc)
	 * @see com.qingniao.content.service.ContentService#update(com.qingniao.pojo.TbContent)
	 */
	@Override
	public QingNiaoResult update(TbContent content) {
		// TODO Auto-generated method stub
		content.setUpdated(new Date());
		tbContentMapper.updateByPrimaryKey(content);
		jedisClientPool.hdel(INDEX_CONTENT, content.getCategoryId()+"");
		return QingNiaoResult.ok();
	}

	/* (non-Javadoc)
	 * @see com.qingniao.content.service.ContentService#delete(long[])
	 */
	@Override
	public QingNiaoResult delete(long[] ids) {
		// TODO Auto-generated method stub
		TbContent selectByPrimaryKey = tbContentMapper.selectByPrimaryKey(ids[0]);
		jedisClientPool.hdel(INDEX_CONTENT, selectByPrimaryKey.getCategoryId()+"");
		for (long id : ids) {
			tbContentMapper.deleteByPrimaryKey(id);
		}
		return QingNiaoResult.ok();
	}
	
	
	@Override
	public List<Ad1> getContentList(long categoryId) {
		// TODO Auto-generated method stub
		//从redis中查询 如果命中就直接返回数据
		try {
			String hget = jedisClientPool.hget(INDEX_CONTENT, categoryId+"");
			if(StringUtils.isNotBlank(hget)){
				return JsonUtils.jsonToList(hget, Ad1.class);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		TbContentExample example=new TbContentExample();
		Criteria create = example.createCriteria();
		create.andCategoryIdEqualTo(categoryId);
		List<TbContent> list = tbContentMapper.selectByExample(example);
		List<Ad1> ads=new ArrayList<>();
		for (TbContent tbContent : list) {
			Ad1 ad=new Ad1();
			ad.setAlt(tbContent.getTitle());
			ad.setHeight(AD1_HEIGHT);
			ad.setHeightB(AD1_HEIGHT_B);
			ad.setHref(tbContent.getUrl());
			ad.setSrc(tbContent.getPic());
			ad.setSrcB(tbContent.getPic2());
			ad.setWidth(AD1_WIDTH);
			ad.setWidthB(AD1_WIDTH_B);
			ads.add(ad);
		}
		//将数据存到redis
		try {
			jedisClientPool.hset(INDEX_CONTENT, categoryId+"", JsonUtils.objectToJson(ads));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ads;
	}

}
 