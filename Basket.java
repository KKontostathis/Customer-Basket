package basket;
import java.math.BigDecimal;
import java.util.*;

public class Basket {
	
	static ArrayList<Order> orderList = new ArrayList<Order>();
	final static BigDecimal butterPrice = BigDecimal.valueOf(0.80);
	final static BigDecimal milkPrice = BigDecimal.valueOf(1.15);
	final static BigDecimal breadPrice = BigDecimal.valueOf(1.00);
	
	public static void main(String[] args) {
				
		BigDecimal totalCost = BigDecimal.valueOf(0.0);
		
		
		System.out.println("Welcome to our e-market!");
		System.out.println("The available products are bread, milk and butter");
		System.out.println("Please enter the name and the amount of the desired product (e.g. milk 2)");
		System.out.println("When you are done shopping, please enter 'done'");
		
		Scanner sc = new Scanner(System.in);
		String[] order = sc.nextLine().split(" ");
		
		//Get the order!
		while(!order[0].equalsIgnoreCase("done")){
			String prName = order[0];
			int num = Integer.parseInt(order[1]);
			
			if(isAvailable(prName))
				addProduct(prName, num);
			
			order = sc.nextLine().split(" ");			
		}
		
		//Return the total cost without any discounts
		for(Order ord : orderList){
			BigDecimal amount = BigDecimal.valueOf(ord.getAmount());
			totalCost = totalCost.add(ord.getProduct().getPrice().multiply(amount));
		}
		
		//Return the lower price due to the first offer!
		if( contains("butter") >= 2 && contains("bread") >= 1)
			totalCost = totalCost.subtract(breadPrice.divide(BigDecimal.valueOf(2))); 
			
		//Return the lower price due to the second offer!
		if(contains("milk") >= 4){
			BigDecimal discount = BigDecimal.valueOf(contains("milk")).divideToIntegralValue(BigDecimal.valueOf(4));
			totalCost = totalCost.subtract(discount.multiply(milkPrice)); 		
		}
			
		System.out.println("Total cost of your customer basket is " + totalCost);
		sc.close();
	}
	
	public static boolean isAvailable(String productName){
		
		if(productName.equalsIgnoreCase("butter") || productName.equalsIgnoreCase("milk") || productName.equalsIgnoreCase("bread"))
			return true;
		else{
			System.out.println("This product is not available");
			return false;
		}
		
	}
	
	/**
	 * Adds one of the available products to the orderlist upon request
	 * @param name
	 * @param amount
	 */
	public static void addProduct(String name, int amount){
		if(name.equalsIgnoreCase("butter"))
			orderList.add(new Order(new Product("butter", butterPrice), amount));
		else if(name.equalsIgnoreCase("milk"))
			orderList.add(new Order(new Product("milk", milkPrice), amount));
		else if(name.equalsIgnoreCase("bread"))
			orderList.add(new Order(new Product("bread", breadPrice), amount));
		else
			System.out.println("This product is not available");
	}
	
	/**
	 * Checks whether the given product exists in the orderlist
	 * @param product name
	 * @return amount of the requested product, otherwise -1
	 */
	static int contains(String prName) {
		for(Order ord : orderList)
			if (ord.getProduct().getName().equalsIgnoreCase(prName)) 
				return ord.getAmount();
        
		return -1;
    }

}
