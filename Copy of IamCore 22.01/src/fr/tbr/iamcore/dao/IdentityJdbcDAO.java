/**
 * 
 */
package fr.tbr.iamcore.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import fr.tbr.iamcore.dao.exceptions.DaoUpdateException;
import fr.tbr.iamcore.datamodel.Identity;

/**
 * @author tbrou
 *
 */
public class IdentityJdbcDAO implements IdentityDAOInterface {

	private Connection connection;

	public IdentityJdbcDAO() {
		try {
			this.connection = getConnection();
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * fr.tbr.iamcore.dao.IdentityDAOInterface#create(fr.tbr.iamcore.datamodel
	 * .Identity)
	 */
	@Override
	public void create(Identity identity) {
		
		PreparedStatement prepareStatementCreate = null;
		try {
			prepareStatementCreate = this.connection.prepareStatement("INSERT INTO IDENTITIES (DISPLAY_NAME, EMAIL_ADDRESS, BIRTHDATE, UID) VALUES(?, ?, ?, ?)");
			prepareStatementCreate.setString(1, identity.getDisplayName());
			prepareStatementCreate.setString(2, identity.getEmail());
			prepareStatementCreate.setDate(3, new java.sql.Date(identity.getBirthDate().getTime()));
			prepareStatementCreate.setString(4, identity.getUid());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    if (prepareStatementCreate != null) {
		    try {
				prepareStatementCreate.executeUpdate();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see fr.tbr.iamcore.dao.IdentityDAOInterface#readAll()
	 */
	@Override
	public List<Identity> readAll() {
		List<Identity> identities = new ArrayList<Identity>();

		try {

			PreparedStatement prepareStatement = this.connection.prepareStatement("select * from IDENTITIES");
			ResultSet rs = prepareStatement.executeQuery();
			while (rs.next()) {
				String displayName = rs.getString("DISPLAY_NAME");
				String email = rs.getString("EMAIL_ADDRESS");
				Date date = rs.getDate("BIRTHDATE");
				String uid = rs.getString("UID");

				Identity identity = new Identity(uid, email, displayName);
				identity.setBirthDate(date);
				identities.add(identity);
			}

		} catch (Exception e) {
			System.out.println(e);

		}

		// finally {
		// if (this.connection != null) {
		// try {
		// this.connection.close();
		// } catch (SQLException e) {
		// e.printStackTrace();
		// }
		// }
		return identities;

	}

	private static Connection getConnection() throws ClassNotFoundException, SQLException {
		Class.forName("org.apache.derby.jdbc.ClientDriver");

		Connection connection = DriverManager.getConnection("jdbc:derby://localhost:1527/IAMDataBase;create=true", "cechy", "cechy");
		System.out.println(connection.getSchema());
		return connection;
	}

	public void close() {
		try {
			this.connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * fr.tbr.iamcore.dao.IdentityDAOInterface#search(fr.tbr.iamcore.datamodel
	 * .Identity)
	 */
	@Override
	public List<Identity> search(Identity criteria) {
		List<Identity> identities = new ArrayList<Identity>();

		try {

			PreparedStatement prepareStatement = this.connection.prepareStatement("select * from IDENTITIES where IDENTITIES.EMAIL_ADDRESS = ? and	IDENTITIES.DISPLAY_NAME = ?");
			prepareStatement.setString(1, criteria.getEmail());
			prepareStatement.setString(2, criteria.getDisplayName());
			ResultSet rs = prepareStatement.executeQuery();
			while (rs.next()) {
				String displayName = rs.getString("DISPLAY_NAME");
				String email = rs.getString("EMAIL_ADDRESS");
				Date date = rs.getDate("BIRTHDATE");
				String uid = rs.getString("UID");

				Identity identity = new Identity(uid, email, displayName);
				identity.setBirthDate(date);
				identities.add(identity);
			}

		} catch (Exception e) {
			System.out.println(e);

		}
		return identities;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * fr.tbr.iamcore.dao.IdentityDAOInterface#update(fr.tbr.iamcore.datamodel
	 * .Identity)
	 */
	@Override
	public void update(Identity identity) throws DaoUpdateException {
		
		//FINISH
		
		String query = "update IDENTITIES set EMAIL_ADDRESS = ? where DISPLAY_NAME = ? and EMAIL_ADDRESS = ? and BIRTHDATE = ? and UID = ?";
		
		try {
			PreparedStatement prepareStatementCreate = this.connection.prepareStatement(query);
			// prepareStatementCreate.setString(1, @insert what do we want to update );
			prepareStatementCreate.setString(2, identity.getDisplayName());
			prepareStatementCreate.setString(3, identity.getEmail());
			prepareStatementCreate.setDate(4, new java.sql.Date(identity.getBirthDate().getTime()));
			prepareStatementCreate.setString(5, identity.getUid());			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * fr.tbr.iamcore.dao.IdentityDAOInterface#delete(fr.tbr.iamcore.datamodel
	 * .Identity)
	 */
	@Override
	public void delete(Identity identity) {
		// TODO Auto-generated method stub

	}

}
