package com.example.bookStore.service;

import com.example.bookStore.UserToolBox;
import com.example.bookStore.exception.type.*;
import com.example.bookStore.model.Book;
import com.example.bookStore.model.Users;
import com.example.bookStore.repository.BookRepository;
import com.example.bookStore.repository.UsersRepository;
import com.example.bookStore.response.Response;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;

import java.util.List;

public class UsersService {
    private final MessageSource messageSource;
    private final UserToolBox userToolBox;
    private final UsersRepository usersRepository;
    private final BookRepository bookRepository;

    public UsersService(MessageSource messageSource, UserToolBox userToolBox, UsersRepository usersRepository, BookRepository bookRepository) {
        this.messageSource = messageSource;
        this.userToolBox = userToolBox;
        this.usersRepository = usersRepository;
        this.bookRepository = bookRepository;
    }
    public Response getUsers(int page) {
        Page<Users> pages = usersRepository.findAll(PageRequest.of(page - 1, 10));
        return new Response(HttpStatus.OK,
                messageSource.getMessage("get.Users.successfully", null, LocaleContextHolder.getLocale()),
                pages.get(),
                pages.getTotalPages());
    }
    public Response postUsers(Users users)
            throws InvalidCharacterException, InvalidLengthException, InvalidPasswordException, EmptyFieldException,
            DuplicateFieldException, InvalidEmailException, InvalidRolesException {
        users = userToolBox.toolBox(users);
        usersRepository.save(users);
        return new Response(HttpStatus.OK,
                messageSource.getMessage("add.user.successfully",
                        null,
                        LocaleContextHolder.getLocale()
                ),
                users,
                1
        );
    }
    public Response deleteUsers(Users users) throws NotFoundException {
        users = usersRepository.findByUsername(users.getUsername())
                .orElseThrow(() -> new NotFoundException("User not found!"));
        usersRepository.delete(users);
        return new Response(HttpStatus.OK,
                messageSource.getMessage("delete.users.successfully", null, LocaleContextHolder.getLocale()),
                users,
                1);
    }
    public Response patchUsers(Users users, String usersName)
            throws NotFoundException, DuplicateFieldException, InvalidCharacterException, InvalidLengthException,
            InvalidPasswordException, InvalidEmailException, InvalidGenderException {
        Users previousUsers = usersRepository.findByUsername(usersName)
                .orElseThrow(() -> new NotFoundException("User not found!"));
        if (users.getUsername() != null) {
            userToolBox.checkDuplicateUsername(users.getUsername());
            userToolBox.checkUsername(users.getUsername());
            previousUsers.setUsername(users.getUsername());
        }
        if (users.getPassword() != null) {
            userToolBox.checkPassword(users.getPassword());
            previousUsers.setPassword(users.getPassword());
        }
        if (users.getEmail() != null) {
            userToolBox.checkDuplicateEmail(users.getEmail());
            userToolBox.checkEmail(users.getEmail());
            previousUsers.setEmail(users.getEmail());
        }
        usersRepository.save(previousUsers);
        return new Response(HttpStatus.OK,
                messageSource.getMessage("delete.users.successfully",
                        null,
                        LocaleContextHolder.getLocale()
                ),
                previousUsers,
                1
        );
    }
    public Response getUsersBook(String usersName) throws NotFoundException {
        Users users = usersRepository.findByUsername(usersName).orElseThrow(
                () -> new NotFoundException("user not found!")
        );
        List<Book> books = users.getBooks();
        return new Response(HttpStatus.OK,
                messageSource.getMessage("get.books.successfully",
                        null,
                        LocaleContextHolder.getLocale()
                ),
                books,
                1
        );
    }
    public Response addBookToUser(long user_id, long book_id) throws InvalidIdException {
        Users user = usersRepository.findById(user_id).orElseThrow(() -> new InvalidIdException("User's id not found"));
        Book book = bookRepository.findById(book_id).orElseThrow(() -> new InvalidIdException("book's id not found"));
        user.getBooks().add(book);
        user.setBooks(user.getBooks());
        usersRepository.save(user);
        return new Response(HttpStatus.OK,
                messageSource.getMessage("update.users.successfully", null, LocaleContextHolder.getLocale()),
                user,
                1);
    }
}
