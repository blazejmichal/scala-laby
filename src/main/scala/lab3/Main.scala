package lab3

import scala.annotation.tailrec


object Main extends App {

  /**
   * Zadanie 1. Zdefiniuj funkcję def sum(l: List[Option[Int]]): Option[Int] = /* ... */
   * "sumującą”" listę opcjonalnych wartości całkowitych. Wynik powinien mieć wartość None wtedy i tylko wtedy, gdy wszystkie elementy listy l będą miały wartość None.
   * W definicji skorzystaj z rekurencji ogonowej i "dopasowania wzorca".
   */

  def sum(l: List[Option[Int]]): Option[Int] = {
    def helper(l: List[Option[Int]], acc: Int): Option[Int] = {
      l match {
        case List() => Some(acc)
        case l1 :: lT => helper(lT, acc + l1.getOrElse(0))
        case None :: lT => helper(lT, acc)
      }
    }

    helper(l, 0)
  }

  val arg = List(Some(1), None, Some(3), None, Some(5))
  val result1 = sum(arg)
  println("Wynik zadania 1: " + result1)

  println("\n-----------------------------------------------------\n")

  /**
   * Zadanie 2. Zdefiniuj następujące generyczne operacje na funkcjach
   * "złożenie"
   * def compose[A,B,C](f: A => B)(g: B => C): A => C
   * "iloczyn"
   * def prod[A,B,C,D](f: A => C, g: B => D): (A, B) => (C, D)
   * "podniesienie operatora"
   * def lift[A,T](op: (T,T) => T)(f: A => T, g: A => T): A => T
   */


  println("\n-----------------------------------------------------\n")

  /**
   * Zadanie 3. Niech MSet[A] "wielozbiory" typu A, czyli "zbiory z krotnością występowania elementów"
   * type MSet[A] = A => Int
   * Zdefiniuj operacje: sumy, różnicy oraz części wspólnej dla wielozbiorów:
   * def plus[A](s1: MSet[A], s2: MSet[A]): MSet[A]
   * def minus[A](s1: MSet[A], s2: MSet[A]): MSet[A]
   * def częśćWspólna[A](s1: MSet[A], s2: MSet[A]): MSet[A]
   * Wykorzystaj funkcje zdefiniowane w rozwiązaniu Zadania 2!
   */

  println("\n-----------------------------------------------------\n")

  /**
   * Zadanie 4. Napisz generyczną funkcję
   * def insertInto[A](a: A, list: List[A])(leq: (A,A) => Boolean): List[A] = /* ... */
   * wstawiającą element a do listy list zgodnie z porządkiem określonym przez leq.
   * Skorzystaj z rekurencji ogonowej i "dopasowania wzorca".
   */

  def sort(a: Int, b: Int): Boolean = a match {
    case a if (a > b) => true
    case a if (a < b) => false
  }

  def insertInto[A](a: A, list: List[A])(leq: (A, A) => Boolean): List[A] = {
    def helper(a: A, list: List[A], listHead: List[A])(compare: (A, A) => Boolean): List[A] = {
      list match {
        case List() => listHead ::: list ::: List(a)
        case element :: restList if (compare(a, element)) => helper(a, restList, listHead ::: List(element))(compare)
        case _ => listHead ::: List(a) ::: list
      }
    }

    helper(a, list, List())(leq)
  }

  val number = 4
  val list = List(1, 2, 3, 5, 6)
  val result4 = insertInto[Int](number, list)(sort)
  println("Wynik zadania 4: " + result4)

  println("\n-----------------------------------------------------\n")

  /**
   * Zadanie 5. Napisz generyczną funkcję
   * def divide[A](list: List[A]): (List[A], List[A]) = /* ... */
   * która podzieli listę list na dwie części.
   * W pierwszej będą się znajdywać elementy na parzystych indeksach w drugiej elementy na nieparzystych.
   * W definicji wykorzystaj rekurencję ogonową.
   * Przykład:
   * divide(List(1, 3, 5, 6, 7)) == (List(1, 5, 7), List(3, 6))
   * */

  def divide[A](list: List[A]): (List[A], List[A]) = {

    @tailrec
    def helper(list: List[A], evens: List[A], odds: List[A]): (List[A], List[A]) = {
      list match {
        case List() => (evens, odds)
        case even :: List() => (evens ::: List(even), odds)
        // starting from index 0
        case even :: odd :: listTail => helper(listTail, evens ::: List(even), odds ::: List(odd))
      }
    }

    helper(list, List(), List())
  }

  val inputDivide: List[String] = List("a", "b", "c", "d", "e")
  val resultDivide: (List[String], List[String]) = divide[String](inputDivide)
  println(resultDivide)

  println("\n-----------------------------------------------------\n")

  /**
   * Zadanie 6. Napisz generyczną funkcję
   * def compress[A](list: List[A]): List[(A, Int)]
   * która w liście list zastępuje każdy podciąg powtarzających się elementów a...a parą (a, długość podciągu).
   * Przykładowo:
   * compress(List('a','a','b','c','c','c','d','d','c')) == List( ('a',2), ('b',1), ('c',3), ('d',2), ('c',1) )
   * Skorzystaj z rekurencji ogonowej i "dopasowania wzorca".
   * */

  def compress[A](list: List[A]): List[(A, Int)] = {
    @tailrec
    def helper(list: List[A], result: List[(A, Int)]): List[(A, Int)] = {
      list match {
        case List() => result
        case element :: listTail => helper(list.filter(_ != element), result :+ (element, list.count(_ == element)))
      }
    }

    val acc: List[(A, Int)] = List()
    helper(list, acc)
  }

  val input6: List[String] = List("a", "b", "c", "d", "a", "b", "c", "d", "a", "z")
  val result6: List[(String, Int)] = compress[String](input6)
  println(result6)

}