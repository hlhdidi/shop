package com.hlhdidi.shop.solr.config;

import java.net.MalformedURLException;

import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SolrConfig {
	@Bean
	public HttpSolrClient solr() throws MalformedURLException {
		HttpSolrClient server=new HttpSolrClient("http://192.168.200.130:8080/solr");
		return server;
	}
}
