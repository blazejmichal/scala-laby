package wyklady.wyklad7

import akka.actor.{Actor, ActorRef, ActorSystem, Props}

import scala.io.StdIn

object PingPong {

  object MyActor {
    case object Ball
    case class Start(oponent: ActorRef)
  }

  class MyActor extends Actor {
    import MyActor._
    def receive: Receive = {
      case Ball =>
        println(s"dostałem piłeczkę od ${sender().path.name} i odbijam")
        sender() ! Ball
      case Start(oponent) =>
        // wysyłamy piłeczkę do oponenta
        oponent ! Ball
    }
  }

  def main(args: Array[String]): Unit = {
    val system = ActorSystem("system")
    val graczA = system.actorOf(Props[MyActor](), "GraczA")
    val graczB = system.actorOf(Props[MyActor](), "GraczB")
    graczA ! MyActor.Start(graczB)
    // możemy nagle wrzucić drugą piłeczkę (sic!)
    graczB ! MyActor.Start(graczA)

    StdIn.readLine("Naciśnij żeby zakończyć działanie systemu aktorów")
    system.terminate()

  }
}
