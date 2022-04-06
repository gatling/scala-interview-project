package io.gatling.interview.command

object ComputerCommand {
  def parse(args: List[String]): Option[ComputerCommand] =
    args match {
      case "list" :: _ => Some(ListComputers)
      case _           => None
    }
}

sealed trait ComputerCommand

case object ListComputers extends ComputerCommand
