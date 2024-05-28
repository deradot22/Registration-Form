package com.example.registrationForm.mapper

import com.example.registrationForm.model.dto.UserDto
import com.example.registrationForm.model.jpa.User

fun UserDto.toEntity(): User {
    return User(
        firstName = this.firstName,
        lastName = this.lastName,
        birthday = this.birthday,
        username = this.username,
        email = this.email,
        password = this.password,
        avatarUrl = null
    )
}