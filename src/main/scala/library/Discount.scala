package library

trait IDiscount {
  def name:String = ""
  def execute:Cart => Option[DiscountResult]
}

case class DiscountResult(discount:Double, price:Double)
case class Discount(override val name:String, execute:Cart => Option[DiscountResult]) extends IDiscount
