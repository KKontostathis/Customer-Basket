package basket;

import java.math.BigDecimal;

public class Product {
	
	private String name;
	private BigDecimal price;
	
	public Product(String productName, BigDecimal productPrice){
		setName(productName);
		setPrice(productPrice);
	}

	/*
	boolean equals (Product pr) {
        if (pr.getName().equalsIgnoreCase(this.getName()) &&  pr.getPrice() == this.getPrice()) 
        	return true;
        else
        	return false;
    }*/

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	
}
