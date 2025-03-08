package bit_transactions.dal;
import java.io.Serializable;
import java.util.ArrayList;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import bit_transactions.transaciton_entity.Transaction;

@Entity
@Table(name = "userdata")
public class UserData implements Serializable{
	@Id
	@Column(name = "phone_number")
	private String phone_number;
	private static final long serialVersionUID = 1L;
	@Transient
	ArrayList<Transaction> list; 
	@Column(name = "balance")
	private float balance;
	@Transient
	private int lastUsedIndex = 1;
	
	public UserData(float startingBalance) {
		this.list = new ArrayList<>();
		this.balance = startingBalance;
		this.phone_number = "0538305358";
	}
	
	public UserData() {
		
	}

	@Override
	public String toString() {
		return "UserData [phone_number= " + phone_number + ", list=" + list + ", balance=" + balance + ", last used index: " + lastUsedIndex + "]";
	}
	
	

	

	public String getPhone_number() {
		return phone_number;
	}

	public void setPhone_number(String phone_number) {
		this.phone_number = phone_number;
	}



	public void setList(ArrayList<Transaction> list) {
		this.list = list;
	}

	public void setLastUsedIndex(int lastUsedIndex) {
		this.lastUsedIndex = lastUsedIndex;
	}

	public ArrayList<Transaction> getList() {
		return list;
	}

	public float getBalance() {
		return balance;
	}

	public void setBalance(float balance) {
		this.balance = balance;
	}
	
	public int getLastUsedIndex() {
		return lastUsedIndex;
	}
	
	public void incLastUsedIndex() {
		this.lastUsedIndex++;
	}
	
}
