import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;

import DATA_STRUCTURE.GenericMyHashMap;

public class DBManager {
	static User client;

	public static void main(String[] args) throws FileNotFoundException 
	{
		GenericMyHashMap<String,Vehicle_group6> vehicleList = new GenericMyHashMap<>();
		GenericMyHashMap<String,User> userList = new GenericMyHashMap<>();
		
		User user = new User(null);
		//user.login(userList);
		
		
		File inputDoc = new File("vehicles.txt");
		File inputUsers = new File("users.txt");
		Scanner inputFile = new Scanner(inputDoc);
		Scanner inputUsersFile = new Scanner(inputUsers);
		

		while (inputFile.hasNextLine()) 
		{
			int idnum = 0;
			String temp = inputFile.nextLine();
			if(temp.isEmpty()) 
			{
				break;
			} else 
			{
				String[] parts = temp.split(",");
				String ID = String.valueOf(idnum);
				ID = parts[0];
				String make = parts[1];
				String model = parts[2];
				String year = parts[3];
				String color = parts[4];
				idnum++;
				
				Vehicle_group6 tempVehicle = new Vehicle_group6(ID,make,model,year,color);
				vehicleList.put(ID,tempVehicle);
			}
		}
		
		while (inputUsersFile.hasNextLine()) 
		{
			String temp = inputUsersFile.nextLine();
			if(temp.isEmpty()) 
			{
				break;
			} else 
			{
				String[] parts = temp.split(",");
				String username = parts[0];
				String password = parts[1];
				//String authenCode = parts[2];
				String firstname = parts[2];
				String lastname = parts[3];
				String phonenumber = parts[4];
				String address = parts[5];
				String email = parts[6];
				String token = parts[7];
				String vehicle = parts[8];
				
				User tempUser = new User(username,password,firstname,lastname,phonenumber,address,email,token,vehicle);
				userList.put(username,tempUser);
			}
		}
		
		//vehicleList.get("1").setRenter(testUser);;
		//System.out.println(user.open(userList).checkToken());
		//System.out.println(userList.get("test").checkToken());
		
		User.navigate(vehicleList,userList,User.open(userList));
		

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
	
}
