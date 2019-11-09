package com.github.dtennander.todorpg.auth

sealed class ValidationResult
case class Success(user: User) extends ValidationResult
case class Failure(exception: Exception) extends ValidationResult

case class User(id: String, name: String, pictureUrl: String)