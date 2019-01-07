package com.taoliu.portal.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.taoliu.common.util.JsonUtils;
import com.taoliu.content.service.ContentService;
import com.taoliu.pojo.TbContent;
import com.taoliu.portal.pojo.Ad1Node;

@Controller
public class PageController {
	
	@Autowired
	private ContentService contentService;
	
	@Value("${AD1_CATEGORY_ID}")
	private Long categoryId;
	
	@Value("${AD1_HEIGHT_B}")
	private String AD1_HEIGHT_B;
	
	@Value("${AD1_HEIGHT}")
	private String AD1_HEIGHT;
	
	@Value("${AD1_WIDTH}")
	private String AD1_WIDTH;
	
	@Value("${AD1_WIDTH_B}")
	private String AD1_WIDTH_B;
	
	@RequestMapping("/index")
	public String showIndex(Model model) {
		
		List<TbContent> contentList = contentService.getContentListByCatId(categoryId);
		List<Ad1Node> nodes = new ArrayList<>();
		for (TbContent tbContent : contentList) {
			Ad1Node node = new Ad1Node();
			node.setAlt(tbContent.getSubTitle());
			node.setHeight(AD1_HEIGHT);
			node.setHeightB(AD1_HEIGHT_B);
			node.setHref(tbContent.getUrl());
			node.setSrc(tbContent.getPic());
			node.setSrcB(tbContent.getPic2());
			node.setWidth(AD1_WIDTH);
			node.setWidthB(AD1_WIDTH_B);
			nodes.add(node);
		}
		model.addAttribute("ad1", JsonUtils.objectToJson(nodes));
		return "index";
	}
}





















