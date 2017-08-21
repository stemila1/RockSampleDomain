package rocksample.state;

public class ObjectNotFoundException extends RuntimeException{

	private static final String DEFAULT_MESSAGE = "Burlap failed to find the requested object.";

	public ObjectNotFoundException(){
		super(DEFAULT_MESSAGE);
	}
	
	public ObjectNotFoundException(String message){
		super(message);
	}
}
