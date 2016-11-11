package models;

import java.util.HashMap;
import java.util.Map;

public class FilterTotalSoldProduct {
	
	private Long id;
	
	private String name;

	private Map<String, Object> params = new HashMap<>();



	public Long getId() {
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

	public void setId(Long id) {
		this.id = id;
		if (id != null)
			params.put("id", id);
	}

	public void setName(String name) {
		this.name = name;
		if (name != "")
			params.put("product.name", name);
	}
}

