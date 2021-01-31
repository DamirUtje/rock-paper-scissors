package de.damirutje.rockpaperscissors.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.Objects;

public class ErrorDescription {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy hh:mm:ss")
    private final LocalDateTime timestamp;
    private final int status;
    private final String message;
    private final String path;

    public ErrorDescription(HttpStatus status, String message, String path) {
        this.timestamp = LocalDateTime.now();
        this.status = status.value();
        this.message = message;
        this.path = path;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public String getPath() {
        return path;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ErrorDescription that = (ErrorDescription) o;
        return status == that.status && timestamp.equals(that.timestamp)
                && Objects.equals(message, that.message) && path.equals(that.path);
    }

    @Override
    public int hashCode() {
        return Objects.hash(timestamp, status, message, path);
    }
}
