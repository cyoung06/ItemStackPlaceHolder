package com.syeyoung.itemplaceholder.api;

import java.util.List;
import java.util.Map;

public interface PlaceHolderable {
	public void setPlaceHolder(String name, Object value);
	
	public Object getPlaceHolder(String name);
	public String getPlaceHolderString(String name);
	public Integer getPlaceHolderInteger(String name);
	public Long getPlaceHolderLong(String name);
	public Double getPlaceHolderDouble(String name);
	public Float getPlaceHolderFloat(String name);
	public Character getPlaceHolderCharacter(String name);
	public Boolean getPlaceHolderBoolean(String name);

	public List<?> getPlaceHolderList(String name);
	public List<String> getPlaceHolderStringList(String name);
	
	public Map<String, Object> getPlaceHolders();
}
