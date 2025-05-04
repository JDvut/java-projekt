package exceptions;

public class InvalidIdException extends Exception {
	public InvalidIdException() {
		super("ID must exist.");
	}
}
