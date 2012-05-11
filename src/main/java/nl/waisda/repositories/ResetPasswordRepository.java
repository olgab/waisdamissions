package nl.waisda.repositories;

import javax.persistence.TypedQuery;

import nl.waisda.domain.ResetPassword;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Repository
@Transactional
public class ResetPasswordRepository extends AbstractRepository<ResetPassword> {

	public ResetPasswordRepository() {
		super(ResetPassword.class);
	}
	
	@Transactional
	@Override
	public void store(ResetPassword resetPassword) {
		super.store(resetPassword);
	}

	public ResetPassword getUserByEmail(String email, String key) {
		TypedQuery<ResetPassword> query = getEntityManager().createQuery("SELECT rpw FROM ResetPassword rpw WHERE rpw.resetkey = :key AND rpw.user.email = :email", ResetPassword.class);
		query.setParameter("key", key);
		query.setParameter("email", email);
		return getSingleResult(query);
	}

}