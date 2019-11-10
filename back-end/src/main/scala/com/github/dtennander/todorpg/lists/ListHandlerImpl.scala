package com.github.dtennander.todorpg.lists

import cats.effect.Sync
import cats.implicits._
import com.github.dtennander.todorpg.endpoints.definitions.SmallListView.Experience
import com.github.dtennander.todorpg.endpoints.definitions.{Error, ListView, SmallListView, Todo, WriteListView}
import com.github.dtennander.todorpg.endpoints.lists.{CreateListResponse, GetListResponse, GetListsResponse, ListsHandler}
import com.github.dtennander.todorpg.todos.TodoRepository

class ListHandlerImpl[F[_]: Sync](listRepo: ListRepository[F], todoRepo: TodoRepository[F]) extends ListsHandler[F] {

  override def getList(respond: GetListResponse.type)(id: BigInt): F[GetListResponse] = for {
    list <- listRepo.getList(id.toInt)
    todos <- list.map(l => todoRepo.getTodos(l.id)).sequence
  } yield (list, todos) match {
    case (Some(l), Some(ts)) =>  respond.Ok(createListView(l, ts))
    case _ => respond.NotFound(Error(404, "Not found"))
  }

  private def createListView(list: ListRepository.TodoList, todos: List[TodoRepository.Todo]) =
    ListView(
      list.id,
      list.name,
      Experience(
        todos.count(_.done), todos.length
      ),
      todos.map(t =>
        Todo(t.id, t.description, t.done)).toIndexedSeq
    )

  override def createList(respond: CreateListResponse.type)(body: WriteListView): F[CreateListResponse] = for {
    newList <- listRepo.createList(body.name)
  } yield respond.Ok(ListView(newList.id, newList.name, experience = Experience(0, 0), todos = IndexedSeq()))

  override def getLists(respond: GetListsResponse.type)(): F[GetListsResponse] = for {
    lists <- listRepo.getLists
    views <- lists.map ( l => for {
      done <- todoRepo.getDoneCount(l.id)
      all <- todoRepo.getCount(l.id)
    } yield SmallListView(l.id, l.name, Experience(done, all))).sequence
  } yield respond.Ok(views.toIndexedSeq)
}
