package com.taoliu.content.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.taoliu.common.pojo.TaotaoResult;
import com.taoliu.common.util.JsonUtils;
import com.taoliu.content.jedis.JedisClient;
import com.taoliu.content.service.ContentService;
import com.taoliu.mapper.TbContentMapper;
import com.taoliu.pojo.TbContent;
import com.taoliu.pojo.TbContentExample;
import com.taoliu.pojo.TbContentExample.Criteria;

@Service
public class ContentServiceImpl implements ContentService {

	@Autowired
	private TbContentMapper mapper;
	
	@Autowired
	private JedisClient client;
	
	@Value("${CONTENT_KEY}")
	private String CONTENT_KEY;
	
	@Override
	public TaotaoResult saveContent(TbContent content) {
		content.setCreated(new Date());
		content.setUpdated(content.getCreated());
		mapper.insertSelective(content);
		return TaotaoResult.ok();
		
	}

	@Override
	public List<TbContent> getContentListByCatId(Long categoryId) {
		
		
		try {
			String jsonstr = client.hget(CONTENT_KEY, categoryId+"");
			if(StringUtils.isNotBlank(jsonstr)) {
				System.out.println("这里有缓存啦！！！！！");
				return JsonUtils.jsonToList(jsonstr, TbContent.class);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		TbContentExample example = new TbContentExample();
		Criteria criteria = example.createCriteria();
		criteria.andCategoryIdEqualTo(categoryId);
		List<TbContent> list = mapper.selectByExample(example);
		
		try {
			System.out.println("没有缓存！！！！！！");
			client.hset(CONTENT_KEY, categoryId+"", JsonUtils.objectToJson(list));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		return list;
	}

}
