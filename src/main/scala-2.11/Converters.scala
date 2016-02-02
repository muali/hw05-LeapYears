import ru.spbau.converters.CurrencyConverter._
import ru.spbau.converters.LengthConverter._
import ru.spbau.converters.date._
//import ru.spbau.converters.date.NoCheck._

object Converters {
  def main(args: Array[String]) {
    println(100.m to km)
    println(54.mi to yd)

    println(29.february of 2012)
    println(28.february of 2013)
    println(28.february of 2012)
//  println(29.february of "2".toInt) //runtime error - not a const int literal
//  println(29.february of 2013)
    println(29 february 2016)
    println(28 february 2013)
    println(28 february 2016)
//  println(29.february(2013))
//  println(32.january)

    println(1.usd to eur at (1.january of 2015))
    println(1.usd to rub at (1 january 2015))
    println(1.eur to rub at (1.january(2015)))

    val curUsd: Double = 1.usd to rub
    println(curUsd)
  }
}
