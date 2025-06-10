package com.clinic.vet_clinic.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.NoSuchElementException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Captura a exceção de regra de negócio (conflito de horário).
     * Retorna um status 400 (Bad Request), que é mais apropriado que um erro de servidor.
     */
    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<ErrorResponse> handleIllegalStateException(IllegalStateException ex) {
        // Log do erro no console do backend (bom para debug)
        System.err.println("Erro de regra de negócio: " + ex.getMessage());

        // Cria nossa resposta de erro padronizada
        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage());

        // Retorna a resposta com o status HTTP 400
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    /**
     * Captura a exceção de "não encontrado" (ex: ID de pet ou usuário inválido).
     * Retorna um status 404 (Not Found).
     */
    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ErrorResponse> handleNoSuchElementException(NoSuchElementException ex) {
        System.err.println("Recurso não encontrado: " + ex.getMessage());
        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    // Você pode adicionar outros handlers para outras exceções aqui
}