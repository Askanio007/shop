package dao;

public interface SettingsDAO {
	
	int timeDiscount();
	
	String pathUploadPic();
	
	String pathUploadAva();
	
	int timeDisactive();

	byte cashback();

	Long deliveredCompleteTime();

	Double maxMonthProfit();

	Double minMonthProfit();
	

}
