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
		int num, choice;
		String prName;
		Scanner sc;
		sc = new Scanner(System.in);
		
		//Welcome Screen!!!
		System.out.println("Welcome to our e-market!");		
		
		do{
			System.out.println("To make a new order, enter '1'");
			System.out.println("To see all the available products, enter '2'");
			System.out.println("To see our amazing offers, enter '3'");
			System.out.println("To exit the program, please enter '0'");

			System.out.print("Choice: ");
			//if(sc1.hasNextLine())
			
			choice = Integer.parseInt(sc.nextLine());
			
			switch(choice){
				case 1:
					System.out.println("Please enter the amount and the name of the desired product (e.g. 2 milk)");
					System.out.println("To complete this order, please enter '0'");

					//sc2 = new Scanner(System.in);
					String[] order = sc.nextLine().split(" ");

					//Get the order!
					while(!order[0].equals("0")){
						num = Integer.parseInt(order[0]);
						prName = order[1];

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

					System.out.println("Total cost of your customer basket is £" + totalCost);
					System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
					orderList.clear();
					totalCost = BigDecimal.valueOf(0.0);
					break;				
				case 2:
					System.out.println("The available products are bread, milk and butter");
					System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
					break;					
				case 3:
					System.out.println("1) Buy 2 Butter and get a Berad a 50% off!");
					System.out.println("2) Buy 3 Milk and get the 4th milk for free!");
					System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");					
					break;					
				case 0:
					System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
					System.out.println("Thank you for your interest in our products! Have a nice day!");
					System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");					
					break;
					//System.exit(0);					
				default:
					System.out.println("This is not a valid choice, please enter 0, 1, 2 or 3 to proceed");
					System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");					
					break;
			}
			
		}while(choice != 0);
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
	
	static void makeOrder(){
		
	}

}
