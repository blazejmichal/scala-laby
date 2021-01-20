package lab10.zad1

import akka.actor.{Actor, ActorRef, PoisonPill, Props}

object Organizator {

  case object Start

  // rozpoczynamy zawody – losujemy 50 osób, tworzymy z nich zawodników
  // i grupę eliminacyjną
  case object Runda

  // polecenie rozegrania rundy (kwalifikacyjnej bądź finałowej) –  wysyłamy Grupa.Runda
  // do aktualnej grupy
  case object Wyniki

  // polecenie wyświetlenia klasyfikacji dla aktualnej grupy
  case class Wyniki(w: Map[ActorRef, Option[Ocena]])

  // wyniki zwracane przez Grupę
  case object Stop

  // kończymy działanie
}

class Organizator extends Actor {
  // importujemy komunikaty na które ma reagować Organizator

  import Organizator._

  def receive: Receive = {
    case Start =>
      // tworzenie 50. osób, opdowiadających im Zawodników
      for (i <- 1 to 50) {
        val imie = "imie" + i
        val nazwisko = "nazwisko" + i
        val osoba: Osoba = Osoba(imie, nazwisko)
        val competitor = context.actorOf(Props(new Zawodnik(osoba)), "zawodnik" + i)
      }
      val competitors: List[ActorRef] = getCompetitors()
      val firstGroup = context.actorOf(Props(new Grupa(competitors)), "Group")
    // oraz Grupy eliminacyjnej

    // Obsługa pozostałych komunikatów

    case Runda =>
      val group: ActorRef = context.child("Group").getOrElse(null)
      if (group != null) {
        group ! Grupa.Runda
        //        group ! Wyniki
        //        group ! PoisonPill
      } else {
        val competitors: List[ActorRef] = getCompetitors()
        val finalGroup = context.actorOf(Props(new Grupa(competitors)), "Group")
        finalGroup ! Grupa.Runda
        //        group ! Wyniki
        //        group ! PoisonPill
      }

    case Wyniki =>
      val group: ActorRef = context.child("Group").get
      group ! Grupa.Wyniki

    case Wyniki(zawodnicy: Map[ActorRef, Option[Ocena]]) =>
      val filteredCompetitors = zawodnicy.filter(_._2.get != null)
      val competitors: List[(ActorRef, Ocena)] = filteredCompetitors.toList.map(item => (item._1, item._2.get))
      val sortedCompetitors = competitors.sorted
      println(competitors.length)
      println(competitors)
      val bestCompetitors = sortedCompetitors.take(20)
      val poorCompetitors = sortedCompetitors.filterNot(bestCompetitors.contains(_))
      poorCompetitors foreach (competitor => {
        competitor._1 ! PoisonPill
      })
      sender() ! PoisonPill

    case Stop =>
      println("kończymy zawody...")
      context.system.terminate()
  }

  def getCompetitors(): List[ActorRef]={

    return context.children.toList.filter(actor => actor.path.name.startsWith("zawodnik"))
  }

}

