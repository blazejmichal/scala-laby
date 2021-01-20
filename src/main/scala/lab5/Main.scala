package lab5

import scala.collection.Set
import scala.collection.immutable.ListMap
import scala.io.Source

object Main {

  def main(args: Array[String]): Unit = {
    runTask1()
    runTask2()
    runTask3()
    runTask4()
    runTask5()
  }

  def runTask1() = {
    /**
     * Zadanie 1. Korzystając z metod oferowanych przez kolekcje zdefiniuj funkcję:
     * def minNotContained(set: Set[Int]): Int = /* ... */
     * która zwróci najmniejszą nieujemną liczbę całkowitą, która nie występuje w zbiorze set.
     * Przykład:
     * Dla: set = Set(-3, 0, 1, 2, 5, 6), funkcja powinna zwrócić: 3.
     */
    def minNotContained(set: Set[Int], check: (Int, Int) => Boolean): Int = {

      val slided = set.toSeq.sorted.sliding(2)
      slided.foreach {
        case Seq(current: Int, next: Int) =>
          if (check(current, next)) {
            return current + 1;
          }
      }

      return 0;
    }

    def check(a: Int, b: Int): Boolean = {
      if (b - a != 1 && a + 1 >= 0) {
        return true;
      }
      return false;
    }

    val arg = Set(-3, 0, 1, 2, 5, 6);
    println(minNotContained(arg, check))
  }

  def runTask2() = {
    /**
     * Zadanie 2. Korzystając z metod oferowanych przez kolekcje zdefiniuj funkcję:
     * def swap[A](seq: Seq[A]): Seq[A] = /* ... */
     * która zamieni kolejnością wartości znajdujących się na parzystych i nieparzystych indeksach.
     * Przykład:
     * Dla: seq = Seq(1, 2, 3, 4, 5), Seq(2, 1, 4, 3, 5).
     * */

    def partOddsEvens(seq: Seq[Int]): (List[Int], List[Int]) = {
      return seq.toList partition (_ % 2 == 0);
    }

    def intersperse[A](a: List[A], b: List[A]): List[A] = {
      a match {
        case first :: rest => first :: intersperse(b, rest)
        case _ => b
      }
    }

    def run(seq: Seq[Int]): Seq[Any] = {
      val oddsEvens = partOddsEvens(seq);
      return intersperse(oddsEvens._1, oddsEvens._2);
    }

    val input: Seq[Int] = Seq(1, 2, 3, 4, 5)
    val result: Seq[Any] = run(input);
    println(result)
  }

  def runTask3() = {
    /**
     * Zadanie 3. Korzystając z ciągu wszystkich stref czasowych (postaci Kontynent/Strefa):
     * val strefy: Seq[String] = java.util.TimeZone.getAvailableIDs.toSeq
     * oraz operacji na ciągach i zasugerowanej we wskazówce operacji stripPrefix na napisach,
     * wyszukaj strefy znajdujące się w Europie i posortuj rosnąco ich nazwy względem długości.
     * Strefy, których nazwy mają taką samą długość posortuj w kolejności alfabetycznej.
     * Podpowiedź: wykorzystaj między innymi metody: map, filter oraz standardową operację na napisach:
     * def stripPrefix(prefix: String): String
     * która usuwa podany prefiks z napisu, np.
     * "ala ma kota".stripPrefix("ala ") -> "ma kota"
     */

    val strefy: Seq[String] = java.util.TimeZone.getAvailableIDs.toSeq
    val european = strefy.filter(_.startsWith("Europe/"))
    val stripped = european.map(el => el.stripPrefix("Europe/"))

    def customSort(left: String, right: String): Boolean = {
      if (left.length != right.length) {
        return left.length < right.length
      } else {
        return left < right
      }
    }

    val sortedByLength = stripped.sortWith((left, right) => customSort(left, right));
    println(sortedByLength)
  }

  def runTask4(): (Int, Int) = {
    /**
     * Zadanie 4. Gra MaterMind polega na odgadnięciu w jakich miejscach zostały umieszczone n ukrytych kul, które są oznaczone powtarzającymi się kolorami.
     * Gracz wygrywa, jeżeli w określonej liczbie ruchów odgadnie w jakich miejscach zostały umieszczone kule.
     * W każdej turze gracz wybiera n kul, po czym zostaje mu wyświetlona informacja czy trafił.
     * Każda prawidłowo odgadnięta kula (kula o właściwym kolorze na właściwym miejscu) sygnalizowana jest czarną kropką.
     * Natomiast jeżeli gracz odgadł kolor kuli, ale nie odgadł jej lokalizacji, jest to sygnalizowane białą kropką.
     * Gracz nie wie, które kule są właściwe, które zaś nie.
     * Korzystając z metod oferowanych przez kolekcję zdefiniuj metodę oceniania ruchów dla gry MaterMind, czyli zwraca liczbę białych i czarnych kropek.
     * Przykład:
     * def score(code: Seq[Int])(move: Seq[Int]): (Int, Int)
     * Przykład:
     * val code = Seq(1, 3, 2, 2, 4, 5)
     * val move = Seq(2, 1, 2, 4, 7, 2)
     * Funkcja powinna zwrócić: (1, 3)
     */

    def countMatches(seqs: (Seq[Int], Seq[Int]), acc: Int = 0): Int = {
      seqs match {
        case (leftHead :: leftTail, rightHead :: rightTail) => if (leftHead == rightHead) {
          return countMatches((leftTail, rightTail), acc + 1);
        } else {
          return countMatches((leftTail, rightTail), acc);
        }
        case (Seq(), Seq()) => acc
      }
    }

    val code = Seq(1, 3, 2, 2, 4, 5)
    val move = Seq(2, 1, 2, 4, 7, 2)

    val sortedCode = code.sorted
    val sortedMove = move.sorted

    val colorPoints: Int = countMatches((sortedCode, sortedMove), 0)
    println("Ilość zgadzających się kolorów: " + colorPoints)
    val positionPoints: Int = countMatches((code, move), 0)
    println("Ilość zgadzających się pozycji: " + positionPoints)

    return (colorPoints, positionPoints)
  }

  def runTask5() = {
    /**
     * Zadanie 5.
     * Korzystając z metod oferowanych przez kolekcje, skonstruuj "histogram" ilustrujący częstotliwość występowanie w tekście poszczególnych liter w pliku ogniem_i_mieczem.txt (autor H. Sienkiewicz).
     * Małe i wielkie litery traktuj jako identyczne.
     * Pomiń występujące w tekście znaki interpunkcyjne (kropki, przecinki, myślniki, cudzysłowy itp).
     * Rozwiązanie przedstaw w postaci funkcji
     * def histogram(max: Int): Unit
     * która przyjmuje argument max oznacza maksymalną szerokość histogramu (jeżeli liter jest więcej histogram nie powinien przekroczyć max).
     * Przykład:
     * a:***************************************
     * ą:**********
     * b:*****************
     * c:**************
     * ć:*******
     * ...
     * Podpowiedź: mogą się przydać metody standardowe, takie jak np. isLetter, toLowerCase, toUpperCase itp.
     */

    def buildPrintLine(entry: (Char, Int), maxStarsAmount: Int): String = {

      val letter = entry._1 + ": "
      val stars = ("*" * entry._2).substring(0, Math.min(entry._2, maxStarsAmount));
      return letter + stars
    }

    val maxStarsAmount = 20;
    val fileContents: String = Source.fromFile("ogniem_i_mieczem.txt").getLines.mkString.toLowerCase.replaceAll("[^a-ząężźćńó]", "");
    val mapFileContents = fileContents.groupBy(identity).mapValues(_.size)
    val sortedMap: ListMap[Char, Int] = ListMap(mapFileContents.toSeq.sortWith(_._1 < _._1): _*);
    sortedMap.foreach(entry => println(buildPrintLine(entry, maxStarsAmount)));
  }

}