package com.example.demo.controller;

import com.example.demo.model.BookEntity;
import com.example.demo.model.UserEntity;
import com.example.demo.model.security.MyCustomUserDetails;
import com.example.demo.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Set;

@Controller
@PreAuthorize("isFullyAuthenticated()")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user-details")
    public ResponseEntity<MyCustomUserDetails> userDetails() {
        final MyCustomUserDetails userDetails = (MyCustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ResponseEntity.ok(userDetails);
    }


    @GetMapping(value = "/liked-books")
    public ResponseEntity<Set<BookEntity>> userLikedBooks() {
        final MyCustomUserDetails userDetails = (MyCustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ResponseEntity.ok(userService.getUsersLikedBooks(userDetails.getUsername()));
    }

    @RequestMapping(value = "/liked-books/{bookId}", method = RequestMethod.POST)
    public ResponseEntity<Set<BookEntity>> bookFormControllerAdd(@PathVariable String bookId) {
        final MyCustomUserDetails userDetails = (MyCustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserEntity userEntity = userService.addUsersLikedBooks(userDetails.getUsername(), Integer.parseInt(bookId));
        return ResponseEntity.ok(userEntity.getLikedBooks());
    }

    @RequestMapping(value = "/liked-books/{bookId}", method = RequestMethod.DELETE)
    public ResponseEntity<Set<BookEntity>> bookFormControllerDelete(@PathVariable String bookId) {
        final MyCustomUserDetails userDetails = (MyCustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserEntity userEntity = userService.deleteUsersLikedBooks(userDetails.getUsername(), Integer.parseInt(bookId));
        return ResponseEntity.ok(userEntity.getLikedBooks());
    }
}

