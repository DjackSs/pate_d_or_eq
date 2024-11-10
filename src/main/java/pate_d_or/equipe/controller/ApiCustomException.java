package pate_d_or.equipe.controller;

import java.util.Map;

import org.springframework.http.HttpStatus;

public class ApiCustomException extends RuntimeException
{
	private static final long serialVersionUID = 6329398917499278863L;
	
	private Map<String,String> errors;
	private HttpStatus status;
	
	public ApiCustomException(String message)
	{
		super(message);
		this.errors = null;
		this.status = null;
	}
	
	public ApiCustomException(Map<String,String> errors, HttpStatus status)
	{
		super();
		this.errors = errors;
		this.status = status;
	}
	
	public Map<String, String> getErrors()
	{
		return this.errors;
	}
	
	public HttpStatus getStatus()
	{
		return this.status;
	}
	

}
