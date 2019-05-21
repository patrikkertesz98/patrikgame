package model;


/**
 * This is a unique exception that needs to be thrown when an ivalid move occurs.
 */
public class MoveException extends Exception {

	public MoveException(String message) {
		super(message);
	}
}
