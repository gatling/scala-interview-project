package io.gatling.interview.service

import cats.effect.Sync
import io.gatling.interview.dto.company.CompanyPrinterDTO
import io.gatling.interview.dto.computer.ComputerCreaterDTO
import io.gatling.interview.model.computer.Computer
import io.gatling.interview.repository.company.CompanyRepository
import io.gatling.interview.repository.computer.ComputerRepository

class Services[F[_]](computerRepository: ComputerRepository[F], companyRepository: CompanyRepository[F])(implicit F: Sync[F]) {

  def findAll() = {
    val companies = companyRepository.loadJsonFileReturnSeqOfCompany
    computerRepository.findAll(companies)
  }


  def insert(c: ComputerCreaterDTO): F[Unit] = F.pure {
    // call verify company if number is set, if the number not exists set to null
    val companyId = findCompanyIdById(c.companyId)
    val maxId = computerRepository.getMaxComputerId()
    val computer = Computer(maxId + 1, c.name, c.introduced, c.discontinued, companyId)
    computerRepository.insert(computer)
  }


  def update(computerId: Long, c: Computer): F[Unit] = F.pure {
    if (computerId == c.id) {
      val computerToUpdate = computerRepository.get(c.id)
      val companyId = findCompanyIdById(c.companyId)
      if (computerToUpdate.isDefined) {
        val computer = computerToUpdate.get
        val updatedComputer = computer.copy(c.id, c.name, c.introduced, c.discontinued, companyId)
        computerRepository.update(computer, updatedComputer)
      }
    }
  }


  def findAllCompanies(): F[Seq[CompanyPrinterDTO]] = F.pure {
    val companies = companyRepository.loadJsonFileReturnSeqOfCompany

    var list: Seq[CompanyPrinterDTO] = Seq.empty[CompanyPrinterDTO]
    for (c <- companies) {
      val companyComputers = findComputersByCompanyId(c.id)
      if (companyComputers.nonEmpty) {
        val some = Some(companyComputers)
        val company = CompanyPrinterDTO(c.id, c.name, some)
        list = list :+ company
      } else {
        val company = CompanyPrinterDTO(c.id, c.name, None)
        list = list :+ company
      }

    }
    list
  }


  def findCompanyIdById(id: Option[Long]): Option[Long] = {
    val comp = companyRepository.get(id)
    if (comp.isDefined)
      Some(comp.get.id)
    else
      None
  }


  def findComputersByCompanyId(id: Long): Seq[Computer] = {
    val allComputers = computerRepository.loadJsonFileReturnSeqOfComputers
    allComputers.filter(_.companyId.isDefined).filter(_.companyId.get == id)
  }


}
