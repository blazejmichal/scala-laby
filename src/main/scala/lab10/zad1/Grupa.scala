package lab10.zad1

import akka.actor.{Actor, ActorRef}
import lab10.zad1.Grupa.Koniec

object Grupa {

  case object Runda

  case object Wyniki

  case class Wynik(ocena: Option[Ocena])

  case object Koniec

}

class Grupa(zawodnicy: List[ActorRef]) extends Actor {

  var results: Map[ActorRef, Option[Ocena]] = Map()

  def receive: Receive = {

    case Grupa.Runda =>
      getResults(zawodnicy)

    case Grupa.Wyniki =>
      sender() ! Organizator.Wyniki(results)

    case Grupa.Wynik(ocena: Option[Ocena]) =>
      //      if (ocena.nonEmpty) {
      results = results + (sender() -> ocena)
    //      }

    case Koniec =>
      println("Koniec rundy")

    case msg =>
      val test = sender()
      println(self + msg.toString)
  }

  def getResults(zawodnicy: List[ActorRef]): Unit = {

    zawodnicy match {
      case head :: tail => {
        head ! Zawodnik.Proba
        getResults(tail)
      }
      case List() => None
    }
  }

}

