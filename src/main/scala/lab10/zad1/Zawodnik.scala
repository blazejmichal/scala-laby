package lab10.zad1

import akka.actor.Actor

import scala.util.Random

object Zawodnik {

  case object Proba

  // polecenie wykonania próby (kończy się zwróceniem Wyniku,
  // za pomocą komunikatu Grupa.Wynik)
}

class Zawodnik(val dane: Osoba) extends Actor {

  def receive: Receive = {

    case Zawodnik.Proba =>
      val ocena: Ocena = new Ocena(Random.nextInt(100), Random.nextInt(100), Random.nextInt(100))
      sender() ! Grupa.Wynik(Some(ocena))

    case msg => println(msg)
  }

}
