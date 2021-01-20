package lab7


object Main {

  def main(args: Array[String]): Unit = {
    runTask1()
    runTask2()
    runTask3()
    runTask4()
  }

  def runTask1() = {
    /**
     * Zadanie 1.
     * Zdefiniuj klasę Point, która będzie reprezentowała punkty w przestrzeni ℝ2
     * oraz będzie zawierała pola reprezentujące współrzędnych x, y.
     * Stwórz możliwość wykonywania operatorów logicznych: ==, !=, <, >, <=, >=,
     * które będą zwracały wartości zgodne z odległością punktów od współrzędnej (0, 0) na osi XY.
     */

    val point1 = Point(1, 2);
    val point2 = Point(2, 4);
    val point3 = Point(2, 1);

    println(point1.==(point2));
    println(point1.==(point3));
    println(point1.!=(point2));
    println(point1.!=(point3));
    println(point1.>(point2));
    println(point1.>(point3));
    println(point1.<(point2));
    println(point1.<(point3));
    println(point1.<=(point2));
    println(point1.<=(point3));
    println(point1.>=(point2));
    println(point1.>=(point3));
  }

  def runTask2() = {
    /**
     * Zadanie 2.
     * Na podstawie klasy abstrakcyjnej
     * abstract class MyList[+A] {
     * def head: A
     * def tail: MyList[A]
     * def isEmpty: Boolean
     * def add(a: A): MyList[A]
     * }
     * zaprojektuj własną listę, korzystając z klas pochodnych:
     * protected class MyEmptyList[A] extends MyList[A] {
     * def head: A = throw new NoSuchElementException
     * def tail: MyList[A] = throw new NoSuchElementException
     * def isEmpty: Boolean = true
     * def add(a: A): MyList[A] = new MyNonEmptyList[A](a, this)
     * }
     *
     * protected class MyNonEmptyList[A](h: A, t: MyList[A]) extends MyList[A] {
     * def head: A = h
     * def tail: MyList[A] = t
     * def isEmpty: Boolean = false
     * def add(a: A): MyList[A] = new MyNonEmptyList[A](a, this)
     * }
     * Dokonaj odpowiednich modyfikacji, które spowodują,
     * że pusta lista będzie tylko jedna (miała tylko jedną instację).
     * Możesz dodać i zmienić powyższy kod oraz dodać nowe własności.
     * Oczywiście API (czyli udostępniane użytkownikowi metody) powinno pozostać bez zmian.
     * Podpowiedź: Dla dowolnego typu X w Scali mamy "Nothing <: X"
     * */

    val myEmptyList: MyList[Nothing] = new MyEmptyList();
    val myNonEmptyList: MyList[String] = new MyNonEmptyList[String]("test", myEmptyList);
    val afterAdding: MyList[String] = myNonEmptyList.add("test2");
    val singletonEmptyList: MyList[Nothing] = SingletonEmptyList;
  }

  def runTask3() = {
    /**
     * Zadanie 3.
     * Spowoduj, żeby było możliwe definiowanie list.
     * MyList()
     * MyList(1, 2, 3)
     * Podpowiedź: Pobranie dowolnej liczby argumentów we funkcji możesz uzyskać poprzez dodanie * po
     * określeniu typu, np. funk(arg: Int*)
     */

    val myNonEmptyList: MyNonEmptyList[Int] = MyNonEmptyList(0, 1, 2, 3, 4, 8, 6);
    println(myNonEmptyList.customToString())
  }

  def runTask4() = {
    /**
     * Zadanie 4.
     * Zmodyfikuj klasę list tak, aby reprezentacja tekstowa jej obiektów była bardziej czytelna:
     * [element1, element2, ..., elementN]
     */

    val myNonEmptyList: MyNonEmptyList[Int] = MyNonEmptyList(0, 1, 2, 3, 4, 8, 6);
    println(myNonEmptyList.customToString())
  }

}