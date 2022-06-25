package ir.ac.ui.connection.http;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.Value;

@Value
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MessageRegistry {

    public static final MessageRegistry INSTANCE = new MessageRegistry();


}
