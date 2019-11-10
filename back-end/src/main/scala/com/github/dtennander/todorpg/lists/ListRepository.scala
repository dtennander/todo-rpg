package com.github.dtennander.todorpg.lists

import cats.effect.Sync
import doobie.util.transactor.Transactor
import doobie.implicits._
import com.github.dtennander.todorpg.lists.ListRepository.TodoList

class ListRepository[F[_]: Sync](transactor: Transactor[F]) {

    def getLists: F[List[TodoList]] =
        sql"SELECT (id, name) FROM todo_list".query[TodoList].to[List].transact(transactor)

    def getList(id: Int): F[Option[TodoList]] =
        sql"SELECT (id, name) FROM todo_list WHERE id = $id".query[TodoList].option.transact(transactor)

    def createList(name: String): F[TodoList] =
        sql"INSERT INTO todo_list (name) values ($name)".query[TodoList].unique.transact(transactor)


}

object ListRepository {
    case class TodoList(id: Int, name: String)
}
