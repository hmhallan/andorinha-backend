package repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import model.Tweet;
import model.Usuario;
import model.exceptions.ErroAoConectarNaBaseException;
import model.exceptions.ErroAoConsultarBaseException;

public class TweetRepository extends AbstractCrudRepository {

	public void inserir(Tweet tweet) throws ErroAoConsultarBaseException, ErroAoConectarNaBaseException {
		try (Connection c = this.abrirConexao()) {

			int id = this.recuperarProximoValorDaSequence("seq_tweet");
			tweet.setId(id);
			
			Calendar hoje = Calendar.getInstance();

			PreparedStatement ps = c.prepareStatement("insert into tweet (id, conteudo, data_postagem, id_usuario) values (?,?,?,?)");
			ps.setInt(1, tweet.getId());
			ps.setString(2, tweet.getConteudo());
			ps.setTimestamp(3, new Timestamp(hoje.getTimeInMillis()));
			ps.setInt(4, tweet.getUsuario().getId());
			ps.execute();
			ps.close();

		} catch (SQLException e) {
			throw new ErroAoConsultarBaseException("Ocorreu um erro ao inserir o tweet", e);
		}
	}

	public void atualizar(Tweet tweet) throws ErroAoConsultarBaseException, ErroAoConectarNaBaseException {
		try (Connection c = this.abrirConexao()) {
			
			Calendar hoje = Calendar.getInstance();

			PreparedStatement ps = c.prepareStatement("update tweet set conteudo = ?, data_postagem = ? where id = ?");
			ps.setString(1, tweet.getConteudo());
			ps.setTimestamp(2, new Timestamp(hoje.getTimeInMillis()));
			ps.setInt(3, tweet.getId());
			ps.execute();
			ps.close();

		} catch (SQLException e) {
			throw new ErroAoConsultarBaseException("Ocorreu um erro ao atualizar o tweet", e);
		}
	}

	public void remover(int id) throws ErroAoConectarNaBaseException, ErroAoConsultarBaseException {
		try (Connection c = this.abrirConexao()) {

			PreparedStatement ps = c.prepareStatement("delete from tweet where id = ?");
			ps.setInt(1, id);
			ps.execute();
			ps.close();

		} catch (SQLException e) {
			throw new ErroAoConsultarBaseException("Ocorreu um erro ao remover o tweet", e);
		}

	}

	public Tweet consultar(int id) throws ErroAoConsultarBaseException, ErroAoConectarNaBaseException {
		try (Connection c = this.abrirConexao()) {

			Tweet tweet = null;
			
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT t.id, t.conteudo, t.data_postagem, t.id_usuario, u.nome as nome_usuario FROM tweet t ");
			sql.append("JOIN usuario u on t.id_usuario = u.id ");
			sql.append("WHERE t.id = ? ");
			
			PreparedStatement ps = c.prepareStatement(sql.toString());
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			
			if (rs.next()) {
				tweet = new Tweet();
				tweet.setId(rs.getInt("id"));
				tweet.setConteudo(rs.getString("conteudo"));
				
				Calendar data = new GregorianCalendar();
				data.setTime( rs.getTimestamp("data_postagem") );
				tweet.setData(data);
				
				Usuario user = new Usuario();
				user.setId(rs.getInt("id_usuario"));
				user.setNome(rs.getString("nome_usuario"));
				tweet.setUsuario(user);
			}
			rs.close();
			ps.close();

			return tweet;

		} catch (SQLException e) {
			throw new ErroAoConsultarBaseException("Ocorreu um erro ao consultar o tweet", e);
		}
	}

	public List<Tweet> listarTodos() throws ErroAoConsultarBaseException, ErroAoConectarNaBaseException {
		try (Connection c = this.abrirConexao()) {

			List<Tweet> tweets = new ArrayList<>();
			
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT t.id, t.conteudo, t.data_postagem, t.id_usuario, u.nome as nome_usuario FROM tweet t ");
			sql.append("JOIN usuario u on t.id_usuario = u.id ");
			
			PreparedStatement ps = c.prepareStatement(sql.toString());

			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Tweet tweet = new Tweet();
				tweet = new Tweet();
				tweet.setId(rs.getInt("id"));
				tweet.setConteudo(rs.getString("conteudo"));
				
				Calendar data = new GregorianCalendar();
				data.setTime( rs.getTimestamp("data_postagem") );
				tweet.setData(data);
				
				Usuario user = new Usuario();
				user.setId(rs.getInt("id_usuario"));
				user.setNome(rs.getString("nome_usuario"));
				tweet.setUsuario(user);
				
				tweets.add(tweet);
			}
			rs.close();
			ps.close();

			return tweets;

		} catch (SQLException e) {
			throw new ErroAoConsultarBaseException("Ocorreu um erro ao consultar todos os tweets", e);
		}
	}


}
