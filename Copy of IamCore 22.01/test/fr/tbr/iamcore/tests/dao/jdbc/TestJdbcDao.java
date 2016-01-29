package fr.tbr.iamcore.tests.dao.jdbc;

import java.sql.PreparedStatement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import fr.tbr.iamcore.dao.IdentityJdbcDAO;
import fr.tbr.iamcore.datamodel.Identity;

public class TestJdbcDao {
	
	
	
	public static void main(String[] args){
//		testReadAll();
//		testSearch();
		testCreate();
	}

	private static void testReadAll() {
		IdentityJdbcDAO dao = new IdentityJdbcDAO();
		List<Identity> identities = dao.readAll();
		
		for (int i = 0; i < identities.size(); i++){
			System.out.println(identities.get(i));
		}
		
		
		dao.close();
	}
	
	private static void testSearch() {
		IdentityJdbcDAO dao = new IdentityJdbcDAO();
		Identity criteria = new Identity(null, "thomas.broussard@gmail.com", "Thomas Broussard");
		
		System.out.println(dao.search(criteria));
		
		
		dao.close();
	}
	
	private static void testCreate() {
		IdentityJdbcDAO dao = new IdentityJdbcDAO();
		Identity identity = new Identity("123456", "matthew.cechak@gmail.com", "Matthew Cechak");
		SimpleDateFormat sdfDateOnly = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Date birthDay = sdfDateOnly.parse("1990-08-31");
			identity.setBirthDate(birthDay);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		dao.create(identity);
		
		
		dao.close();
	}
	

}
