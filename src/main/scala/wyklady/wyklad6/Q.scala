package wyklady.wyklad6

import scala.annotation.tailrec

class Q(l: Int, m: Int) extends Ordered[Q] {
  // liczba wymierna nie może mieć zerowego mianownika
  require(m != 0) // wyjątek IllegalArgumentException

  // obecność zaimplementowanej funkcji „compare” jest jedynym wymogiem
  // stawianym przez cechę Ordered[A] – w zamian dostajemy „za darmo”
  // operatory <, <=, >, =>
  def compare(that: Q): Int = licz * that.mian - that.licz * mian
  // this: 1/2, that: 1/3
  // 3 - 2 > 0

  private val g = gcd(l.abs, m.abs)
  val licz: Int = l/g
  val mian: Int = m/g

  // toString powinno zwracać możliwie czytelną postać liczby wymiernej
  override def toString: String =
    if (mian == 1) s"$licz"
    else s"$licz/$mian"

  // dodawanie liczby wymiernej
  def +(q: Q): Q = new Q(licz * q.mian + q.licz * mian, mian * q.mian)
  // dodawanie liczbę całkowitej
  def +(n: Int): Q = this + Q(n)
  //mnożenie przez liczbę wymierną
  def *(q: Q): Q = new Q(licz * q.licz, mian * q.mian)
  // mnożenie przez liczbę całkowitą
  def *(q: Int): Q = new Q(licz * q, mian)
  // dodajemy konstruktor pozwalający traktować Int jako Q
  def this(n: Int) = this(n, 1)

  // poniższa adnotacja informuje kompilator, że naszą intencją
  // jest użycie rekurencji „ogonowej”, która realizowana jest
  // za pomocą pętli – oczywiście kompilator weryfikuje czy nam
  // się to udało ;)
  @tailrec
  private def gcd(a: Int, b: Int): Int =
    if(b == 0) a else gcd(b, a % b)

  // metoda definiująca równość liczb wymiernych – zwróćcie uwagę
  // na fakt, że porównywać możemy liczbę wymierną z czymś dowolnym.
  override def equals(q: Any): Boolean = q match {
    case that: Q => licz == that.licz && this.mian == that.mian
    case _ => false
  }

  // metoda wykorzystywana prze „struktury haszowe” (np. HashTable)
  override def hashCode: Int = {
    licz * 31 * 31 + mian * 31
  }

}

// „Obiekt stowarzyszony”
object Q {
  def apply(l: Int, m: Int) = new Q(l, m) // => val q = Q(1,2)
  def apply(n: Int) = new Q(n)
  def unapply(q: Q): Option[(Int, Int)] = {
    Some((q.licz, q.mian))
  }
  // ilekroć działamy z liczbami wymiernymi, to wartość całkowita
  // może być potencjalnie potraktowana jako wymierna – jest to
  // przykład zastosowania strktur/konwersji „niejawnej”. Istnieją
  // też inne zastosowania konstrukcji niejawnych, o których
  // jeszcze powiemy sobie w dalszej części wykładu.
  implicit class intAsQ(n: Int) extends Q(n,1)
}


