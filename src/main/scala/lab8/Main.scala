package lab8

import akka.actor.{ActorSystem, Props}
import lab8.zad1.Player
import lab8.zad1.Player.PlayWith
import lab8.zad2.Wezel

import scala.io.StdIn


object Main {

  def main(args: Array[String]): Unit = {
    runTask1()
    runTask2()
    runTask3()
  }

  def runTask1(): Unit = {
    /**
     * Zadanie 1.
     * Zmodyfikuj/uogólnij kod rozgrywki Ping Ponga tak, aby:
     * rozgrywka pomiędzy Johnem i Kate składała się z zadanej liczby odbić,
     * podanej w komunikacie typu.
     * case class Play(a: ActorRef, maks: Int)
     * Po wykonaniu maks odbić zakończ działanie programu korzystając z metody
     * context.system.terminate()
     * mogła odbywać się "po okręgu" składającym się z zadanej liczby aktorów,
     * a gra kończyła się po tym, jak piłeczka zatoczy maks pełnych okręgów.
     */

    val actorSystem: ActorSystem = ActorSystem("sys")
    val addrOfJohn = actorSystem.actorOf(Props[Player](), "John")
    val addrOfKate = actorSystem.actorOf(Props[Player](), "Kate")
    val addrOfWilliam = actorSystem.actorOf(Props[Player](), "William")

    addrOfJohn ! PlayWith(List(addrOfKate, addrOfWilliam, addrOfJohn), 10)

    println("-" * 50 + "Naciśnij ENTER żeby zakończyć" + "-" * 50)
    StdIn.readLine()
    actorSystem.terminate()
  }

  def runTask2(): Unit = {
    /**
     * Zadanie 2. Zdefiniuj klasę
     * class Węzeł extends Actor { ... }
     * tak, aby aktorzy przez nią określeni mogli posłużyć do budowy "binarnych drzew poszukiwań".
     * Powinni oni reagować na dwa typy komunikatów:
     * case class Wstaw(n: Int)
     * case class Znajdź(n: Int)
     * Każdy Węzeł po utworzeniu, korzystając z "tożsamości"
     * def liść(wartość: Int): Receive = { ... }
     * przechowuje liczbę, którą otrzymał w pierwszym komunikacie Wstaw.
     * Kolejne komunikaty tej postaci powinny spowodować,
     * że węzeł (w zależności od ich zawartości) zmieni swoją tożsamość na jedną z:
     * def zLewymPoddrzewem(lewe: ActorRef, wartość: Int): Receive = { ... }
     * def zPrawymPoddrzewem(wartość: Int, prawe: ActorRef): Receive = { ... }
     * def zPoddrzewami(lewe: ActorRef, wartość: Int, prawe: ActorRef): Receive = { ... }
     * Wybór tożsamości zależy od tego czy otrzymana liczba n jest mniejsza (wtedy przechodzimy do zLewymPoddrzewem)
     * lub większa (przechodzimy do zPrawymPoddrzewem) od wartość. Jeśli n == wartość to taki komunikat Węzeł pomija.
     * W ten sposób, wraz z kolejnymi komunikatami Wstaw powstaje binarne drzewo złożone z aktorów,
     * które ma własność: „dla każdego węzła wartości węzłów w jego lewym poddrzewie są mniejsze,
     * a w prawym – większe od wartość.
     * Komunikat Znajdź(n) powinien "spłynąć" po drzewie (od jego korzenia w dół), aż do węzła,
     * który może jednoznacznie stwierdzić, że zawiera on jego wartość, bądź,
     * że w drzewie brak liczby n.
     * Fakt znalezienia (bądź nie) liczby n aktor powinien zasygnalizować komunikatem na konsoli.
     */

    val actorSystem: ActorSystem = ActorSystem("system")
    val rootActor = actorSystem.actorOf(Props[Wezel](), "3")
    rootActor ! Wezel.Setup(3)
    rootActor ! Wezel.Wstaw(5)
    rootActor ! Wezel.Wstaw(2)
    rootActor ! Wezel.Znajdz(2)
  }

  def runTask3(): Unit = {
    /**
     * Zadanie 3. Zdefiniuj klasę
     * class Element extends Actor { … }
     * tak, żeby reagował na komunikaty
     * case class Wstaw(n: Int)
     * case class Usuń(n: Int)
     * Pierwszy otrzymany komunikat Wstaw(n) powinien spowodować zmianę tożsamości na
     * def korzeń(wartość: Int): Receive = { ... }
     * (oczywiście wywołaną z wartością n). Kolejne komunikaty postaci Wstaw(n),
     * gdzie n == wartość powinny spowodować przejście do tożsamości
     * def zPotomkami(wartość, kolejny: Set[ActorRef])
     * W tej tożsamości kolejne komunikaty Wstaw(wartość) powinny dodawać kolejne elementy do zbioru potomkowie.
     * Liczba elementów zbioru potomkowie powiększona o jeden będzie zatem równa liczbie otrzymanych przez "korzeń" komunikatów postaci Wstaw(wartość).
     * Dążymy do sytuacji, w której – przykładowo – po otrzymaniu czterech komunikatów Wstaw(wartość 1),
     * dwóch Wstaw(wartość 2), trzech Wstaw(wartość 3) oraz jednego Wstaw(wartość 4) "stan" systemu będzie wyglądał następująco:
     * Zadanie
     * Element-y "stojące w korzeniach" pomijają komunikaty Wstaw(n) dla n != wartość.
     * Analogicznie pomijane są przez nie komunikaty Usuń(n) dla n != wartość.
     * Jeśli "korzeń" otrzyma komunikat Usuń(wartość) to powinien zakończyć działanie jednego ze swoich potomków wysyłając do niego sygnał PoisonPill,
     * a następnie usunąć go ze zbioru potomkowie (a tak naprawdę, przejść do nowej tożsamości).
     * Jeśli "korzeń" nie ma jeszcze potomków to musi zadbać o usunięcie samego siebie i poinformowanie o tym fakcie "nadzorcy" za pomocą komunikatu.
     * case class Pusto(n: Int)
     * postaci Pusto(wartość).
     * Aktor typu
     * class Nadzorca extends Actor { ... }
     * czuwa nad całością i reaguje na komunikaty Wstaw,
     * Usuń oraz Pusto i musi zadbać o tworzenie "elementów-korzeni" w sytuacji,
     * gdy dana wartość nie jest w systemie w danym momencie reprezentowana.
     * W tym celu, w swojej "tożsamości" przechowuje zbiór
     * def stan(listy: Set[Int]): Receive = { ... }
     * Otrzymując komunikat Wstaw(n) taki, że listy.contains(n) "wie", że musi utworzyć "korzeń" dla wartości n.
     * Otrzymując komunikat Pusto "nadzorca" musi stosownie zmodyfikować swój stan (tzn. zamienić tożsamość na nową,
     * w której zbiór stan ma uaktualnioną wartość).
     * Dla poprawnego działania ważne jest, aby nadzorca, tworząc kolejne korzenie nadawał im nazwy postaci rootNNN,
     * a komunikaty Wstaw oraz Usuń rozsyłał (jedynie) do nich – korzystając z konstrukcji
     * context.actorSelection("/user/nadzorca/root*") ! ???
     * gdzie nadzorca jest nazwą nadaną nadzorcy w momencie powoływania go do życia.
     */

  }

}