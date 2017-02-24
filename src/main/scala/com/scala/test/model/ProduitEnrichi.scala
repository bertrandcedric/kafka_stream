package com.scala.test.model

import java.math.BigDecimal

import play.api.libs.json.{JsNumber, JsValue, Json, Writes}

case class ProduitEnrichi(
                           id: Option[Long],
                           name: Option[String],
                           price: Option[BigDecimal])

object ProduitEnrichi {
  implicit val bigdecimalWrites = new Writes[BigDecimal] {
    def writes(o: BigDecimal): JsValue = {
      JsNumber(o.setScale(2))
    }
  }
  implicit val format = Json.format[ProduitEnrichi]
}
