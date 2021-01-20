package wyklady.wyklad6

object Wyklad_06 {

  def main(args: Array[String]): Unit = {
    // test polskich znaków „na wyjściu”
    println("Pożółkłe liście drzew...") 
    val jednaDruga = Q(1,2)
    val jednaCzwarta = Q(1,4)
    println(s"$jednaDruga + $jednaCzwarta == ${jednaDruga + jednaCzwarta}")
    println(s"$jednaDruga * $jednaCzwarta == ${jednaDruga * jednaCzwarta}")

    val jeden = jednaDruga * Q(2)
    val dwa = jednaDruga * 4
    val q = jednaDruga + 4

    println(s"1/2 + 4 == $q")
    val dwaIJednaDruga = 2 + jednaDruga * 4

    println(s"2 + 1/2 * 4 = $dwaIJednaDruga")

    println(s"1/2 > 1/3 ? : ${Q(1,2) > Q(1,3)}")
    println(s"1 == 3/3 ? : ${Q(1) == Q(3,3)}")

    println(s"Q(3,3).licz, Q(3,3).mian ==> ${Q(3,3).licz}, ${Q(3,3).mian}")

    f(2)
    f(Q(2, 3))

  }

  // żeby móc stosować „dopasowanie wzorca” na obiektach typu Q
  // klasa liczb wymiernych musi implementować metodę „unapply”
  def f(q: Q): Unit = q match {
    case Q(n, 1) => println(s"całkowita liczba wymierna: $n")
    case Q(n, m) => println(s"„prawdziwa” liczba wymierna: $q")
  }

}
