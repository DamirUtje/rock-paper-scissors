package de.damirutje.rockpaperscissors.controller;

import de.damirutje.rockpaperscissors.exception.GameNotExistException;
import de.damirutje.rockpaperscissors.exception.GameReadonlyException;
import de.damirutje.rockpaperscissors.exception.InvalidGameMoveException;
import de.damirutje.rockpaperscissors.exception.InvalidGameSettingsException;
import de.damirutje.rockpaperscissors.model.ErrorDescription;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class GameControllerErrorHandler {

    @ExceptionHandler(GameNotExistException.class)
    public ResponseEntity<ErrorDescription> handleGameNotExists(WebRequest request,
                                                                GameNotExistException exception) {
        return handleException(request, exception, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(GameReadonlyException.class)
    public ResponseEntity<ErrorDescription> handleGameReadonly(WebRequest request,
                                                               GameReadonlyException exception) {
        return handleException(request, exception, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(InvalidGameMoveException.class)
    public ResponseEntity<ErrorDescription> handleInvalidGameMove(WebRequest request,
                                                                  InvalidGameMoveException exception) {
        return handleException(request, exception, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidGameSettingsException.class)
    public ResponseEntity<ErrorDescription> handleInvalidGameSettings(WebRequest request,
                                                                      InvalidGameSettingsException exception) {
        return handleException(request, exception, HttpStatus.BAD_REQUEST);
    }

    private ResponseEntity<ErrorDescription> handleException(WebRequest request,
                                                             Exception exception,
                                                             HttpStatus status) {
        ErrorDescription response = new ErrorDescription(status, exception.getMessage(), request.getContextPath());
        return new ResponseEntity<>(response, status);
    }
}
