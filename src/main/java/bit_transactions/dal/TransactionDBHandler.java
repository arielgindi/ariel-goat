package bit_transactions.dal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import bit_transactions.transaciton_entity.*;

@Component("transactionDBHandler")
@PropertySource("classpath:param.properties")
public class TransactionDBHandler implements TransactionDao {
	
	private SessionFactory factory;
	
	@PostConstruct
	private void daoSetUp() {
		 factory = new Configuration()
				.configure("hibernate.cfg.xml")
				.addAnnotatedClass(UserData.class)
				.addAnnotatedClass(Transaction.class)
				.buildSessionFactory();
		 
		Session session = factory.getCurrentSession();
		session.beginTransaction();
		Long count = (Long)session.createQuery("SELECT COUNT(*) FROM UserData").uniqueResult();
        if (count == 0) {
            UserData newUser = new UserData(10000);
            session.save(newUser);
        }
        session.getTransaction().commit();
        session.close();
		
	}

	public float getUserBalance() throws Exception {
		Session session = factory.getCurrentSession();
		session.beginTransaction();
		UserData ud = session.get(UserData.class, "0538305358");
		session.getTransaction().commit();
		session.close();
		return ud.getBalance();
	}

	public void updateUserBalance(float amount) throws Exception {
		Session session = factory.getCurrentSession();
		session.beginTransaction();
		UserData ud = session.get(UserData.class, "0538305358");
		ud.setBalance(ud.getBalance() + amount);
		session.getTransaction().commit();
		session.close();
	}

	public List<Transaction> getTransactions() throws Exception {
		Session session = factory.getCurrentSession();
		session.beginTransaction();
		ArrayList<Transaction> list = (ArrayList<Transaction>)session.createQuery("from Transaction").getResultList();	
		Collections.sort(list);
		session.close();
		return list;
	}

	public void addTransaction(Transaction t) throws Exception {
		Session session = factory.getCurrentSession();
		session.beginTransaction();
		session.save(t);
		session.getTransaction().commit();
		session.close();
	}

	public void removeTransaction(int id) throws Exception{
		Session session = factory.getCurrentSession();
		session.beginTransaction();
		session.createQuery("delete from transactions where id=" + id).executeUpdate();
		session.getTransaction().commit();
		session.close();
	}

	public void updateTransaction(int id, float newAmount)throws Exception {
		Session session = factory.getCurrentSession();
		session.beginTransaction();
		session.createQuery("update transactions set transaction_value=" + newAmount + "where id=" + id);
		session.getTransaction().commit();
		session.close();
	}

	public Transaction getTransaction(int id) throws Exception {
		Session session = factory.getCurrentSession();
		session.beginTransaction();
		Transaction t = session.get(Transaction.class, id);
		session.close();
		return t;
	}
	
	@PreDestroy
	public void closeSessionFactory() {
	    if (factory != null) {
	        factory.close();
	        System.out.println("SessionFactory closed.");
	    }
	}
	
}
