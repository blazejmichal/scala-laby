package lab7

class Point {

  var x: Int = 0;
  var y: Int = 0;

  def this(x: Int, y: Int) {
    this();
    this.x = x;
    this.y = y;
  }

  override def toString(): String = {

    return "x: " + this.x + " y: " + this.y
  }

  def calculateLengthFromZero(): Double = {

    return Math.sqrt(Math.pow(this.x, 2) + Math.pow(this.y, 2));
  }

  def ==(point: Point): Boolean = {

    return this.calculateLengthFromZero() == point.calculateLengthFromZero();
  }

  def !=(point: Point): Boolean = {

    return this.calculateLengthFromZero() != point.calculateLengthFromZero();
  }

  def <(point: Point): Boolean = {

    return this.calculateLengthFromZero() < point.calculateLengthFromZero();
  }

  def >(point: Point): Boolean = {

    return this.calculateLengthFromZero() > point.calculateLengthFromZero();
  }

  def <=(point: Point): Boolean = {

    return this.calculateLengthFromZero() <= point.calculateLengthFromZero();
  }

  def >=(point: Point): Boolean = {

    return this.calculateLengthFromZero() >= point.calculateLengthFromZero();
  }

}

object Point {

  def apply(x: Int, y: Int): Point = {

    return new Point(x, y);
  }

}