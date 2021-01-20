package lab4

object Main {

  def main(args: Array[String]): Unit = {
    runTask1()
    runTask2()
    runTask3()
    runTask4()
    runTask5()
    runTask6()
  }

  def runTask1(): Unit = {
    /**
     * Zadanie 1. Korzystając z metod drop i take zdefiniuj funkcję:
     * def subSeq[A](seq: Seq[A], begIdx: Int, endIdx: Int): Seq[A] = /* ... */
     * która zwraca podciąg ciągów sekwencji seq z przedziału od indeksu begIdx do endIdx.
     * */

    def subSeq[A](seq: Seq[A], begIdx: Int, endIdx: Int): Seq[A] = {

      return seq.drop(begIdx).take(endIdx - begIdx)
    }

    val input1: Seq[Int] = Seq(1, 2, 3, 4, 5, 6)
    val result1: Seq[Int] = subSeq[Int](input1, 2, 4)
    println("\nTask1: ")
    println(result1)
    println()
  }

  def runTask2(): Unit = {
    /**
     * Zadanie 2. Korzystając z metod filter, map i zipWithIndex zdefiniuj funkcję:
     * def remElems[A](seq: Seq[A], k: Int): Seq[A] = /* ... */
     * która usunie k-ty element sekwencji seq.
     * */

    def remElems[A](seq: Seq[A], k: Int): Seq[A] = {

      val zipped = seq.zipWithIndex
      val filtered = zipped filter (n => n._2 % k == 0)
      val mapped = filtered.map { case (element, index) => element }
      return mapped
    }

    val input2: Seq[Char] = Seq('a', 'b', 'c', 'd', 'e', 'f', 'g', 'c')
    val result2: Seq[Char] = remElems[Char](input2, 2)
    println("\nTask2: ")
    println(result2)
    println()
  }

  def runTask3(): Unit = {
    /**
     * Zadanie 3. Korzystając z metod sliding, map, foldLeft/foldRight zdefiniuj funkcję:
     * def isOrdered[A](seq: Seq[A])(leq:(A, A) => Boolean): Boolean = /* ... */
     * która zwróci informację czy wszystkie sąsiednie elementy w seq, są zgodne z predykatem leq.
     * Przykłady:
     * Dla seq = Seq(1, 2, 2, 4) i (_ < _) funkcja powinna zwrócić false.
     * Dla seq = Seq(1, 2, 2, 4) i (_ <= _) funkcja powinna zwrócić true.
     * */

    def isOrdered[A](seq: Seq[A])(leq: (A, A) => Boolean): Boolean = {

      seq.sliding(2).foreach { case Seq(current, next) =>
        if (!leq(current, next)) {
          return false;
        }
      }
      return true
    }

    def check(a: Int, b: Int): Boolean = {
      if (a <= b) return true else return false
    }

    val input3: Seq[Int] = Seq(1, 2, 3, 3, 4, 5)
    val result3: Boolean = isOrdered[Int](input3)(check)
    println("\nTask3: ")
    println(result3)
    println()
  }

  def runTask4(): Unit = {
    /**
     * Zadanie 4. Korzystając z metody groupBy zdefiniuj funkcję:
     * def freq[A](seq: Seq[A]): Seq[(A, Int)] = /* ... */
     * która zwróci częstość wystąpienia poszczególnych elementów w ciągu seq.
     * Przykład:
     * Dla seq = Seq('a','b','a','c','c','a') funkcja powinna zwrócić Seq(('a', 3),('b', 1),('c', 2)).
     * */

    def freq[A](seq: Seq[A]): Seq[(A, Int)] = {
      return seq.groupBy(identity).map(element => (element._1, element._2.length)).toSeq
    }

    val input: Seq[Char] = Seq('a', 'b', 'a', 'c', 'd', 'd');
    val result: Seq[(Char, Int)] = freq[Char](input)
    println("\nTask4: ")
    println(result)
    println()
  }

  def runTask5(): Unit = {
    /**
     * Zadanie 5. Korzystając z metody foldLeft/foldRight i zdefiniuj generyczną funkcję:
     * def deStutter[A](seq: Seq[A]): Seq[A] = /* ... */
     * która usunie z sekwencji seq wszystkie powtarzające się ciągi.
     * Przykład:
     * Dla: seq = Seq(1, 1, 2, 4, 4, 4, 1, 3), funkcja powinna zwrócić: Seq(1, 2, 4, 1, 3).
     * */

    def deStutter[A](seq: Seq[A]): Seq[A] = {
      return seq.foldLeft(Seq[A]()) {
        case (acc, item) if acc.contains(item) => acc
        case (acc, item) => acc :+ item
      }
    }

    val input: Seq[Char] = Seq('a', 'b', 'a', 'c', 'd', 'd');
    val result: Seq[Char] = deStutter[Char](input)
    println("\nTask5: ")
    println(result)
    println()
  }

  def runTask6(): Unit = {
    /**
     * Zadanie 6. Korzystając z metod oferowanych przez kolekcje zdefiniuj funkcję:
     * def diff[A](seq1: Seq[A], seq2: Seq[A]): Seq[A] = /* ... */
     * która zwróci wszystkie elementy z seq1, które nie pasują wg "indeksów" z seq2.
     * Przykład:
     * Dla: seq1 = Seq(1, 2, 3), seq2 = Seq(2, 2, 1, 3) funkcja powinna zwrócić: Seq(1, 3), ponieważ
     * seq1(0) != seq2(0) // zostawiamy
     * seq1(1) != seq2(1) // usuwamy
     * seq1(2) != seq2(2) // zostawiamy
     *
     * */

    def diff[A](seq1: Seq[A], seq2: Seq[A]): Seq[A] = {
      val zippedSeq1: Seq[(A, Int)] = seq1.zipWithIndex
      val zippedSeq2: Seq[(A, Int)] = seq2.zipWithIndex
      val result = zippedSeq1 diff zippedSeq2
      return result.sortWith(_._2 < _._2).map(item => item._1)
    }

    val input1: Seq[Int] = Seq(1, 2, 3)
    val input2: Seq[Int] = Seq(2, 2, 1, 3)
    val result: Seq[Int] = diff[Int](input1, input2)
    println("\nTask6: ")
    println(result)
    println()
  }

}
