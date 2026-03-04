package mod4.jpaapi.models;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@MappedSuperclass
public abstract class UserBaseModel {
    /** ID field is the UUID generated value
     * */
    @Id
    @GeneratedValue
    @UuidGenerator
    protected UUID id;
    /** Name field in an embedded class with its own structure of 3 fields and should have at least name
     * */
    @Embedded
    protected Name name;
    /** Email field can be empty or matches standard email pattern
     * */
    @Column(length = 85)
    protected String email;
    /** Field birthday can not be null and later than now
     * */
    @Column(name = "birthday", nullable = false)
    protected LocalDate birthday;
    /** Field created is set once and then should not be updated. Anyway, it will not be updated in database
     * */
    @Column(name = "created_at", updatable = false)
    protected LocalDateTime created;
    /** Field updated is used for any action which modifies any user data and shows date and time the user last updated
     * */
    @Column(name = "updated_at", nullable = false)
    protected LocalDateTime updated;

}
