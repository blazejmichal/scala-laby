package lab6

object Main {

  def main(args: Array[String]): Unit = {
    runTask1()
    runTask2()
    runTask3()
    runTask4()
    runTask5()
    runTask6()
    runTask7()
  }

  def runTask1() = {
    /**
     * Zadanie 1.
     * Zdefiniuj klasę C,
     * która będzie reprezentowała liczby zespolone,
     * która będzie zawierała pola re i im reprezentujące część rzeczywistą i urojoną liczby.
     * Utwórz odpowiedni konstruktor.
     */

    val c: C = new C(1, 1);
    println("Task1: " + c.re + " " + c.im + "i")
  }

  def runTask2() = {
    /**
     * Zadanie 2.
     * Skonstruuj klasę liczb zespolonych tak,
     * aby reprezentacja tekstowa jej obiektów była bardziej czytelna:
     * dla b>0: a + bi
     * dla b<0: a - bi
     * dla b=0: a
     * */

    val c: C = new C(1, 1);
    println("Task2: " + c)
  }

  def runTask3() = {
    /**
     * Zadanie 3.
     * Dodaj możliwość tworzenia nowych obiektów bez konieczności pisania new.
     * Przykładowo powinno działać przypisanie w takiej formie: val c = C(1.0, 2.5)
     */

    val c: C = C(2, -1);
    println("Task3: " + c)
  }

  def runTask4() = {
    /**
     * Zadanie 4.
     * Stwórz metody +(that: C), -(that: C), *(that: C),
     * które umożliwią wykonanie operacji arytmetycznych na liczbach zespolonych.
     * Podpowiedź:
     * (a+bi)+-(c+di) = (a+-c)+(b+-d)i
     * (a+bi)(c+di) = (ac-bd)+(bc+ad)i
     */

    val c1: C = C(1, 2);
    val c2: C = C(4, 3);
    val addResult: C = c1.+(c2);
    val subtractResult: C = c1.-(c2);
    val multiplyResult: C = c1.+(c2);
    println("Task4: ")
    println("Dodawanie: " + addResult);
    println("Odejmowanie: " + subtractResult);
    println("Mnożenie: " + multiplyResult);
  }

  def runTask5() = {
    /**
     * Zadanie 5.
     * Stwórz metody /(that: C),
     * które umożliwią wykonanie operacji arytmetycznej na liczbach zespolonych.
     * Jeżeli podany argument będzie powodował dzielenie przez 0,
     * powinien zostać uruchomiony wyjątek IllegalArgumentException.
     * Wywołaj metodę i obsłuż odpowiednio wyjątek.
     * Podpowiedź:
     * (a+bi)/(c+di) = ((ac+bd)+(bc-ad)i)/(c^2+d^2)
     */


    val c1: C = C(1, 2);
    val c2: C = C(4, 3);
    val c3: C = C(0, 3);

    println("Task5: ")
    val divideResult: C = c1./(c2);
    println("Dzielenie: " + divideResult);
    try {
      val shouldThrowException: C = c1./(c3);
    } catch {
      case exception: IllegalArgumentException => println("Wyjątek przy dzieleniu: " + exception.getMessage);
    }
  }

  def runTask6() = {
    /**
     * Zadanie 6.
     * Zdefiniuj konstruktor pomocniczy dzięki,
     * któremu będziesz mógł przypisać jedynie wartość części rzeczywistej (pierwszy argument) tworząc obiekt.
     */

    val c: C = new C(2);
    println("Task 6: " + c);
  }

  def runTask7() = {
    /**
     * Zadanie 7.
     * Dodaj możliwość wykonywania operacji arytmetycznych ze w zwykłymi liczbami rzeczywistymi.
     * Przykładowo powinny działać operacje:
     * 5.3 + C(2.1, 3.5)
     * C(2.2, 3.4) * 2.5
     */

    val c: C = C(1, 2);
    val re: Int = 2;
    val addResult: C = c.+(re);
    val subtractResult: C = c.-(re);
    val multiplyResult: C = c.+(re);
    val divideResult: C = c./(re);
    println("Task7: ")
    println("Dodawanie: " + addResult);
    println("Odejmowanie: " + subtractResult);
    println("Mnożenie: " + multiplyResult);
    println("Dzielenie: " + divideResult);
    try {
      val re0: Int = 0;
      val shouldThrowException: C = c./(re0);
    } catch {
      case exception: IllegalArgumentException => println("Wyjątek przy dzieleniu: " + exception.getMessage);
    }
  }

}