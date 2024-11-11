package org.project.loja_virtual.controller;

import org.hibernate.exception.ConstraintViolationException;
import org.project.loja_virtual.model.dto.ObjectErroDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.sql.SQLException;
import java.util.List;

/**
 * Classe responsável por tratar exceções de forma centralizada na aplicação.
 * Oferece respostas padronizadas em casos de erros de validação, integridade de dados e genéricos.
 */
@RestControllerAdvice
public class ControllExceptions extends ResponseEntityExceptionHandler {

    private static Logger LOGGER = LoggerFactory.getLogger(ControllExceptions.class);

    /**
     * Manipula exceções genéricas e fornece uma resposta padronizada.
     *
     * @param ex      A exceção lançada.
     * @param body    O corpo da resposta original.
     * @param headers Os cabeçalhos HTTP da resposta.
     * @param status  O status HTTP da resposta.
     * @param request A solicitação web associada.
     * @return Um ResponseEntity contendo detalhes do erro.
     */
    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {

        ObjectErroDTO errorResponse = new ObjectErroDTO();
        String errorMessage = getErrorMessage(ex);

        errorResponse.setError(errorMessage);
        errorResponse.setCode(status.value() + " ==> " + status.getReasonPhrase());

        logError(ex); // Log do erro para auditoria
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }

    /**
     * Manipula exceções relacionadas à integridade de dados, como violações de chave ou constraints.
     *
     * @param ex A exceção lançada.
     * @return Um ResponseEntity com detalhes sobre a violação de dados.
     */
    @ExceptionHandler({DataIntegrityViolationException.class, ConstraintViolationException.class, SQLException.class})
    protected ResponseEntity<Object> handleDataIntegrityException(Exception ex) {

        ObjectErroDTO errorResponse = new ObjectErroDTO();
        String errorMessage = getRootCauseMessage(ex);

        errorResponse.setError(errorMessage);
        errorResponse.setCode(HttpStatus.INTERNAL_SERVER_ERROR + " ==> " + HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());

        logError(ex); // Log do erro para auditoria
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }

    /**
     * Extrai a mensagem de erro de exceções específicas, como validação de argumentos.
     *
     * @param ex A exceção lançada.
     * @return Uma string contendo a mensagem do erro.
     */
    private String getErrorMessage(Exception ex) {
        if (ex instanceof MethodArgumentNotValidException) {
            List<ObjectError> errors = ((MethodArgumentNotValidException) ex).getBindingResult().getAllErrors();
            StringBuilder messageBuilder = new StringBuilder();
            errors.forEach(error -> messageBuilder.append(error.getDefaultMessage()).append("\n"));
            return messageBuilder.toString().trim();
        }
        return ex.getMessage();
    }

    /**
     * Recupera a mensagem raiz da causa de exceções de integridade de dados.
     *
     * @param ex A exceção lançada.
     * @return Uma string com a mensagem da causa raiz do erro.
     */
    private String getRootCauseMessage(Exception ex) {
        Throwable rootCause = ex.getCause();
        while (rootCause != null && rootCause.getCause() != null) {
            rootCause = rootCause.getCause();
        }
        return rootCause != null ? rootCause.getMessage() : ex.getMessage();
    }

    /**
     * Loga detalhes do erro no sistema para auditoria e depuração.
     *
     * @param ex A exceção lançada.
     */
    private void logError(Exception ex) {
        LOGGER.error("Erro capturado: " + ex.getClass().getName() + " - " + ex.getMessage());
    }
}