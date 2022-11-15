import java.util.Collections;
import java.util.List;

public class Vehicle_group6  implements Comparable<Vehicle_group6>{
		
	public String make;
	private String model;
	private String year;
	private String color;
	private User user;
	private String ID;
	public boolean rented;
	
	Vehicle_group6(String ID, String make, String model, String year, String color)
	{
		this.make = make;
		this.model = model;
		this.year = year;
		this.color = color;
		this.user = null;
		this.ID = ID;
		this.rented = false;
	}
	
	public String toString() 
	{
		return ("\n----------------------------------------------\nVehicle ID: "+ String.format("%39s", this.ID) +"\nVehicle Make and Model: "+String.format("%27s", (make+" "+model))+"\nYear: "+String.format("%45s", year)+"\nColor: "+String.format("%44s",color)+"\nCurrent Renter: "+String.format("%35s", this.showRenter(user))+"\n----------------------------------------------");
		
	}
	public String toString1() 
	{
		return String.format("%15s", this.ID);
		
	}
	
	public String getRenter() {
		if(user == null) {
			return "0";
		} else {
		return this.user.getID();
		}
	}
	
	public String showRenter(User u) {
		if( u == null) {
			return "No current Renter";
		} else {
			return u.toString();
		}
	}

	public void setID(String ID) {
		this.ID = ID;
	}
	
	//constructor
	public Vehicle_group6(String id) {
		ID = id;
	}
	//accessor method
	public String getVehicleID() {
                return ID;
	}
	//method to compare to id of vehicle 0 to id of the current object this
	public int compareTo(Vehicle_group6 o) {
		return ID.compareTo(o.getID());
	}

	public String getID() {
		return this.ID;
	}

	//sets the user or renter of the vehicle
	public void setRenter(User user) {
		this.user = user;	
	}
	
	//remove the user or renter of the vehicle
	public void removeRenter() {
		this.user = null;	
	}

	public void setMake(String make) {
		this.make = make;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getMake() {
		return this.make;
	}

	public String getColor() {
		return this.color;
	}




}
