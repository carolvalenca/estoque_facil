package com.ufcg.psoft.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ufcg.psoft.model.Usuario;
import com.ufcg.psoft.model.DTO.ObjResposta;
import com.ufcg.psoft.model.DTO.UsuarioDTO;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class JWTService {
	@Autowired
	private UsuarioService usuarioService;
	private final String TOKEN_KEY = "login do batman";

	public ObjResposta autentica(UsuarioDTO usuario) {

		if (!usuarioService.validaUsuarioSenha(usuario)) {
			return new ObjResposta("Usuario ou senha invalidos. Nao foi realizado o login.");
		}

		String token = geraToken(usuario.getEmail());
		return new ObjResposta(token);
	}

	private String geraToken(String email) {
		return Jwts.builder().setSubject(email)
				.signWith(SignatureAlgorithm.HS512, TOKEN_KEY)
				.setExpiration(new Date(System.currentTimeMillis() + 3 * 60 * 1000)).compact();// 3 min
	}
}
