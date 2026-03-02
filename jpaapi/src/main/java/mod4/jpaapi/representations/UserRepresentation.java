package mod4.jpaapi.representations;

import mod4.jpaapi.dto.UserDTO;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;

public class UserRepresentation extends EntityModel<UserDTO> {

    public UserRepresentation(UserDTO content, Iterable<Link> links) {
        super(content, links);
    }

}
