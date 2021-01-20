package lab10.zad1

object Utl {
  import scala.util.Random
  val rand = new Random
  val próbaUdana = 0.05

  def osoba(): Osoba = {
    // nie da sie tego zaimportowac
    //    val imie = Gen.forename().sample.get
    //    val nazwisko = Gen.surname.sample.get
    val jakasCyfra = rand.nextInt(51)
    val imie = "imie" + jakasCyfra
    val nazwisko = "nazwisko" + jakasCyfra
    Osoba(imie, nazwisko)
  }

  def ocena(): Option[Ocena] = {
    if (rand.nextDouble() > próbaUdana) {
      val nota1 = rand.nextInt(21)
      val nota2 = rand.nextInt(21)
      val nota3 = rand.nextInt(21)
      Some(Ocena(nota1, nota2, nota3))
    } else {
      None
    }
  }

}