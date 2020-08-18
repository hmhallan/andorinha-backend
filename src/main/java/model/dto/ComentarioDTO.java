package model.dto;

import java.util.Calendar;

public class ComentarioDTO {
	
	private int id;
	private int idTweet;
	private int idUsuario;
	
	private String nomeUsuario;
	
	private Calendar data;
	private String conteudo;
	
	public ComentarioDTO() {
		super();
	}
	
	
	public ComentarioDTO(int id, int idTweet, int idUsuario, String nomeUsuario, Calendar data, String conteudo) {
		super();
		this.id = id;
		this.idTweet = idTweet;
		this.idUsuario = idUsuario;
		this.nomeUsuario = nomeUsuario;
		this.data = data;
		this.conteudo = conteudo;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getIdTweet() {
		return idTweet;
	}
	public void setIdTweet(int idTweet) {
		this.idTweet = idTweet;
	}
	public int getIdUsuario() {
		return idUsuario;
	}
	public void setIdUsuario(int idUsuario) {
		this.idUsuario = idUsuario;
	}
	public String getNomeUsuario() {
		return nomeUsuario;
	}
	public void setNomeUsuario(String nomeUsuario) {
		this.nomeUsuario = nomeUsuario;
	}
	public Calendar getData() {
		return data;
	}
	public void setData(Calendar data) {
		this.data = data;
	}

	public String getConteudo() {
		return conteudo;
	}

	public void setConteudo(String conteudo) {
		this.conteudo = conteudo;
	}
	
	
}
