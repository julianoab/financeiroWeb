package financeiro.usuario;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Transaction;
import org.hibernate.classic.Session;

public class UsuarioDAOHibernate implements UsuarioDAO {

	private Session session;
	private Transaction transacao;

	public void setSession(Session session) {
		this.session = session;
	}

	public void salvar(Usuario usuario) {
		try {
			this.transacao = session.beginTransaction();
			this.session.save(usuario);
			this.session.flush();
			this.transacao.commit();
		} catch (HibernateException e) {
			System.out.println("Não foi possivel inserir o usuario. Erro"
					+ e.getMessage());
		} finally {
			try {
				if (this.session.isOpen()) {
					this.session.close();
				}
			} catch (Throwable e) {
				System.out
						.println("Erro ao fechar operação de inserção. Message"
								+ e.getMessage());
			}
		}
	}

	public void atualizar(Usuario usuario) {
		try {
			this.transacao = session.beginTransaction();
			this.session.update(usuario);
			this.session.flush();
			this.transacao.commit();
		} catch (HibernateException e) {
			System.out.println("Não foi possivel atualizar o usuario. Erro"
					+ e.getMessage());
		} finally {
			try {
				if (this.session.isOpen()) {
					this.session.close();
				}
			} catch (Throwable e) {
				System.out
						.println("Erro ao fechar operação de inserção. Message"
								+ e.getMessage());
			}
		}
	}

	public void excluir(Usuario usuario) {
		try {
			this.transacao = session.beginTransaction();
			this.session.delete(usuario);
			this.session.flush();
			this.transacao.commit();
		} catch (HibernateException e) {
			System.out.println("Não foi possivel atualizar o usuario. Erro"
					+ e.getMessage());
		} finally {
			try {
				if (this.session.isOpen()) {
					this.session.close();
				}
			} catch (Throwable e) {
				System.out
						.println("Erro ao fechar operação de inserção. Message"
								+ e.getMessage());
			}
		}
	}

	public Usuario carregar(Integer codigo) {
		return (Usuario) this.session.get(Usuario.class, codigo);
	}

	public List<Usuario> listar() {
		return this.session.createCriteria(Usuario.class).list();
	}

	public Usuario buscarPorLogin(String login) {
		String hql = "Select u from Usuario u where u.login = :login";
		Query consulta = this.session.createQuery(hql);
		consulta.setString("login", login);
		return (Usuario) consulta.uniqueResult();
	}

}
