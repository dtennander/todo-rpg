package com.github.dtennander.todorpg.users

import cats.effect.Sync
import com.github.dtennander.todorpg.auth._
import com.github.dtennander.todorpg.endpoints.definitions.{Error, User}
import com.github.dtennander.todorpg.endpoints.users.{GetUserResponse, UsersHandler}
import org.http4s.Status
import cats.syntax.functor._

class UserHandlerImpl[F[_]: Sync: Authorized]() extends UsersHandler[F] {
  override def getUser(respond: GetUserResponse.type)(authorization: String): F[GetUserResponse] = {
    authorization.validate.map {
        case Success(user) => respond.Ok(User(user.id, user.name, user.pictureUrl))
        case _ => respond.Unauthorized(Error(Status.Unauthorized.code, "Unauthorized"))
    }
  }
}
