package com.taoliu.content.service;

import java.util.List;

import com.taoliu.common.pojo.EasyUITreeNode;
import com.taoliu.common.pojo.TaotaoResult;

public interface ContentCategoryService {
	
	public List<EasyUITreeNode> getContentCategoryList(Long parentId);
	
	public TaotaoResult createContentCategory(Long parentId,String name);
}
