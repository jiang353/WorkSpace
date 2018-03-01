package com.qingniao.serviceimpl;

import java.util.Date;
import java.util.List;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.apache.activemq.command.ActiveMQTopic;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.qingniao.common.EasyUIDataGridResult;
import com.qingniao.common.QingNiaoResult;
import com.qingniao.common.utils.IDUtils;
import com.qingniao.common.utils.JsonUtils;
import com.qingniao.jedis.JedisClient;
import com.qingniao.mapper.TbItemDescMapper;
import com.qingniao.mapper.TbItemMapper;
import com.qingniao.pojo.TbItem;
import com.qingniao.pojo.TbItemDesc;
import com.qingniao.pojo.TbItemExample;
import com.qingniao.service.ItemService;


@Service
public class ItemServiceImpl implements ItemService {
	
	@Autowired
	private TbItemMapper mapper;
	
	@Autowired
	private TbItemDescMapper tbItemDescMapper;
	
	@Autowired
	private JmsTemplate jmsTemplate;
	
	@Autowired
	private ActiveMQTopic itemAddtopic;
	//redis
	@Autowired
	private JedisClient jedis;
	//缓存的KEY
	@Value("${ITEM_INFO_PRE}")
	private String ITEM_INFO_PRE;
	//缓存过期时间
	@Value("${ITEM_INFO_EXPIRE}")
	private int ITEM_INFO_EXPIRE;

	
	/**
	 * 查询商品信息
	 * 添加缓存
	 */
	@Override
	public TbItem selectByprimarKey(long itenid) {
		// TODO Auto-generated method stub
		//从缓存获取数据
		try {
			String hget = jedis.get(ITEM_INFO_PRE+":"+itenid+":BASE");
			if(StringUtils.isNotBlank(hget)){
				System.out.println("缓存里存在该商品信息！！");
				return JsonUtils.jsonToPojo(hget,TbItem.class);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		//如果缓存里不存在数据就将数据保存到redis缓存
		TbItem tbItem = mapper.selectByPrimaryKey(itenid);
		//将数据放到缓存
		jedis.set(ITEM_INFO_PRE+":"+itenid+":BASE", JsonUtils.objectToJson(tbItem));
		//缓存过期时间 
		jedis.expire(ITEM_INFO_PRE+":"+itenid+":BASE", ITEM_INFO_EXPIRE);
		System.out.println("缓存里不存在该商品信息！！");
		return tbItem;
	}

	@Override
	public EasyUIDataGridResult selectByExample(TbItemExample example,int page, int rows) {
		// TODO Auto-generated method stub
		//设置分页信息
		PageHelper.startPage(page, rows);
		//开始自动分页
		List<TbItem> itemlist = mapper.selectByExample(example);
		
		PageInfo<TbItem> list=new PageInfo<TbItem>(itemlist);
		//System.out.println("总记录数："+list.getTotal());
		EasyUIDataGridResult resule=new EasyUIDataGridResult(list.getTotal(),list.getList());
		return resule;
	}
	

	/* (non-Javadoc)
	 * @see com.qingniao.service.ItemService#saveItem(com.qingniao.pojo.TbItem, java.lang.String)
	 * 添加商品
	 * 
	 * 添加完成后发送消息队列信息
	 */
	@Override
	public QingNiaoResult saveItem(TbItem item, String desc) {
		// TODO Auto-generated method stub
		//设置商品id
		//向商品表添加信息
		//接收 类 qingniao-search-srvice  com.qingniao.util.ItemAddListener
		try {
			final long ItemId = IDUtils.genItemId();
			item.setId(ItemId);
			item.setStatus((byte)1);
			Date date=new Date();
			item.setCreated(date);
			item.setUpdated(date);
			mapper.insert(item);
			//向描述表添加信息
			TbItemDesc itemDesc=new TbItemDesc();
			itemDesc.setItemId(ItemId);
			itemDesc.setItemDesc(desc);
			itemDesc.setCreated(date);
			itemDesc.setUpdated(date);
			tbItemDescMapper.insert(itemDesc);
			//添加完成后发送同步索引库消息
			jmsTemplate.send(itemAddtopic,new MessageCreator() {
				@Override
				public Message createMessage(Session session) throws JMSException {
					// TODO Auto-generated method stub
					return session.createTextMessage(ItemId+"");
				}
			});
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return QingNiaoResult.build(500, "添加失败");
		}
		return QingNiaoResult.ok();
	}

	/* (non-Javadoc)
	 * @see com.qingniao.service.ItemService#updateItem(com.qingniao.pojo.TbItem, java.lang.String)
	 * 修改商品信息
	 */
	@Override
	public QingNiaoResult updateItem(TbItem item, String desc) {
		// TODO Auto-generated method stub
		//封装信息
		Date date=new Date();
		item.setUpdated(date);
		mapper.updateByPrimaryKeySelective(item);
		//商品描述
		TbItemDesc record=new TbItemDesc();
		record.setItemDesc(desc);
		tbItemDescMapper.updateByPrimaryKeySelective(record);
		return QingNiaoResult.ok();
	}

	
	
	/* (non-Javadoc)
	 *  删除商品
	 * @see com.qingniao.service.ItemService#deleteItem(long)
	 */
	@Override
	public QingNiaoResult deleteItem(long[] itemID) {
		// TODO Auto-generated method stub
		// 1正常 2下架 3删除
		/*for(long id:itemID){
			mapper.deleteByPrimaryKey(id);
		}
		return QingNiaoResult.ok();*/
		return all(itemID,(byte)3);
	}

	/**
	 * 上架商品
	 */
	@Override
	public QingNiaoResult putaway(long[] itemID) {
		// 1正常 2下架 3删除
		return all(itemID,(byte)1);
	}
	
	/**
	 * 下架商品
	 */
	@Override
	public QingNiaoResult soldOut(long[] itemID) {
		// 1正常 2下架 3删除
		return all(itemID,(byte)2);
	}

	/**
	 * 1正常 2下架 3删除
	 * @param itemID
	 * @param status
	 * @return
	 */
	private QingNiaoResult all(long[] itemID, byte status){
		try {
			for(long id:itemID){
				TbItem record=new TbItem();
				record.setId(id);
				//1正常 2下架 3删除
				record.setStatus(status);
				record.setUpdated(new Date());
				mapper.updateByPrimaryKeySelective(record);
			}
			return QingNiaoResult.ok();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return QingNiaoResult.build(500, "!!!!!!!!!!!!!!!!!!");
	}

	/* (non-Javadoc)
	 * @see com.qingniao.service.ItemService#selectItemDescByKey(long)
	 * 查询商品详细信息
	 * 添加缓存
	 */
	@Override
	public TbItemDesc selectItemDescByKey(long itenid) {
		// TODO Auto-generated method stub
		try {
			String key=jedis.get(ITEM_INFO_PRE+":"+itenid+":DESC");
			//从缓存取数据 若找到直接返回数据到服务
			if(StringUtils.isNotEmpty(key)){
				return JsonUtils.jsonToPojo(key, TbItemDesc.class);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		TbItemDesc itemDesc = tbItemDescMapper.selectByPrimaryKey(itenid);
		try {
			//将数据存到缓存
			jedis.set(ITEM_INFO_PRE+":"+itenid+":DESC", JsonUtils.objectToJson(itemDesc));
			//设置缓存过期时间
			jedis.expire(ITEM_INFO_PRE+":"+itenid+":DESC", ITEM_INFO_EXPIRE);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return itemDesc;
	}
}
