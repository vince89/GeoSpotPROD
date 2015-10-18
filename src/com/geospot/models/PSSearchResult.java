package com.geospot.models;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.context.annotation.ScopedProxyMode;

@Component
@Scope(value="session", proxyMode=ScopedProxyMode.TARGET_CLASS)
public class PSSearchResult {
	private Map<String, Float> categoryHitCount = new HashMap<String, Float>();
	Map<String, Integer> prodCodeHitCount = new HashMap<String, Integer>();
	private HashMap<String, List<ResultDocument>> resultDocuments = new HashMap<String, List<ResultDocument>>();
}
