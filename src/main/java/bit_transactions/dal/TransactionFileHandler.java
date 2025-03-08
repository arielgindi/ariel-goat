package bit_transactions.dal;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import bit_transactions.exceptions.NoSuchTransaction;
import bit_transactions.transaciton_entity.*;



@Component("transactionFileHandler")
@PropertySource("classpath:param.properties")
public class TransactionFileHandler {
				
			
		@Value("${StartingBalance}")
		private float startingBalance;
	
	
	@PostConstruct
	private void daoSetUp() {
		UserData ud = new UserData(startingBalance);
		try {
            if (!SerializeUtils.fileExists()) 
                SerializeUtils.serializeData(ud); 
        } catch (IOException e) {
            e.printStackTrace();
        }
	}	
	
	public float getUserBalance() throws IOException, ClassNotFoundException{
		// Get the users data and return the users balance
		UserData ud = SerializeUtils.deserializeData();
		return ud.getBalance();
	}
	
	
	public void updateUserBalance(float amount) throws IOException, ClassNotFoundException {
	    // Get the user data
	    UserData ud = SerializeUtils.deserializeData();
	    // Update the balance 
	    ud.setBalance(ud.getBalance() + amount);
	    // Put the new data back in the file
	    SerializeUtils.serializeData(ud);
	}
	
	
	public List<Transaction> getTransactions()throws IOException, ClassNotFoundException{
		// Get the users data and return the transactions list
		UserData ud = SerializeUtils.deserializeData();
		// sort the list by transaction date
		ArrayList<Transaction> list = ud.getList();
		Collections.sort(list);
		return list;
	}
	
	public void addTransaction(Transaction t) throws IOException, ClassNotFoundException{
		// Get the users data
		
		UserData ud = SerializeUtils.deserializeData();
		ArrayList<Transaction> list = ud.getList();
		t.setTransactionId(ud.getLastUsedIndex());
		// Add the data to the list
		list.add(t);
		ud.incLastUsedIndex();
		// Put the new data back in the file
		SerializeUtils.serializeData(ud);
	}
	
	public void removeTransaction(int id) throws ClassNotFoundException, IOException, NoSuchTransaction {
		// Get the users data and remove the transaction
		UserData ud = SerializeUtils.deserializeData();
		ArrayList<Transaction> list = ud.getList();
		int indexToRemove = getTransactionIndex(id);
		list.remove(indexToRemove);
		// Put the new data back in the file
		SerializeUtils.serializeData(ud);
	}
	
	public void updateTransaction(int id, float newAmount) throws ClassNotFoundException, IOException, NoSuchTransaction {
		// Get the users data and remove the transaction
		UserData ud = SerializeUtils.deserializeData();
		ArrayList<Transaction> list = ud.getList();
		int indexToUpdate = getTransactionIndex(id);
		// Update the transaction value to the new value
		list.get(indexToUpdate).setTransactionValue(newAmount);
		// Put the new data back in the file
		SerializeUtils.serializeData(ud);
	}
	
	public Transaction getTransaction(int id) throws ClassNotFoundException, IOException, NoSuchTransaction  {
		// Get the users data and remove the transaction
		UserData ud = SerializeUtils.deserializeData();
		ArrayList<Transaction> list = ud.getList();
		return list.get(getTransactionIndex(id));
	}
	

	private int getTransactionIndex(int id) throws IOException, ClassNotFoundException, NoSuchTransaction{
		Transaction testTransaction = new Transaction(TransactionType.receive, "", 0, "");
		testTransaction.setTransactionId(id);
		// Get the index of a transaction by id, if it doesn't exist throw an error
		UserData ud = SerializeUtils.deserializeData();
		ArrayList<Transaction> list = ud.getList();
		for(int i = 0; i < list.size(); i++) {
			if(list.get(i).equals(testTransaction)) {
				return i;
			}
		}
		// throw no such transaction error
		throw new NoSuchTransaction(id);
	}
}
