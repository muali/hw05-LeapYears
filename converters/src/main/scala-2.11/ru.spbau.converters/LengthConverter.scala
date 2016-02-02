package ru.spbau.converters

/**
  * Created by Maxim Moskvitin
  */
object LengthConverter {
  sealed trait Length {
    def meters : Double
  }

  object m extends Length {
    def meters = 1.0
  }

  object km extends Length {
    def meters = 1000.0
  }

  object mi extends Length {
    def meters = 1609.344
  }

  object ft extends Length {
    def meters = 0.3048
  }

  object yd extends Length {
    def meters = 0.9144
  }

  object in extends Length {
    def meters = 0.0254
  }

  case class Meters (value: Double) {
    def to(unit: Length) : Double = value / unit.meters
  }

  implicit class LengthProxy[T : Numeric](value: T) {

    val units = implicitly[Numeric[T]].toDouble(value)

    def m = Meters(units)
    def km = Meters(units * LengthConverter.km.meters)
    def mi = Meters(units * LengthConverter.mi.meters)
    def ft = Meters(units * LengthConverter.ft.meters)
    def yd = Meters(units * LengthConverter.yd.meters)
    def in = Meters(units * LengthConverter.in.meters)
  }
}
