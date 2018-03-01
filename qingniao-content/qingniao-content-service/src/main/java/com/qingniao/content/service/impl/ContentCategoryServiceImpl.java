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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.qingniao.common.EasyUITreeNode;
import com.qingniao.common.QingNiaoResult;
import com.qingniao.content.service.ContentCategoryService;
import com.qingniao.mapper.TbContentCategoryMapper;
import com.qingniao.pojo.TbContentCategory;
import com.qingniao.pojo.TbContentCategoryExample;
import com.qingniao.pojo.TbContentCategoryExample.Criteria;

/** 
* @author 作者: Jiang Song
* @version 创建时间：2017年12月13日 下午3:18:37 
* 类说明 
*/
@Service
@Transactional
public class ContentCategoryServiceImpl implements ContentCategoryService {
	
	@Autowired
	private TbContentCategoryMapper contentCategoryMapper;
	
	

	/* (non-Javadoc)
	 * @see com.qingniao.contene.service.ContentCategoryService#getContentCategory(long)
	 */
	@Override
	public List<EasyUITreeNode> getContentCategory(long parentId) {
		// TODO Auto-generated method stub
		//1封装查询条件
		TbContentCategoryExample example=new TbContentCategoryExample();
		Criteria create = example.createCriteria();
		create.andParentIdEqualTo(parentId);
		List<TbContentCategory> contentList = contentCategoryMapper.selectByExample(example);
		//2封装数据
		List<EasyUITreeNode> nodes=new ArrayList<EasyUITreeNode>();
		for(TbContentCategory list:contentList){
			EasyUITreeNode node=new EasyUITreeNode();
			node.setId(list.getId());
			node.setText(list.getName());
			node.setState(list.getIsParent()?"closed":"open");
			nodes.add(node);
		}
		return nodes;
	}

	/* (non-Javadoc)
	 * @see com.qingniao.service.ContentCategoryService#saveNodes(long, java.lang.String)
	 * 添加节点
	 */
	@Override
	public QingNiaoResult saveNodes(long parentId, String name) {
		// TODO Auto-generated method stub
		TbContentCategory record =new TbContentCategory();
		record.setParentId(parentId);
		record.setName(name);
		record.setStatus(1);
		record.setSortOrder(1);
		record.setUpdated(new Date());
		record.setCreated(new Date());
		record.setIsParent(false);
		contentCategoryMapper.insert(record);
		//如果所添加的父节点下面没有节点 修改状态
		TbContentCategory ByPrimaryKey = contentCategoryMapper.selectByPrimaryKey(parentId);
		if(!ByPrimaryKey.getIsParent()){
			ByPrimaryKey.setIsParent(true);
			contentCategoryMapper.updateByPrimaryKey(ByPrimaryKey);
		}
		return QingNiaoResult.ok(record);
	}
	/**
	 * 修改节点信息
	 */
	@Override
	public QingNiaoResult updateNode(long id, String name) {
		// TODO Auto-generated method stub
		TbContentCategory record=new TbContentCategory();
		record.setId(id);
		record.setName(name);
		contentCategoryMapper.updateByPrimaryKeySelective(record);
		return QingNiaoResult.ok();
	}

	/* (non-Javadoc)
	 * @see com.qingniao.service.ContentCategoryService#deleteNode(long)
	 * 删除节点
	 */
	
	@Override
	public QingNiaoResult deleteNode(long id) {
		//如果删除的节点为父节点的最后一个节点 测修改父节点的状态
		//获取要删除的节点信息
		TbContentCategory ByPrimaryKey = contentCategoryMapper.selectByPrimaryKey(id);
		//获取要删除的节点的父id
		long parentId=ByPrimaryKey.getParentId();
		//通过父id判断是否为唯一的子节点
		//封装数据查询
		TbContentCategoryExample example1=new TbContentCategoryExample();
		Criteria create1 = example1.createCriteria();
		create1.andParentIdEqualTo(parentId);
		//查询并处理
		if(contentCategoryMapper.selectByExample(example1).size()==1){
			TbContentCategory selectByPrimaryKey = contentCategoryMapper.selectByPrimaryKey(parentId);
			selectByPrimaryKey.setIsParent(false);
			contentCategoryMapper.updateByPrimaryKeySelective(selectByPrimaryKey);
		}
		//先删子节点
		TbContentCategoryExample example=new TbContentCategoryExample();
		Criteria create = example.createCriteria();
		create.andParentIdEqualTo(id);
		//删除节点
		contentCategoryMapper.deleteByExample(example);
		//删除节点
		contentCategoryMapper.deleteByPrimaryKey(id);
		
		return QingNiaoResult.ok();
	}
	
	

}
 