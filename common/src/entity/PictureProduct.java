package entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "picture_product")
public class PictureProduct {

	@Id
	@Column(name = "pic_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "path")
	private String path;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "product_id", nullable = false)
	private Product prod;

	public PictureProduct() {
	}

	public PictureProduct(String path) {
		this.path = path;
	}

	public PictureProduct(long id, String path) {
		this.id = id;
		this.path = path;
	}
	
	public void setPicId(Long id)
	{
		this.id = id;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public void setProd(Product product) {
		this.prod = product;
	}

	public Long getPicId() {
		return id;
	}

	public String getPath() {
		return path;
	}

	public Product getProd() {
		return prod;
	}

}
