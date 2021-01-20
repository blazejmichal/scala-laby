package wyklady.wyklad9

import akka.actor.typed.{ActorSystem, Behavior}
import akka.actor.typed.scaladsl.Behaviors

object Main1 {
  // Aktorzy „utypowieni” – ActorRef[T]
  sealed trait ShoppingCartMessage
  case class AddItem(item: String) extends ShoppingCartMessage
  case class RemoveItem(item: String) extends ShoppingCartMessage
  case object ValidateCart extends ShoppingCartMessage

  def main(args: Array[String]): Unit = {
    val actorBehavior1 = Behaviors.receiveMessage[ShoppingCartMessage] { msg: ShoppingCartMessage =>
      msg match {
        case AddItem(item) => println(s"Dodaje $item do koszyka")
        case RemoveItem(item) => println(s"Usuwam $item z koszyka")
        case ValidateCart => println("Sprawdzam zawartość")
      }
      Behaviors.same
    }

    val actorBehavior2 = Behaviors.setup[ShoppingCartMessage] { ctx =>
      // tutaj możemy np. zorganizować „lokalny, mutowalny stan”
      var items: Set[String] = Set()

      Behaviors.receiveMessage[ShoppingCartMessage] { msg: ShoppingCartMessage =>
        msg match {
          case AddItem(item) =>
            items += item
            println(s"Dodaje $item do koszyka: $items")
          case RemoveItem(item) =>
            items -= item
            println(s"Usuwam $item z koszyka: $items")
          case ValidateCart => println(s"Sprawdzam zawartość: $items")
        }
        Behaviors.same
      }
    }

    // Jak pozbyć się „mutowalnego stanu”? Inaczej – jak nie używać zmiennych?
    def actorBehavior3(items: Set[String] = Set()): Behavior[ShoppingCartMessage] =
      Behaviors.receiveMessage[ShoppingCartMessage] {
        case AddItem(item) =>
          val newItems = items + item
          println(s"Dodaje $item do koszyka: $newItems")
          actorBehavior3(newItems)
        case RemoveItem(item) =>
          val newItems = items - item
          println(s"Usuwam $item z koszyka: $newItems")
          actorBehavior3(newItems)
        case ValidateCart =>
          println(s"Sprawdzam zawartość: $items")
          Behaviors.same
      }

//    val system = ActorSystem(actorBehavior1, "systemUtypowiony")
//    val system = ActorSystem(actorBehavior2, "systemUtypowiony2")
    val system = ActorSystem(actorBehavior3(), "systemUtypowiony3")
    system ! AddItem("kosiarka")
    system ! AddItem("klej „Butapren”")
    system ! RemoveItem("kosiarka")
    system ! ValidateCart

    Thread.sleep(1000)
    system.terminate()
  }

}
