package com.github.dtennander.todorpg.auth

import java.util.Collections

import cats.effect.Sync
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier
import com.google.api.client.http.HttpTransport
import com.google.api.client.http.javanet.NetHttpTransport
import com.google.api.client.json.JsonFactory
import com.google.api.client.json.jackson2.JacksonFactory


class GoogleTokenValidator[F[_]: Sync](val verifier: GoogleIdTokenVerifier) extends TokenValidator[F] {
  override def validate(token: Token): F[ValidationResult] = Sync[F].delay {
    verifier.verify(token) match {
      case null => Failure(new Exception("Invalid token"))
      case t    => Success(User(
        t.getPayload.getSubject,
        t.getPayload.get("given_name").asInstanceOf[String],
        t.getPayload.get("picture").asInstanceOf[String],
      ))
    }
  }
}

object GoogleTokenValidator {
  private val transport: HttpTransport = new NetHttpTransport()
  private val jsonFactory: JsonFactory = JacksonFactory.getDefaultInstance
  def withToken[F[_]: Sync](clientId: String): F[GoogleTokenValidator[F]] = Sync[F].delay {
    val verifier = new GoogleIdTokenVerifier.Builder(transport, jsonFactory)
      .setAudience(Collections.singletonList(clientId))
      .build()
    new GoogleTokenValidator[F](verifier)
  }
}
