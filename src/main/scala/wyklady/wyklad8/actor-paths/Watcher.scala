package wyklady.wyklad8.actor-paths

import akka.actor.{Actor, ActorIdentity, ActorRef, ActorSelection, ActorSystem, Identify}

class Watcher extends Actor {

  var counterRef: ActorRef = _

  val selection: ActorSelection = context.actorSelection("/user/counter")

  selection ! Identify(None)

  def receive: Receive = {
    case ActorIdentity(_, Some(ref)) =>
      println(s"Actor Reference for counter is ${ref}")
    case ActorIdentity(_, None) =>
      println("Actor selection for actor is dead :( ")

  }
}
