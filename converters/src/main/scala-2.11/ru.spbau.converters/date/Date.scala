package ru.spbau.converters.date

import java.time.{LocalDate, LocalDateTime}

class Day31(day: Int) {
  def january = new DayOfMonth(day, 1)
  def january(year: Year): Date = january of year

  def march = new DayOfMonth(day, 3)
  def march(year: Year): Date = march of year

  def may = new DayOfMonth(day, 5)
  def may(year: Year): Date = may of year

  def july = new DayOfMonth(day, 7)
  def july(year: Year): Date = july of year

  def august = new DayOfMonth(day, 8)
  def august(year: Year): Date = august of year

  def october = new DayOfMonth(day, 10)
  def october(year: Year): Date = october of year

  def december = new DayOfMonth(day, 12)
  def december(year: Year): Date = december of year
}

class Day30(day: Int) extends Day31(day) {
  def april = new DayOfMonth(day, 4)
  def april(year: Year): Date = april of year

  def june = new DayOfMonth(day, 6)
  def june(year: Year): Date = june of year

  def september = new DayOfMonth(day, 9)
  def september(year: Year): Date = september of year

  def november = new DayOfMonth(day, 11)
  def november(year: Year): Date = november of year
}

class Day29(day: Int) extends Day30(day) {
  def february = new DayOfMonthLeap(day, 2)
  def february(year: LeapYear): Date = february of year
}

class Day28(day: Int) extends Day29(day){
  override def february = new DayOfMonth(day, 2)
  def february(year: Year): Date = february of year
}

class DayOfMonth(day: Int, month: Int) extends DayOfMonthLeap(day, month) {
  def of(year: Year): Date = Date(day, month, year.year)
}

case class DayOfMonthLeap(day: Int, month: Int) {
  def of(year: LeapYear): Date = Date(day, month, year.year)
}

case class LeapYear(year: Int) {
}

case class Year(year: Int) {
}

case class Date(day: Int, month: Int, year: Int) {

  LocalDate.of(year, month, day)

  def this(day: Int, month: Int) = {
    this(day, month, Date.lastYear(day, month))
  }
}

object Date {

  def now = Date(LocalDateTime.now().getDayOfMonth, LocalDateTime.now().getMonthValue, LocalDateTime.now().getYear)

  def lastYear(day: Int, month: Int) = {
    val cYear = LocalDateTime.now().getYear
    val cMonth = LocalDateTime.now().getMonthValue
    val cDay = LocalDateTime.now().getDayOfMonth
    if (cMonth > month || cMonth == month && cDay >= day)
      cYear
    else
      cYear - 1
  }

}
