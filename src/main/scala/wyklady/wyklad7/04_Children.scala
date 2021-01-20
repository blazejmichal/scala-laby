package wyklady.wyklad7

import akka.actor.{Actor, ActorSystem, Props}

import scala.io.StdIn

// Aktorzy mogą tworzyć aktorów potomnych (i nimi zarządzać,
// o czym później)
object Children {

  case class Work(args: List[String])

  class Boss extends Actor {
    def receive: Receive = {
      case work: Work =>
        for (w <- work.args) {
          val subworker = context.actorOf(Props[Worker])
          subworker ! w
        }
      // Nieznany komunikat nie blokuje aktora, ale jest
      // odsyłany do „biura listów zagubionych”.
      // Możemy oczywiście obsłużyć „nieznane” wiadomości
      // samodzielnie:
      case msg =>
        println(s"Nieznany komunikat: $msg")
    }
  }

  class Worker extends Actor {
    def receive: Receive = {
      case msg =>
        println(s"Dostałem wiadomość $msg")
    }
  }

  def main(args: Array[String]): Unit = {
    val system = ActorSystem("system")
    val boss = system.actorOf(Props[Boss](), "Boss")

    // Poniższy komunikat uznany zostanie za nieznany
    boss ! List("Ala", "ma", "kota")
    // a ten zostanie obsłużony przez „workerów””
    boss ! Work(List("Ala", "ma", "kota"))

    StdIn.readLine("Naciśnij żeby zakończyć działanie systemu aktorów\n")
    system.terminate()

  }
}
