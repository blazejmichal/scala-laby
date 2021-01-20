package wyklady.wyklad7

import akka.actor.{Actor, ActorRef, ActorSystem, Props}

import scala.io.StdIn

object PingPong2 {

  object MyActor {
    case object Ball
    case class Start(oponent: ActorRef)
    case object Play
  }

  class MyActor extends Actor {
    import MyActor._

    def receive: Receive = {
      case Start(oponent) =>
        // wysyłamy piłeczkę do oponenta
        context.become(afterInit(oponent))
        self ! Ball
      case Play =>
        context.become(readyToPlay)
    }

    def afterInit(oponent: ActorRef): Receive = {
      case Ball =>
        println(s"odbijam piłeczkę d ${oponent.path.name}")
        oponent ! Ball
    }

    def readyToPlay: Receive = {
      case Ball =>
        println(s"odbijam piłeczkę do ${sender().path.name}")
        sender() ! Ball
    }


  }

  def main(args: Array[String]): Unit = {
    val system = ActorSystem("system")
    val graczA = system.actorOf(Props[MyActor](), "GraczA")
    val graczB = system.actorOf(Props[MyActor](), "GraczB")
    // dajemy znać graczowiA, że ma zacząć grę z graczemB
    graczA ! MyActor.Start(graczB)
    // dajemy znać graczowiB, że ma grać
    graczB ! MyActor.Play

    StdIn.readLine("Naciśnij żeby zakończyć działanie systemu aktorów")
    system.terminate()

  }
}
