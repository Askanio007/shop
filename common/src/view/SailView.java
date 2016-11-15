package view;

import java.util.ArrayList;
import java.util.List;
import java.util.Collection;

import entity.Buyer;
import entity.Sail;
import entity.SoldProduct;

public class SailView {

	private Long id;
	private String date;
	private Integer amount;
	private Double totalsum;
	private Collection <SoldProduct> products;
	private Collection <Buyer> buyers;


	public SailView(Sail sail) {
		this.date = DateConverter.getFormatView().format(sail.getDate());
		this.id = sail.getId();
		this.amount = sail.getAmount();
		this.buyers = sail.getBuyers();
		this.products = sail.getProducts();
		this.totalsum = sail.getTotalsum();
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

	public Double getTotalsum() {
		return totalsum;
	}
	public Collection<SoldProduct> getProducts() {
		return products;
	}

	public Collection<Buyer> getBuyers() {
		return buyers;
	}
	
	public static List<SailView> convertSail(List<Sail> sails) {
		List<SailView> list = new ArrayList<>();
		for (Sail s : sails) {
			list.add(new SailView(s));
		}
		return list;
	}
}
