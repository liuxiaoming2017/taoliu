package com.taoliu.search.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.taoliu.common.pojo.SearchItem;
import com.taoliu.common.pojo.SearchResult;

@Repository
public class SearchDao {
	@Autowired
	private SolrServer solrServer;
	
	public SearchResult search(SolrQuery query) throws Exception{
		SearchResult searchResult = new SearchResult();
		QueryResponse response = solrServer.query(query);
		SolrDocumentList results = response.getResults();
		searchResult.setRecordCount(results.getNumFound());
		ArrayList<SearchItem> list = new ArrayList<>();
		Map<String, Map<String, List<String>>> highlighting = response.getHighlighting();
		for (SolrDocument solrDocument : results) {
			SearchItem item = new SearchItem();
			item.setCategory_name(solrDocument.get("item_category_name").toString());
			item.setId(Long.parseLong(solrDocument.get("id").toString()));
			item.setImage(solrDocument.get("item_image").toString());
			//item.setItem_desc(item_desc);
			item.setPrice((Long)solrDocument.get("item_price"));
			item.setSell_point(solrDocument.get("item_sell_point").toString());
			
			List<String> list2 = highlighting.get(solrDocument.get("id")).get("item_title");
			String gaoliangstr = "";
			if(list2!=null && list2.size()>0) {
				gaoliangstr = list2.get(0);
			}else {
				gaoliangstr = solrDocument.get("item_title").toString();
			}
			item.setTitle(gaoliangstr);
			list.add(item);
		}
		searchResult.setItemList(list);
		return searchResult;
	}
}























