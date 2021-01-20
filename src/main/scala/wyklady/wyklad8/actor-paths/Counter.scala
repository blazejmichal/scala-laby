package wyklady.wyklad8.actor-paths

import akka.actor.Actor

class Counter extends Actor {
  import Counter._

  var count = 0

  def receive: Receive = {
    case Inc(x) =>
      count += x
    case Dec(x) =>
      count -= x
  }

}

object Counter {
  case class Inc(num: Int)
  case class Dec(num: Int)
}
