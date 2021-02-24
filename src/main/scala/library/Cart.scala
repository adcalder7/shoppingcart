package library

import com.typesafe.scalalogging.LazyLogging

class Cart extends LazyLogging {
  import collection.mutable.{LinkedHashMap, Set}

  private var count:Int = 0
  private var total:Double = 0.0
  private val categoryItems:LinkedHashMap[String, Set[Item]] = LinkedHashMap[String, Set[Item]]()

  def getCategoryItems(category:String):Option[Set[Item]] = {
    categoryItems.get(category)
  }

  def addItem(item:Item):Unit = {
    if (categoryItems.get(item.category).isDefined && categoryItems(item.category).contains(item)) {
      logger.info(s"Item id ${item.id} with name ${item.name} is already in cart")
      return
    }

    total += item.price
    count += 1
    categoryItems.getOrElseUpdate(item.category, Set.empty).add(item)
  }

  def remoteItem(item:Item):Unit = {
    if (!categoryItems.contains(item.category)) {
      logger.info(s"Item id ${item.id} with name ${item.name} is not in cart")
      return
    }

    val set = categoryItems(item.category)
    set.remove(item)
    total -= item.price
    count -= 1
    if (set.isEmpty) categoryItems.remove(item.category)
  }

  def clear():Unit = {
    count = 0
    total = 0
    categoryItems.clear()
  }

  def isEmpty():Boolean = {
    categoryItems.isEmpty
  }

  def getCount():Int = {
    count
  }

  def getTotal():Double = {
    total
  }

}

