package basket;
import java.math.BigDecimal;
import java.util.*;

public class Basket {

	//orderList is practically our "basket" containing amount and type of the wishing items
	static ArrayList<Order> orderList = new ArrayList<Order>();
	
	//predefined prices for the available products
	static final BigDecimal butterPrice = BigDecimal.valueOf(0.80);
	static final BigDecimal milkPrice = BigDecimal.valueOf(1.15);
	static final BigDecimal breadPrice = BigDecimal.valueOf(1.00);
	static Scanner sc = new Scanner(System.in);

	public static void main(String[] args) {

		int choice = 0;

		System.out.println("Welcome to our e-market!");		

		do{
			System.out.println("To make a new order, enter '1'");
			System.out.println("To see all our available products, enter '2'");
			System.out.println("To see our amazing offers, enter '3'");
			System.out.println("To exit the program, please enter '0'");

			System.out.print("Choice: ");	
			try{
				choice = Integer.parseInt(sc.nextLine());
			}
			catch(Exception e){
				System.out.println("The input must be an integer between 0 and 3 as indicated above");
				System.out.println("Please run the program again");
				System.exit(-1);
			}
			switch(choice){
			case 1:
				makeOrder();
				break;				
			case 2:
				System.out.println("The available products are:");
				System.out.println("Butter £0.80");
				System.out.println("Milk £1.15");
				System.out.println("Bread £1.00");
				System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
				break;					
			case 3:
				System.out.println("1) Buy 2 Butter and get a Bread a 50% off!");
				System.out.println("2) Buy 3 Milk and get the 4th Milk for free!");
				System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");					
				break;					
			case 0:
				System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
				System.out.println("Thank you for your interest in our products! Have a nice day!");
				System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");					
				break;					
			default:
				System.out.println("This is not a valid choice, please enter 0, 1, 2 or 3 to proceed");
				System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");					
				break;
			}


		}while(choice != 0);
		sc.close();		
	}

	/**
	 * Checks whether the given product is available
	 * @param productName
	 * @return true if it is, false otherwise
	 */
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
		Product product = new Product();

		if(name.equalsIgnoreCase("butter"))
			product = new Product("butter", butterPrice);
		else if(name.equalsIgnoreCase("milk"))
			product = new Product("milk", milkPrice);
		else if(name.equalsIgnoreCase("bread"))
			product = new Product("bread", breadPrice);
		else{
			System.out.println("This product is not available");
			return;
		}
		//If we haven't already added the product in the basket
		if(contains(name) == -1)			
			orderList.add(new Order(product, amount));
		//else increase the amount of the already existing product
		else{
			for(Order ord : orderList){
				if(ord.getProduct().getName().equals(product.getName())){
					ord.setAmount(ord.getAmount() + amount);
				}
			}
		}

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

	/**
	 * Used to order new products
	 */
	static void makeOrder(){
		BigDecimal totalCost = BigDecimal.valueOf(0.0);
		int num;
		String prName;

		System.out.println("Please enter the amount and the name of the desired product (e.g. 2 milk)");
		System.out.println("To complete this order, please enter '0'");

		try{
			String[] order = sc.nextLine().split(" ");

			//Get the order!
			while(!order[0].equals("0")){
				num = Integer.parseInt(order[0]);
				prName = order[1];

				if(isAvailable(prName))
					addProduct(prName, num);

				order = sc.nextLine().split(" ");			
			}
		}
		catch(Exception e){
			System.out.println("An input like 'amount product' or '0' was expected!");
		}
		//Calculate the total cost without any discounts
		for(Order ord : orderList){
			BigDecimal amount = BigDecimal.valueOf(ord.getAmount());
			totalCost = totalCost.add(ord.getProduct().getPrice().multiply(amount));
		}

		//Calculate a lower price due to the first offer if applicable!
		if( contains("butter") >= 2 && contains("bread") >= 1)
			totalCost = totalCost.subtract(breadPrice.divide(BigDecimal.valueOf(2))); 

		//Calculate a lower price due to the second offer if applicable!
		if(contains("milk") >= 4){
			BigDecimal discount = BigDecimal.valueOf(contains("milk")).divideToIntegralValue(BigDecimal.valueOf(4));
			totalCost = totalCost.subtract(discount.multiply(milkPrice)); 		
		}

		totalCost = totalCost.setScale(2);
		System.out.println("Total cost of your customer basket is £" + totalCost);
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		orderList.clear();
		totalCost = BigDecimal.valueOf(0.0);		
	}
}
