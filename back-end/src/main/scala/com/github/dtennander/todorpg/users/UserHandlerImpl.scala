package com.github.dtennander.todorpg.users

import cats.Monad
import cats.implicits._
import com.github.dtennander.todorpg.auth.{Success, TokenValidator}
import com.github.dtennander.todorpg.endpoints.definitions.{Error, User}
import com.github.dtennander.todorpg.endpoints.users.{GetUserResponse, UsersHandler}
import org.http4s.Status

class UserHandlerImpl[F[_]: Monad](tokenValidator: TokenValidator[F]) extends UsersHandler[F] {
  override def getUser(respond: GetUserResponse.type)(authorization: String): F[GetUserResponse] = for {
    validationResult <- tokenValidator.validate(authorization)
  } yield validationResult match {
    case Success(user) => respond.Ok(User(user.id, user.name, user.pictureUrl))
    case _ => respond.Forbidden(Error(Status.Unauthorized.code, "Unauthorized call"))
  }
}
