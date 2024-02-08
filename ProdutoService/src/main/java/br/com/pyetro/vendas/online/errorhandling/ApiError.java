package br.com.pyetro.vendas.online.errorhandling;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.boot.context.properties.bind.BindException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.client.HttpClientErrorException.NotFound;
import org.springframework.web.client.HttpClientErrorException.UnprocessableEntity;

import com.fasterxml.jackson.annotation.JsonFormat;

import br.com.pyetro.vendas.online.errorhandling.ApiError.StatusError;
import br.com.pyetro.vendas.online.exception.BadRequestException;
import br.com.pyetro.vendas.online.exception.EntityNotFoundException;
import jakarta.validation.ConstraintViolation;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiError {

    private HttpStatus status;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private LocalDateTime timestamp;
    private String message;
    private String debugMessage;
    private List<ApiSubError> subErrors;
    private StatusError status1;

    private ApiError() {
        timestamp = LocalDateTime.now();
    }

    public ApiError(HttpStatus status) {
        this();
        this.status = status;
    }

    public ApiError(HttpStatus status, Throwable ex) {
        this();
        this.status = status;
        this.message = "Unexpected error";
        this.debugMessage = ex.getLocalizedMessage();
    }

    public ApiError(HttpStatus status, String message, Throwable ex) {
        this();
        this.status = status;
        this.message = message;
        this.debugMessage = ex.getLocalizedMessage();
    }

    private void addSubError(ApiSubError subError) {
        if (subErrors == null) {
            subErrors = new ArrayList<>();
        }
        subErrors.add(subError);
    }

    private void addValidationError(String object, String field, Object rejectedValue, String message) {
        addSubError(new ApiValidationError(object, field, rejectedValue, message));
    }

    private void addValidationError(String object, String message) {
        addSubError(new ApiValidationError(object, message));
    }

    private void addValidationError(FieldError fieldError) {
        this.addValidationError(
                fieldError.getObjectName(),
                fieldError.getField(),
                fieldError.getRejectedValue(),
                fieldError.getDefaultMessage());
    }

    public void addValidationErrors(List<FieldError> fieldErrors) {
        fieldErrors.forEach(this::addValidationError);
    }

    private void addValidationError(ObjectError objectError) {
        this.addValidationError(
                objectError.getObjectName(),
                objectError.getDefaultMessage());
    }

    public void addValidationError(List<ObjectError> globalErrors) {
        globalErrors.forEach(this::addValidationError);
    }

    
    private void addValidationError(ConstraintViolation<?> cv) {
        this.addValidationError(
                cv.getRootBeanClass().getSimpleName(),
                "",
                cv.getInvalidValue(),
                cv.getMessage());
    }

    public void addValidationErrors(Set<ConstraintViolation<?>> constraintViolations) {
        constraintViolations.forEach(this::addValidationError);
    }

    public void setMessage(String string) {
		// TODO Auto-generated method stub

	}

	public enum StatusError {
		ERRO_INTERNO_SERVIDOR(500, "Erro interno do servidor"), REQUISICAO_INVALIDA(400, "Requisição inválida"),
		NAO_AUTORIZADO(401, "Não autorizado"), NAO_ENCONTRADO(404, "Recurso não encontrado");

		private final int codigo;
		private final String mensagem;

		StatusError(int codigo, String mensagem) {
			this.codigo = codigo;
			this.mensagem = mensagem;
		}

		public int getCodigo() {
			return codigo;
		}

		public String getMensagem() {
			return mensagem;
		}
	}

	public StatusError getStatus() {

		return status1;
	}

}