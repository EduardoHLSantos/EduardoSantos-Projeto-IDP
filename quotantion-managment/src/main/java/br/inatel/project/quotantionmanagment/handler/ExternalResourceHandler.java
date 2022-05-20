package br.inatel.project.quotantionmanagment.handler;

import java.net.ConnectException;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.exception.JDBCConnectionException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExternalResourceHandler {

	@ExceptionHandler(ConnectException.class)
	public List<String> failToConnectHandler(ConnectException exception) {
		List<String> messages = new ArrayList<>();
		messages.add(exception.getMessage());
		return messages;
	}
	
	@ExceptionHandler(JDBCConnectionException.class)
	public List<String> failToConnectHandler(JDBCConnectionException exception) {
		List<String> messages = new ArrayList<>();
		messages.add(exception.getMessage());
		return messages;
	}
}