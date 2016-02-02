package ru.spbau.converters.date

import scala.languageFeature.implicitConversions

/**
  * Created by Maxim Moskvitin
  */
object NoCheck extends DateConversions {
  implicit def int2DayNoCheck(day: Int) : Day28 = new Day28(day)
  implicit def int2YearNoCheck(year: Int) : Year = new Year(year)
}
