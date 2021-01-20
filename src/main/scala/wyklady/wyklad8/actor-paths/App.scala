package wyklady.wyklad8.actor-paths

import akka.actor.{ActorSystem, Props, Actor}

object Main extends App {

  val system = ActorSystem("watch-actor-selection")
  val counter = system.actorOf(Props[Counter](), "counter")
  val watcher = system.actorOf(Props[Watcher](), "watcher")

  Thread.sleep(1000)

  system.terminate()
}
