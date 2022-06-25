package ir.ac.ui.akka.model;

import lombok.Value;
import lombok.experimental.NonFinal;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

@Value
@NonFinal
@Jacksonized
@SuperBuilder
public class ErrorResponse implements SerializableMessage {

    int errorCode;
    String errorMessage;

    public static ErrorResponse validation(String message) {
        return builder()
                .errorCode(400)
                .errorMessage(message)
                .build();
    }

    public static ErrorResponse unexpected(String message) {
        return builder()
                .errorCode(500)
                .errorMessage(message)
                .build();
    }
}
