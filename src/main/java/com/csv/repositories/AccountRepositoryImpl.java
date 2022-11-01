package com.csv.repositories;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.csv.model.Account;

@Repository("accountRepository")
public class AccountRepositoryImpl implements AccountRepository {

	@Override
	public List<Account> findAll() {
		List<Account> accounts = new ArrayList<Account>();
		accounts.add(new Account("acc1", "123456", new String[] {"ROLE_SUPER_ADMIN", "ROLE_ADMIN", "ROLE_EMPLOYEE"}));
		accounts.add(new Account("acc2", "123456", new String[] {"ROLE_ADMIN", "ROLE_EMPLOYEE"}));
		accounts.add(new Account("acc3", "123456", new String[] {"ROLE_EMPLOYEE"}));
		return accounts;
	}

	@Override
	public Account find(String username) {
		for (Account account : this.findAll()) {
			if (account.getUsername().equalsIgnoreCase(username))
				return account;
		}
		return null;
	}

}
