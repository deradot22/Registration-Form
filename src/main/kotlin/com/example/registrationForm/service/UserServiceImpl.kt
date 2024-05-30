package com.example.registrationForm.service

import com.example.registrationForm.exception.UserAlreadyExistsException
import com.example.registrationForm.mapper.toEntity
import com.example.registrationForm.model.dto.UserDto
import com.example.registrationForm.model.jpa.User
import com.example.registrationForm.repo.UserRepository
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths

@Service
class UserServiceImpl(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder
) : UserService {
    private val uploadDir: Path = Paths.get("uploads")

    init {
        Files.createDirectories(uploadDir)
    }

    override fun registerUser(userDto: UserDto): User {
        val user = userDto.toEntity()
        if (userRepository.findByUsername(user.username) != null) {
            throw UserAlreadyExistsException("Username is already taken")
        }
        if (userRepository.findByEmail(user.email) != null) {
            throw UserAlreadyExistsException("Email is already taken")
        }

        val hashedPassword = passwordEncoder.encode(user.password)
        return userRepository.save(user.copy(password = hashedPassword))
    }
}