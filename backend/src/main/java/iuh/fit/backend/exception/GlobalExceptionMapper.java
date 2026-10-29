package iuh.fit.backend.exception;

import iuh.fit.backend.dto.ErrorResponse;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Provider
public class GlobalExceptionMapper implements ExceptionMapper<Throwable> {
    private static final Logger LOGGER = Logger.getLogger(GlobalExceptionMapper.class.getName());

    @Override
    public Response toResponse(Throwable exception) {
        int status = Response.Status.INTERNAL_SERVER_ERROR.getStatusCode();
        String error = "Internal Server Error";
        String message = exception.getMessage() != null ? exception.getMessage() : "An unexpected error occurred";

        if (exception instanceof ResourceNotFoundException) {
            status = Response.Status.NOT_FOUND.getStatusCode();
            error = "Not Found";
        } else if (exception instanceof BadRequestException) {
            status = Response.Status.BAD_REQUEST.getStatusCode();
            error = "Bad Request";
        } else if (exception instanceof UnauthorizedException) {
            status = Response.Status.UNAUTHORIZED.getStatusCode();
            error = "Unauthorized";
        } else if (exception instanceof ConstraintViolationException) {
            status = Response.Status.BAD_REQUEST.getStatusCode();
            error = "Validation Error";
            ConstraintViolationException cve = (ConstraintViolationException) exception;
            message = cve.getConstraintViolations().stream()
                    .map(ConstraintViolation::getMessage)
                    .collect(Collectors.joining("; "));
        } else if (exception instanceof WebApplicationException) {
            WebApplicationException wae = (WebApplicationException) exception;
            status = wae.getResponse().getStatus();
            error = wae.getResponse().getStatusInfo().getReasonPhrase();
        } else {
            LOGGER.log(Level.SEVERE, "Unhandled exception: " + exception.getMessage(), exception);
        }

        ErrorResponse errorResponse = new ErrorResponse(status, error, message);
        return Response.status(status)
                .entity(errorResponse)
                .type(MediaType.APPLICATION_JSON)
                .build();
    }
}

