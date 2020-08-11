package service;

import java.util.List;

import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import model.Usuario;
import model.seletor.UsuarioSeletor;
import repository.UsuarioRepository;

@Path("/usuario")
public class UsuarioService {

	
	@EJB
	UsuarioRepository usuarioRepository;
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Usuario> listarTodos(){
		return this.usuarioRepository.listarTodos();
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public int inserir(Usuario usuario) {
		this.usuarioRepository.inserir(usuario);
		return usuario.getId();
	}
	
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public void atualizar(Usuario usuario) {
		this.usuarioRepository.atualizar(usuario);
	}
	
	
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Usuario consultar( @PathParam("id") Integer id) {
		return this.usuarioRepository.consultar(id);
	}
	
	@DELETE
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public void remover( @PathParam("id") Integer id) {
		this.usuarioRepository.remover(id);
	}
	
	
	@POST
	@Path("/pesquisar")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public List<Usuario> pesquisar( UsuarioSeletor seletor ){
		return this.usuarioRepository.pesquisar(seletor);
	}
	
}
