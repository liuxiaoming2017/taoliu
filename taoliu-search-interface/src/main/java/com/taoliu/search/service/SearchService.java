package com.taoliu.search.service;



import com.taoliu.common.pojo.SearchResult;
import com.taoliu.common.pojo.TaotaoResult;

public interface SearchService {
	public TaotaoResult importAllSearchItems() throws Exception;
	
	public SearchResult search(String queryString,Integer page,Integer rows) throws Exception;
}
