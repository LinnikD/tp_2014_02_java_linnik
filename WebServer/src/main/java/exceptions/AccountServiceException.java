package exceptions;

/**
 * Created by uzzz on 28.03.14.
 */

public class AccountServiceException extends Exception {
    public AccountServiceException(String message) {
        super(message);
    }
}
