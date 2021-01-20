package lab1

object Main extends App {

  def findHighestCommonDivisor() = {
    println("Znajdywanie największego wspólnego dzielnika")

    println("Podaj pierwszą liczbe")
    var a = io.StdIn.readInt()

    println("podaj drugą liczbę")
    var b = io.StdIn.readInt()

    while (a != b) {
      if (a > b)
        a = a - b;
      else
        b = b - a;
    }

    println("Najwiekszy wspólny dzielnik to: " + b);
  }

  def checkIfPrime() = {
    println("Sprawdzanie czy podana liczba jest pierwszą")

    println("Podaj liczbę")
    val a: Int = io.StdIn.readInt()

    var result: Boolean = false;

    if (a == 1) {
      result = false;
    } else {
      for (i <- 2 to a - 1) {
        if (a % i == 0) {
          result = false
        }
        else {
          result = true
        }
      }
    }

    println("Podana liczba jest pierwszą: " + result)
  }

  findHighestCommonDivisor()
  checkIfPrime()

}


