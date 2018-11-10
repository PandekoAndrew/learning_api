package com.example.controller;

import com.example.domain.Role;
import com.example.domain.User;
import com.example.service.TokenGenerationService;
import com.example.service.impl.UserService;
import com.example.service.impl.UserValidationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.example.security.SecurityConstants.HEADER_STRING;
import static com.example.security.SecurityConstants.TOKEN_PREFIX;

/**
 * Handles requests to /users
 *
 * @author Roman
 * @author Andrew
 * @version 1.0
 */
@Component
@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final TokenGenerationService tokenGenerationService;
    private final UserValidationService validatorService;

    @Autowired
    public UserController(AuthenticationManager authenticationManager,
                          TokenGenerationService tokenGenerationService, UserService userService,
                          UserValidationService validatorService) {
        this.authenticationManager = authenticationManager;
        this.tokenGenerationService = tokenGenerationService;
        this.userService = userService;
        this.validatorService = validatorService;
    }

    /**
     * Triggered if User has role USER, and fully authenticated
     *
     * @return text massage
     */
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/hello")
    @PreAuthorize("isFullyAuthenticated() and hasAnyAuthority('USER', 'ADMIN')")
    public Response hello() {
        return Response.ok().entity("Hello World!").build();
    }

    /**
     * User sign up method. Includes user validation
     *
     * @return new {@link User} with Authentication token, or Map of errors
     */
    @POST
    public Response signUp(@RequestBody User user) {
        logger.warn("{}", user);
        Map<String, String> err = validatorService.validate(user);

        if (userService.isExists(user.getUsername())) {
            err.put("username", "Username already exists");
        }
        if (err.size() > 0) {
            logger.warn("Validation failed for user {}", user.getUsername());
            throw new BadRequestException("Validation failed for user:\n" + err);
        }
        logger.debug("Try to register new User {}", user.getUsername());
        String actualPassword = user.getPassword();
        userService.save(user);

        logger.debug("Authenticate User {}", user.getUsername());
        auth(user.getUsername(), actualPassword);


        logger.debug("Generate token for User {}", user.getUsername());
        String token = tokenGenerationService.generate();

        return Response.ok().header(HEADER_STRING, TOKEN_PREFIX + token).entity(user).build();
    }

    /**
     * User edit method. Includes user validation. User could edit only himself
     *
     * @param username Editable username. not null
     * @param user     User form with params that will be changed.
     * @return Success if user has been updated
     * @throws NullPointerException If user is null
     */
    @PATCH
    @Path("/{username}")
    @PreAuthorize("hasAnyAuthority('USER','ADMIN') and isFullyAuthenticated()")
    public Response edit(@PathParam("username") String username, @RequestBody User user) {

        String authUsername = ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        if (!authUsername.equals(username)) {
            throw new ForbiddenException("User is allowed to only edit himself!");
        }

        user.setUsername(username);

        User updatedUser = userService.getUpdatedUser(user);

        Map<String, String> err = validatorService.validate(updatedUser);

        if (err.size() > 0) {
            logger.warn("Validation failed for user {}", user.getUsername());
            throw new BadRequestException("Validation failed for user:\n" + err);
        }

        userService.update(updatedUser);

        if (user.getPassword() != null) {
            logger.debug("Authenticate User {}", user.getUsername());
            auth(user.getUsername(), user.getPassword());

            logger.debug("Generate token for User {}", user.getUsername());
            String token = tokenGenerationService.generate();

            return Response.ok().header(HEADER_STRING, TOKEN_PREFIX + token).entity("Success!").build();
        }

        return Response.ok().entity("Success!").build();
    }

    /**
     * User login method. A token is generated in the process.
     *
     * @param username not null
     * @param password not null
     * @return Authentication error or new token in Header
     */
    @POST
    @Path("/login")
    @Produces(MediaType.TEXT_PLAIN)
    public Response login(@QueryParam("username") String username, @QueryParam("password") String password) {
        auth(username, password);
        logger.warn("User {} successfully authenticated", username);

        logger.debug("Generate token for User {}", username);
        String token = tokenGenerationService.generate();

        return Response.ok().header(HEADER_STRING, TOKEN_PREFIX + token).entity("Success!").build();
    }

    /**
     * Depends of pageSize returns a different quantity of users
     *
     * @param pageNum  if null, return all users
     * @param pageSize if null, return all users
     * @return List of users
     */
    @GET
    public Response getAllUsers(@QueryParam("pageNum") int pageNum, @QueryParam("pageSize") int pageSize) {
        List<User> users;
        if (pageSize != 0) {
            users = userService.getPage(pageNum, pageSize);
        } else {
            users = userService.getUsers();
        }
        return Response.ok().entity(users).build();
    }

    /**
     * Check does user exist and return them
     *
     * @param username can be null
     * @return the requested {@link User}
     * @throws NotFoundException if username is null
     */
    @GET
    @Path("/{username}")
    public Response getSpecificUser(@PathParam("username") String username) {
        User user = userService.getUser(username);
        if (user == null) {
            throw new NotFoundException();
        }
        return Response.ok().entity(user).build();
    }

    /**
     * Only user with {@link Role} 'ADMIN' can delete user
     *
     * @param username can be null
     * @return OK if user successfully deleted
     * NOT_FOUND if user not found
     * @throws NullPointerException if username is null
     */
    @DELETE
    @Path("/{username}")
    @PreAuthorize("hasAuthority('ADMIN') and isFullyAuthenticated()")
    public Response deleteUser(@PathParam("username") String username) {
        if (username.equals(SecurityContextHolder.getContext().getAuthentication().getName())) {
            throw new ForbiddenException("You can't delete yourself.");
        }

        if (userService.delete(username)) {
            return Response.ok().entity("Delete success!").build();
        }
        throw new NotFoundException("User not found.");
    }

    /**
     * Authenticate user using {@link AuthenticationManager}
     *
     * @param username if null return Authentication error
     * @param password if null return Authentication error
     */
    private void auth(String username, String password) {
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        username,
                        password,
                        new ArrayList<>()
                ));
        SecurityContextHolder.getContext().setAuthentication(auth);
    }
}
