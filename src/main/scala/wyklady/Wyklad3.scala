package wyklady

class Wyklad3 extends App{

  // Kolekcja iterowalna - Iterable[A]
  // ---------------------------------
  // bazuje na abstrakcyjnej metodzie
  // def iterator: Iterator[A]

  val c = Iterable[Int](1,2,3,4,5,6)

  val it = c.iterator
  it.next
  it.next
  it.next
  it.next
  it.next
  it.next
  it.isEmpty
  c: Iterable[Int] = List(1, 2, 3, 4, 5, 6)
  it: Iterator[Int] = empty iterator
    res6_2: Int = 1
  res6_3: Int = 2
  res6_4: Int = 3
  res6_5: Int = 4
  res6_6: Int = 5
  res6_7: Int = 6
  res6_8: Boolean = true
  val c2 = Iterable[Int](1,2,3,4,5,6)
  // wynik zastosowania foreach jest typu Unit
  val wynik = c2 foreach ((n) => { val wyn = n * 2; println(wyn); 123 })
  println(wynik)
  2
  4
  6
  8
  10
  12
  ()
  c2: Iterable[Int] = List(1, 2, 3, 4, 5, 6)
  // inne iteratory
  val it1 = c2 grouped 2
  val it2 = c2 sliding 3

  it1.next
  it1.next

  it2.next
  it2.next
  it2.next
  it2.next
  //it2.next
  it1: Iterator[Iterable[Int]] = non-empty iterator
    it2: Iterator[Iterable[Int]] = empty iterator
    res20_2: Iterable[Int] = List(1, 2)
  res20_3: Iterable[Int] = List(3, 4)
  res20_4: Iterable[Int] = List(1, 2, 3)
  res20_5: Iterable[Int] = List(2, 3, 4)
  res20_6: Iterable[Int] = List(3, 4, 5)
  res20_7: Iterable[Int] = List(4, 5, 6)
  // przetwarzanie kolekcji iterowalnych
  // c: Iterable[Int]
  // funkcja będąca argumentem jest typu „Int => Int”
  val wynik = c map ((n) => { n * 2 })

  // "*" * 5 // *****
  val wynik2 = c flatMap ((n) => ("*" * n).toSeq)
  wynik: Iterable[Int] = List(2, 4, 6, 8, 10, 12)
  wynik2: Iterable[Char] = List(
    '*',
    '*',
    '*',
    '*',
    '*',
    '*',
    '*',
    '*',
    '*',
    '*',
    '*',
    '*',
    '*',
    '*',
    '*',
    '*',
    '*',
    '*',
    '*',
    '*',
    '*'
  )
  println(c2)
  c2.collect({case n if n < 3 => n * 2})
  List(1, 2, 3, 4, 5, 6)
  res28_1: Iterable[Int] = List(2, 4)
  // konwersje
  c2.toSet
  c2.toArray
  res30_0: Set[Int] = HashSet(5, 1, 6, 2, 3, 4)
  res30_1: Array[Int] = Array(1, 2, 3, 4, 5, 6)
  // rozmiar kolekcji
  c2.isEmpty
  c2.size
  c2.knownSize // jeśli rozmiar jest znany „od razu” to rozmiar, w przeciwnym wypadku -1
  res31_0: Boolean = false
  res31_1: Int = 6
  res31_2: Int = -1
  // „dokładanie elementów” do niemutowalnej kolekcji
  val lista = List(1,2,3)
  val wynik = 9 :: lista
  lista: List[Int] = List(1, 2, 3)
  wynik: List[Int] = List(9, 1, 2, 3)
  // dostęp do elementów kolekcji iterowalnej
  c2.head
  Iterable[Int]().headOption
  c2.headOption

  c2.last
  Iterable[Int]().lastOption
  c2.lastOption

  c2.find(n => n % 2 == 0)
  c2.find(n => n > 100)
  res40_0: Int = 1
  res40_1: Option[Int] = None
  res40_2: Option[Int] = Some(1)
  res40_3: Int = 6
  res40_4: Option[Int] = None
  res40_5: Option[Int] = Some(6)
  res40_6: Option[Int] = Some(2)
  res40_7: Option[Int] = None
  // podkolekcje
  c2.tail // bardzo sprawna odpowiedź O(1)
  c2.init // mniej sprawne - O(n)

  c2.drop(2)
  c2 dropWhile (n => n <= 3)
  c2.take(2)
  c2 takeWhile (n => n <= 3)

  c2 filter (n => n % 2 == 0)
  res45_0: Iterable[Int] = List(2, 3, 4, 5, 6)
  res45_1: Iterable[Int] = List(1, 2, 3, 4, 5)
  res45_2: Iterable[Int] = List(3, 4, 5, 6)
  res45_3: Iterable[Int] = List(4, 5, 6)
  res45_4: Iterable[Int] = List(1, 2)
  res45_5: Iterable[Int] = List(1, 2, 3)
  res45_6: Iterable[Int] = List(2, 4, 6)
  // podziały kolekcji
  c2 splitAt 3
  c2 splitAt 4
  c2 splitAt 10

  c2 span (n => n % 2 != 0)
  c2 span (n => n < 3)

  c2 partition (n => n % 2 == 0)

  c2 groupBy (n => n % 2 == 0)

  c2.groupMap(n => n % 2 == 0)(n => n * 2)

  // Ćwiczenie: jaką funkcję można by wykorzystać w miejscu „fff”
  //c2.groupMapReduce(n => n % 2 == 0)(n => n * 2)(fff)
  res53_0: (Iterable[Int], Iterable[Int]) = (List(1, 2, 3), List(4, 5, 6))
  res53_1: (Iterable[Int], Iterable[Int]) = (List(1, 2, 3, 4), List(5, 6))
  res53_2: (Iterable[Int], Iterable[Int]) = (List(1, 2, 3, 4, 5, 6), List())
  res53_3: (Iterable[Int], Iterable[Int]) = (List(1), List(2, 3, 4, 5, 6))
  res53_4: (Iterable[Int], Iterable[Int]) = (List(1, 2), List(3, 4, 5, 6))
  res53_5: (Iterable[Int], Iterable[Int]) = (List(2, 4, 6), List(1, 3, 5))
  res53_6: Map[Boolean, Iterable[Int]] = HashMap(
    false -> List(1, 3, 5),
    true -> List(2, 4, 6)
  )
  res53_7: Map[Boolean, Iterable[Int]] = Map(
    false -> List(2, 6, 10),
    true -> List(4, 8, 12)
  )
  // „zwijanie” (od lewej) kolekcji iterowalnych
  val wynik = c2.foldLeft("")((akk, el) => akk + ("@" * el))
  wynik.length

  val wynik2 = c2.reduceLeft((e1, e2) => e1 + e2)
  wynik: String = "@@@@@@@@@@@@@@@@@@@@@"
  res56_1: Int = 21
  wynik2: Int = 21
  // „zipowanie” kolekcji
  c.map(n => n + 1) zip c2

  c zip Iterable(99,98)

  c zipAll (Iterable(99, 98), -100, -200)
  res60_0: Iterable[(Int, Int)] = List(
    (2, 1),
    (3, 2),
    (4, 3),
    (5, 4),
    (6, 5),
    (7, 6)
  )
  res60_1: Iterable[(Int, Int)] = List((1, 99), (2, 98))
  res60_2: Iterable[(Int, Int)] = List(
    (1, 99),
    (2, 98),
    (3, -200),
    (4, -200),
    (5, -200),
    (6, -200)
  )
  Widok kolekcji (View)
  // obliczenie wyrażenia tworzy „kolekcję pośrednią”
  c map (n => n + 1) map (n => n * 3)

  // wydajniejszy sposób - widoki
  val widok = c.view map (n => n + 1) map (n => n * 3)
  // obiekt typu View (taki jak widok) nie

  // widok można „zmarterializować” konwertując go na kolekcję, np.
  widok.toSeq
  res67_0: Iterable[Int] = List(6, 9, 12, 15, 18, 21)
  widok: collection.View[Int] = SeqView(6, 9, 12, 15, 18, 21)
  res67_2: Seq[Int] = List(6, 9, 12, 15, 18, 21)
  Scala Collection API

}
