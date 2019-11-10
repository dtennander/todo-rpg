package com.github.dtennander.todorpg.todos

import cats.Monad
import cats.implicits._
import com.github.dtennander.todorpg.endpoints.definitions.{Error, Todo, WriteTodo}
import com.github.dtennander.todorpg.endpoints.todos.{GetTodoResponse, TodosHandler, UpdateTodoResponse}

class TodosHandlerImpl[F[_]: Monad](todoRepo: TodoRepository[F]) extends TodosHandler[F] {
  override def getTodo(respond: GetTodoResponse.type)(id: BigInt): F[GetTodoResponse] = for {
    todo <- todoRepo.getTodo(id.toInt)
  } yield todo match {
    case Some(t) => respond.Ok(Todo(t.id, t.description, t.done))
    case None    => respond.NotFound(Error(404, "Not found"))
  }

  override def updateTodo(respond: UpdateTodoResponse.type)(id: BigInt, body: WriteTodo): F[UpdateTodoResponse] = for {
    old <- todoRepo.getTodo(id.toInt)
    newTodo = old.map(o => o.copy(description = body.text.getOrElse(o.description), done = body.done.getOrElse(o.done)))
    storedTodo <- newTodo.map(todoRepo.save).sequence
  } yield storedTodo match {
    case Some(t) => respond.Ok(Todo(t.id, t.description, t.done))
    case None => respond.InternalServerError(Error(500, "Internal error"))
  }
}
