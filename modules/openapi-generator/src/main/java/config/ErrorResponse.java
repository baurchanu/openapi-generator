package config;

import java.text.MessageFormat;
import org.springframework.http.HttpStatus;

public enum ErrorResponse {
    PET_NOT_FOUND(HttpStatus.NOT_FOUND, "ABC", "adadda"),
    PET_ALREADY_DELETED(HttpStatus.UNPROCESSABLE_ENTITY, "DFR", "adada");

    /**
     * HTTP status code
     */
    private final HttpStatus httpStatus;
    /**
     * Error code
     */
    private final String code;
    /**
     * Message template
     */
    private final DefaultMessageSource messageSource;

    ErrorResponse(HttpStatus httpStatus, String code, String messageTemplate) {
        if (!httpStatus.isError()) {
            throw new IllegalArgumentException("Illegal HTTP status!");
        }
        this.httpStatus = httpStatus;
        this.code = code;
        this.messageSource = new DefaultMessageSource(ErrorResponse.class.getName() + this.name(), messageTemplate);
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public String getCode() {
        return code;
    }

    public String getText(Object ... args) {
        return messageSource.getMessage(args);
    }

    public static class DefaultMessageSource {
        private final String messageTemplate;

        public DefaultMessageSource(String code, String defaultMessageTemplate) {
            messageTemplate = defaultMessageTemplate;
        }

        String getMessage(Object ... args) {
            return new MessageFormat(messageTemplate).format(args);
        }
    }
}
