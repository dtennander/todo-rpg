package com.github.dtennander.todorpg.users

import cats.{Applicative, Monad}
import com.github.dtennander.todorpg.endpoints.definitions.User
import com.github.dtennander.todorpg.endpoints.users.{GetUserResponse, UsersHandler}
import cats.implicits._
import com.github.dtennander.todorpg.auth.{Success, TokenValidator}

class UserHandlerImpl[F[_]: Monad](tokenValidator: TokenValidator[F]) extends UsersHandler[F] {
  override def getUser(respond: GetUserResponse.type)(authorization: String): F[GetUserResponse] = for {
    validationResult <- tokenValidator.validate(authorization)
  } yield validationResult match {
    case Success(user) => respond.Ok(User(user.id, user.name, user.pictureUrl))
  }
}
