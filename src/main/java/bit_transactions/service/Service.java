package bit_transactions.service;

import java.util.List;


import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import bit_transactions.*;
import bit_transactions.dal.TransactionDao;
import bit_transactions.exceptions.InvalidPhoneNumber;
import bit_transactions.exceptions.InvalidTransactionAmount;
import bit_transactions.exceptions.InvalidTransactionAttempt;
import bit_transactions.exceptions.InvalidUpdateAttempt;
import bit_transactions.exceptions.NoSuchTransaction;
import bit_transactions.exceptions.TooManyTransactions;
import bit_transactions.transaciton_entity.Transaction;
import bit_transactions.transaciton_entity.TransactionType;


@Component
@PropertySource("classpath:param.properties")
public class Service {	
	
	
	@Autowired
	
	private TransactionDao daoHand; 
		
	@Value("${PhoneNumber}")
	private String myPhoneNum;
	
	@Value("${MaximumTransactions}")
	private int maxTrans;
	
	@Value("${MaximumPriceInTrans}")
	private float maxPrice;
	
	
	
	@PostConstruct
	private void serviceSetUp() {
		try {
			
			System.out.println(getTransactions() + "\n" + getUserBalance());
		} catch (Exception e) {
			e.getMessage();
		}
	}	
	
	
	public void addTransaction(Transaction t) throws Exception, InvalidPhoneNumber, InvalidTransactionAttempt, InvalidTransactionAmount, TooManyTransactions{
		//checking if the transaction is valid
		TransactionValidility.checkValidility(t, daoHand, myPhoneNum);
		TransactionValidility.checkValueValidility(t, maxPrice);
		
		if(getTransactions().size() > maxTrans)
			throw new TooManyTransactions("I'm sorry but you don't have space.");				
		
		daoHand.addTransaction(t);
		//adding or removing money from the user balance according to the transfer type
		if(t.getType() == TransactionType.send)
			daoHand.updateUserBalance(-t.getTransactionValue());
		else
			daoHand.updateUserBalance(t.getTransactionValue());
	}
	
	
	public Transaction getTransaction(int id) throws NoSuchTransaction, Exception{
		return daoHand.getTransaction(id);		
	}
	
	
	public List<Transaction> getTransactions() throws Exception {
		if(daoHand.getTransactions() == null)
        	daoHand.addTransaction(new Transaction(null, null, 0, null));

		return daoHand.getTransactions();	
	}
	
	
	public float getUserBalance() throws Exception {
		return daoHand.getUserBalance();	
	}

	
	
	public void removeTransaction(int id) throws Exception, NoSuchTransaction  {	
		Transaction trans = getTransaction(id);
		TransactionType type = trans.getType();
		float amount = trans.getTransactionValue();
		//adding or removing money from the user balance according to the transfer type
		if(type == TransactionType.send) {
			daoHand.updateUserBalance(amount);
			daoHand.removeTransaction(id);
		}
	}
	
	
	
	public void updateTransaction(int id, float newAmount) 
			throws Exception, InvalidTransactionAmount, NoSuchTransaction, InvalidUpdateAttempt, InvalidTransactionAmount {
		
		Transaction trans = getTransaction(id);
		TransactionType type = trans.getType();
		float oldAmount = trans.getTransactionValue();
			
		TransactionValidility.checkValueValidility(trans, maxPrice);

		if(newAmount > maxPrice || newAmount <= 0) {
			throw new InvalidTransactionAmount("You cannot update the transaction amount to: " + newAmount);
		}
		//adding or removing money from the user balance according to the transfer type
		if(type == TransactionType.send) {
			if(newAmount - oldAmount > getUserBalance())
				throw new InvalidUpdateAttempt("You cannot update the transaction amount to: " + newAmount);
			daoHand.updateUserBalance(oldAmount - newAmount);
		}
		else
			daoHand.updateUserBalance(newAmount - oldAmount);
		
		daoHand.updateTransaction(id, newAmount);
	}
	
	
	@PreDestroy
	private void serviceShutDown() {
		try {
			System.out.println(getTransactions() + "\n" + getUserBalance());
		} catch (Exception e) {
			e.getMessage();
		}
	}	
	
}
