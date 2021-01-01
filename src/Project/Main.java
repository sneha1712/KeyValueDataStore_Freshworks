package Project;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import java.util.*;
public class Main {
	public static void main(String args[]) throws IOException, ParseException, InterruptedException
	{
		Scanner scan=new Scanner(System.in);
		String loc=System.getProperty("user.dir");
		int n,i;
		String key="";
		String name="";
		String filepath="";
		String city="";
		int age;
		int ttlvalue;
		System.out.println("If you want to specify Filepath give the location else give NA");
		filepath=scan.nextLine();
		if(filepath.equals("NA"))
		filepath=loc+"\\data";
		else
		filepath=filepath+"\\data";
		CRDMethods dtstore=new CRDMethods(filepath);
		JSONObject json = new JSONObject();
//										**USER INPUT **
//		System.out.println("Enter the no of users to store");
//		n=scan.nextInt(); // no of users
//		for(i=0;i<n;i++)
//		{
//			scan.nextLine();
//			System.out.println("Enter the Key");
//			key=scan.nextLine();
//			if(store.exists(key))
//			{
//				System.out.println("Key Already Exists");
//			}
//			else
//			{
//			System.out.println("Enter the Name");
//			name=scan.nextLine();
//			System.out.println("Enter the city");
//			city=scan.nextLine();
//			System.out.println("Enter the age");
//			age=scan.nextInt();
//			System.out.println("If you want to specify TTL give the value in seconds else give -1");
//			ttlvalue=scan.nextInt();
//			json.put("name", name);
//			json.put("city", city);
//			json.put("age", age);
//			store.createfn(key, json,ttlvalue);
//			json.clear();
//			}
//		}
		json.put("name", "Sneha Ravi");
		json.put("city","chennai");
		json.put("age", 21);
		System.out.println(dtstore.create("sneha",json,-1)); 
		json.clear();
		json.put("name", "user1");
		json.put("city","bangalore");
		json.put("age", 21);
		System.out.println(dtstore.create("user",json,2)); 
		json.clear();
		json.put("name", "user2");
		json.put("city","mysore");
		json.put("age", 22);
		System.out.println(dtstore.create("SomeKeyLengthGreaterThan32Charaters",json,2)); //key size is greater than 32 chars
		System.out.println(dtstore.read("user"));//reads the user key values
		Thread.sleep(5000); //sleeps for 5 seconds
		System.out.println(dtstore.read("user1"));//key not found as it gets deleted after specified ttl
		System.out.println(dtstore.read("sneha"));//reads the user key values
		System.out.println(dtstore.read("xyz"));//key not found
		System.out.println(dtstore.delete("sneha"));//values with key sneha gets deleted
		System.out.println(dtstore.delete("user1"));//key not found
	}
}
	
