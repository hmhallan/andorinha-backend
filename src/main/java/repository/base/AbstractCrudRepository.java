package repository.base;

import java.lang.reflect.ParameterizedType;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;

import model.Tweet;
import model.Usuario;
import model.exceptions.ErroAoConectarNaBaseException;
import model.exceptions.ErroAoConsultarBaseException;
import model.seletor.TweetSeletor;

public abstract class AbstractCrudRepository<T> {
	
	@Resource(name = "andorinhaDS")
	protected DataSource ds;
	
	@PersistenceContext
	protected EntityManager em;
	
	protected Class<T> persistentClass;
	
	@PostConstruct
	public void init() {
		this.persistentClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
	}
	
	public void inserir(T entity)  {
		this.em.persist(entity);
	}
	
	public void atualizar(T entity)  {
		this.em.merge(entity);
	}
	
	public T consultar(int id)  {
		return this.em.find(this.persistentClass, id);
	}
	
	public void remover(int id)  {
		T entity = this.consultar(id);
		this.em.remove(entity);
	}
	
	public List<T> listarTodos()  {
		return this.em.createQuery("select t from " + this.persistentClass.getName() + " t" ).getResultList();
	}
	
	protected EntityQuery<T> createEntityQuery() {
		return EntityQuery.create(this.em, this.persistentClass);
	}
	
	protected EntityQuery<T> createCountQuery() {
		return EntityQuery.createCount(this.em, this.persistentClass);
	}
	
	protected TupleQuery<T> createTupleQuery() {
		return TupleQuery.create(this.em, this.persistentClass);
	}

	

}
