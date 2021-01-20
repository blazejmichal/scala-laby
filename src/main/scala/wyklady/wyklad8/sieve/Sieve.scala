package wyklady.wyklad8.sieve

import akka.actor.{Actor, ActorRef, ActorSystem, Props}

import scala.io.StdIn

object Sieve {

  object Boss {
    case class DoIt(max: Int)
    case class Result(primes: List[Int])
  }
  class Boss extends Actor {
    import Boss._
    def receive: Receive = {
      case DoIt(max) =>
        println(s"${self.path}: przygotowuję szukanie w [2..$max]")
        val worker = context.actorOf(Props[Worker](), "pracownik")
        for {
          n <- 2 to max
        } worker ! Worker.Work(n)
        worker ! Worker.Result(List(), self)
      case Result(seq) =>
        println(s"Znalezione liczby pierwsze:\n $seq")
    }
  }

  object Worker {
    case class Work(n: Int)
    case class Result(res: List[Int], boss: ActorRef)
  }

  class Worker extends Actor {
    import Worker._
    def receive: Receive = {
      case Work(n) =>
        println(s"Wyłowiłem: $n")
        context.become(withNumber(n))
    }
    def withNumber(id: Int): Receive = {
      case Work(n) =>
        if (n % id != 0) {
          // n „potencjalnie” jest liczbą pierwszą
          val worker = context.actorOf(Props[Worker](), s"pracownik$n")
          worker ! Work(n)
          context.become(withWorker(id, worker))
        }
      case Result(seq, boss) =>
        boss ! Boss.Result((id :: seq).reverse)
    }
    def withWorker(id: Int, worker: ActorRef): Receive = {
      case Work(n) =>
        if (n % id != 0) {
          // n „potencjalnie” jest liczbą pierwszą
          worker ! Work(n)
        }
      case Result(seq, boss) =>
        worker ! Result(id :: seq, boss)
    }
  }

  def main(args: Array[String]): Unit = {
    val system = ActorSystem("system")
    val boss = system.actorOf(Props[Boss](), "szef")
    val max = 100
    boss ! Boss.DoIt(max)

    println("-" * 50 + "\nNaciśnij ENTER\n" + "-" * 50)
    StdIn.readLine()
    system.terminate()
  }
}

/*
  Ćwiczenie dla chętnych:

    Dodać interfejs HTTP do powyższego przykładu (używając DSL z akka-http)
    pozwalający na podawanie argumentów dla a Boss-a z poziomu klienta HTTP.

 */
