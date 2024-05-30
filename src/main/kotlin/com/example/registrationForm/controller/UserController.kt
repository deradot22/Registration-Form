package com.example.registrationForm.controller

import com.example.registrationForm.model.dto.UserDto
import com.example.registrationForm.service.UserService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/api/register")
class UserController(private val userService: UserService) {
    @PostMapping
    fun registerUser(
        @Valid @RequestBody userDto: UserDto
    ): ResponseEntity<Map<String, Long>> {
        val registeredUser = userService.registerUser(userDto)
        return ResponseEntity(mapOf("user_id" to registeredUser.id), HttpStatus.CREATED)
    }
}