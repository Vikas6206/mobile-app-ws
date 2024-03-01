package com.appdelevloperblogapp.ws.ui.controller;

import com.appdelevloperblogapp.ws.ui.model.request.UserDetailsRequestModel;
import com.appdelevloperblogapp.ws.ui.model.response.UserRest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("users") //http://localhist:8080/users
public class UserController {

    /**
     * MediaType is used to support json as well as xml value, update pom.xml-> jackson dependency
     * @param userId
     * @return
     */
    @GetMapping(path = "/{userId}",
            produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<UserRest> getUser(@PathVariable String userId) {

        if (userId.equals("-1")) {
            return ResponseEntity.badRequest().build();
        }
        UserRest userRest = new UserRest();
        userRest.setEmail("ak@gmail.com");
        userRest.setFirstName("Vikas");
        userRest.setLastName("Kumar");
        userRest.setUserId(userId);
        return new ResponseEntity<UserRest>(userRest,HttpStatus.OK);
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
    public ResponseEntity<UserRest> createUser(@RequestBody UserDetailsRequestModel userDetails) {
        UserRest userRest = new UserRest();
        userRest.setEmail(userDetails.getEmail());
        userRest.setFirstName(userDetails.getFirstName());
        userRest.setLastName(userDetails.getLastName());
        userRest.setUserId(userDetails.getPassword());
        return new ResponseEntity<UserRest>(userRest,HttpStatus.OK);
    }

    @PutMapping
    public String updateUser() {
        return "update user was called";
    }

    @DeleteMapping
    public String deleteUser() {
        return "delete user was called";
    }
}
