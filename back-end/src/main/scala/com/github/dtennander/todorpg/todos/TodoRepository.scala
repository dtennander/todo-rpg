package com.github.dtennander.todorpg.todos

import cats.effect.Sync
import com.github.dtennander.todorpg.todos.TodoRepository.Todo
import doobie.util.transactor.Transactor
import doobie.implicits._

class TodoRepository[F[_]: Sync](transactor: Transactor[F]) {
    def getTodos(id: Int): F[List[Todo]] =
        sql"SELECT (id, description, ordering, done) FROM todos WHERE list_id = $id".query[Todo].to[List].transact(transactor)
}

object TodoRepository {
    case class Todo(id: Int, desctiption: String, ordering: Int, done: Boolean)
}
