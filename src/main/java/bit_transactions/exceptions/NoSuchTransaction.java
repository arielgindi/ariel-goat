package bit_transactions.exceptions;

public class NoSuchTransaction extends Exception{

	public NoSuchTransaction(int id) {
		super("There is no transaction with the id: " + id);
	}
	
}
	