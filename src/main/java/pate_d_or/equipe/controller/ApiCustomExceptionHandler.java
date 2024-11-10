package pate_d_or.equipe.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice()
public class ApiCustomExceptionHandler extends ResponseEntityExceptionHandler
{
	@ExceptionHandler(value= {ApiCustomException.class})
	public ResponseEntity<Object> pateDOrExceptionHandler(ApiCustomException error)
	{
		return new ResponseEntity<>(error.getErrors(), error.getStatus());
		
	}

}
