package lab9.actors

import akka.actor.Actor
import lab9.actors.Boss.Wynik
import lab9.actors.Worker.Work

class Worker extends Actor {

  def receive: Receive = {

    case Work(task: String) =>
      println("Jestem" + self + "i dostalem task")
      val result: Int = task.split(" ").length
      sender() ! Wynik(result)

    case msg => println(s"Jestem pracownikiem i odebrałem wiadomosc: $msg")
  }

}

object Worker {

  case class Work(task: String)

}
