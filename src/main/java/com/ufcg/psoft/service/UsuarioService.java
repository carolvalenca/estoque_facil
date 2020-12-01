package com.ufcg.psoft.service;

import org.jasypt.util.password.PasswordEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ufcg.psoft.model.Usuario;
import com.ufcg.psoft.model.DTO.UsuarioDTO;
import com.ufcg.psoft.repositories.UsuarioRepository;

@Service
public class UsuarioService {
	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private PasswordEncryptor passwordEncryptor;
	
	public Usuario findByEmail(String email) {
		Usuario usuario = usuarioRepository.findByEmail(email);
		
		return usuario;
	}
	
	public Usuario saveUsuario(Usuario usuario) {
		usuario.setSenha(passwordEncryptor.encryptPassword(usuario.getSenha()));
		Usuario usuarioSalvo = usuarioRepository.save(usuario);
		
		return usuarioSalvo;
	}
	
	public boolean verificaUsuarioExiste(String email) {
		Usuario usuario = usuarioRepository.findByEmail(email);
		
		if (usuario == null) {
			return false;
		}
		
		return true;
	}
	
	public boolean validaUsuarioSenha(UsuarioDTO usuario) {
		Usuario optUsuario = usuarioRepository.findByEmail(usuario.getEmail());
		if (passwordEncryptor.checkPassword(usuario.getSenha(), optUsuario.getSenha()) && optUsuario.getEmail().contentEquals(usuario.getEmail()))
			return true;
		return false;
	}
}
