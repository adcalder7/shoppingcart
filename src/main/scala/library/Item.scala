package library

trait IItem {
  def id:Long
  def category:String
  def name:String
  def price:Double
}

case class Item(id:Long, category:String, name:String, price:Double) extends IItem
