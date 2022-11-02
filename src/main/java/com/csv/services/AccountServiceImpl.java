package com.csv.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.csv.model.Account;
import com.csv.repositories.AccountRepository;

@Service("accountService")
public class AccountServiceImpl implements AccountService

{
	
	@Autowired
	private AccountRepository accountRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Account account = accountRepository.find(username);
		if (account == null) {
			throw new UsernameNotFoundException("Username not found for "+username);
		}
		List<GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority>();
		for (String role : account.getRoles()) {
			grantedAuthorities.add(new SimpleGrantedAuthority(role));
		}
		return new User(account.getUsername(), account.getPassword(), grantedAuthorities);
	}

}
