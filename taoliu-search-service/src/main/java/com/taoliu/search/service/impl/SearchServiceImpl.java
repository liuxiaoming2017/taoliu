package com.taoliu.search.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taoliu.common.pojo.SearchItem;
import com.taoliu.common.pojo.SearchResult;
import com.taoliu.common.pojo.TaotaoResult;
import com.taoliu.search.dao.SearchDao;
import com.taoliu.search.mapper.SearchItemMapper;
import com.taoliu.search.service.SearchService;

@Service
public class SearchServiceImpl implements SearchService {

	@Autowired
	private SearchItemMapper mapper;
	
	@Autowired
	private SolrServer solrServer;
	
	@Autowired
	private SearchDao searchDao;
	
	@Override
	public TaotaoResult importAllSearchItems() throws Exception {
		List<SearchItem> searchItems = mapper.getSearchItemList();
		for (SearchItem searchItem : searchItems) {
			SolrInputDocument document = new SolrInputDocument();
			document.addField("id", searchItem.getId().toString());
			document.addField("item_title", searchItem.getTitle());
			document.addField("item_sell_point", searchItem.getSell_point());
			document.addField("item_price", searchItem.getPrice());
			document.addField("item_image", searchItem.getImage());
			document.addField("item_category_name", searchItem.getCategory_name());
			document.addField("item_desc", searchItem.getItem_desc());
			solrServer.add(document);
		}
		solrServer.commit();
		return TaotaoResult.ok();
	}

	@Override
	public SearchResult search(String queryString, Integer page, Integer rows) throws Exception {
		SolrQuery query = new SolrQuery();
		if(StringUtils.isNotBlank(queryString)) {
			query.setQuery(queryString);
		}else {
			query.setQuery("*:*");
		}
		if(page==null) {
			page = 1;
		}
		if(rows==null) {
			rows=60;
		}
		query.setStart((page-1)*rows);
		query.setRows(rows);
		query.set("df", "item_keywords");
		query.setHighlight(true);
		query.setHighlightSimplePre("<em style=\"color:red\">");
		query.setHighlightSimplePost("</em>");
		query.addHighlightField("item_title");
		
		SearchResult search = searchDao.search(query);
		long pageCount = 0l;
		pageCount = search.getRecordCount()/rows;
		if(search.getRecordCount()%rows>0) {
			pageCount++;
		}
		search.setPageCount(pageCount);
		return search;
	}

}
























