package org.project.loja_virtual.controller;


import org.hibernate.exception.ConstraintViolationException;
import org.project.loja_virtual.model.dto.ObjectErroDTO;
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

    @ExceptionHandler(ExceptionCustom.class)
    public ResponseEntity<Object> handleExceptionCustom(ExceptionCustom ex) {

        ObjectErroDTO objectErroDTO = new ObjectErroDTO();

        objectErroDTO.setError(ex.getMessage());
        objectErroDTO.setCode(HttpStatus.OK.toString());

        return new ResponseEntity<Object>(objectErroDTO, HttpStatus.OK);
    }


    /*Captura execeçoes do projeto*/
    @ExceptionHandler({Exception.class, RuntimeException.class, Throwable.class})
    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers,
                                                             HttpStatus status, WebRequest request) {

        ObjectErroDTO objectErroDTO = new ObjectErroDTO();

        String msg = "";

        if (ex instanceof MethodArgumentNotValidException) {

            List<ObjectError> list = ((MethodArgumentNotValidException) ex).getBindingResult().getAllErrors();

            for (ObjectError objectError : list) {
                msg += objectError.getDefaultMessage() + "\n";
            }
        } else {
            msg = ex.getMessage();
        }

        objectErroDTO.setError(msg);
        objectErroDTO.setCode(status.value() + " ==> " + status.getReasonPhrase());

        ex.printStackTrace();

        return new ResponseEntity<Object>(objectErroDTO, HttpStatus.INTERNAL_SERVER_ERROR);
    }


    /*Captura erro na parte de banco*/
    @ExceptionHandler({DataIntegrityViolationException.class,
            ConstraintViolationException.class, SQLException.class})
    protected ResponseEntity<Object> handleExceptionDataIntegry(Exception ex) {

        ObjectErroDTO objectErroDTO = new ObjectErroDTO();

        String msg = "";

        if (ex instanceof DataIntegrityViolationException) {
            msg = "Erro de integridade no banco: " + ((DataIntegrityViolationException) ex).getCause().getCause().getMessage();
        } else if (ex instanceof ConstraintViolationException) {
            msg = "Erro de chave estrangeira: " + ((ConstraintViolationException) ex).getCause().getCause().getMessage();
        } else if (ex instanceof SQLException) {
            msg = "Erro de SQL do Banco: " + ((SQLException) ex).getCause().getCause().getMessage();
        } else {
            msg = ex.getMessage();
        }

        objectErroDTO.setError(msg);
        objectErroDTO.setCode(HttpStatus.INTERNAL_SERVER_ERROR.toString());

        ex.printStackTrace();

        return new ResponseEntity<Object>(objectErroDTO, HttpStatus.INTERNAL_SERVER_ERROR);

    }
}
