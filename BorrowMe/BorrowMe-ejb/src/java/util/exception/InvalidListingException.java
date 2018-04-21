package util.exception;

public class InvalidListingException extends Exception{
     public InvalidListingException() {
    }
    
   public InvalidListingException(String msg){
       super(msg);
   }
}