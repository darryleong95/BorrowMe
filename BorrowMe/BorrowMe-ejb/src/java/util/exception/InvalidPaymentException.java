package util.exception;

public class InvalidPaymentException extends Exception{
     public InvalidPaymentException() {
    }
    
   public InvalidPaymentException(String msg){
       super(msg);
   }
}