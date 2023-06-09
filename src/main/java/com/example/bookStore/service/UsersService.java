package com.example.bookStore.service;

import com.example.bookStore.UserToolBox;
import com.example.bookStore.document.Elastic;
import com.example.bookStore.exception.type.*;
import com.example.bookStore.model.Book;
import com.example.bookStore.model.Users;
import com.example.bookStore.repository.BookRepository;
import com.example.bookStore.repository.ElasticRepository;
import com.example.bookStore.repository.UsersRepository;
import com.example.bookStore.request.UserRequest;
import com.example.bookStore.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
@Service
@Slf4j
public class UsersService {
//    private static final Logger log = LogManager.getLogger(UsersService.class);
    private final MessageSource messageSource;
    private final UserToolBox userToolBox;
    private final UsersRepository usersRepository;
    private final BookRepository bookRepository;
    private final ElasticRepository elasticRepository;

    public UsersService(MessageSource messageSource,
                        UserToolBox userToolBox,
                        UsersRepository usersRepository,
                        BookRepository bookRepository,
                        ElasticRepository elasticRepository) {
        this.messageSource = messageSource;
        this.userToolBox = userToolBox;
        this.usersRepository = usersRepository;
        this.bookRepository = bookRepository;
        this.elasticRepository = elasticRepository;
    }
    public Response getUsers(int page) {
        Page<Users> pages = usersRepository.findAll(PageRequest.of(page - 1, 10));
        log.info("get all users successfully");
        elasticRepository.save(
                new Elastic(
                        LocalDateTime.now().toString(),
                        HttpStatus.OK.toString(),
                        "info",
                        "get all users successfully"
                )
        );
        return new Response(HttpStatus.OK,
                messageSource.getMessage("get.Users.successfully", null, LocaleContextHolder.getLocale()),
                pages.get(),
                pages.getTotalPages());
    }
    public Response postUsers(UserRequest userRequest)
            throws InvalidCharacterException, InvalidLengthException, InvalidPasswordException, EmptyFieldException,
            DuplicateFieldException, InvalidEmailException, InvalidRolesException {
        Users users = userToolBox.toolBox(userRequest);
        usersRepository.save(users);
        log.info("add user "+userRequest.getUsername()+" successfully");
        elasticRepository.save(
                new Elastic(
                        LocalDateTime.now().toString(),
                        HttpStatus.OK.toString(),
                        "info",
                        "add user "+userRequest.getUsername()+" successfully"
                )
        );
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
        log.info("delete user "+users.getUsername()+" successfully");
        elasticRepository.save(
                new Elastic(
                        LocalDateTime.now().toString(),
                        HttpStatus.OK.toString(),
                        "info",
                        "delete user "+users.getUsername()+" successfully"
                )
        );
        return new Response(HttpStatus.OK,
                messageSource.getMessage("delete.users.successfully", null, LocaleContextHolder.getLocale()),
                users,
                1);
    }
    public Response patchUsers(UserRequest userRequest, String usersName)
            throws NotFoundException, DuplicateFieldException, InvalidCharacterException, InvalidLengthException,
            InvalidPasswordException, InvalidEmailException, InvalidGenderException {
        Users previousUsers = usersRepository.findByUsername(usersName)
                .orElseThrow(() -> new NotFoundException("User not found!"));
        if (userRequest.getUsername() != null) {
            userToolBox.checkDuplicateUsername(userRequest.getUsername());
            userToolBox.checkUsername(userRequest.getUsername());
            previousUsers.setUsername(userRequest.getUsername());
        }
        if (userRequest.getPassword() != null) {
            userToolBox.checkPassword(userRequest.getPassword());
            previousUsers.setPassword(userRequest.getPassword());
        }
        if (userRequest.getEmail() != null) {
            userToolBox.checkDuplicateEmail(userRequest.getEmail());
            userToolBox.checkEmail(userRequest.getEmail());
            previousUsers.setEmail(userRequest.getEmail());
        }
        usersRepository.save(previousUsers);
        log.info("edit user "+usersName+" successfully");
        elasticRepository.save(
                new Elastic(
                        LocalDateTime.now().toString(),
                        HttpStatus.OK.toString(),
                        "info",
                        "edit user "+usersName+" successfully"
                )
        );
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
        log.info("get "+usersName+"'s books successfully");
        elasticRepository.save(
                new Elastic(
                        LocalDateTime.now().toString(),
                        HttpStatus.OK.toString(),
                        "info",
                        "get "+usersName+"'s books successfully"
                )
        );
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
        log.info("add book "+book.getTitle()+" to user "+user.getUsername()+" successfully");
        elasticRepository.save(
                new Elastic(
                        LocalDateTime.now().toString(),
                        HttpStatus.OK.toString(),
                        "info",
                        "add book "+book.getTitle()+" to user "+user.getUsername()+" successfully"
                )
        );
        return new Response(HttpStatus.OK,
                messageSource.getMessage("update.users.successfully", null, LocaleContextHolder.getLocale()),
                user,
                1);
    }
}
