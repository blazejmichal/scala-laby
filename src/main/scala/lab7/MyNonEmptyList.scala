package lab7

class MyNonEmptyList[A](h: A, t: MyList[A]) extends MyList[A] {

  def head: A = h

  def tail: MyList[A] = t

  def isEmpty: Boolean = false

  override def add[B >: A](a: B): MyList[B] = new MyNonEmptyList[B](a, this)

  def this() {
    this(null.asInstanceOf[A], null.asInstanceOf[MyList[A]]);
  }

  def customToString(): String = {

    def helper(myList: MyList[A], text: String): String = {
      try {
        myList.head match {
          case null => text
          case _ => helper(myList.tail, text + myList.head + ", ")
        }
      } catch {
        case _ => text
      }
    }

    val text: String = ("[" + this.h + ", " + helper(this.t, "")).dropRight(2) + "]"
    return text;
  }

}

object MyNonEmptyList extends MyNonEmptyList[Int]() {

  def apply(arguments: Int*): MyNonEmptyList[Int] = {

    val h: Int = arguments.head;
    val t: Seq[Int] = arguments.toList.tail.reverse;
    def addTails(items: Seq[Int], myList: MyList[Int] = new MyEmptyList()): MyList[Int] = {
      items match {
        case item :: tail => addTails(tail, myList.add(item));
        case Seq() => myList;
      }
    }

    val myList: MyList[Int] = addTails(t);

    new MyNonEmptyList[Int](h, myList);
  }

}
