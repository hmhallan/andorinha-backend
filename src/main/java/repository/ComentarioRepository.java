package repository;

import java.util.List;

import javax.ejb.Stateless;
import model.Comentario;
import model.dto.ComentarioDTO;
import model.seletor.ComentarioSeletor;
import repository.base.AbstractCrudRepository;

@Stateless
public class ComentarioRepository extends AbstractCrudRepository<Comentario> {

	public List<Comentario> listarTodos() {
		return pesquisar(new ComentarioSeletor());
	}

	public List<Comentario> pesquisar(ComentarioSeletor seletor) {
		return super.createEntityQuery()
				.innerJoinFetch("usuario")
				.innerJoinFetch("tweet")
				.equal("id", seletor.getId())
				.equal("usuario.id", seletor.getIdUsuario())
				.equal("tweet.id", seletor.getIdTweet())
				.like("conteudo", seletor.getConteudo())
				.equal("data", seletor.getData())
				.setFirstResult(seletor.getOffset())
				.setMaxResults(seletor.getLimite())
				.list();
	}
	
	public List<ComentarioDTO> pesquisarDTO(ComentarioSeletor seletor) {
		return super.createTupleQuery()
					.select("id", "tweet.id as idTweet", "usuario.id as idUsuario", "usuario.nome as nomeUsuario", "data", "conteudo")
					.join("usuario")
					.join("tweet")
					.equal("id", seletor.getId())
					.equal("usuario.id", seletor.getIdUsuario())
					.equal("tweet.id", seletor.getIdTweet())
					.like("conteudo", seletor.getConteudo())
					.equal("data", seletor.getData())
					.setFirstResult(seletor.getOffset())
					.setMaxResults(seletor.getLimite())
					.list(ComentarioDTO.class);
	}
	
	public Long contar(ComentarioSeletor seletor) {
		return super.createCountQuery()
				.equal("id", seletor.getId())
				.equal("usuario.id", seletor.getIdUsuario())
				.equal("tweet.id", seletor.getIdTweet())
				.like("conteudo", seletor.getConteudo())
				.equal("data", seletor.getData())
				.count();
	}

}