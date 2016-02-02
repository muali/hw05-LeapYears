package ru.spbau.converters

import java.net.URL

import scala.languageFeature.implicitConversions
import ru.spbau.converters.date.{Date, DateConversions}

/**
  * Created by Maxim Moskvitin
  */
object CurrencyConverter extends DateConversions {
  sealed trait CurrencyUnit {
  }

  object rub extends CurrencyUnit {
    override def toString = "RUB"
  }

  object usd extends CurrencyUnit {
    override def toString = "USD"
  }

  object eur extends CurrencyUnit {
    override def toString = "EUR"
  }

  def getCourseInRub(unit: String, date: Date): Double = {
    val day = date.day
    val month = date.month
    val year = date.year
    val url = f"http://www.cbr.ru/scripts/XML_daily.asp?date_req=$day%02d/$month%02d/$year%04d"
    val xml = scala.xml.XML.load(new URL(url))
    val Some(res) = xml \\ "Valute" collectFirst {
      case x if (x \\ "CharCode").text == unit => (x \\ "Value").text.replace(',', '.').toDouble
    }
    res
  }

  def convertToRub(value: Double, from: String, date: Date): Double = {
    if (from == "RUB") return value
    value * getCourseInRub(from, date)
  }

  def convertFromRub(value: Double, to: String, date: Date): Double = {
    if (to == "RUB") return value
    value / getCourseInRub(to, date)
  }

  case class ConvCurrency(value: Double, from: String, to: String) {
    def at(date: Date): Double = {
      val asRub = convertToRub(value, from, date)
      convertFromRub(asRub, to, date)
    }
  }

  case class Currency(value: Double, unit: String) {
    def to(toUnit: CurrencyUnit) = ConvCurrency(value, unit, toUnit.toString)
  }

  implicit class CurrencyProxy[T: Numeric](value: T) {
    val dValue = implicitly[Numeric[T]].toDouble(value)

    def rub = Currency(dValue, "RUB")
    def usd = Currency(dValue, "USD")
    def eur = Currency(dValue, "EUR")
  }

  implicit def convCurrencyToDouble(convCurrency: ConvCurrency): Double = convCurrency.at(Date.now)

}
