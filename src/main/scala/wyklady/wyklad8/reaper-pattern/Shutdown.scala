package wyklady.wyklad8.reaper-pattern

import akka.actor.{ActorSystem, Actor, Props, PoisonPill, ActorLogging}
import pattern._

class Target extends Actor with ReaperWatched with ActorLogging {

  def receive: Receive = {
    case msg =>
      log.info("{} dostał wiadomość: „{}”", self.path.name, msg)
  }

}

class MainActor extends Actor with Reaper with ActorLogging {

  override def allSoulsReaped(): Unit = {
    super.allSoulsReaped()
  }

  def localReceive: Receive = {
    case msg: String => log.info("{} dostał wiadomość: „{}”", self.path.name, msg)
  }

  override def receive: Receive = {
    super.receive orElse localReceive
  }

}

object ShutdownApp extends App {

  val system = ActorSystem("reaper_pattern")
  val reaper = system.actorOf(Props[MainActor](), Reaper.name)

  val target1 = system.actorOf(Props[Target](), "target1")
  val target2 = system.actorOf(Props[Target](), "target2")
  val target3 = system.actorOf(Props[Target](), "target3")

  target1 ! "Hello 1"
  target2 ! "Hello 2"
  target3 ! "Hello 3"
  reaper ! "Wiadomość"
  target1 ! PoisonPill
  target2 ! PoisonPill
  target3 ! PoisonPill

}
