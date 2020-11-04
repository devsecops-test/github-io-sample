package com.example.controllers;

import java.security.Principal;
import java.util.List;

import com.example.bean.Account;
import com.example.dao.AccountDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/admin")
public class AdminController {

	@Autowired
	AccountDao accountDao;

	@RequestMapping
	public String admin(final Model model, final Principal principal, final String username) {

		Account account = accountDao.findUsersByUsername(principal.getName()).get(0);

		List<Account> accounts = accountDao.findAllUsers();
		String str = "select * from account where username='" + username + "'";
		model.addAttribute("account", account);
		model.addAttribute("accounts", accounts);

		return "admin";
	}

}
