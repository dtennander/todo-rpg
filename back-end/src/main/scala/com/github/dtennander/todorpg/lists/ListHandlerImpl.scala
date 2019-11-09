package com.github.dtennander.todorpg.lists

import cats.Applicative
import com.github.dtennander.todorpg.endpoints.definitions.SmallListView.Experience
import com.github.dtennander.todorpg.endpoints.definitions.{ListView, SmallListView, WriteListView}
import com.github.dtennander.todorpg.endpoints.lists.{CreateListResponse, GetListResponse, GetListsResponse, ListsHandler}
import cats.implicits._

class ListHandlerImpl[F[_]: Applicative] extends ListsHandler[F] {
  override def getList(respond: GetListResponse.type)(id: BigInt): F[GetListResponse] = for {
    list <- ListView(id, "/asd/asd", "My List", experience = Experience(1, 5), todos = IndexedSeq()).pure[F]
  } yield respond.Ok(list)

  override def createList(respond: CreateListResponse.type)(body: WriteListView): F[CreateListResponse] = for {
    list <- ListView(1, "/asd/asd", body.name, experience = Experience(1, 5), todos = IndexedSeq()).pure[F]
  } yield respond.Ok(list)

  override def getLists(respond: GetListsResponse.type)(): F[GetListsResponse] = for {
    list <- SmallListView(1, "/asd/asd", "Ops", Experience(1,5)).pure[F]
  } yield respond.Ok(IndexedSeq(list))
}
