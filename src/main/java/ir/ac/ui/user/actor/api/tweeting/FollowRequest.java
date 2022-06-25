package ir.ac.ui.user.actor.api.tweeting;

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
public class FollowRequest extends UserRequest {

    long targetUserId;
}
