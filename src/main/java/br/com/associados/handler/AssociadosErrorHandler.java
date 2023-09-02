package br.com.associados.handler;

import br.com.associados.exceptions.AssociadoNaoEncontradoException;
import br.com.associados.exceptions.BoletoEmAbertoException;
import br.com.associados.exceptions.FormatoDocumentoInvalidoException;
import br.com.associados.exceptions.TipoPessoaInconsistenteException;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class AssociadosErrorHandler {

    @ExceptionHandler(value = {ConstraintViolationException.class})
    public ResponseEntity<Object> catchConstraintViolationException(RuntimeException ex, WebRequest request) {
        return new ResponseEntity<Object>("Já Existe um associado para o documento informado.", new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {TipoPessoaInconsistenteException.class, FormatoDocumentoInvalidoException.class, AssociadoNaoEncontradoException.class, BoletoEmAbertoException.class})
    public ResponseEntity<Object> catchTipoPessoaInconsistenteException(RuntimeException ex, WebRequest request) {
        return new ResponseEntity<Object>(ex.getMessage(), new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {javax.validation.ConstraintViolationException.class})
    public ResponseEntity<Object> catchJavaxConstraintViolationException(RuntimeException ex, WebRequest request) {
        return new ResponseEntity<Object>(ex.getMessage(), new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

}