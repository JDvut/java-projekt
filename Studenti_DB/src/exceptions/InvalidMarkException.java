package exceptions;

public class InvalidMarkException extends Exception {
	public InvalidMarkException() {
		super("Mark must an int from 1 to 5.");
	}
}
