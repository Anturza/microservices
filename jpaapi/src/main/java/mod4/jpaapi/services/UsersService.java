package mod4.jpaapi.services;

import mod4.jpaapi.controllers.UsersController;
import mod4.jpaapi.exceptionhandling.exceptions.NotValidUserInputException;
import mod4.jpaapi.exceptionhandling.exceptions.UserNotFoundException;
import mod4.jpaapi.dto.UserDTO;
import messaging.UserEvent;
import mod4.jpaapi.messaging.UserEventProducer;
import mod4.jpaapi.models.Name;
import mod4.jpaapi.models.User;
import mod4.jpaapi.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Service
public class UsersService {

    private final UsersRepository usersRepository;

    private final UserEventProducer userEventProducer;

    @Autowired
    public UsersService(UsersRepository usersRepository, UserEventProducer userEventProducer) {
        this.usersRepository = usersRepository;
        this.userEventProducer = userEventProducer;
    }

    public UserDTO getUser(UUID id) {
        User user = usersRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found"));
        return mapToDTO(user);
    }

    public List<UserDTO> getAllUsers() {
        List<User> lst = usersRepository.findAll();
        return lst.stream().map(UsersService::mapToDTO).toList();
    }

    public ResponseEntity<EntityModel<UserDTO>> createUser(User user) {
        checkReceivedUserData(user);
        User newUser = mapReceivedUser(user, new User());
        User createdUser = usersRepository.save(newUser);
        UserDTO createdUserDto = UsersService.mapToDTO(createdUser);
        EntityModel<UserDTO> resource = EntityModel.of(createdUserDto);
        resource.add(linkTo(methodOn(UsersController.class).getUserById(createdUserDto.id())).withSelfRel());
        resource.add(linkTo(methodOn(UsersController.class).getAllUsers()).withRel("All users"));
        if (!createdUser.getEmail().isBlank()) {
            UserEvent event = new UserEvent( UserEvent.UserEventDescription.CREATED, createdUser.getEmail());
            userEventProducer.sendUserEvent(event);
        }
        return ResponseEntity.created(resource.getRequiredLink("self").toUri()).body(resource);
    }

    public ResponseEntity<EntityModel<UserDTO>> updateUser(UUID id, User userDetails) {
        Optional<User> userOpt = usersRepository.findById(id);
        checkReceivedUserData(userDetails);
        if (userOpt.isPresent()) {
            User user = mapReceivedUser(userDetails, userOpt.get());
            user.setUpdated(LocalDateTime.now());
            User updatedUser = usersRepository.save(user);
            EntityModel<UserDTO> resource = EntityModel.of(UsersService.mapToDTO(updatedUser));
            resource.add(linkTo(methodOn(UsersController.class).updateUser(updatedUser.getId(), null) ).withRel("Edit used detials"));
            return ResponseEntity.ok(resource);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    public ResponseEntity<Void> deleteUser(UUID id) {
        if (usersRepository.existsById(id)) {
            UserDTO deletedUser = getUser(id);
            usersRepository.deleteById(id);
            if (!deletedUser.email().isBlank()) {
                UserEvent event = new UserEvent(UserEvent.UserEventDescription.DELETED, deletedUser.email());
                userEventProducer.sendUserEvent(event);
            }
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    public static UserDTO mapToDTO(User user) {
        return new UserDTO(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getBirthday()
        );
    }

    public static User mapReceivedUser(User userDetails, User targetUser) {
        Name userName = Name.nameFromString(userDetails.getName().toString());
        targetUser.setName(userName);
        targetUser.setEmail(userDetails.getEmail());
        targetUser.setBirthday(userDetails.getBirthday());
        targetUser.setCreated(LocalDateTime.now());
        targetUser.setUpdated(LocalDateTime.now());
        return targetUser;
    }

    public static void checkReceivedUserData(User userDetails) {
        if (userDetails.getName() == null || userDetails.getName().toString().isBlank()){
            throw new NotValidUserInputException("Username can not be empty and must not be over 100 characters");
        }
        if (!userDetails.getEmail().matches("^$|^[\\w-\\.]+@[\\w-]+(\\.[\\w-]+)*\\.[a-z]{2,}$")) {
            throw new NotValidUserInputException("Provided not valid email");
        }
        if (userDetails.getBirthday().isAfter(LocalDate.now())) {
            throw new NotValidUserInputException("Day of birth can not be more than current date");
        }
    }

}
