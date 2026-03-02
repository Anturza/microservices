package mod4.jpaapi.dto;

import mod4.jpaapi.models.Name;

import java.time.LocalDate;
import java.util.UUID;
/** A final class Record (immutable) for User entity used for transmitting some user data between layers in app */
public record UserDTO(
        UUID id,
        Name name,
        String email,
        LocalDate birthday
) {}
