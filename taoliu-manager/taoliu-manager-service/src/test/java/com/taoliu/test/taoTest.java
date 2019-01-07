package com.taoliu.test;

import java.util.List;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taoliu.mapper.TbItemMapper;
import com.taoliu.pojo.TbItem;
import com.taoliu.pojo.TbItemExample;

public class taoTest {
	
	@SuppressWarnings("resource")
	@Test
	public void testHelper() {
	 ApplicationContext context	 = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-dao.xml");
	 TbItemMapper itemMapper = context.getBean(TbItemMapper.class);
	  PageHelper.startPage(1, 3);
	  TbItemExample tbItemExample = new TbItemExample();
	  List<TbItem> list = itemMapper.selectByExample(tbItemExample);
	  List<TbItem> list2 = itemMapper.selectByExample(tbItemExample);
	  
	  PageInfo<TbItem> pageInfo = new PageInfo<>(list);
	  System.out.println("第一个分页的list的集合长度"+list.size());
		System.out.println("第二个分页的list的集合长度"+list2.size());
	  
		for (TbItem tbItem : list) {
			System.out.println(tbItem.getId()+"》》》mingch>>"+tbItem.getTitle());
		}
		
	}
}
























