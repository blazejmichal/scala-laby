package wyklady.wyklad9

import akka.actor.typed.{ActorSystem, Behavior}
import akka.actor.typed.scaladsl.Behaviors

import scala.concurrent.{ExecutionContext, Future}
import scala.util.{Failure, Success}

object Main2 {
  object Infrastructure {
    // wprowadzamy „kontekst wykonania” żeby mieć mieć dostęp do wątków
    implicit val ec: ExecutionContext = scala.concurrent.ExecutionContext.global

    private val phoneBook: Map[String, Int] = Map(
      "Daniel" -> 123,
      "Alicja" -> 456,
      "Tomek" -> 999
    )
    def asyncGetPhoneNumber(name: String): Future[Int] =
      Future(phoneBook(name))
  }

  // protokół komunikacyjny
  sealed trait PhoneCallProtocol
  case class FindAndCall(name: String) extends PhoneCallProtocol
  // dwa nowe rodzaje komunikatów (do wykorzystania w wersji 2 i 3):
  case class InitiatePhoneCall(number: Int) extends PhoneCallProtocol
  case class LogCallFailure(reason: Throwable) extends PhoneCallProtocol

  val phoneCallerV1: Behavior[PhoneCallProtocol] = Behaviors.setup { context =>
    // stan lokalny
    var noOfCalls = 0
    var noOfFailures = 0
    implicit val ec: ExecutionContext = context.executionContext

    Behaviors.receiveMessage {
      case FindAndCall(name) =>
        val futureNumber: Future[Int] = Infrastructure.asyncGetPhoneNumber(name)
        futureNumber.onComplete {
          case Success(number) =>
            context.log.info(s"Inicjuję rozmowę z $number")
            noOfCalls += 1
          case Failure(exc) =>
            context.log.error(s"Numeru brak w książce: $exc")
            noOfFailures += 1

        }
        Behaviors.same
    }
  }

  // PIPE Pattern (przekazanie wyniku Future jako wiadomości do siebie samego) – rozwiązanie powyższego problemu
  val phoneCallerV2: Behavior[PhoneCallProtocol] = Behaviors.setup { context =>
    // stan pisany zmiennymi :/
    var noOfCalls = 0
    var noOfFailures = 0
    implicit val ec: ExecutionContext = context.executionContext

    Behaviors.receiveMessage {
      case FindAndCall(name) =>
        val futureNumber: Future[Int] = Infrastructure.asyncGetPhoneNumber(name)
        context.pipeToSelf(futureNumber) {
          case Success(number) => InitiatePhoneCall(number)
          case Failure(exc) => LogCallFailure(exc)
        }
        Behaviors.same
      case InitiatePhoneCall(number) =>
        context.log.info(s"Initiating phone call for $number")
        noOfCalls += 1
        Behaviors.same
      case LogCallFailure(exc) =>
        context.log.error(s"Phone call failed: $exc")
        noOfFailures += 1
        Behaviors.same
    }
  }

  // PIPE Pattern poprawiony – zamiast zmieniającego się stanu – parametryczność
  def phoneCallerV3(noOfCalls: Int = 0, noOfFailures: Int = 0): Behavior[PhoneCallProtocol] =
    Behaviors.receive { (context, message) =>
      message match {
        case FindAndCall(name) =>
          val futureNumber: Future[Int] = Infrastructure.asyncGetPhoneNumber(name)
          context.pipeToSelf(futureNumber) {
            case Success(number) => InitiatePhoneCall(number)
            case Failure(exc) => LogCallFailure(exc)
          }
          Behaviors.same
        case InitiatePhoneCall(number) =>
          context.log.info(s"Dzwonię do $number")
          phoneCallerV3(noOfCalls + 1, noOfFailures)
        case LogCallFailure(exc) =>
          context.log.info(s"Nie udało się: $exc")
          phoneCallerV3(noOfCalls, noOfFailures + 1)
      }
    }

  def main(args: Array[String]): Unit = {
//    val root = ActorSystem(phoneCallerV1, "PhoneCaller")
//    val root = ActorSystem(phoneCallerV2, "PhoneCaller")
    val root = ActorSystem(phoneCallerV3(), "PhoneCaller")
    root ! FindAndCall("Daniel")
    root ! FindAndCall("Barbara")

    Thread.sleep(1000)
    root.terminate()
  }
}
