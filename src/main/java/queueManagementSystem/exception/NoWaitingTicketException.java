package queueManagementSystem.exception;

public class NoWaitingTicketException extends RuntimeException{
    public NoWaitingTicketException(String message) {
        super(message);
    }
}
