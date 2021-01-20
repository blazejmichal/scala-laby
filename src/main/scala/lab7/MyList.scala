package lab7

// plus oznacza ze klasy rozszerzajace A
abstract class MyList[+A] {

  def head: A

  def tail: MyList[A]

  def isEmpty: Boolean

  // B rozszerzajace A
  def add[B >: A](a: B): MyList[B]

}
