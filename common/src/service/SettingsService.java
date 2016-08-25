package service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import dao.SettingsDAO;

@Service
public class SettingsService {

	@Autowired
	@Qualifier("settingDAO")
	private SettingsDAO settingsDAO;

	@Transactional
	public String getPathUploadPicProduct() {
		return settingsDAO.pathUploadPic();
	}

	@Transactional
	public String getPathUploadAva() {
		return settingsDAO.pathUploadAva();
	}

	@Transactional
	public int getTimeGeneralDiscount() {
		return settingsDAO.timeDiscount();
	}

	@Transactional
	public int getTimeDisactiveDiscount() {
		return settingsDAO.timeDisactive();
	}

	@Transactional
	public byte getBaseCashback() {
		return settingsDAO.cashback();
	}

	@Transactional
	public Long deliveredCompleteTime() {
		return settingsDAO.deliveredCompleteTime();
	}

	@Transactional
	public Double maxMonthProfit() {
		return settingsDAO.maxMonthProfit();
	}

	@Transactional
	public Double minMonthProfit() {
		return settingsDAO.minMonthProfit();
	}

}
