package com.github.dtennander.todorpg

import cats.Applicative
import org.http4s.{AuthScheme, Credentials}
import org.http4s.headers.Authorization
import cats.implicits._

package object auth {
  type Token = String

  implicit class ops[F[_]: Applicative: Authorized](token: String) {

    def validate: F[ValidationResult] = for {
      res <- (for {
        parsedToken <- Authorization.parse (token)
        t <- parsedToken.credentials match {
          case Credentials.Token (AuthScheme.Bearer, t) => Right (t)
          case _ => Left ("Wrong type of Authorization")
        }
      } yield Authorized[F].validate(t)).sequence
    } yield res match {
      case Right(a) => a
      case Left(e: Exception) => Failure(e)
      case Left(s: java.io.Serializable) => Failure(new Exception(s.toString))
    }
  }
}
