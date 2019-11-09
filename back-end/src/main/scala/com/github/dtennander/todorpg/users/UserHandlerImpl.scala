package com.github.dtennander.todorpg.users

import cats.Applicative
import com.github.dtennander.todorpg.endpoints.definitions.User
import com.github.dtennander.todorpg.endpoints.users.{GetUserResponse, UsersHandler}
import cats.implicits._

class UserHandlerImpl[F[_]: Applicative] extends UsersHandler[F] {
  override def getUser(respond: GetUserResponse.type)(): F[GetUserResponse] = for {
    user <- User("David", shortName = "David").pure[F]
  } yield GetUserResponse.Ok(user)
}
