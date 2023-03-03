package rodrigues.ferreira.ricardo.auth.user.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class PasswordTokenPublicData {
    private final String email;
    private final Long createdAtTimestamp;
}
