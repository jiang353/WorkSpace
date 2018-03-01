package com.qingniao.serviceimpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.qingniao.common.EasyUITreeNode;
import com.qingniao.mapper.TbItemCatMapper;
import com.qingniao.pojo.TbItemCat;
import com.qingniao.pojo.TbItemCatExample;
import com.qingniao.pojo.TbItemCatExample.Criteria;
import com.qingniao.service.ItemCatService;

@Service
@Transactional
public class ItemCatServiceImpl implements ItemCatService {
	
	@Autowired
	private TbItemCatMapper tbItemCatMapper;

	@Override
	public List<EasyUITreeNode> getItemCatList(long parentId) {
		// TODO Auto-generated method stub
		List<EasyUITreeNode> list=new ArrayList<>();
		//设置查询条件
		TbItemCatExample example =new TbItemCatExample();
		Criteria createCriteria = example.createCriteria();
		createCriteria.andParentIdEqualTo(parentId);
		//执行查询
		List<TbItemCat> itemCat = tbItemCatMapper.selectByExample(example);
		//封装数据
		for(TbItemCat cat:itemCat){
			EasyUITreeNode node=new EasyUITreeNode();
			node.setId(cat.getId());
			node.setText(cat.getName());
			node.setState(cat.getIsParent()?"closed":"open");
			list.add(node);
		}
		return list;
	}

}
