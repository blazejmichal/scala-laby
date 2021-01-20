package wyklady.wyklad8.reaper-pattern.pattern

import akka.actor.{Actor, ActorRef, ActorLogging, Terminated}

object Reaper {
  val name = "reaper"
  // Prośba o zarejestrowanie
  case class WatchMe(ref: ActorRef)
}

trait Reaper extends Actor with ActorLogging {
  import Reaper._
  import scala.collection.mutable.ArrayBuffer

  // Zapamiętujemy kogo „nadzorujemy”
  private val watched = ArrayBuffer.empty[ActorRef]

  // Funkcja, która zostanie wywołana, gdy wszyscy
  // aktorzy „roboczy” będą już zatrzymani
  def allSoulsReaped(): Unit = {
    context.system.terminate()
    log.info("System aktorów „{}” zakończył działanie", context.system.name)
  }

  def receive: Receive = {
    case WatchMe(ref) =>
      context.watch(ref)
      if (!watched.contains(ref)) watched += ref
    case Terminated(ref) =>
      watched -= ref
      if (watched.isEmpty) allSoulsReaped()
  }

}
