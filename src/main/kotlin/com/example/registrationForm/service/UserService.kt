package com.example.registrationForm.service

import com.example.registrationForm.model.dto.UserDto
import com.example.registrationForm.model.jpa.User
import org.springframework.web.multipart.MultipartFile

interface UserService {
    fun registerUser(userDto: UserDto): User
}