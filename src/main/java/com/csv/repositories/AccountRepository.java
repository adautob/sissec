package com.csv.repositories;

import java.util.List;

import com.csv.model.Account;

public interface AccountRepository {

	public List<Account> findAll();
	
	public Account find(String username);
	
}
