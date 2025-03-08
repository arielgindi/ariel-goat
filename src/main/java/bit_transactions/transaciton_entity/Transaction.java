package bit_transactions.transaciton_entity;
import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "transactions")
public class Transaction implements Serializable ,Comparable<Transaction>{

	private static final long serialVersionUID = 1L;
	
	@Column(name = "type")
	private TransactionType type;
	
	@Column(name = "other_phone_number")
	private String otherPhoneNum;
	
	@Column(name = "transaction_value")
	private float transactionValue;
	
	@Column(name = "transaction_date")
	private LocalDate transactionDate;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	
	@Column(name = "transaction_reason")
	private String transactionReason;
	
	public Transaction(TransactionType type, String otherPhoneNum ,float transactionValue, String transactionReason) {
		this.type = type;
		this.otherPhoneNum = otherPhoneNum;
		this.transactionValue = transactionValue;
		this.transactionDate = LocalDate.now();
		this.transactionReason = transactionReason;
	}
	
	public Transaction() {
		
	}
	
	

	public TransactionType getType() {
		return type;
	}

	public String getOtherPhoneNum() {
		return otherPhoneNum;
	}

	public float getTransactionValue() {
		return transactionValue;
	}

	public void setTransactionValue(float transactionValue) {
		this.transactionValue = transactionValue;
	}

	public LocalDate getTransactionDate() {
		return transactionDate;
	}
	
	public String getTransactionReason() {
		return transactionReason;
	}
	
	public int getTransactionId() {
		return id;
	}
	
	public void setTransactionId(int id) {
		this.id =  id;
	}
	
	public boolean equals(Object o) {
		Transaction t;
		if(this == o) {
			return true;
		}
		if(o == null) {
			return false;
		}
		if(this.getClass() == o.getClass()) {
			t = (Transaction)o;
			return id == t.getTransactionId();
		}
		return false;
	}


	@Override
	public int compareTo(Transaction other) {
		// return negative number if this is earlier than other
		// return positive number if this is later than other
		// return 0 if they are created on the same day
	    return this.transactionDate.compareTo(other.transactionDate);
	}

	@Override
	public String toString() {
		return "\nTransaction Id: " + id + "\nOther phone number: " + otherPhoneNum + "\nTransaction Value: " + transactionValue
				+ "\nTransaction Date: " + transactionDate + "\nTransaction type: " + type + "\nTransaction Reason: " + transactionReason + "\n";
	}
	
	
}