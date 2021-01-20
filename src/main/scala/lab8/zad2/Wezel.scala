package lab8.zad2

import akka.actor.{Actor, _}
import lab8.zad2.Wezel.{Setup, Wstaw, Znajdz}


class Wezel extends Actor {

  def lisc(wartosc: Int): Receive = {
    case cmd: Object =>
      if (cmd.isInstanceOf[Wezel.Wstaw]) {
        val n = cmd.asInstanceOf[Wezel.Wstaw].n
        if (n == wartosc) {
        }
        else if (n < wartosc) {
          val nodeActor = context.actorOf(Props[Wezel](), n.toString)
          nodeActor ! Setup(n)
          context.become(zLewymPoddrzewem(nodeActor,wartosc))
        } else {
          val nodeActor = context.actorOf(Props[Wezel](), n.toString)
          nodeActor ! Setup(n)
          context.become(zPrawymPoddrzewem(wartosc,nodeActor))
        }
      } else if (cmd.isInstanceOf[Wezel.Znajdz]) {
        val n = cmd.asInstanceOf[Wezel.Znajdz].n
        if (n == wartosc) {
          println("Znaleziono wezel: " + self.toString())
        }
        else {
          println("Nie ma takiego wezla")
        }
      } else {
        print("test" + self)
      }
    case _ =>
      println()
  }

  def zLewymPoddrzewem(lewe: ActorRef, wartosc: Int): Receive = {
    case cmd: Object =>
      if (cmd.isInstanceOf[Wezel.Wstaw]) {
        val n = cmd.asInstanceOf[Wezel.Wstaw].n
        if (n == wartosc) {
          println("Taki wezel juz istnieje")
        }
        else if (n < wartosc) {
          lewe ! Wstaw(n)
        } else {
          val nodeActor = context.actorOf(Props[Wezel](), n.toString)
          nodeActor ! Setup(n)
          context.become(zPoddrzewami(lewe, wartosc, nodeActor))
        }
      }
      else if (cmd.isInstanceOf[Wezel.Znajdz]) {
        val n = cmd.asInstanceOf[Wezel.Znajdz].n
        if (n == wartosc) {
          println("Znaleziono wezel: " + self.toString())
        }
        else if (n < wartosc) {
          lewe ! Znajdz(n)
        } else {
          println("Nie ma takiego wezla")
        }
      } else {
        print("test" + self)
      }
    case _ =>
      println()
  }

  def zPrawymPoddrzewem(wartosc: Int, prawe: ActorRef): Receive = {
    case cmd: Object =>
      if (cmd.isInstanceOf[Wezel.Wstaw]) {
        val n = cmd.asInstanceOf[Wezel.Wstaw].n
        if (n == wartosc) {
          println("Taki wezel juz istnieje")
        }
        else if (n > wartosc) {
          prawe ! Wstaw(n)
        } else {
          val nodeActor = context.actorOf(Props[Wezel](), n.toString)
          nodeActor ! Setup(n)
          context.become(zPoddrzewami(nodeActor, wartosc, prawe))
        }
      }
      else if (cmd.isInstanceOf[Wezel.Znajdz]) {
        val n = cmd.asInstanceOf[Wezel.Znajdz].n
        if (n == wartosc) {
          println("Znaleziono wezel: " + self.toString())
        }
        else if (n > wartosc) {
          prawe ! Znajdz(n)
        } else {
          println("Nie ma takiego wezla")
        }
      } else {
        print("test" + self)
      }
    case _ =>
      println()
  }

  def zPoddrzewami(lewe: ActorRef, wartosc: Int, prawe: ActorRef): Receive = {
    case cmd: Object =>
      if (cmd.isInstanceOf[Wezel.Wstaw]) {
        val n = cmd.asInstanceOf[Wezel.Wstaw].n
        if (n == wartosc) {
          println("Taki wezel juz istnieje")
        }
        else if (n > wartosc) {
          prawe ! Wstaw(n)
        } else {
          lewe ! Wstaw(n)
        }
      }
      else if (cmd.isInstanceOf[Wezel.Znajdz]) {
        val n = cmd.asInstanceOf[Wezel.Znajdz].n
        if (n == wartosc) {
          println("Znaleziono wezel: " + self.toString())
        }
        else if (n > wartosc) {
          prawe ! Znajdz(n)
        } else {
          lewe ! Znajdz(n)
        }
      } else {
        print("test" + self)
      }
    case _ =>
      println()
  }

  def receive: Receive = {
    case Wstaw(n: Int) =>
      self ! Wstaw(n)
    case Znajdz(n: Int) =>
      self ! Znajdz(n)
    case Setup(n: Int) =>
      context.become(lisc(n))
  }
}

object Wezel {

  case class Wstaw(n: Int)

  case class Znajdz(n: Int)

  case class Setup(n: Int)

}


