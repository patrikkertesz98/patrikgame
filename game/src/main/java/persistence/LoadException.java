package persistence;

/**
 * This class is the exception that needs to be thrown when "corrupted save file" occurs.
 */
public class LoadException extends Exception {
	public LoadException(String message) {
		super(message);
	}
}
