package com.scala.test.model

import play.api.libs.json.Json

case class Referentiel(
                        id: Option[Long],
                        name: Option[String])

object Referentiel {
  implicit val format = Json.format[Referentiel]
}
