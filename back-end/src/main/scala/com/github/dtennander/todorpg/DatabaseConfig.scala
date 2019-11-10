package com.github.dtennander.todorpg

import scala.language.postfixOps

case class DatabaseConfig(username: String, password: String, host: String, port: Int, database: Option[String]) {

}

object DatabaseConfig {
    private val regex = "postgres:\\/\\/(\\w+):(\\S*)@(\\S+):([0-9]+)\\/?(\\S+)*"r
    def fromUrl(url: String): DatabaseConfig = {
        val result = regex.findAllIn(url)
        DatabaseConfig(
            username = result.group(1),
            password = result.group(2),
            host      = result.group(3),
            port     = Integer.valueOf(result.group(4)),
            database = Option(result.group(5))
        )
    }
}
