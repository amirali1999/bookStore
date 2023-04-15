//package com.example.bookStore.controller;
//
//import com.example.bookStore.exception.type.*;
//import com.example.bookStore.model.RefreshToken;
//import com.example.bookStore.request.LoginRequest;
//import com.example.bookStore.request.SignUpRequest;
//import com.example.bookStore.request.TokenRefreshRequest;
//import com.example.bookStore.response.Response;
//import com.example.bookStore.response.TokenRefreshResponse;
//import com.example.bookStore.service.AuthService;
//import com.example.bookStore.service.RefreshTokenService;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.MessageSource;
//import org.springframework.context.i18n.LocaleContextHolder;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import javax.validation.Valid;
//
//@RestController
//@RequestMapping("/auth")
//@Slf4j
//public class AuthController {
//    private final AuthService authService;
//    private final MessageSource messageSource;
//
//    @Autowired
//    public AuthController(AuthService authService, MessageSource messageSource) {
//        this.authService = authService;
//        this.messageSource = messageSource;
//    }
//
//    @PostMapping("/signin")
//    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
//        return authService.authenticateUser(loginRequest).createResponseEntity();
//    }
//
//    @PostMapping("/signup")
//    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest)
//            throws InvalidCharacterException, DuplicateFieldException, InvalidRolesException, InvalidPasswordException,
//            InvalidLengthException, InvalidEmailException, EmptyFieldException {
//        return authService.registerUser(signUpRequest).createResponseEntity();
//    }
//
//    @PostMapping("/refreshtoken")
//    public ResponseEntity<?> refreshtoken(@Valid @RequestBody TokenRefreshRequest request) {
//        String requestRefreshToken = request.getRefreshToken();
//
//        return refreshTokenService.findByToken(requestRefreshToken)
//                .map(refreshTokenService::verifyExpiration)
//                .map(RefreshToken::getUsers)
//                .map(users -> {
//                    String token = jwtUtils.generateTokenFromUsername(users.getUsername());
//                    return new Response(HttpStatus.OK,
//                            messageSource.getMessage("get.token.successfully",
//                                    null,
//                                    LocaleContextHolder.getLocale()
//                            ),
//                            new TokenRefreshResponse(token, requestRefreshToken),
//                            1);
//                })
//                .orElseThrow(() -> new TokenRefreshException(requestRefreshToken,
//                        "Refresh token is not in database!")).createResponseEntity();
//    }
//
//    @PostMapping("/logout")
//    public ResponseEntity<?> logoutUser() {
//        return authService.logoutUser().createResponseEntity();
//    }
//}
