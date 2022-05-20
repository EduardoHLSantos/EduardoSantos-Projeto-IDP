package br.inatel.project.quotantionmanagment.handler;

import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import br.inatel.project.quotantionmanagment.dto.FormErrorDto;

@RestControllerAdvice
public class ValidationErrorHandler {

	@Autowired
	private MessageSource messageSource;

	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	public List<FormErrorDto> invalidArgumentHandler(MethodArgumentNotValidException exception) {
		List<FormErrorDto> dto = new ArrayList<>();
		List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();
		fieldErrors.forEach(e -> {
			String message = messageSource.getMessage(e, LocaleContextHolder.getLocale());
			FormErrorDto error = new FormErrorDto(e.getField(), message);
			dto.add(error);
		});
		return dto;
	}

	@ExceptionHandler(DateTimeParseException.class)
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	public List<FormErrorDto> parseExceptionHandler(DateTimeParseException exception) {
		List<FormErrorDto> dtos = new ArrayList<>();
		dtos.add(new FormErrorDto("Date", exception.getParsedString()));
		return dtos;
	}

	@ExceptionHandler(DataIntegrityViolationException.class)
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	public List<FormErrorDto> dataViolationHanlder(DataIntegrityViolationException exception) {
		List<FormErrorDto> dtos = new ArrayList<>();
		dtos.add(new FormErrorDto("Quotes", exception.getLocalizedMessage()));
		return dtos;
	}

	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	public List<String> parseExceptionHandler(HttpRequestMethodNotSupportedException exception) {
		List<String> messages = new ArrayList<>();
		messages.add(exception.getMessage());
		return messages;
	}
	
	@ExceptionHandler(HttpMessageNotReadableException.class)
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	public List<FormErrorDto> invalidArgumentHandler(HttpMessageNotReadableException exception) {
		List<FormErrorDto> dtos = new ArrayList<>();
		dtos.add(new FormErrorDto("Quotes", exception.getLocalizedMessage()));
		return dtos;
	}
}