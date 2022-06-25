package ir.ac.ui.akka.model;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
public class DefaultResponse implements SerializableMessage {

    public static final DefaultResponse SUCCESSFUL = builder()
            .success(true)
            .build();
    public static final DefaultResponse NOT_SUCCESSFUL = builder()
            .success(false)
            .build();

    boolean success;
}
