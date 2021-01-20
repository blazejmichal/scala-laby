package wyklady

class Wyklad2 extends App {

  //=====================================================================
  // Zmienne
  //=====================================================================
  var x = 0  // deklaracja (i inicjalizacja) zmiennej x (typu Int)

  // wyrażenia i efekty uboczne ich obliczania
  x += 1 // x = x + 1

  // Jakiego typu jest wyrażenie „x += 1” ?
  // Jest ono typu „Unit”  - ()  // <= te nawiasy to jedyna wartość typu Unit (sic!)
  var y: Unit = (x += 1)

  println(s"x=${x}, y=${y}")

  // Wartości
  //=====================================================================
  // „wartości są niezmienne”
  val y = 5   // deklaracja wartości y
  val (z, v, _, _) = (10, "aqq", 3.14, (1,2)) // po lewej stronie prosty „wzorzec” – czwórka (element typu „iloczyn kartezjański”)
  //z += 1 // wystąpi błąd kompilacji
  println(s"Wartość z=$z, a wartość v=$v")

  //=====================================================================
  // Typy podstawowe
  //---------------------------------------------------------------------
  // typy numeryczne
  val byteValue: Byte = 127       //  8-bit (-2^7 .. 2^7-1)
  val shortValue: Short = 1000    // 16-bit (-2^15 .. 2^15-1)
  val intValue: Int = 30000       // 32-bit (-2^31 .. 2^31-1)
  val longValue: Long = 650000    // 64-bit (-2^63 .. 2^63-1)
  val floatValue: Float = 3.14f   // 32-bit (IEEE 754 single-precision float)
  val doubleValue: Double = 3.14d // 64-bit (IEEE 754 double-precision float)
  val pi = 3.14 // pi otrzyma domyślnie typ Double
  // pozostałe typy proste
  val charValue: Char = 'A' // 16-bit (Unicode – nie mylić z kodowaniem użytym w pliku!)
  val stringValue: String = "Ala ma kota\ni psa"
  val multiLineStringValue: String =
    """
      |Ala
      |ma
      |kota
      |i psa
""".stripMargin
  val booleanValue: Boolean = x < y

  //=====================================================================
  //  Wyrażenia
  //=====================================================================
  var x = 0
  val aqq: Unit = (x += 1) // „efekt uboczny” (side effect)
  println(aqq) // wypisze (jedyną możliwą) wartość typu Unit – ()

  val wyrażenieWarunkowe = if (x > 0) 100 else 200
  val instrukcjaWarunkowa = if (x > 0) x -= 1 else x += 1

  println(s"wartością równości jest: ${instrukcjaWarunkowa == ()}")

  // Wyrażenie blokowe – wartość całości to wartość ostatniego wyrażenia w bloku
  val wyrażenieBlokowe = {
    123
    "ala ma kota"
    if (x > 0) "aqq" else "ahoj"
    233
  }

  var zmienneWyrażenieBlokowe = {
    123
    "ala ma kota"
    if (x > 0) "aqq" else "ahoj"
  }

  zmienneWyrażenieBlokowe += " buueeee"
  zmienneWyrażenieBlokowe = {
    99
    "ala ma kota"
  }

  // „instrukcje”, jak np. pętla while, też są w Scali wyrażeniami,
  // a ich typem jest Unit
  val pętla = while (x < 5) {
    println(x)
    x += 1
  }

  println(s"wyrażenie „pętla==()” ma wartość: ${pętla == ()}")

  // Funkcje, rekurencja, rekurencja ogonowa
  // funkcja jako wartość
  val f = (x:Int) => x + 1
  f(12)                                   // ==> 13
  // „lambda” (czyli literał funkcyjny)
  (x:Int) => x + 7

  // proste funkcje
  def inc(x: Int): Int = x + 1
  // inc: Int => Int
  println(inc(12))                        // ==> 13

  // funkcje mogą mieć więcej parametrów
  def max(x: Int, y: Int): Int = {
    if (x > y) x
    else y
  }

  // a teraz jako funkcja a nie „metoda”
  val max2 = (x: Int, y: Int) => {
    if (x > y) x
    else y
  }

  // albo mniej...
  // funkcja (metoda) z pustą listą parametrów:
  def emptyParListFun(): String = "Aqq!"
  println(emptyParListFun())              // ==> "Aqq!"

  val emptyParListFun2 = () => "Aqq!"

  emptyParListFun2


  emptyParListFun2()

  //@annotation.tailrec
  def silnia(n: Int): Int = {
    if (n <= 0) 1
    else n * silnia(n - 1)
  }
  //  Jak wygląda obliczenie silnia(3) ???
  /*
        3 * silnia(2) ==>
            2 * silnia(1) ==>
                1 * silnia(0) ==>
                    1
                1
            2
        6
  */
  // println(s"silnia(11620) == ${silnia(11620)}") // <= a to „wylatuje w kosmos” - przepaełnienie stosu wywołań

  @annotation.tailrec
  def silniaOgonowa(n: BigInt, wynik: BigInt = 1): BigInt = {
    if (n <= 0) wynik
    else silniaOgonowa(n - 1, n * wynik)
  }

  println(s"silniaOgonowa(11620) == ${silniaOgonowa(11620)}")

}
