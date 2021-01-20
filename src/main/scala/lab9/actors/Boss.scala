package lab9.actors

import akka.actor.{Actor, ActorRef, PoisonPill, Props}
import lab9.actors.Boss.{Initialize, Wynik, Zlecenie}
import lab9.actors.Worker.Work

class Boss extends Actor {

  var wordCounter: Int = 0

  def receive: Receive = {

    case Initialize(workersAmount: Int) =>
      restart()
      println(s"${self.path}: przygotowuję liczenie dla [$workersAmount] pracownikow.")
      for (i <- 1 to workersAmount) {
        val worker = context.actorOf(Props[Worker](), "pracownik" + i)
      }
      println("Wszystko gotowe")

    case Zlecenie(taskLines: List[String]) =>
      val workers: List[ActorRef] = context.children.toList
      instructTask(taskLines, workers)

    case Wynik(result: Int) =>
      wordCounter = wordCounter + result
      println("Otrzymalem wynik. Policzona liczba slow to: " + wordCounter)

    case msg => println(s"Jestem szefem i odebrałem wiadomosc: $msg")
  }

  def instructTask(taskLines: List[String], workers: List[ActorRef]): Unit = {
    taskLines match {
      case head :: tail => {
        workers.head ! Work(taskLines.head)
        instructTask(taskLines.tail, workers.tail ::: List(workers.head))
      }
      case List() => println("Skonczono przydzielac zadania.")
    }
  }

  def restart():Unit = {
    wordCounter = 0
    if (context.children != null && context.children.toList.nonEmpty) {
      for (worker <- context.children) {
        worker ! PoisonPill
      }
    }
  }

}

object Boss {

  case class Initialize(workersAmount: Int)

  case class Zlecenie(tekst: List[String])

  case class Wynik(result: Int)

}
