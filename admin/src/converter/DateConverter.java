package converter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class DateConverter implements Converter<String, Date> {

	//todo Kirill wtf?
	// todo C view сюда приходит дата строкой в формате "yyyy-MM-ddTHH:mm", поэтому и режется строка по символу Т, а потом конверируется в Date
	@Override
	public Date convert(String arg0) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		String[] a = arg0.trim().split("T");
		String dateStr = a[0] + " " + a[1];
		try {
			Date date = format.parse(dateStr);
			return date;
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}

	}

}
