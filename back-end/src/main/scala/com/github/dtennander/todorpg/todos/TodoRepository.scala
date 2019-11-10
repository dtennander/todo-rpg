package com.github.dtennander.todorpg.todos

import cats.effect.Sync
import com.github.dtennander.todorpg.todos.TodoRepository.Todo
import doobie.util.transactor.Transactor
import doobie.implicits._

class TodoRepository[F[_]: Sync](transactor: Transactor[F]) {

    def create(description: String, ordering: Int, done: Boolean): F[Todo] =
        sql"INSERT INTO todo (description, ordering, done) VALUES ($description, $ordering, $done)".update
                .withUniqueGeneratedKeys[Todo]("id", "description", "ordering", "done").transact(transactor)

    def save(todo: Todo): F[Todo] =
        sql"""UPDATE todo
              SET descripion=${todo.description}, ordering=${todo.ordering}, done=${todo.done}
              WHERE id = ${todo.id}""".update.withUniqueGeneratedKeys[Todo]("id", "descripion", "ordering", "done").transact(transactor)

    def getTodos(id: Int): F[List[Todo]] =
        sql"SELECT id, description, ordering, done FROM todo WHERE list_id = $id".query[Todo].to[List].transact(transactor)

    def getDoneCount(listId: Int): F[Int] =
        sql"SELECT count(*) FROM todo WHERE list_id = $listId AND done = true".query[Int].unique.transact(transactor)

    def getCount(listId: Int): F[Int] =
        sql"SELECT count(*) FROM todo WHERE list_id = $listId".query[Int].unique.transact(transactor)

    def getTodo(id: Int): F[Option[Todo]] =
        sql"SELECT id, description, ordering, done FROM todo WHERE id = $id".query[Todo].option.transact(transactor)
}

object TodoRepository {
    case class Todo(id: Int, description: String, ordering: Int, done: Boolean)
}
