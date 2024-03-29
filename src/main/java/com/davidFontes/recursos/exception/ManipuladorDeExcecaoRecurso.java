package com.davidFontes.recursos.exception;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.davidFontes.servicos.exception.ArquivoException;
import com.davidFontes.servicos.exception.AutorizacaoException;
import com.davidFontes.servicos.exception.ObjetoNaoEncontradoException;
import com.davidFontes.servicos.exception.ViolacaoDeRestricaoDeIntegridadeException;

@ControllerAdvice
public class ManipuladorDeExcecaoRecurso {

	@ExceptionHandler(ObjetoNaoEncontradoException.class)
	public ResponseEntity<ErroPadrao> objetoNaoEncontrado(ObjetoNaoEncontradoException e, HttpServletRequest request){
		
		ErroPadrao err = new ErroPadrao(System.currentTimeMillis(),HttpStatus.NOT_FOUND.value(), "Não encontrado", e.getMessage(), request.getRequestURI());	
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
	}
	
	@ExceptionHandler(ViolacaoDeRestricaoDeIntegridadeException.class)
	public ResponseEntity<ErroPadrao> restricaoDeIntegridade(ViolacaoDeRestricaoDeIntegridadeException e, HttpServletRequest request){
		
		ErroPadrao err = new ErroPadrao(System.currentTimeMillis(),HttpStatus.BAD_REQUEST.value(), "Integridade de dados", e.getMessage(), request.getRequestURI());
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErroPadrao> validacao(MethodArgumentNotValidException e, HttpServletRequest request){
		
		ErroValidacao err = new ErroValidacao(System.currentTimeMillis(),HttpStatus.UNPROCESSABLE_ENTITY.value(), "Erro de validação", e.getMessage(), request.getRequestURI());
		for(FieldError x: e.getBindingResult().getFieldErrors()) {
			err.addErro(x.getField(), x.getDefaultMessage());
		}
		return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(err);
	}
	
	@ExceptionHandler(AutorizacaoException.class)
	public ResponseEntity<ErroPadrao> authorization(AutorizacaoException e, HttpServletRequest request) {

		ErroPadrao err = new ErroPadrao(System.currentTimeMillis(),HttpStatus.FORBIDDEN.value(), "Acesso negado", e.getMessage(), request.getRequestURI());
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(err);
	}
	
	@ExceptionHandler(ArquivoException.class)
	public ResponseEntity<ErroPadrao> arquivo(ArquivoException e, HttpServletRequest request) {

		ErroPadrao err = new ErroPadrao(System.currentTimeMillis(),HttpStatus.BAD_REQUEST.value(), "Erro de arquivo", e.getMessage(), request.getRequestURI());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
	}
	
	@ExceptionHandler(AmazonServiceException .class)
	public ResponseEntity<ErroPadrao> amazonService(AmazonServiceException e, HttpServletRequest request) {

		HttpStatus code = HttpStatus.valueOf(e.getErrorCode());
		
		ErroPadrao err = new ErroPadrao(System.currentTimeMillis(),code.value(), "Erro Amazon Service", e.getMessage(), request.getRequestURI());
		return ResponseEntity.status(code).body(err);
	}
	
	@ExceptionHandler(AmazonClientException.class)
	public ResponseEntity<ErroPadrao> amazonClient(AmazonClientException e, HttpServletRequest request) {

		ErroPadrao err = new ErroPadrao(System.currentTimeMillis(),HttpStatus.BAD_REQUEST.value(), "Erro Amazon Client", e.getMessage(), request.getRequestURI());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
	}
	
	@ExceptionHandler(AmazonS3Exception.class)
	public ResponseEntity<ErroPadrao> amazonS3(AmazonS3Exception e, HttpServletRequest request) {

		ErroPadrao err = new ErroPadrao(System.currentTimeMillis(),HttpStatus.BAD_REQUEST.value(), "Erro S3", e.getMessage(), request.getRequestURI());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
	}
	
}
