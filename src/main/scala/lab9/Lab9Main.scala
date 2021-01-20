package lab9

import akka.actor.{ActorSystem, Props}
import lab9.actors.Boss
import lab9.actors.Boss.{Initialize, Zlecenie}

object Lab9Main {

  def main(args: Array[String]): Unit = {
    runTask1()
  }

  def runTask1(): Unit = {
    /**
     * Zadanie 1.
     * (Autor zadania: dr W. Pawłowski)
     * Wykorzystując "zalążek" znajdujący się w pliku lab9.zip zaimplementuj "rozproszony licznik słów".
     * Jego infrastruktura powinna składać się z aktora głównego, typu Nadzorca class Nadzorca extends Actor { ... }
     * oraz dynamicznie określanej (w momencie inicjowania działania nadzorcy) liczby aktorów "roboczych", typu Pracownik
     * class Pracownik extends Actor { ... }
     * Po uruchomieniu, Nadzorca jest w stanie przyjąć jedynie komunikat inicjalizacyjny postaci
     * case class Init(liczbaPracownikow: Int)
     * w którego efekcie powinien utworzyć zadaną liczbę aktorów typu Pracownik i przejść do stanu,
     * w którym jest w stanie przyjmować "zlecenia" w postaci komunikatów
     * case class Zlecenie(tekst: List[String])
     * Poszczególne elementy listy zawartej w zleceniu powinny być następnie przekazane do wykonania pracownikom,
     * zgodnie z zasadą "w koło Macieju". Służyć do tego powinny komunikaty
     * case class Wykonaj( /* argumenty */ )
     * Pracownicy zwracają informację o liczbie znalezionych słów do nadzorcy, używając komunikatów
     * case class Wynik( /* argumenty */ )
     * Nadzorca sumuje napływające wyniki
     * i po otrzymaniu wszystkich odpowiedzi od pracowników wypisuje na konsoli wynik i wraca do stanu oczekiwania na kolejne zlecenie.
     *
     * Podpowiedź:
     * Plik ogniem_i_mieczem.txt znajduje się już w odpowiednim katalogu szablonu rozwiązania.
     */

    def dane(): List[String] = {
      scala.io.Source.fromResource("ogniem_i_mieczem.txt").getLines.toList
    }

    val lines: List[String] = dane();

    val system = ActorSystem("WordCounter")
    val boss = system.actorOf(Props[Boss], "Boss")
    boss ! Initialize(5)
    boss ! Zlecenie(lines)

  }

}
