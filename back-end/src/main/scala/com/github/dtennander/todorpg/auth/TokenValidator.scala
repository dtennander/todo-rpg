package com.github.dtennander.todorpg.auth

trait TokenValidator[F[_]] {
  def validate(token: Token): F[ValidationResult]
}
