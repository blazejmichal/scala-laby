package lab10.zad1

case class Ocena(nota1: Int, nota2: Int, nota3: Int) extends Ordered[Ocena] {

  def compare(that: Ocena): Int = {
    if (this.sumUp() > that.sumUp()) {
      return 1
    } else if (this.sumUp() == that.sumUp() && this.nota1 > that.nota1) {
      return 1
    } else if (this.sumUp() == that.sumUp() && this.nota1 == that.nota1 && this.nota3 > that.nota3) {
      return 1
    } else if (this.sumUp() == this.sumUp()) {
      return 0
    } else {
      return -1
    }
  }

  def sumUp(): Int = {

    return nota1 + nota2 + nota3
  }

}

/*
  o1 > o2 jeśli:
    - suma1 > suma2  (suma = nota1 + nota3 + nota3)
    - suma1 == suma2 oraz o1.nota1 > o2.nota1
    - suma1 == suma2, o1.nota1 == o2.nota1 oraz o1.nota3 > o2.nota3

  Jeśli o1 i o2 mają identyczne wszystkie noty to mamy REMIS

 */