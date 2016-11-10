package converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import entity.Buyer;
import service.BuyerService;

@Component
public class BuyerConverter implements Converter<String, Buyer> {

	@Autowired
	private BuyerService serviceBuyer;
	
	@Override
	public Buyer convert(String arg0) {
		return serviceBuyer.get(Long.parseLong(arg0));
	}

}
