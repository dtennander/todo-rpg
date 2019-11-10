package com.github.dtennander.todorpg.users

import cats.effect.Sync
import cats.implicits._
import com.github.dtennander.todorpg.auth.{Success, TokenValidator}
import com.github.dtennander.todorpg.endpoints.definitions.{Error, User}
import com.github.dtennander.todorpg.endpoints.users.{GetUserResponse, UsersHandler}
import org.http4s.headers.Authorization
import org.http4s.{AuthScheme, Credentials, Status}

class UserHandlerImpl[F[_]: Sync](tokenValidator: TokenValidator[F]) extends UsersHandler[F] {
  override def getUser(respond: GetUserResponse.type)(authorization: String): F[GetUserResponse] = for {
    validationResult <- (for {
      token <- Authorization.parse(authorization)
      t <- token.credentials match {
        case Credentials.Token(AuthScheme.Bearer, t) => Right(t)
        case _ => Left("Wrong type of Authorization")
      }
    } yield tokenValidator.validate(t)).sequence
  } yield validationResult match {
    case Right(Success(user)) => respond.Ok(User(user.id, user.name, user.pictureUrl))
    case _ => respond.Unauthorized(Error(Status.Unauthorized.code, "Unauthorized"))
  }
}
