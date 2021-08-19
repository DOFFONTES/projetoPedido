package com.davidFontes.recursos;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.davidFontes.dto.EmailDTO;
import com.davidFontes.security.JWTUtil;
import com.davidFontes.security.UserSS;
import com.davidFontes.servicos.AuthServico;
import com.davidFontes.servicos.UserServico;

@RestController
@RequestMapping(value = "/auth")
public class AuthResource {

	@Autowired
	private JWTUtil jwtUtil;
	
	@Autowired
	private AuthServico servico;

	@PostMapping(value = "/refresh_token")
	public ResponseEntity<Void> refreshToken(HttpServletResponse response) {
		UserSS user = UserServico.authenticated();
		String token = jwtUtil.generateToken(user.getUsername());
		response.addHeader("Authorization", "Bearer " + token);
		response.addHeader("access-control-expose-headers", "Authorization");
		
		return ResponseEntity.noContent().build();
	}
	
	@PostMapping(value = "/forgot")
	public ResponseEntity<Void> forgot(@Valid @RequestBody EmailDTO objDto) {
		servico.sendNewPassword(objDto.getEmail());
		return ResponseEntity.noContent().build();
	}
}
