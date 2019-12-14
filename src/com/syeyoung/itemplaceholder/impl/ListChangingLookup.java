package com.syeyoung.itemplaceholder.impl;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang.text.StrLookup;

public class ListChangingLookup extends StrLookup{
	private Map<String,Object> placeholders;
	public ListChangingLookup(Map<String,Object> placeholders) {
		this.placeholders = placeholders;
	}
	
	public String lookup(String arg0) {
		Object result = placeholders.get(arg0);
		if (result == null) return null;
		if (result instanceof List) {
			List<Object> listresult = (List<Object>) result;
			return String.join("\n", listresult.stream().map(a -> a.toString()).collect(Collectors.toList()).toArray(new String[0]));
		}
		return result.toString();
	}
}
