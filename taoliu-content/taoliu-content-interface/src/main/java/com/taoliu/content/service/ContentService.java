package com.taoliu.content.service;

import java.util.List;

import com.taoliu.common.pojo.TaotaoResult;
import com.taoliu.pojo.TbContent;

public interface ContentService {
	
	/**
	 * 
	 * @param content
	 * @return
	 */
	
	public TaotaoResult saveContent(TbContent content);
	
	/**
	 * 根据内容分类的ID，查询其下的内容列表
	 * @param categoryId
	 * @return
	 */
	public List<TbContent> getContentListByCatId(Long categoryId);
}
