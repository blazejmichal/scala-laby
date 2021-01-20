package lab8.zad1

import akka.actor.{Actor, ActorRef}

class Player extends Actor {

  import Player._

  def receive: Receive = {
    case PlayWith(players, max) =>
      players.head ! pass(players.tail :+ players.head, max - 1)
    case pass(players, max) =>
      if (max == 0) {
        println("Koniec")
        context.system.terminate()
      } else {
        println(self.path.name + " passes")
        players.head ! pass(players.tail :+ players.head, max - 1)
      }
  }

}

object Player {

  case class pass(players: List[ActorRef], max: Int)

  case class PlayWith(players: List[ActorRef], max: Int)

}