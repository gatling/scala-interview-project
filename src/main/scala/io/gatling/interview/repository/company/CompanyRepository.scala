package io.gatling.interview.repository.company

import cats.effect.Sync
import io.circe.parser.parse
import io.gatling.interview.model.company.Company

import java.nio.file.{Files, Paths}

class CompanyRepository[F[_]](implicit F: Sync[F]) {

  def fetchAll(): F[Seq[Company]] = F.pure {
    //Simulate  find * in db
    loadJsonFileReturnSeqOfCompany
  }

  def get(id: Option[Long]): Option[Company] = {
    if (id.isDefined) {
      val companies = loadJsonFileReturnSeqOfCompany
      companies.find(company => company.id == id.get)
    } else {
      None
    }
  }


  // Create a function return the json
  def loadJsonFileReturnSeqOfCompany = {
    val json = Files.readString(Paths.get("src/main/resources/data/mycompanies.json"))
    val r = for {
      doc <- parse(json)
      e <- doc.as[List[Company]]
    } yield e

    r match {
      case Left(e) =>
        //println(e)
        Seq()
      case Right(v) =>
        //println(v.asJson)
        v
    }
  }

}
