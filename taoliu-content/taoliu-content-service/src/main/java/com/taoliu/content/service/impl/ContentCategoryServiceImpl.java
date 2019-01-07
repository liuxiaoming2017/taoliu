package com.taoliu.content.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taoliu.common.pojo.EasyUITreeNode;
import com.taoliu.common.pojo.TaotaoResult;
import com.taoliu.content.service.ContentCategoryService;
import com.taoliu.mapper.TbContentCategoryMapper;
import com.taoliu.pojo.TbContentCategory;
import com.taoliu.pojo.TbContentCategoryExample;
import com.taoliu.pojo.TbContentCategoryExample.Criteria;

@Service
public class ContentCategoryServiceImpl implements ContentCategoryService {

	@Autowired
	private TbContentCategoryMapper mapper;
	
	
	@Override
	public List<EasyUITreeNode> getContentCategoryList(Long parentId) {
		TbContentCategoryExample example = new TbContentCategoryExample();
		Criteria criteria = example.createCriteria();
		criteria.andParentIdEqualTo(parentId);
		List<TbContentCategory> list = mapper.selectByExample(example);
		List<EasyUITreeNode> nodes = new ArrayList<>();
		for (TbContentCategory tbContentCategory : list) {
			EasyUITreeNode node = new EasyUITreeNode();
			node.setId(tbContentCategory.getId());
			node.setState(tbContentCategory.getIsParent()?"closed":"open");
			node.setText(tbContentCategory.getName());
			nodes.add(node);
		}
		return nodes;
	}

	@Override
	public TaotaoResult createContentCategory(Long parentId, String name) {
		TbContentCategory category = new TbContentCategory();
		category.setCreated(new Date());
		category.setIsParent(false);
		category.setName(name);
		category.setParentId(parentId);
		category.setSortOrder(1);
		category.setStatus(1);
		category.setUpdated(category.getCreated());
		mapper.insertSelective(category);
		
		TbContentCategory parent = mapper.selectByPrimaryKey(parentId);
		
		if(parent.getIsParent() == false) {
			parent.setIsParent(true);
			mapper.updateByPrimaryKeySelective(parent);
		}
		
		return TaotaoResult.ok(category);
	}

}

















