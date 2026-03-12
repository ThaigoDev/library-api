package com.thai.tec.librayapi.controller;

import com.thai.tec.librayapi.domain.dtos.bookDTO.ResponseBookDTO;
import com.thai.tec.librayapi.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class LoginController {
    private final BookService bookService;
    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/")
    @ResponseBody
    public ResponseEntity<List<ResponseBookDTO>> paginaHome (Authentication authentication) {
        return ResponseEntity.ok().body(bookService.getAllBooks());
    }
}
