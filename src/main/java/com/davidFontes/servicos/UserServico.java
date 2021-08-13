package com.davidFontes.servicos;

import org.springframework.security.core.context.SecurityContextHolder;

import com.davidFontes.security.UserSS;

public class UserServico {

	public static UserSS authenticated() {
		try {
			return (UserSS) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		}
		catch (Exception e) {
			return null;
		}
	}
}
