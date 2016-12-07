package view;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Collection;

import dto.SailDto;
import entity.Buyer;
import entity.Sail;
import entity.SoldProduct;

public class SailView {

	private Long id;
	private String date;
	private Integer amount;
	private BigDecimal totalSum;
	private String buyerName;


	public SailView(SailDto sail) {
		this.date = DateConverter.getFormatView().format(sail.getDate());
		this.id = sail.getId();
		this.amount = sail.getAmount();
		this.buyerName = sail.getBuyerName();
		this.totalSum = sail.getTotalsum();
	}

	public String getDateStr() {
		return date;
	}

	public Long getId() {
		return id;
	}

	public Integer getAmount() {
		return amount;
	}

	public BigDecimal getTotalSum() {
		return totalSum;
	}

	// TODO: Kirill и это ::: Убрал отсюда энтити
	public String getBuyerName() {
		return buyerName;
	}
	
	public static List<SailView> convertSail(List<SailDto> sails) {
		List<SailView> list = new ArrayList<>();
		for (SailDto s : sails) {
			list.add(new SailView(s));
		}
		return list;
	}
}
