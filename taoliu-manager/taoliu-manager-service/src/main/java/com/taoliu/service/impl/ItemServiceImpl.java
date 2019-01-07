package com.taoliu.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taoliu.common.pojo.EasyUIDataGridResult;
import com.taoliu.mapper.TbItemMapper;
import com.taoliu.pojo.TbItem;
import com.taoliu.pojo.TbItemExample;
import com.taoliu.service.ItemService;

@Service
public class ItemServiceImpl implements ItemService {

	@Autowired
	private TbItemMapper mapper;
	
	@Override
	public EasyUIDataGridResult getItemList(Integer page, Integer rows) {
		
		if(page==null)page=1;
		if(rows==null)rows=30;
		
		PageHelper.startPage(page, rows);
		TbItemExample tbItemExample = new TbItemExample();
		List<TbItem> list = mapper.selectByExample(tbItemExample);
		
		
		PageInfo<TbItem> pageInfo = new PageInfo<>(list);
		EasyUIDataGridResult result = new EasyUIDataGridResult();
		result.setTotal((int)pageInfo.getTotal()); 
		result.setRows(pageInfo.getList());
		return result;
	}

}



























