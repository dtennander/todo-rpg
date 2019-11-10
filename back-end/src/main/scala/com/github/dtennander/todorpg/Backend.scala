package com.github.dtennander.todorpg

import cats.effect._
import cats.implicits._
import com.github.dtennander.todorpg.auth.{Authorized, GoogleAuth}
import com.github.dtennander.todorpg.db.Database
import com.github.dtennander.todorpg.endpoints.lists.ListsResource
import com.github.dtennander.todorpg.endpoints.todos.TodosResource
import com.github.dtennander.todorpg.endpoints.users.UsersResource
import com.github.dtennander.todorpg.lists.ListRepository
import com.github.dtennander.todorpg.todos.TodoRepository
import com.github.dtennander.todorpg.users.UserHandlerImpl
import doobie.hikari.HikariTransactor
import fs2.Stream
import org.http4s.HttpRoutes
import org.http4s.implicits._
import org.http4s.server.blaze.BlazeServerBuilder
import org.http4s.server.middleware.{CORS, Logger}

object Backend extends IOApp {

  def stream[F[_]:Timer :ConcurrentEffect: ContextShift :Effect]: Stream[F, Nothing] = {
      for {
          config <- Stream.eval(Config.create[F])
          validator <- Stream.eval(GoogleAuth.withToken[F](config.googleClientId))
          transactor <- Stream.resource(Database.transactor(config.databaseConfig))
          _ <- Stream.eval(Database.initialize(transactor))
          httpApp <- Stream(addMiddleware(routes(transactor)(Effect[F], validator)).orNotFound)
          finalHttpApp = Logger.httpApp(logHeaders = true, logBody = true)(httpApp)
          exitCode <- BlazeServerBuilder[F]
            .bindHttp(config.port, "0.0.0.0")
            .withHttpApp(finalHttpApp)
            .serve
      } yield exitCode
    }.drain

  private def addMiddleware[F[_]: Effect](routes: HttpRoutes[F]) = {
    CORS(routes)
  }

  private def routes[F[_]: Effect](transactor: HikariTransactor[F])(implicit auth: Authorized[F]): HttpRoutes[F] = {
      val listRepo = new ListRepository[F](transactor)
      val todoRepo = new TodoRepository[F](transactor)
      new UsersResource[F]().routes(new UserHandlerImpl[F]()) <+>
      new ListsResource[F]().routes(new lists.ListHandlerImpl[F](listRepo, todoRepo)) <+>
      new TodosResource[F]().routes(new todos.TodosHandlerImpl[F](todoRepo))
  }

    def run(args: List[String]): IO[ExitCode] = Backend.stream.compile.drain.as(ExitCode.Success)
}
