package com.example.demo.controller;

import com.example.demo.model.BookEntity;
import com.example.demo.model.PermissionEntity;
import com.example.demo.model.UserEntity;
import com.example.demo.model.dto.UserDTO;
import com.example.demo.model.security.MyCustomUserDetails;
import com.example.demo.model.type.Permission;
import com.example.demo.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Controller
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PreAuthorize("isFullyAuthenticated()")
    @GetMapping("/user-details")
    public ResponseEntity<MyCustomUserDetails> userDetails() {
        final MyCustomUserDetails userDetails = (MyCustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ResponseEntity.ok(userDetails);
    }


    @PreAuthorize("isFullyAuthenticated()")
    @GetMapping(value = "/liked-books")
    public ResponseEntity<Set<BookEntity>> userLikedBooks() {
        final MyCustomUserDetails userDetails = (MyCustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ResponseEntity.ok(userService.getUsersLikedBooks(userDetails.getUsername()));
    }

    @PreAuthorize("isFullyAuthenticated()")
    @RequestMapping(value = "/liked-books/{bookId}", method = RequestMethod.POST)
    public ResponseEntity<Set<BookEntity>> bookFormControllerAdd(@PathVariable String bookId) {
        final MyCustomUserDetails userDetails = (MyCustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserEntity userEntity = userService.addUsersLikedBooks(userDetails.getUsername(), Integer.parseInt(bookId));
        return ResponseEntity.ok(userEntity.getLikedBooks());
    }

    @PreAuthorize("isFullyAuthenticated()")
    @RequestMapping(value = "/liked-books/{bookId}", method = RequestMethod.DELETE)
    public ResponseEntity<Set<BookEntity>> bookFormControllerDelete(@PathVariable String bookId) {
        final MyCustomUserDetails userDetails = (MyCustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserEntity userEntity = userService.deleteUsersLikedBooks(userDetails.getUsername(), Integer.parseInt(bookId));
        return ResponseEntity.ok(userEntity.getLikedBooks());
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity<UserDTO> registerUserController(@Valid @RequestBody final UserDTO userModel) {
        System.out.println(userModel.getPassword());
        List<PermissionEntity> permissions = new ArrayList<>();
        permissions.add(new PermissionEntity(1, Permission.LIKED_BOOK));
        permissions.add(new PermissionEntity(1, Permission.VIEW_CATALOG));
        UserEntity userEntity = userService.registerUser(userModel.getLogin(), userModel.getPassword(), userModel.getCustomAuthField(), permissions);
        UserDTO userDTO = new UserDTO();
        userDTO.setId(userEntity.getId());
        userDTO.setPassword(null);
        userDTO.setCustomAuthField(userEntity.getCustomAuthField());
        userDTO.setLogin(userEntity.getLogin());
        return ResponseEntity.ok(userDTO);
    }
}

