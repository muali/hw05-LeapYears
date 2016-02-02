package ru.spbau.converters.date

import scala.language.experimental.macros
import scala.reflect.macros.whitebox

object DateConversionsMacros {
  def int2DayImpl[T](c: whitebox.Context)(day: c.Expr[Int]): c.Expr[T] = {
    import c.universe._
    day.tree match {
      case Literal(Constant(value: Int)) => {
        value match {
          case 31 => c.Expr[T](q"new Day31($value)")
          case 30 => c.Expr[T](q"new Day30($value)")
          case 29 => c.Expr[T](q"new Day29($value)")
          case x if x > 0 && x <= 28 => c.Expr[T](q"new Day28($value)")
          case _ => c.abort(c.enclosingPosition, s"Invalid day: $value")
        }
      }
      case _ => c.Expr[T](q"new Day28($day)")
    }
  }

  def isLeapYear(year: Int) = year % 4 == 0 && year % 100 != 0 || year % 400 == 0

  def int2YearImpl[T: c.WeakTypeTag](c: whitebox.Context)(year: c.Expr[Int]): c.Expr[T] = {
    import c.universe._
    val tpe = implicitly[c.WeakTypeTag[T]].tpe
    year.tree match {
      case Literal(Constant(value: Int)) if isLeapYear(value) && tpe =:= typeOf[LeapYear] =>
        c.Expr[T](q"new LeapYear($value)")
      case Literal(Constant(value: Int)) => c.Expr[T](q"new Year($value)")
      case _ if tpe =:= typeOf[LeapYear] => c.Expr[T](q"new LeapYear($year)")
      case _ => c.Expr[T](q"new Year($year)")
    }
  }

}


class FebruaryDateConversion {
    //implicit def int2FebruaryDay[T >: FebruaryDay](day: Int): T = macro DateConversionsMacros.int2DayImpl
  implicit def int2LeapYear(year: Int): LeapYear = macro DateConversionsMacros.int2YearImpl[LeapYear]
}

class DateConversionsLP2 {
  implicit def int2Day29[T >: Day29](day: Int): T = macro DateConversionsMacros.int2DayImpl[T]
}

class DateConversionsLP1 extends DateConversionsLP2 {
  implicit def int2Day30[T >: Day30](day: Int): T = macro DateConversionsMacros.int2DayImpl[T]
  implicit def int2LeapYear(year: Int): LeapYear = macro DateConversionsMacros.int2YearImpl[LeapYear]
}

class DateConversions extends DateConversionsLP1 {
  implicit def int2Day31[T >: Day31](day: Int): T = macro DateConversionsMacros.int2DayImpl[T]
  implicit def int2Year(year: Int): Year = macro DateConversionsMacros.int2YearImpl[Year]
  implicit def dayOfMonthToDate(dayOfMonth: DayOfMonth): Date = new Date(dayOfMonth.day, dayOfMonth.month)
  implicit def leapDayOfMonthToDate(dayOfMonthLeap: DayOfMonthLeap): Date = new Date(dayOfMonthLeap.day, dayOfMonthLeap.month)
}