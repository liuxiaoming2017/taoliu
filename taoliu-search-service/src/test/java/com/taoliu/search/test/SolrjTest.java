package com.taoliu.search.test;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;

public class SolrjTest {
	@Test
	public void add() throws Exception{
		SolrServer solrServer = new HttpSolrServer("http://127.0.0.1:8080/solr");
		SolrInputDocument document = new SolrInputDocument();
		document.addField("id", "test001");
		document.addField( "product_name", "这是一个测试");
		solrServer.add(document);
		solrServer.commit();
	}
	
	@Test
	public void testQuery() throws Exception{
		SolrServer solrServer = new HttpSolrServer("http://127.0.0.1:8080/solr");
		SolrQuery query = new SolrQuery();
		query.setQuery("product_name:家天下");
		query.addFilterQuery("product_price:[0 TO 4]");
		//query.set("家天下", "product_name");
		
		QueryResponse response = solrServer.query(query);
		SolrDocumentList results = response.getResults();
		System.out.println("查询的总记录数"+results.getNumFound());
		for (SolrDocument solrDocument : results) {
			System.out.println(solrDocument.get("id"));
			System.out.println(solrDocument.get("product_name"));
		}
		
		
	}
}






























