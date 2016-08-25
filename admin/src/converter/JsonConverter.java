package converter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import entity.Product;
import models.ProductBasket;

@Component
public class JsonConverter {

	public String objectToJson(Object obj) throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		String jsonObject = mapper.writeValueAsString(obj);
		return jsonObject;
	}

	public List<String> listObjectToJson(List<ProductBasket> obj) throws JsonProcessingException {
		List<String> jsonProdList = new ArrayList<>();
		for (ProductBasket pojo : obj) {
			jsonProdList.add(objectToJson(pojo));
		}
		return jsonProdList;
	}

	public Object jsonToObject(String json) throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		Object obj = mapper.readValue(json, Product.class);
		return obj;
	}

}
