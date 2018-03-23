package util.exception;

public class InvalidItemException extends Exception{
     public InvalidItemException() {
    }
    
   public InvalidItemException(String msg){
       super(msg);
   }
}