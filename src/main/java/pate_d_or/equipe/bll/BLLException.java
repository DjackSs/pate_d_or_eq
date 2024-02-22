package pate_d_or.equipe.bll;

import java.util.HashMap;
import java.util.Map;

public class BLLException extends Exception 
{

	private static final long serialVersionUID = -8402728185430838053L;
  
	private Map<String, String> errors;

	public BLLException() 
	{
		this.errors = new HashMap<>();
	}

	public BLLException(String message, Throwable cause) 
	{
		super(message, cause);
		this.errors = new HashMap<>();
	}
	
	public void addError(String key, String value) 
	{
		this.errors.put(key, value);
	}
	
	public Map<String, String> getErrors()
	{
		return this.errors;
	}
}
