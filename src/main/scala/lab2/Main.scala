package lab2

import scala.annotation.tailrec
object Main extends App {

  def jestPierwsza(n: Int): Boolean = {

    @tailrec
    def helper(n: Int, i: Int = 2): Boolean = {
      if (n <= 2)
        if (n == 2)
          return true
        else
          return false
      else if (n % i == 0)
        return false;
      else if (i * i > n)
        return true;
      else helper(n, i + 1)
    }

    helper(n, 2)
  }

  val result = jestPierwsza(11)
  println("Wynik zadania 1:" + result)

  println("----------------------------------------------------")

  def ciąg(n: Int): BigInt = {

    @scala.annotation.tailrec
    def helper(n: Int, acc1: BigInt, acc2: BigInt): BigInt =
      n match {
        case 0 => acc1
        case 1 => acc2
        case _ => helper(n - 1, acc2, acc1 + acc2)
      }

    helper(n, 0, 1)
  }

  val result2 = ciąg(9)
  println("Wynik zadania 2: " + result2)

  println("----------------------------------------------------")

  @scala.annotation.tailrec
  def uporządkowana(tab: Array[Int], mlr: (Int, Int) => Boolean): Boolean = {
    if (tab.length == 0 || tab.length == 1 || tab.isEmpty) true
    else if (!mlr(tab(0), tab(1))) false
    else uporządkowana(tab.tail, mlr)
  }

  var liczby = Array(1, 2, 3, 4)
  def mlr(a: Int, b: Int): Boolean = {
    if (a < b) return true else return false
  }
  val result3 = uporządkowana(liczby, mlr)
  println("Wynik zadania 3: " + result3)

  println("----------------------------------------------------")
  @scala.annotation.tailrec
  def daSię(n: Int): Boolean = {
    if (n % 2 == 0 && n < 2) {
      println("Podaj parzysta liczbe naturalna z przedzialu (2...n]")
      return false
    }
    if (n == 2) {
      return true
    }
    if (sprawdzCzySumaOk(n, 2)) {
      return daSię(n - 2)
    } else {
      println("Cos nie zadzialalo")
      return false
    }
  }

  def sprawdzCzySumaOk(n: Int, s: Int = 2): Boolean = {
    if (!jestPierwsza(s)) {
      return sprawdzCzySumaOk(n, s + 1)
    }
    if (jestPierwsza(n - s)) {
      println("Rozklad: " + (n - s) + " i " + (s))
      return true
    } else {
      return sprawdzCzySumaOk(n, s + 1)
    }
  }

  val result4 = daSię(10)
  println("Wynik zadania 4: " + result4)

}