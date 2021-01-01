package Test;

import static org.junit.jupiter.api.Assertions.*;

import org.json.simple.JSONObject;
import org.junit.jupiter.api.Test;

import Project.CRDMethods;


class JunitTest {
	
	@Test
	void test_create() {
		String s1="";
		String s2="";
		String s="successfully created";
		String snew="key exists already";
		CRDMethods datastoreobj=new CRDMethods("S:\\JAVA\\default_loc\\data");
		JSONObject jsonobjtest=new JSONObject();
		jsonobjtest.put("name", "Sneha_R");
		jsonobjtest.put("city", "chennai");
		jsonobjtest.put("age", 21);
		s1=datastoreobj.create("Sneha",jsonobjtest,-1);
		s2=datastoreobj.create("Sneha",jsonobjtest,-1);
		assertEquals(s1,s);
		assertEquals(s2,snew);
		datastoreobj.delete("Sneha");	
	}
	
	@Test
	void test_read() {
		String s1="";
		String s2="Sneha {\"city\":\"chennai\",\"name\":\"Sneha_R\",\"age\":22}";
		CRDMethods datastoreobj=new CRDMethods("S:\\JAVA\\default_loc\\data");
		JSONObject jsonobjtest=new JSONObject();
		jsonobjtest.put("name", "Sneha_R");
		jsonobjtest.put("city", "chennai");
		jsonobjtest.put("age", 22);
		s1=datastoreobj.create("Sneha",jsonobjtest,-1);
		s1=datastoreobj.read("Sneha");
		assertEquals(s1,s2);
		datastoreobj.delete("Sneha");
		s1=datastoreobj.read("Sneha");
		s2="Key not found";
		assertEquals(s1,s2);
	}
	
	@Test
	void test_ttl() throws InterruptedException {
		String s1="";
		String s2="Sneha {\"city\":\"chennai\",\"name\":\"Sneha_R\",\"age\":22}";
		CRDMethods datastoreobj=new CRDMethods("S:\\JAVA\\default_loc\\data");
		JSONObject jsonobjtest=new JSONObject();
		jsonobjtest.put("name", "Sneha_R");
		jsonobjtest.put("city", "chennai");
		jsonobjtest.put("age", 22);
		s1=datastoreobj.create("Sneha",jsonobjtest,1);
		s1=datastoreobj.read("Sneha");
		assertEquals(s1,s2);
		Thread.sleep(2000);
		s1=datastoreobj.read("Sneha");
		s2="Key not found";
		assertEquals(s1,s2);
	}
	
	@Test
	void test_delete() {
		CRDMethods datastoreobj=new CRDMethods("S:\\JAVA\\default_loc\\data");
		JSONObject jsonobjtest=new JSONObject();
		jsonobjtest.put("name", "Sneha_R");
		jsonobjtest.put("city", "chennai");
		jsonobjtest.put("age", 22);
		datastoreobj.create("Sneha",jsonobjtest,-1);
		String s1="";
		String s2="sucessfully deleted";
		s1=datastoreobj.delete("Sneha");
		assertEquals(s1,s2);
		s2="Key not found";
		s1=datastoreobj.delete("Sneha");
		assertEquals(s1,s2);
	}
	
	

}
