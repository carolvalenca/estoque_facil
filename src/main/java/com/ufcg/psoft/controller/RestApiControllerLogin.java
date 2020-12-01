package com.ufcg.psoft.controller;

import javax.servlet.ServletException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ufcg.psoft.model.Usuario;
import com.ufcg.psoft.model.DTO.LoteDTO;
import com.ufcg.psoft.model.DTO.ObjResposta;
import com.ufcg.psoft.model.DTO.UsuarioDTO;
import com.ufcg.psoft.service.JWTService;
import com.ufcg.psoft.service.UsuarioService;
import com.ufcg.psoft.util.CustomErrorType;

import exceptions.ObjetoInexistenteException;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class RestApiControllerLogin {
	
	@Autowired
	private JWTService jwtService;
	
	@Autowired
	private UsuarioService usuarioService;

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ResponseEntity<?> autentica(@RequestBody UsuarioDTO usuario) throws ServletException  {
		return new ResponseEntity<ObjResposta>(jwtService.autentica(usuario), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/cadastro", method = RequestMethod.POST)
	public ResponseEntity<?> cadastrar(@RequestBody UsuarioDTO usuario) throws ServletException  {
		if (usuarioService.verificaUsuarioExiste(usuario.getEmail())) {
			return new ResponseEntity<>(new CustomErrorType("Email ja cadastrado"), HttpStatus.NOT_ACCEPTABLE);
		}
		
		Usuario usuarioSalvo = usuarioService.saveUsuario(new Usuario(usuario.getEmail(), usuario.getSenha()));
		
		return new ResponseEntity<Usuario>(usuarioSalvo, HttpStatus.CREATED);
	}
	
}
