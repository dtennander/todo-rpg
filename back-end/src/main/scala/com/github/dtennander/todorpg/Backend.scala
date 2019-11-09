package com.github.dtennander.todorpg

import cats.effect._
import cats.implicits._
import com.github.dtennander.todorpg.endpoints.lists.ListsResource
import com.github.dtennander.todorpg.endpoints.todos.TodosResource
import com.github.dtennander.todorpg.endpoints.users.UsersResource
import com.github.dtennander.todorpg.users.UserHandlerImpl
import fs2.Stream
import org.http4s.HttpRoutes
import org.http4s.implicits._
import org.http4s.server.blaze.BlazeServerBuilder
import org.http4s.server.middleware.{CORS, Logger}

object Backend extends IOApp {

  def stream[F[_]: ConcurrentEffect](implicit T: Timer[F], C: ContextShift[F]): Stream[F, Nothing] = {
      for {
        httpApp <- Stream(addMiddleware(routes).orNotFound)
        finalHttpApp = Logger.httpApp(logHeaders = true, logBody = true)(httpApp)
        port <- Stream.eval(getPort)
        exitCode <- BlazeServerBuilder[F]
          .bindHttp(port, "0.0.0.0")
          .withHttpApp(finalHttpApp)
          .serve
      } yield exitCode
    }.drain

  private def addMiddleware[F[_] : ConcurrentEffect](routes: HttpRoutes[F]) = {
    CORS(routes)
  }

  private def routes[F[_] : ConcurrentEffect]: HttpRoutes[F] =
      new UsersResource[F]().routes(new UserHandlerImpl[F]()) <+>
      new ListsResource[F]().routes(new lists.ListHandlerImpl[F]()) <+>
      new TodosResource[F]().routes(new todos.TodosHandlerImpl[F]())

  def getPort[F[_]: Sync]: F[Int] = for {
      port <- Sync[F].delay(Integer.valueOf(sys.env("PORT"))).attempt
    } yield port match {
      case Right(p) => p.toInt
      case Left(_) => 8080
    }

  def run(args: List[String]): IO[ExitCode] = Backend.stream[IO].compile.drain.as(ExitCode.Success)
}
