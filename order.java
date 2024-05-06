import java.util.Scanner;
import java.sql.*;
import java.sql.SQLException;

public class database{
	
	String vdbDriver = "jdbc:ucanaccess://";
	String vDatabase="U://Year One/Customer Database/OrdersDB.accdb";
	Connection vConnection = DriverManager.getConnection(vdbDriver + vDatabase);
	Statement vStatement = vConnection.createStatement();
	Scanner input = new Scanner(System.in);
	
	database() throws SQLException{
		menu();
	}
	
	
	void menu() throws SQLException{
		testCon();
		
		double op1;
		double op2;
		
		System.out.println("What would you like to do?");
		System.out.println("1 Get customer details?");
		System.out.println("2: Update customer?");
		System.out.println("3: Search order?");
		System.out.println("4: Update stock level?");
		System.out.println("5: search product code?");
		op1= input.nextDouble();
		//this part of code will display a list of tasks you can choose from 
		
		if (op1 == 1){
			System.out.println("Get customer record");
			getCustomer();
		}
		if (op1== 2){
			System.out.println("would you like to update surname (1) or credit limit (2)? ");
			op2= input.nextDouble();
			
				if (op2== 1){
					System.out.println("Update surname");
					upSurname();
				}
				if (op2==2){
					System.out.println("Update credit limit");
					upCreditLimit();
				}
		}
		if (op1== 3){
			System.out.println("Search order");
			searchOrder();
		}
		if (op1== 4){
			System.out.println("Update stock level");
			stockLvl();
		}
		if (op1== 5){
			System.out.println("Search product level");
			searchProduct();
		}
		//all my if statements are connected to methods that will run when chosen
	}	

	boolean testCon() { 
		try{	
			vConnection = DriverManager.getConnection(vdbDriver + vDatabase);
			System.out.println("connection successful");
			return true;
		//this try will display successful if the program connects to the database
		} catch (Exception x) {
			System.out.println("connection unsuccesful" + x.toString());
			return false;
		// if the connection is unsuccessful it will display this message and will also catch an exception
		}
	}
	
	void getCustomer() throws SQLException{
		int id;
		try{
		System.out.println("Please enter customer id");
		id=input.nextInt();
		//this will ask you to enter an id and then will run though the database to find it 
		
		String vSQL = "SELECT CUST_ID, CUST_FIRSTNAME, CUST_SURNAME, ADDRESS, PHONE_NUMBER, "
				+ "CREDIT_LIMIT  FROM CUSTOMER WHERE CUST_ID = "+id+";";	
		// this SQL code selects id, first name, surname, address,phone number and credit limit fields from the customer table where the id has been chosen.
		Statement vStatement = vConnection.createStatement();
		//makes a statement object to save in vStatement
		ResultSet vResults = vStatement. executeQuery(vSQL);
		// makes a result set and gives it the string SQL
		
	if(!vResults.next()){
		System.out.println("customer not found");
		//will display this message if a unknown customer is selected
	}
	else{
		System.out.println("Name: " + vResults.getString("CUST_FIRSTNAME") + " " + vResults.getString("CUST_SURNAME") + "\t" + "Address:" + vResults.getString("ADDRESS") +"\t" + "Phone Number:" + vResults.getString("PHONE_NUMBER") + "\t" +"Credit Limit:" + vResults.getString("CREDIT_LIMIT")+ "\t" );
		//this will display id, first name, surname, address,phone number and credit limit fields
	}
		}
		catch (Exception x){
			System.out.println("Query unsuccesful" + x.toString());
			//if the query is unsuccessful it will display this message and will also catch an exception
		}

	rtnMenu();
	
		
	}

	void displayCustomer(int vcustid) throws SQLException{
		
		try{
		String vSQL = "SELECT CUST_ID, CUST_FIRSTNAME, CUST_SURNAME, ADDRESS, PHONE_NUMBER, "
				+ "CREDIT_LIMIT  FROM CUSTOMER WHERE CUST_ID = "+vcustid+";";	
		// this SQL code selects customer fields from the customer table.
		Statement vStatement = vConnection.createStatement();
		//makes a statement object to save in vStatement
		ResultSet vResults = vStatement. executeQuery(vSQL);
		// makes a result set and gives it the string SQL
		
		if(!vResults.next()){
			System.out.println("customer not found");
			//will display this message if a unknown customer is selected
		}
		else{
			System.out.println("Name: " + vResults.getString("CUST_FIRSTNAME") + " " + vResults.getString("CUST_SURNAME") + "\t" + "Address:" + vResults.getString("ADDRESS") +"\t" + "Phone Number:" + vResults.getString("PHONE_NUMBER") + "\t" +"Credit Limit:" + vResults.getString("CREDIT_LIMIT")+ "\t" );
		}
		//this will display id, first name, surname, address,phone number and credit limit fields
		}
		catch (Exception x){
			System.out.println("Query unsuccesful" + x.toString());
		}
		//if the query is unsuccessful it will display this message and will also catch an exception

	}
	
	void upSurname() throws SQLException {
		String surname;
		int id;
		
		try{
		System.out.println("Please enter id of the surname you want to change");
		id = input.nextInt();
		displayCustomer(id);
		//this will display the  id, first name, surname, address,phone number and credit limit fields that you have chosen 
		
		System.out.println("Please enter new Surname");
		surname = input.next();
		//this will ask you enter new surname which you can enter 
		
		String vSQL ="UPDATE CUSTOMER SET CUST_SURNAME = '"+ surname +"' WHERE cust_id = "+id;
		Statement vStatement = vConnection.createStatement();
		vStatement.executeUpdate(vSQL);
		//this will run the sql code 
		
		displayCustomer(id);
		//displays the customer will the updated surname 
		}
		catch (Exception x){
			System.out.println("Query unsuccesful" + x.toString());
			//if the query is unsuccessful it will display this message and will also catch an exception

		}
		rtnMenu();
		//this will display my return menu method 
	}		
			
	void upCreditLimit () throws SQLException{
		double credit;
		int id;
		
		try{
		System.out.println("Please enter id of the credit limit you want to change");
		id = input.nextInt();
		displayCustomer(id);
		//this will display the  id, first name, surname, address,phone number and credit limit fields that you have chosen 
		
		System.out.println("Please enter new credit limit Between 0-5000");
		credit = input.nextDouble();
		//will ask you to enter a number 
		
		while(credit<0||credit>5000){
		     System.out.println("You Entered the wrong number , Enter The number again (between 0-5000):");
		     credit = input.nextDouble();
		}
		//this will check that you have entered a number between 0-5000 other wise will ask you to enter it again.
		
		String vSQL ="UPDATE CUSTOMER SET CREDIT_LIMIT = "+ credit +" WHERE cust_id = "+id;
		Statement vStatement = vConnection.createStatement();
		vStatement.executeUpdate(vSQL);
		//this will run the sql code 
		
		displayCustomer(id);
		//displays the customer will the updated credit limit
		}
		catch (Exception x){
			System.out.println("Query unsuccesful" + x.toString());
			//if the query is unsuccessful it will display this message and will also catch an exception

		}
		rtnMenu();
		//this will display my return menu method 
	}
	
	void searchOrder () throws SQLException{
		int orderid;
		boolean vFound;
		ResultSet vResults;
		
		try{
		System.out.println("Please enter the Order ID");
		orderid =input.nextInt();
		//this will ask you to enter the id you want displayed 
		
		String vSQL = "SELECT LINE_ORDERID, LINE_ID, ORDER_PLACED, LINE_QUANTITY, PRODUCT FROM ORDER_LINE JOIN ORDER_HEADER ON (LINE_ORDERID = ORDERID) JOIN PRODUCTS ON (LINE_PRODUCT = PRODUCTID) WHERE LINW_ORDERID = "+orderid+";"; 
		vResults = vStatement.executeQuery(vSQL);
		vFound = vResults.next();
		//will run the sql code
		
		if (vFound){
			do{
				System.out.println(" Order id : " + vResults.getString("LINE_ORDERID") + "\t" + "Order Placed:"+ vResults.getString("ORDER_PLACED")+ "   " + "Product Name:" + vResults.getString("PRODUCT"));
			//if a result is found it will display the line order id, when the order was placed and the product name 
				
			}while (vResults.next());
		}
		else{
			System.out.println("no record found for that order id");
			//if the id is invalid it will show this error message
		}
		}
		catch (Exception x){
			System.out.println("Query unsuccesful" + x.toString());
		}
		//if the query is unsuccessful it will display this message and will also catch an exception

		
		rtnMenu();
		//this will display my return menu method 
	}
	
	void getStock (int vStockId) throws SQLException{
		try {
		String vSQL = "SELECT STOCKID, STOCKITEM, STOCKLEVEL FROM PRODUCT_STOCK WHERE STOCKID = "+vStockId+";";	
		// this SQL code selects stock fields from the product stock table.
		Statement vStatement = vConnection.createStatement();
		//makes a statement object to save in vStatement
		ResultSet vResults = vStatement. executeQuery(vSQL);
		// makes a result set and gives it the string SQL
		
		if(!vResults.next()){
			System.out.println("customer not found");
			//if invalid will show this message
		}
		else{
			System.out.println("Stock ID:" + vResults.getString("STOCKID") + "\t" + "Stock Item:" + vResults.getString("STOCKITEM") + "\t" +"Stock Level: "+ vResults.getString("STOCKLEVEL"));
			//if found it will display the stock id, stock item and stock level
		}
		}
		catch (Exception x){
			System.out.println("Query unsuccesful" + x.toString());
			//if the query is unsuccessful it will display this message and will also catch an exception
		}
		
	}
	
	void stockLvl () throws SQLException{
		int stock;
		int vStockId;
		
		try {
		System.out.println("Please enter stockid of the quantity you want to update?");
		vStockId = input.nextInt();
		//this will ask you to enter the id you want displayed 
		
		getStock(vStockId);
		//this will run my getStock method and display  the stock id, stock item and stock level
		
		System.out.println("please enter the new stocklevel");
		stock = input.nextInt();
		//asks you what you want to change the stock level too 
		
		String vSQL ="UPDATE PRODUCT_STOCK SET STOCKLEVEL =  STOCKLEVEL + "+ stock +" WHERE STOCKID = "+vStockId;
		Statement vStatement = vConnection.createStatement();
		vStatement.executeUpdate(vSQL);
		//runs the update sql code 
		
		System.out.println("Your new stock level is");
		getStock(vStockId);
		//will run my getStock method again and display the updated stock level 
	}
		
		catch (Exception x){
		System.out.println("Query unsuccesful" + x.toString());
		//if the query is unsuccessful it will display this message and will also catch an exception
				
	}
		
		rtnMenu();
		//this will display my return menu method 
	}

	void searchProduct () throws SQLException{
		int prodid;
		ResultSet vResults;
		boolean vFound ;
		
		try{
		System.out.println("Please enter the Order ID");
		prodid =input.nextInt();
		//this will ask you to enter the id you want displayed
		
		String vSQL = "SELECT STOCKID, STOCKITEM, STOCKLEVEL, PRODUCT FROM PRODUCT_STOCK, PRODUCTS JOIN PRODUCTS ON (PRODUCTID = STOCKID ) WHERE PRODUCTID = "+prodid; 
		vResults = vStatement.executeQuery(vSQL);
		vFound = vResults.next();
		//runs the sql code
		
		if (vFound){
			System.out.println("Product id : " + vResults.getString("STOCKID") + "\t" + " Stock level: " + vResults.getString("STOCKLEVEL") + "   " + "Product= " + vResults.getString("PRODUCT"));
		//if the id is found will display the stock id, stock level and the product
		}
		else{
			System.out.println("no record found for that order id");
			//will display this if the id is invalid
		}
		}
		catch (Exception x){
			System.out.println("Query unsuccesful" + x.toString());
			//if the query is unsuccessful it will display this message and will also catch an exception
			
		}
		
		rtnMenu();	
		//this will display my return menu method 
	}
	void rtnMenu () throws SQLException{
		int rtnmenu; 
		System.out.println("would you like to do another task");
		System.out.println("1 = yes");
		System.out.println("2 = exit");
		rtnmenu = input.nextInt();
		//this will display a message if you want to do another task 
		
		if(rtnmenu==1){
		//you enter 1 to go back to menu 
		menu();
		
		}
		if(rtnmenu == 2){
		System.exit(0);
		//you enter 2 to terminate program
		}
	}
	
	}


	
