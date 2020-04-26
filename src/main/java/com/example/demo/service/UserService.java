package com.example.demo.service;

import com.example.demo.model.BookEntity;
import com.example.demo.model.PermissionEntity;
import com.example.demo.model.UserEntity;
import com.example.demo.repository.BookRepository;
import com.example.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final BookRepository bookRepository;


    @Transactional
    public UserEntity registerUser(final String username, final String password, final String customAuthField, final List<PermissionEntity> permissions) {
        final UserEntity user = new UserEntity();
        user.setLogin(username);
        user.setPassword(password);
        user.setCustomAuthField(customAuthField);
        user.setLikedBooks(new HashSet<>());
        user.setPermissions(permissions);
        return userRepository.saveAndFlush(user);
    }

    @Transactional
    public Set<BookEntity> getUsersLikedBooks(final String username) throws UsernameNotFoundException {
        final UserEntity user = userRepository.findByLogin(username)
                .orElseThrow(() -> new UsernameNotFoundException("No user with login: " + username));

        return user.getLikedBooks();
    }

    @Transactional
    public UserEntity deleteUsersLikedBooks(final String username, final int book_id) throws UsernameNotFoundException {
        final UserEntity user = userRepository.findByLogin(username)
                .orElseThrow(() -> new UsernameNotFoundException("No user with login: " + username));

        user.setLikedBooks(user.getLikedBooks().stream().filter(book -> book.getId() != book_id).collect(Collectors.toSet()));
        System.out.println(user.getLikedBooks());
        userRepository.saveAndFlush(user);
        return user;
    }

    @Transactional
    public UserEntity addUsersLikedBooks(final String username, final int book_id) throws UsernameNotFoundException {
        final UserEntity user = userRepository.findByLogin(username)
                .orElseThrow(() -> new UsernameNotFoundException("No user with login: " + username));

        final BookEntity bookEntity = bookRepository.findById(book_id).orElseThrow(() -> new EntityNotFoundException("No book with id: " + book_id));

        Set<BookEntity> books = new HashSet<>(user.getLikedBooks());
        books.add(bookEntity);
        user.setLikedBooks(books);
        userRepository.saveAndFlush(user);
        return user;
    }

}
