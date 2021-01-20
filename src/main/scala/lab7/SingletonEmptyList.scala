package lab7

object SingletonEmptyList extends MyList[Nothing] {

  def head: Nothing = throw new NoSuchElementException

  def tail: MyList[Nothing] = throw new NoSuchElementException

  def isEmpty: Boolean = true

  def add[A](a: A): MyList[A] = new MyNonEmptyList[A](a, this)

}
