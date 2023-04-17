package com.example.bookStore.controller;

import com.example.bookStore.exception.type.*;
import com.example.bookStore.model.Users;
import com.example.bookStore.service.UsersService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
@RestController
@RequestMapping(path = "user")
public class UsersController {
    private final UsersService usersService;

    public UsersController(UsersService usersService) {
        this.usersService = usersService;
    }

    @GetMapping(path = "getallusers/{page}")
    public ResponseEntity<?> getUsers(@PathVariable("page") int page) {
        return usersService.getUsers(page).createResponseEntity();
    }

    @GetMapping(path = "get_users_book/{username}")
    public ResponseEntity<?> getUsersBook(@PathVariable("username") String usersName) throws NotFoundException {
        return usersService.getUsersBook(usersName).createResponseEntity();
    }

    @PostMapping(path = "adduser")
    public ResponseEntity<?> postUsers(@Valid @RequestBody Users users) throws InvalidCharacterException,
            DuplicateFieldException, InvalidRolesException, InvalidPasswordException,
            InvalidLengthException, InvalidEmailException, EmptyFieldException {
        return usersService.postUsers(users).createResponseEntity();
    }

    @DeleteMapping(path = "deleteuser")
    public ResponseEntity<?> deleteUsers(@Valid @RequestBody Users users) throws NotFoundException {
        return usersService.deleteUsers(users).createResponseEntity();
    }

    @PatchMapping(path = "updateuser/{usersname}")
    public ResponseEntity<?> patchUsers(@PathVariable("usersname") String usersName,
                                        @Valid @RequestBody Users users) throws InvalidCharacterException,
            InvalidGenderException, DuplicateFieldException, NotFoundException, InvalidPasswordException,
            InvalidLengthException, InvalidEmailException {
        return usersService.patchUsers(users, usersName).createResponseEntity();
    }
    @PostMapping(path = "add_book_to_user/{user_id}/{book_id}")
    public ResponseEntity<?> addBookToUser(@PathVariable("user_id") long user_id,
                                           @PathVariable("book_id") long book_id) throws InvalidIdException {
        return usersService.addBookToUser(user_id, book_id).createResponseEntity();
    }
}
