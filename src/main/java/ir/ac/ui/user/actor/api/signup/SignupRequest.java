package ir.ac.ui.user.actor.api.signup;

import ir.ac.ui.user.actor.api.UserRequest;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Value;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

@Value
@Jacksonized
@SuperBuilder
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class SignupRequest extends UserRequest {

    String name;
    String phone;
}
