package wyklady.wyklad8.reaper-pattern.pattern

import akka.actor.{Actor, ActorLogging}

trait ReaperWatched {
  this: Actor with ActorLogging =>

  override def preStart(): Unit = {
    context.actorSelection("/user/" + Reaper.name) ! Reaper.WatchMe(self)
  }

  override def postStop(): Unit = {
    log.info("{} zakończył działanie", self.path.name)
  }

}
