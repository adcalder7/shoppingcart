
import library.{Cart, Checkout, Discount, DiscountResult, Item}

object TestData {

  // Simulate checkout process
  val checkout = new Checkout(getDiscountRules())

  // Simulate discount rules
  def getDiscountRules():List[Discount] = {
    List(
      Discount("20% off with 4+ Chairs", (cart:Cart) => {
        val chairItems = cart.getCategoryItems("chair")

        if (chairItems.isDefined && chairItems.get.size >= 4) {
          val discountPercent = 0.2
          Some(DiscountResult(discountPercent, cart.getTotal() - (chairItems.get.size * discountPercent)))
        } else None
      }),
      Discount("17% off per set", (cart:Cart) => {
        val hasChair = cart.getCategoryItems("chair").isDefined
        val hasTable = cart.getCategoryItems("table").isDefined
        val hasDesk = cart.getCategoryItems("desk").isDefined
        val hasCouch = cart.getCategoryItems("couch").isDefined

        if (hasChair && hasTable && hasDesk && hasCouch) {
          val discountPercent = 0.17

          // Get total amount of sets in cart
          val setCount = List(cart.getCategoryItems("chair").get.size,
            cart.getCategoryItems("table").get.size,
            cart.getCategoryItems("desk").get.size,
            cart.getCategoryItems("couch").get.size).min

          // NOTE: This can be optimized to get best price if same type of items have different values
          val setPriceSum = cart.getCategoryItems("chair").get.head.price * setCount +
            cart.getCategoryItems("table").get.head.price * setCount +
            cart.getCategoryItems("desk").get.head.price * setCount +
            cart.getCategoryItems("couch").get.head.price * setCount

          val discountAmount = setPriceSum * discountPercent

          Some(DiscountResult(discountPercent, cart.getTotal() - discountAmount))
        } else None
      }),
      Discount("15% off with 1000+ cart total", (cart:Cart) => {
        if (cart.getTotal() > 1000) {
          val discountPercent = 0.15
          val total = cart.getTotal()
          Some(DiscountResult(discountPercent, total - (total * discountPercent)))
        }
        else None
      }))
  }

  // Simulate inventory
  val chair1 = Item(1, "chair", "Chair1", 100.01)
  val chair2 = Item(2, "chair", "Chair2", 100.01)
  val chair3 = Item(3, "chair", "Chair3", 100.01)
  val chair4 = Item(4, "chair", "Chair4", 100.01)
  val couch = Item(5, "couch", "Couch", 749.99)
  val couch1 = Item(6, "couch", "Couch1", 749.99)
  val table = Item(7, "table", "Table", 249.90)
  val desk = Item(8, "desk", "Desk", 500.10)

}
