package wyklady.wyklad7

import akka.actor.{Actor, ActorSystem, Props}

import scala.io.StdIn

object HelloAkka {

  // Cecha „Actor” opisuje (sekwencyjnego) „aktora”
  // który swoje oblicznia prowadzi na podstawie danych
  // uzyskiwanych za pośrednictwem komunikatów.
  class MyActor extends Actor {
    def receive: Receive = {
      case msg => println(s"Dostałem wiadomość $msg")
    }
  }

  def main(args: Array[String]): Unit = {
    // Tworzymy środowisko do działania aktorów
    val system = ActorSystem("system")
    // i dodajemy do niego aktora (rusza od razu po utworzeniu)
    val aktor = system.actorOf(Props[MyActor](), "Aktor")

    aktor ! 123
    aktor ! "A qq"
    for( n <- 1 to 10000) {
      aktor ! n
    }

    StdIn.readLine("Naciśnij żeby zakończyć działanie systemu aktorów")
    system.terminate()

  }
}
