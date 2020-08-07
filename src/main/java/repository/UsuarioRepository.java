package repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;

import model.Usuario;
import model.exceptions.ErroAoConectarNaBaseException;
import model.exceptions.ErroAoConsultarBaseException;
import model.seletor.UsuarioSeletor;

@Stateless
public class UsuarioRepository extends AbstractCrudRepository {
	
	public void inserir(Usuario usuario) throws ErroAoConectarNaBaseException, ErroAoConsultarBaseException {
		//abrir uma conexao com o banco
		try (Connection c = super.ds.getConnection()) {
			
			//proximo valor da sequence
			int id = this.recuperarProximoValorDaSequence("seq_usuario");
			usuario.setId(id);
			
			//criar e executar a sql
			PreparedStatement ps = c.prepareStatement("insert into usuario (id, nome) values (?, ?)");
			ps.setInt(1, usuario.getId());
			ps.setString(2, usuario.getNome());
			ps.execute();
			ps.close();
			
		} catch (SQLException e) {
			throw new ErroAoConsultarBaseException("Ocorreu um erro ao inserir usuário", e);
		}
	}
	
	public void atualizar(Usuario usuario) throws ErroAoConectarNaBaseException, ErroAoConsultarBaseException {
		try (Connection c = this.abrirConexao()) {

			PreparedStatement ps = c.prepareStatement("update usuario set nome = ? where id = ?");
			ps.setString(1, usuario.getNome());
			ps.setInt(2, usuario.getId());
			ps.execute();
			ps.close();

		} catch (SQLException e) {
			throw new ErroAoConsultarBaseException("Ocorreu um erro ao atualizar o usuário", e);
		}
	}
	
	public void remover(int id) throws ErroAoConectarNaBaseException, ErroAoConsultarBaseException {
		try (Connection c = this.abrirConexao()) {

			PreparedStatement ps = c.prepareStatement("delete from usuario where id = ?");
			ps.setInt(1, id);
			ps.execute();
			ps.close();

		} catch (SQLException e) {
			throw new ErroAoConsultarBaseException("Ocorreu um erro ao remover o usuário", e);
		}
	}
	
	public Usuario consultar(int id) throws ErroAoConectarNaBaseException, ErroAoConsultarBaseException {

		//abrir uma conexao com o banco
		try (Connection c = this.abrirConexao()) {
			
			Usuario user = null;
			
			//criar e executar a sql
			PreparedStatement ps = c.prepareStatement("select id, nome from usuario where id = ?");
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				user = this.criarModel(rs);
			}
			rs.close();
			ps.close();
			
			return user;
			
		} catch (SQLException e) {
			throw new ErroAoConsultarBaseException("Ocorreu um erro ao consultar usuário", e);
		}
	}
	
	public List<Usuario> pesquisar(UsuarioSeletor seletor) throws ErroAoConsultarBaseException, ErroAoConectarNaBaseException {
		try (Connection c = this.abrirConexao()) {

			List<Usuario> users = new ArrayList<>();

			StringBuilder sql = new StringBuilder();
			sql.append("select id, nome from usuario ");
			
			this.criarFiltro(sql, seletor);
			
			PreparedStatement ps = c.prepareStatement( sql.toString() );
			
			this.adicionarParametros(ps, seletor);
			
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				users.add( this.criarModel(rs) );
			}
			rs.close();
			ps.close();

			return users;

		} catch (SQLException e) {
			throw new ErroAoConsultarBaseException("Ocorreu um erro ao listar todos os usuários", e);
		}
	}

	public Long contar(UsuarioSeletor seletor) throws ErroAoConsultarBaseException, ErroAoConectarNaBaseException {
		try (Connection c = this.abrirConexao()) {

			Long id = 0L;

			StringBuilder sql = new StringBuilder();
			sql.append("select count(id) as total from usuario ");
			
			this.criarFiltro(sql, seletor);
			
			PreparedStatement ps = c.prepareStatement( sql.toString() );
			
			this.adicionarParametros(ps, seletor);
			
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				id = rs.getLong("total");
			}
			rs.close();
			ps.close();

			return id;

		} catch (SQLException e) {
			throw new ErroAoConsultarBaseException("Ocorreu um erro ao listar todos os usuários", e);
		}
	}
	
	public List<Usuario> listarTodos() throws ErroAoConectarNaBaseException, ErroAoConsultarBaseException {
		return this.pesquisar( new UsuarioSeletor() );
	}
	
	private void criarFiltro(StringBuilder sql, UsuarioSeletor seletor) {
		if (seletor.possuiFiltro()) {
			sql.append("WHERE ");
			boolean primeiro = true;
			if ( seletor.getId() != null ) {
				sql.append("id = ? ");
			}
			
			if (seletor.getNome() != null && !seletor.getNome().trim().isEmpty() ) {
				if (!primeiro) {
					sql.append("AND ");
				}
				sql.append("nome like ? ");
			}
		}
	}
	
	private void adicionarParametros(PreparedStatement ps, UsuarioSeletor seletor) throws SQLException {
		int indice = 1;
		
		if (seletor.possuiFiltro()) {
			if ( seletor.getId() != null ) {
				ps.setInt(indice++, seletor.getId());
			}
			
			if (seletor.getNome() != null && !seletor.getNome().trim().isEmpty() ) {
				ps.setString(indice++, String.format("%%%s%%", seletor.getNome()) );
			}
		}
	}
	
	
	private Usuario criarModel(ResultSet rs) throws SQLException {
		Usuario user = new Usuario();
		user.setId(rs.getInt("id"));
		user.setNome(rs.getString("nome"));
		return user;
	}

}
