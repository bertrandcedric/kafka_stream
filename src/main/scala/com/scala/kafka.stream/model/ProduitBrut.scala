package com.scala.test.model

import java.math.BigDecimal

import play.api.libs.json.{JsNumber, JsValue, Json, Writes}

case class ProduitBrut(
                        id: Option[Long],
                        price: Option[BigDecimal])

object ProduitBrut {
  implicit val bigdecimalWrites = new Writes[BigDecimal] {
    def writes(o: BigDecimal): JsValue = {
      JsNumber(o.setScale(2))
    }
  }
  implicit val format = Json.format[ProduitBrut]
}
