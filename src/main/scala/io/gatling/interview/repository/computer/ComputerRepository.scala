package io.gatling.interview.repository.computer

import cats.effect.Sync
import io.circe.parser._
import io.circe.syntax.EncoderOps
import io.gatling.interview.dto.computer.ComputerPrinterDTO
import io.gatling.interview.model.company.Company
import io.gatling.interview.model.computer.Computer

import java.nio.file.{Files, Paths}

class ComputerRepository[F[_]](implicit F: Sync[F]) {


  def findAll(companies: Seq[Company]): F[Seq[ComputerPrinterDTO]] = F.pure {
    //Simulate  find * in db
    val listOfAllComputers = loadJsonFileReturnSeqOfComputers
    //Simulate where clause
    val filteredComputers = listOfAllComputers.filter(_.introduced.isDefined).filter(_.discontinued.isDefined)
    // Convert "Entity" to "DTO" to print extended values
    var list :Seq[ComputerPrinterDTO]=Seq.empty[ComputerPrinterDTO]
      for(f <- filteredComputers){
        if(f.companyId.isDefined){
          val company = companies.filter((_.id == f.companyId.get)).head
          val computer = Computer.computerMapper(f,Some(company.name))
          list= list:+computer
        }else{
          val computer = Computer.computerMapper(f,None)
          list= list:+computer
        }
    }
    list
  }

  // Retreive the computer from the id
  def get(computerId: Long): Option[Computer] =  {
    val computers = loadJsonFileReturnSeqOfComputers
    computers.find(computer => computer.id == computerId)
  }


  def insert(newComputer: Computer): F[Unit] = F.pure {
    //Simulate an insert into database (insert into json file)
    val listOfAllComputers = loadJsonFileReturnSeqOfComputers
    val newList = listOfAllComputers :+ newComputer
    Files.writeString(Paths.get("src/main/resources/data/mycomputers.json"), newList.asJson.toString())
  }

  def update(initComputer: Computer, modifiedComputer: Computer): F[Unit] = F.pure {
    val listOfAllComputers = loadJsonFileReturnSeqOfComputers
    val i = listOfAllComputers.indexOf(initComputer)
    val newList = listOfAllComputers.updated(i, modifiedComputer)
    Files.writeString(Paths.get("src/main/resources/data/mycomputers.json"), newList.asJson.toString())

  }

  // Return the max id of the computer list
  def getMaxComputerId(): Long = {
    val listOfAllComputers = loadJsonFileReturnSeqOfComputers
    listOfAllComputers.map(_.id).max
  }

  // function return the json file
  def loadJsonFileReturnSeqOfComputers = {
    val json = Files.readString(Paths.get("src/main/resources/data/mycomputers.json"))
    val r = for {
      doc <- parse(json)
      e <- doc.as[List[Computer]]
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
