package lab6

import scala.math.abs

class C() {

  var re: Int = 0;
  var im: Int = 0;

  def this(re: Int, im: Int) {
    this();
    this.re = re;
    this.im = im;
  }

  def this(re: Int) {
    this();
    this.re = re;
  }

  override def toString(): String = {

    val im: Int = this.im
    im match {
      case im if im > 0 => this.re + " + " + this.im + "i"
      case im if im < 0 => this.re + " - " + abs(this.im) + "i"
      case 0 => this.re.toString
    }
  }

  def +(c: C): C = {

    val result: C = new C();
    result.re = this.re + c.re
    result.im = this.im + c.im
    return result;
  }

  def -(c: C): C = {

    val result: C = new C();
    result.re = this.re - c.re
    result.im = this.im - c.im
    return result;
  }

  def *(c: C): C = {

    val result: C = new C();
    result.re = (this.re * c.re - this.im * c.im)
    result.im = (this.im * c.re + this.re * c.im)
    return result;
  }

  def /(c: C): C = {

    if (c.re == 0 || c.im == 0) {
      throw new IllegalArgumentException("Nie dziel przez zero.");
    }
    val result: C = new C();
    result.re = (this.re * c.re + this.im * c.im) / (c.re ^ 2 + c.im ^ 2);
    result.im = (this.im * c.re - this.re * c.im) / (c.re ^ 2 + c.im ^ 2);
    return result;
  }

  def +(re: Int): C = {

    val result: C = new C();
    result.re = this.re + re
    result.im = this.im + re
    return result;
  }

  def -(re: Int): C = {

    val result: C = new C();
    result.re = this.re - re
    result.im = this.im - re
    return result;
  }

  def *(re: Int): C = {

    val result: C = new C();
    result.re = this.re * re
    result.im = this.im * re
    return result;
  }

  def /(re: Int): C = {

    if (re == 0 || re == 0) {
      throw new IllegalArgumentException("Nie dziel przez zero.");
    }
    val result: C = new C();
    result.re = this.re / re;
    result.im = this.im / re;
    return result;
  }
}

object C {

  def apply(re: Int, im: Int): C = {

    return new C(re, im);
  }

}