package br.com.pyetro.vendas.online.errorhandling;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
public class ApiValidationError extends ApiSubError {

	private String object;
	private String field;
	private Object rejectedValue;
	private String message;
	
	public ApiValidationError(String object, String field, Object rejectedValue, String message) {
		this.field = field;
		this.rejectedValue = rejectedValue;
		
	}

	public ApiValidationError(String object, String message) {
		this.object = object;
		this.message = message;
		
	
	}

}
