package com.appdelevloperblogapp.ws.ui.controller;

import com.appdelevloperblogapp.ws.ui.model.request.UpdateUserDetailsRequestModel;
import com.appdelevloperblogapp.ws.ui.model.request.UserDetailsRequestModel;
import com.appdelevloperblogapp.ws.ui.model.response.UserRest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("users") //http://localhist:8080/users
public class UserController {

    /**
     * MediaType is used to support json as well as xml value, update pom.xml-> jackson dependency
     * @param userId
     * @return
     */

    Map<String,UserRest> users;
    @GetMapping(path = "/{userId}",
            produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<UserRest> getUser(@PathVariable String userId) {

        if (userId.equals("-1")) {
            return ResponseEntity.badRequest().build();
        }

        if(users.containsKey(userId)) return new ResponseEntity<UserRest>(users.get(userId),HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     *
     * @param page making this as optional or providing default value
     * @param limit
     * @return
     */
    @GetMapping()
    public String getUsers(@RequestParam(value = "page",defaultValue = "1") int page,
                           @RequestParam(value = "limit",defaultValue = "25") int limit,
                           @RequestParam(value = "sort", defaultValue = "ascending",required = false) String sort){
        return "get user was called with page = " + page + " and limit = " + limit +" sort val "+sort;
    }

    /**
     * read json/xml request body payload
     * @return
     */
    @PostMapping(consumes = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE},
                 produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<UserRest> createUser(@Valid @RequestBody UserDetailsRequestModel userDetails) {
        UserRest userRest = new UserRest();
        userRest.setEmail(userDetails.getEmail());
        userRest.setFirstName(userDetails.getFirstName());
        userRest.setLastName(userDetails.getLastName());
        String userId = UUID.randomUUID().toString();
        userRest.setUserId(userId);

        if(users == null) users = new HashMap<>();
        users.put(userId,userRest);

        return new ResponseEntity<UserRest>(userRest,HttpStatus.OK);
    }

    @PutMapping(path = "/{userId}",consumes = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public UserRest updateUser(@PathVariable String userId, @Valid @RequestBody UpdateUserDetailsRequestModel userDetails) {

        UserRest storedUserDetails = this.getUser(userId).getBody();
        assert storedUserDetails != null;
        storedUserDetails.setFirstName(userDetails.getFirstName());
        storedUserDetails.setLastName(userDetails.getLastName());
        users.put(userId,storedUserDetails);
        return storedUserDetails;
    }

    @DeleteMapping
    public String deleteUser() {
        return "delete user was called";
    }
}
