package com.example.dao;

import java.util.Date;
import java.util.List;

import com.example.bean.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ActivityDaoImpl implements ActivityDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public List<Transaction> findTransactionsByCashAccountNumber(final String number) {
		String sql = "SELECT * FROM transaction WHERE number = '" + number + "'";

		List<Transaction> customers = jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Transaction.class));
		return customers;
	}
	
	@Override
	public List<Transaction> findTransactionsByCustomerName(final String customerName) {
		String sql = "SELECT * FROM transaction WHERE customerName = '" + customerName + "'";

		List<Transaction> customers = jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Transaction.class));
		return customers;
	}	

	@Override
	public void insertNewActivity(final Date date, final String description, final String number, final double amount,
			final double availablebalance, final String username) {

		try { 
			String sql = "INSERT INTO transaction " + "(date, description, number, amount, availablebalance) VALUES (?, ?, ?, ?, ?)";

			String str = "select * from account where username='" + username + "'";

			RowMapper<Account> rowMapper = new RowMapper<Account>() {
				@Override
				public Account mapRow(final ResultSet paramResultSet, final int paramInt) throws java.sql.SQLException {
					Account localAccount = new Account();
					localAccount.setUsername(paramResultSet.getString("username"));
					localAccount.setName(paramResultSet.getString("name"));
					localAccount.setSurname(paramResultSet.getString("surname"));
					localAccount.setPassword(paramResultSet.getString("password"));
					return localAccount;
				}
			};

			return jdbcTemplate.query(str, rowMapper);
			
			jdbcTemplate.update(sql, new Object[] { date, description, number, amount, availablebalance, });
		}
		catch (Exception e) {
			System.out.println(e);
		}
	}

	public void setJdbcTemplate(final JdbcTemplate paramJdbcTemplate) {
		jdbcTemplate = paramJdbcTemplate;
	}

}
