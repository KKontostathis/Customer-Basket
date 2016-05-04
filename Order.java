package basket;

public class Order {
	private Product product;
	private int amount;
	
	public Order(Product pr, int num){
		setProduct(pr);
		setAmount(num);
	}
	
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}	

}
