package com.schoolvaccination.backend.exception;

public class CompletedVaccinationDriveException extends RuntimeException {
    public CompletedVaccinationDriveException(String message) {
        super(message);
    }
}