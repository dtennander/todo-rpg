package com.github.dtennander.todorpg.todos

import cats.Applicative
import com.github.dtennander.todorpg.endpoints.definitions.Todo
import com.github.dtennander.todorpg.endpoints.definitions.WriteTodo
import com.github.dtennander.todorpg.endpoints.todos.{GetTodoResponse, TodosHandler, UpdateTodoResponse}
import cats.implicits._

class TodosHandlerImpl[F[_]: Applicative] extends TodosHandler[F] {
  override def getTodo(respond: GetTodoResponse.type)(id: BigInt): F[GetTodoResponse] = for {
    todo <- Todo(1, IndexedSeq(), "A List", false).pure[F]
  } yield respond.Ok(todo)

  override def updateTodo(respond: UpdateTodoResponse.type)(id: BigInt, body: WriteTodo): F[UpdateTodoResponse] = for {
    todo <- Todo(id, IndexedSeq(), "A List", false).pure[F]
  } yield respond.Ok(todo)
}
