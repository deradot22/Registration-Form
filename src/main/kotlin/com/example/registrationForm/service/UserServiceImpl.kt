package com.example.registrationForm.service

import com.example.registrationForm.mapper.toEntity
import com.example.registrationForm.model.dto.UserDto
import com.example.registrationForm.model.jpa.User
import com.example.registrationForm.repo.UserRepository
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.util.*

@Service
class UserServiceImpl(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder
) : UserService {
    private val uploadDir: Path = Paths.get("uploads")

    init {
        Files.createDirectories(uploadDir)
    }
    override fun registerUser(userDto: UserDto, avatar: MultipartFile?): User {
        val user = userDto.toEntity()
        if (userRepository.findByUsername(user.username) != null) {
            throw IllegalArgumentException("Username is already taken")
        }
        if (userRepository.findByEmail(user.email) != null) {
            throw IllegalArgumentException("Email is already taken")
        }

        val hashedPassword = passwordEncoder.encode(user.password)

        var avatarUrl: String? = null
        if (avatar != null && !avatar.isEmpty) {
            val avatarFilename = UUID.randomUUID().toString() + "_" + avatar.originalFilename
            val avatarPath = uploadDir.resolve(avatarFilename)
            Files.copy(avatar.inputStream, avatarPath)
            avatarUrl = avatarPath.toString()
        }

        val userToSave = user.copy(password = hashedPassword, avatarUrl = avatarUrl)

        return userRepository.save(userToSave)
    }
}