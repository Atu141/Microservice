package br.com.fiap.ms_pagamento.service.exceptions;

public class ResorceNotFoundException extends RuntimeException{
    public ResorceNotFoundException(String message) {
        super(message);
    }
}
