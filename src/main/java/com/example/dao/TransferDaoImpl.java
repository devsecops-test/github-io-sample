package com.example.dao;

import com.example.bean.Transfer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class TransferDaoImpl implements TransferDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public void insertTransfer(final Transfer transfer, final String username) {
		
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
		
		String sql = "INSERT INTO transfer "
				+ "(fromAccount, toAccount, description, amount, fee, username, date) VALUES (?, ?, ?, ?, ?, ?, ?)";

		jdbcTemplate.update(sql, new Object[] { transfer.getFromAccount(), transfer.getToAccount(), transfer.getDescription(),
				transfer.getAmount(), transfer.getFee(), transfer.getUsername(), transfer.getDate(), });
	}

	public void setJdbcTemplate(final JdbcTemplate paramJdbcTemplate) {
		jdbcTemplate = paramJdbcTemplate;
	}
}
