package Project;

import java.io.*;
import java.lang.management.ManagementFactory;
import java.util.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class CRDMethods {
	String filePath="";
	public CRDMethods(String path)
	{
		this.filePath=path;
	}
	HashMap<String,DataClass> hmap;
	FileInputStream fileip;
	FileOutputStream fileop;
	ObjectInputStream objectip;
	ObjectOutputStream objectop;
	public synchronized String create(String key,JSONObject value,int ttl)
	{
		if(exists(key))
		{
			return "key exists already";
		}
		else if(key.length()>32)
		{
			return "key greater than 32 chars";
		}
		else
		{
		DataClass ds=new DataClass();
		ds.setKey(key);
		ds.setValue(value);
		ds.setTtl(ttl);
		ds.setStarttime((new Date().getTime()));
		//System.out.println(ds.getKey()+" "+ds.getStarttime()+" "+ds.ttl+" "+ds.getValue());
		try {
			File file = new File(filePath);
			if (file.exists()) {
				if(file.length()>1024*1024*1024)
					return "file exceeding 1 GB";
				fileip = new FileInputStream(file);
				objectip = new ObjectInputStream(fileip);
				hmap = (HashMap<String, DataClass>) objectip.readObject();
				hmap.put(ds.getKey(), ds);
				fileop = new FileOutputStream(file);
				objectop = new ObjectOutputStream(fileop);
				objectop.writeObject(hmap);
				fileip.close();
				objectip.close();
				fileop.close();
				objectop.close();
				fileip = new FileInputStream(file);
				objectip = new ObjectInputStream(fileip);
				hmap = (HashMap<String, DataClass>) objectip.readObject();
				return "successfully created";
			} else {
				hmap = new HashMap<String, DataClass>();
				hmap.put(ds.getKey(), ds);
				fileop = new FileOutputStream(file);
				objectop = new ObjectOutputStream(fileop);
				objectop.writeObject(hmap);
				fileop.close();
				objectop.close();
				return "successfully created";
			}
		}
		catch(Exception e) {
			System.out.println(e);
			return "creation failed";
		}
		
		}
	}
	public synchronized Boolean exists(String key)
	{
		Boolean exist=false;
		try {
			File file = new File(filePath);
			if (file.exists()) {
				fileip = new FileInputStream(file);
				objectip = new ObjectInputStream(fileip);
				hmap = (HashMap<String, DataClass>) objectip.readObject();
				fileip.close();
				objectip.close();
				if(hmap.containsKey(key))
					exist=true;
				fileop = new FileOutputStream(file);
				objectop = new ObjectOutputStream(fileop);
				objectop.writeObject(hmap);
				fileop.close();
				objectop.close();
			}
			if (exist) {
				DataClass ds = hmap.get(key);
				long now = new Date().getTime();
				int ttlvalue=ds.getTtl();
				long diff=(now - ds.getStarttime());
				if (ttlvalue> 0 && diff >= (ttlvalue*1000)) {
					hmap.remove(key);
					fileop = new FileOutputStream(file);
					objectop = new ObjectOutputStream(fileop);
					objectop.writeObject(hmap);
					fileop.close();
					objectop.close();
					exist = false;
				}
			}
		} catch (Exception exception) {}
		return exist;
	}
	public synchronized String read(String key)
	{
		try {
			File file = new File(filePath);
			if (file.exists() && exists(key)) {
				fileip = new FileInputStream(file);
				objectip = new ObjectInputStream(fileip);
				hmap = (HashMap<String, DataClass>) objectip.readObject();				
				fileip.close();
				objectip.close();
				return (key+" "+hmap.get(key).getValue());
			} 
			else
			{
				return "Key not found";
			}
		} catch (Exception exception) {
			return "Key not found";
		}
	}
	public synchronized String delete(String key)
	{
		if(exists(key))
		{
		try {
			File file = new File(filePath);
			if (file.exists()) {
				fileip = new FileInputStream(file);
				objectip = new ObjectInputStream(fileip);
				hmap = (HashMap<String, DataClass>) objectip.readObject();
				fileip.close();
				objectip.close();
				hmap.remove(key);
				fileop = new FileOutputStream(file);
				objectop = new ObjectOutputStream(fileop);
				objectop.writeObject(hmap);
				fileop.close();
				objectop.close();
				return "sucessfully deleted";
			}
		} catch (Exception exception) {return "deletion failed";}
		}
		else
		{
				return "Key not found";
		}
		return "";
	}
	
}