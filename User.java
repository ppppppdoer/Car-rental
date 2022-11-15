import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import DATA_STRUCTURE.GenericMyHashMap;

public class User  implements Comparable<User>{
	
	GenericMyHashMap<String,User> userList = new GenericMyHashMap<>();
		
	public String username;
	private String lastName;
	private String firstName;
	private String phoneNumber;
	private String Address;
	private String email;
	private String password;
	private static String AuthenCode;
	private String token = "0";
	private String vehicleID;

	User(String username,String password,String firstName,String lastName,String phonenumber,String Address,String email,String token,String vehicleID)
	{
		this.username = username;
		this.lastName = lastName;
		this.firstName = firstName;
		this.phoneNumber = phonenumber;
		this.Address = Address;
		this.email = email;
		this.password = password;
		this.AuthenCode = AuthenCode;
		this.token = token;
		this.vehicleID = vehicleID;
	}
	
	public static void register(GenericMyHashMap<String,User> u) {
		int total = u.size();
		System.out.println("Welcome to EasyCars! Let's do the registration now!\nYour username: ");
		Scanner userID = new Scanner(System.in);
		String username = userID.nextLine();
		System.out.println("Your password: ");
		Scanner userPassword = new Scanner(System.in);
		String password = userPassword.nextLine();
		System.out.println("First Name: ");
		Scanner userFname = new Scanner(System.in);
		String firstName = userFname.nextLine();
		System.out.println("Last Name: ");
		Scanner userLname = new Scanner(System.in);
		String lastName = userLname.nextLine();
		System.out.println("Phone number: ");
		Scanner userPhoneNum = new Scanner(System.in);
		String phoneNumber = userPhoneNum.nextLine();
		System.out.println("Your address: ");
		Scanner userAddress = new Scanner(System.in);
		String Address = userAddress.nextLine();
		System.out.println("E-mail address: ");
		Scanner userEmail = new Scanner(System.in);
		String email = userFname.nextLine();
		System.out.println("If you have a token, you may enter it now, otherwise continue");
		Scanner userToken = new Scanner(System.in);
		String token = userToken.nextLine();
		if(token.contentEquals("group6admin")) {
			token = "1";
		} else {
			token = "0";
		}
		
		User temp = new User(username,password,firstName,lastName,phoneNumber,Address,email,token,"0");
		u.put(username,temp);
		//System.out.println(u.size());  //used for simple debugging
		if(u.size()>total) {
			System.out.println("User added successfully");
			appendText("users.txt",(username+","+password+","+firstName+","+lastName+","+phoneNumber+","+Address+","+email+","+token+","+"0\n"));
			
		}else {
			System.out.println("Failed to add User to database.");
		}
	}
	
	public static User login(GenericMyHashMap<String,User> u) {
		System.out.println("Username: ");
		Scanner userID = new Scanner(System.in);
		String username = userID.nextLine();
		System.out.println("Password: ");
		Scanner userPW = new Scanner(System.in);
		String password = userPW.nextLine();
		System.out.println("Authenticator code(six digit): \n");
		System.out.println(getRN());
		
		Scanner userAC = new Scanner(System.in);
		String AuthenCode = userAC.nextLine();
		
		User temp = new User(username);
		if(u.containsKey(temp.getID()) != true) {
			System.out.println("Incorrect Username");
			return temp;
		} else if (u.containsKey(temp.getID()) == true) {
			if(u.get(username).getPw().contains(password) && u.get(username).getRN().contains(AuthenCode)) {
				System.out.println("Successful Login");
				temp = u.get(username);
				System.out.println(u.get(username).toString());
			} else if(u.get(username).getPw() != password) {
				System.out.println("Incorrect Password");
			}
			temp.toString();
			return temp;
			}
		return temp;
		
		
	}
	
	private static Object println(String string) {
		// TODO Auto-generated method stub
		return null;
	}

	public static void navigate(GenericMyHashMap<String,Vehicle_group6> c,GenericMyHashMap<String,User> u, User client) {
		if(client.checkToken().contains("1")) {
			menuAdmin(c,u);
		} else {
			menuBasic(c, u, client);
		}
	}
	
	public static User open(GenericMyHashMap<String,User> u) {
		boolean active = true;
		boolean vlogin = true;
		User temp = new User(null);
		System.out.println("-------------------Rent-N-Go--------------------");
		System.out.println("How may we help you today?\nPlease select one of the following options:\n[1] Login\n[2] Register\n[9] Exit");
		Scanner userInput = new Scanner(System.in);
		int input = userInput.nextInt();
		
		
		while(active == true) {
		switch(input)
		{
		case 1:
		{
			temp = login(u);
			while(vlogin == true) {
			if(u.containsValue(temp) != true)
			{
				System.out.println("Would you like to try again?\n[1] Yes\n[2] Move to Registering an account");
				Scanner choice = new Scanner(System.in);
				int uChoice = choice.nextInt();
				switch(uChoice)
				{
				case 1:
					{
						temp = login(u);
						continue;
					}
				case 2:
					{
						vlogin = false;
						active = false;
						register(u);
						break;
					}
				}break;
			}
			active = false;
			vlogin = false;
			break;
		}break;
		}
		case 2:
		{
			register(u);
			active = false;
		}
		case 9:
			active = false;
			System.out.println("-------------------Rent-N-Go--------------------");
		}
		}
		return temp;
		
	}
	
	public static void menuBasic(GenericMyHashMap<String,Vehicle_group6> c,GenericMyHashMap<String,User> u, User client) 
	{
		boolean menuOpen = true;
		while(menuOpen == true) {
		int total = c.size();
		System.out.println("\nRent-N-GO :: DBManager.java\nSELECT A TASK - R\n--------------------------------------\n1.Show Available Vehicles\n2.Show my Rented Vehicle\n3.Search for a Vehicle\n4.Cancel my registration\n5.Update My Information\n\n0.Exit");
		Scanner menuSelect = new Scanner(System.in);
		int menuChoice = menuSelect.nextInt();
		switch(menuChoice) 
		{
		case 1:
		{
		
			ArrayList<Vehicle_group6> available = new ArrayList<Vehicle_group6>();
			System.out.println(c.size());
			int j = 1;
			int i = 0;
			while(i<c.size()) {
				String k;
			if(c.get(k = Integer.toString(j)).getRenter() == "0") {
				Vehicle_group6 temp = c.get(k);
				available.add(temp);
				 j++;
				 i++;
			} else {
				j++;
				i++;
			} continue;
			}			System.out.println(available.toString());
			break;

		}
		case 2:
		{
			if(client.vehicleID.contains("0")) {
			System.out.println("No Vehicle is currently Rented.");
			break;
			} else {
				System.out.println(c.get(client.getVehicleID()).toString());
				break;
			}
			//replaceSelected(client.userFormat(), "1", "0");
			
		}
		case 3:
		{
			Scanner makeFinder = new Scanner(System.in);
            Scanner colorFinder = new Scanner(System.in);
            Scanner vehicleIDFinder = new Scanner(System.in);
            
            System.out.println("Please enter the make of the vehicle: ");
            String make = makeFinder.nextLine();
            makeFinder.nextLine(); //This is here because it likes to skip over things.
            System.out.println("Please enter the color of the vehicle: ");
            String color = colorFinder.nextLine();
            
           Vehicle_group6 temp = new Vehicle_group6("1");
            ArrayList<String> vehicleMatchMake = new ArrayList<String>();
            ArrayList<String> vehicleMatchColor = new ArrayList<String>();
            
            ArrayList<Vehicle_group6> available = new ArrayList<Vehicle_group6>();
            //System.out.println(c.size());
            int j = 1;
            int i = 0;
            while(i<c.size()) {
                String k;
                if((c.get(k = Integer.toString(j)).getMake()).equals(make) && (c.get(k = Integer.toString(j)).getColor()).equals(color) ) { //c.get(k = Integer.toString(j)).getMake() == make
                    //Vehicle_group6 temp = c.get(k);
                    temp = c.get(k);
                    available.add(temp);
                    j++;
                    i++;
                } else {
                    j++;
                    i++;
                }
            }
            System.out.println("Printing list of available vehicles: ");
            System.out.println(available.toString());
            System.out.println();
            System.out.println("Input the Vehicle ID # of the vehicle you would like to rent: ");
            String choice = vehicleIDFinder.nextLine();
            //System.out.println(c.get(client.vehicleID));
            client.vehicleID = choice;
            
            replaceSelected(client.userFormat(),choice,"0");
            //System.out.println(client.vehicleID);
            
            temp.setRenter(client);
            break;
			}
			
		case 4:
		{
			
			if(client.vehicleID.contains("0")) {
				System.out.println("No Vehicle is currently Rented.");
				break;
				} else {
					System.out.println(c.get(client.getVehicleID()).toString());
					client.setVehicle("0");
					replaceSelected(client.userFormat(), "0", client.getVehicleID());
					break;
				}
		}
		case 5:{
			boolean updater = true;
			System.out.println("Please enter the Username to update.");
			Scanner searchID = new Scanner(System.in);
			String searchUid = searchID.nextLine();
			User temp = new User(searchUid);
			if(u.containsKey(temp.getID()) == true){
				temp = u.get(temp.getID());
			} else {
				System.out.println("Client not found.");
				break;
			}
				while(updater == true) 
				{
					System.out.println("1. Last name\n2. First name\n3. Phone\n4. Address\n0. DONE.");
					Scanner choice = new Scanner(System.in);
					int userChoice = choice.nextInt();
					switch(userChoice) 
					{
					case 1:
					{
						System.out.println("Please enter the last name to update.");
						Scanner lNupdate = new Scanner(System.in);
						String lName = lNupdate.nextLine();
						temp.setLN(lName);
						break;
					}
					case 2:
					{
						System.out.println("Please enter the first name to update.");
						Scanner fNupdate = new Scanner(System.in);
						String fName = fNupdate.nextLine();
						temp.setFN(fName);
						break;
					}
					case 3:
					{
						System.out.println("Please enter the phone to update.");
						Scanner pNupdate = new Scanner(System.in);
						String phone = pNupdate.nextLine();
						temp.setPhoneNumber(phone);
						break;
					}
					case 4:
					{
						System.out.println("Please enter the address to update.");
						Scanner aUpdate = new Scanner(System.in);
						String address = aUpdate.nextLine();
						temp.setAddress(address);
						break;
					}
					case 0:
						User tempUpdate = temp;
						u.remove(temp.getID());
						u.put(tempUpdate.getID(),tempUpdate);
						System.out.println("Client successfully updated.");
						updater = false;
						break;
					}
					
				}break;
		}
		case 0:
			System.out.println("Thank you for choosing Rent-N-GO, now ending.");
			menuOpen = false;
			break;
		}
	}
	}
	
	public void setVehicle(String unit) {
		this.vehicleID = "0";
	}

	public String getVehicleID() {
		return vehicleID;
	}

	public static void menuAdmin(GenericMyHashMap<String,Vehicle_group6> c,GenericMyHashMap<String,User> u ) 
	{
		boolean menuOpen = true;
		while(menuOpen == true) {
		int total = c.size();
		System.out.println("\nRent-N-GO :: DBManager.java\nSELECT A TASK - R\n--------------------------------------\n1.Insert Vehicle\n2.Search Vehicle with Vehicle ID\n3.Read a Vehicle with Vehicle ID\n4.Delete Vehicle with Vehicle ID\n5.Update Information of a Client\n6.Show all Vehicles\n0.Exit");
		Scanner menuSelect = new Scanner(System.in);
		int menuChoice = menuSelect.nextInt();
		switch(menuChoice) 
		{
		case 1:
		{
			System.out.println("Please insert a new Vehicle into the car lot.\nCar Make: ");
			Scanner vehicleMake = new Scanner(System.in);
			String vMake = vehicleMake.nextLine();
			System.out.println("Type Vehicle Model: ");
			Scanner vehicleModel = new Scanner(System.in);
			String vModel = vehicleModel.nextLine();
			System.out.println("Type Vehicle Year: ");
			Scanner vehicleYear = new Scanner(System.in);
			String vYear = vehicleYear.nextLine();
			System.out.println("Type Vehicle Color: ");
			Scanner vehicleColor = new Scanner(System.in);
			String vColor = vehicleColor.nextLine();
			String vID = Integer.toString(c.size()+1);
			
			Vehicle_group6 temp = new Vehicle_group6(vID,vMake,vModel,vYear,vColor);
			c.put(vID,temp);
			System.out.println(c.size());
			if(c.size()>total) {
				System.out.println("Vehicle added successfully");
				appendText("vehicles.txt",(vID+","+vMake+","+vModel+","+vYear+","+vColor+"\n"));
				
			}else {
				System.out.println("Failed to add vehicle to database.");
			}
			break;
		}
		case 2:
		{
			System.out.println("Please enter the Vehicle ID to search.");
			Scanner searchID = new Scanner(System.in);
			String searchVid = searchID.nextLine();
			Vehicle_group6 temp = new Vehicle_group6(searchVid);
			if(c.containsKey(searchVid) == true){
			System.out.println("Vehicle found.");
			break;
			}
			else {
				System.out.println("Vehicle not found.");
				break;
			}
			
		}
		case 3:
		{
			System.out.println("Pease enter the Vehicle ID for detailed report..");
			Scanner searchID = new Scanner(System.in);
			String searchVid = searchID.nextLine();
			Vehicle_group6 temp = new Vehicle_group6(searchVid);
			if(c.containsKey(temp.getID()) == true){
			System.out.println("Vehicle located");
			System.out.println("\nRent-N-GO Vehicle Report\n----------------------------------------------\n"+c.get(temp.getID())+"\n----------------------------------------------");
			break;
			}
			else {
				System.out.println("Vehicle not found");
				break;
			}
			
		}
		case 4:
		{
			System.out.println("Please enter Vehicle ID to remove from list.");
			Scanner searchID = new Scanner(System.in);
			String searchVid = searchID.nextLine();
            Vehicle_group6 temp = new Vehicle_group6(searchVid);
			if(c.containsKey(temp.getID()) == true){
				int beforeSize = c.size();
				c.remove(temp.getID());
				if(c.get(temp.getID()) == null && c.size() < beforeSize) {
					System.out.println("Vehicle successfully deleted.");
				} else {
					System.out.println("Vehicle Removal Failed.");
				}
			break;
			} else {
				System.out.println("Vehicle not found.");
				break;
			}
			}
		case 5:{
			boolean updater = true;
			System.out.println("Please enter the Username to update.");
			Scanner searchID = new Scanner(System.in);
			String searchUid = searchID.nextLine();
			User temp = new User(searchUid);
			if(u.containsKey(temp.getID()) == true){
				temp = u.get(temp.getID());
			} else {
				System.out.println("Client not found.");
				break;
			}
				while(updater == true) 
				{
					System.out.println("1. Last name\n2. First name\n3. Phone\n4. Address\n0. DONE.");
					Scanner choice = new Scanner(System.in);
					int userChoice = choice.nextInt();
					switch(userChoice) 
					{
					case 1:
					{
						System.out.println("Please enter the last name to update.");
						Scanner lNupdate = new Scanner(System.in);
						String lName = lNupdate.nextLine();
						temp.setLN(lName);
						break;
					}
					case 2:
					{
						System.out.println("Please enter the first name to update.");
						Scanner fNupdate = new Scanner(System.in);
						String fName = fNupdate.nextLine();
						temp.setFN(fName);
						break;
					}
					case 3:
					{
						System.out.println("Please enter the phone to update.");
						Scanner pNupdate = new Scanner(System.in);
						String phone = pNupdate.nextLine();
						temp.setPhoneNumber(phone);
						break;
					}
					case 4:
					{
						System.out.println("Please enter the address to update.");
						Scanner aUpdate = new Scanner(System.in);
						String address = aUpdate.nextLine();
						temp.setAddress(address);
						break;
					}
					case 0:
						User tempUpdate = temp;
						u.remove(temp.getID());
						u.put(tempUpdate.getID(),tempUpdate);
						System.out.println("Client successfully updated.");
						updater = false;
						break;
					}
					
				}break;
		}
		case 6:
			System.out.println("Rent-N-Go.java\r\n" + 
					"SHOW ALL VEHICLES\r\n" + 
					"----------------------------------------------\r" + 
					"");
			c.toString();
			break;
		case 0:
			System.out.println("Thank you for choosing Rent-N-GO, now ending.");
			menuOpen = false;
			break;
		}
	}
	}
	
	public String toString() 
	{
		return ("\n----------------------------------------------\nUser ID: "+ String.format("%32s", this.username) +"\nClient Name: "+String.format("%30s", (firstName+" "+lastName))+"\nPhone: "+String.format("%37s", phoneNumber)+"\nAddress: "+String.format("%35s",Address)+"\n----------------------------------------------");
		
	}
	
	public String toString1() 
	{
		return String.format("%15s", this.username);
		
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
	//constructor
	public User(String id) {
		username = id;
	}
	//accessor method
	public String getUserId() {
                return username;
	}
	//method to compare to username of client 0 to id of the current object this
	public int compareTo(User o) {
		return username.compareTo(o.getID());
	}

	public String getID() {
		return this.username;
	}

	public String getPw() {
		return this.password;
	}
	
	public static String getRN() {
		Random rnd = new Random();
		int RND = (rnd).nextInt(999999);
		return String.format("%06d", RND);
	}
	
	public void setLN(String lName) {
		this.lastName = lName;	
	}

	public void setFN(String fName) {
		this.firstName = fName;
	}

	public void setAddress(String address) {
		this.Address = address;
	}
	
	public void setToken() {
		this.token = "1";
	}
	
	public String checkToken() {
		return this.token;
	}
	
	public static void appendText(String filePath, String text)
	{
		File file = new File(filePath);	
		FileWriter fr = null;
		BufferedWriter br = null;
		PrintWriter pr = null;
		try {
			fr = new FileWriter(file, true);
			br = new BufferedWriter(fr);
			pr = new PrintWriter(br);
			pr.print(text);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				pr.close();
				br.close();
				fr.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public String userFormat() {
		return this.username+","+this.getPw()+","+this.firstName+","+this.lastName+","+this.phoneNumber+","+this.Address+","+email+","+this.checkToken()+",";
	}
	public static void replaceSelected(String replaceWith, String vehicleID, String newVehicle) {
	    try {
	        // input the file content to the StringBuffer "input"
	        BufferedReader file = new BufferedReader(new FileReader("users.txt"));
	        StringBuffer inputBuffer = new StringBuffer();
	        String line;
	        
	        File document = new File("users.txt");

	        while ((line = file.readLine()) != null) {
	            inputBuffer.append(line);
	            inputBuffer.append('\n');
	        }
	        file.close();
	        String inputStr = inputBuffer.toString();

	       // System.out.println(inputStr); // display the original file for debugging

	        // logic to replace lines in the string (could use regex here to be generic)
	        if (vehicleID.equals("0")) {
	            inputStr = inputStr.replace(replaceWith + newVehicle, replaceWith + vehicleID); 
	        } else {
	            inputStr = inputStr.replace(replaceWith + "0", replaceWith + vehicleID);
	        }

	        // display the new file for debugging
	        //System.out.println("----------------------------------\n" + inputStr);
	        appendText("users.txt",inputStr);

	        // write the new string with the replaced line OVER the same file
	        FileOutputStream fileOut = new FileOutputStream("users.txt");
	        fileOut.write(inputStr.getBytes());
	        fileOut.close();

	    } catch (Exception e) {
	        System.out.println("Problem reading file.");
	    }
	}

}
