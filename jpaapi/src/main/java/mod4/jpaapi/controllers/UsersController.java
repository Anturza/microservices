package mod4.jpaapi.controllers;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.tags.Tag;
import mod4.jpaapi.dto.UserDTO;
import mod4.jpaapi.models.User;
import mod4.jpaapi.repositories.UsersRepository;
import mod4.jpaapi.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@OpenAPIDefinition(info=@Info(title="Users API"))
@Tag(name = "user", description = "The user API")
@RestController
@RequestMapping("/api/users")
public class UsersController {

    private final UsersRepository usersRepository;

    private final UsersService usersService;

    @Autowired
    public UsersController(UsersRepository usersRepository, UsersService usersService) {
        this.usersRepository = usersRepository;
        this.usersService = usersService;
    }

    @Autowired

    @Operation(summary = "Get list of users", description = "For now (while study project) it can be done by any user", tags = { "user" })
    @GetMapping
    public List<UserDTO> getAllUsers() {
        return usersService.getAllUsers();
    }

    @Operation(summary = "Get user by id", description = "For now (while study project) it can be done by any user", tags = { "user" })
    @GetMapping("/{id}")
    public UserDTO getUserById(@PathVariable UUID id) {
        return usersService.getUser(id);
    }

    @Operation(summary = "Create user", description = "For now (while study project) it can be done by any user", tags = { "user" })
    @PostMapping
    public ResponseEntity<UserDTO> createUser(@RequestBody User user) {
        return usersService.createUser(user);

    }

    @Operation(summary = "Update user data by id", description = "For now (while study project) it can be done by any user", tags = { "user" })
    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable UUID id, @RequestBody User userDetails) {
        return usersService.updateUser(id, userDetails);
    }

    @Operation(summary = "Delete user by id", description = "For now (while study project) it can be done by any user", tags = { "user" })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserById(@PathVariable UUID id) {
        return usersService.deleteUser(id);
    }

}
