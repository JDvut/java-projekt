package exceptions;

public class InvalidGroupIdException extends Exception{
    public InvalidGroupIdException() {
        super("Group_id must be 1 (Tel.) or 2 (Cyb.).");  
    }
}
