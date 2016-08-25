package dao;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository("settingDAO")
public class SettingsDAOImpl implements SettingsDAO {

	@Autowired
	private SessionFactory sessionFactory;

	public int timeDiscount() {
		return getPropertyInteger("time_general_discount");
	}

	public String pathUploadPic() {
		return getPropertyString("path_upload_pic_product");
	}

	public String pathUploadAva() {
		return getPropertyString("path_upload_avatar");
	}

	public int timeDisactive() {
		return getPropertyInteger("time_disactive_discount");
	}

	public byte cashback(){
		return getPropertyByte("base_cashback");
	}

	public Long deliveredCompleteTime() { 
		return getPropertyLong("auto_delivered_complete"); }

	@Override
	public Double maxMonthProfit() {
		return getPropertyDouble("max_month_profit");
	}

	@Override
	public Double minMonthProfit() {
		return getPropertyDouble("min_month_profit");
	}


	private String getPropertyString(String name) {
		Query q = sessionFactory.getCurrentSession().createQuery("select value from Settings where name = '" + name + "'");
		return (String) q.uniqueResult();
	}

	private int getPropertyInteger(String name) {
		return Integer.parseInt(getPropertyString(name));
	}

	private byte getPropertyByte(String name)
	{
		return (byte)getPropertyInteger(name);
	}

	private Long getPropertyLong(String name) {
		return Long.parseLong(getPropertyString(name));
	}

	private Double getPropertyDouble (String name)
	{
		return Double.parseDouble(getPropertyString(name));
	}

}
