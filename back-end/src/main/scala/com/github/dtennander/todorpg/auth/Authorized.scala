package com.github.dtennander.todorpg.auth


trait Authorized[F[_]] {
  def validate(token: Token): F[ValidationResult]
}

object Authorized {
  def apply[F[_]:Authorized]: Authorized[F] = implicitly[Authorized[F]]
}

