package models;

import java.util.HashMap;
import java.util.Map;

public class FilterTotalSoldProduct {
	
	private long id;
	
	private String name;

	private Map<String, Object> params = new HashMap<>();

	public long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public boolean haveParam()
	{
		return !params.isEmpty();
	}

	public void clear()
	{
		params.clear();
	}

	public Map<String, Object> getParams() {
		return params;
	}

	public FilterTotalSoldProduct() {

	}

	public void setId(Long id) {
		this.id = id;
		if (id != null )
			params.put("id", id);
	}

	public void setName(String name) {
		this.name = name;
		if (!"".equals(name))
			params.put("product.name", name);
	}
}

