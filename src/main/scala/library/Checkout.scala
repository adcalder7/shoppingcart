package library

import com.typesafe.scalalogging.LazyLogging

trait ICheckout {
  def submit(cart:Cart):Boolean
  def calculateDiscount(cart:Cart):Option[DiscountResult]
}

class Checkout(discounts:List[Discount]) extends ICheckout with LazyLogging {

  override def submit(cart:Cart):Boolean = {
    if (cart.isEmpty()) {
      logger.info(s"Cart is empty")
      return false
    }

    val subTotal = cart.getTotal()
    val discount = calculateDiscount(cart)

    val finalTotal = if (discount.isDefined) discount.get.price else subTotal

    logger.info(f"Customer bought ${cart.getCount()} items with original price $$$subTotal%1.2f")

    if (discount.isDefined) {
      logger.info(f"After applying ${discount.get.discount * 100}%% discount the final price is $$$finalTotal%1.2f")
    }

    logger.info(s"Clearing customer cart")
    logger.info(s"/**************/")
    cart.clear()

    true
  }

  override def calculateDiscount(cart:Cart):Option[DiscountResult] = {
    var finalDiscountResult:Option[DiscountResult] = None

    for (discount <- discounts) {
      val discountResultOpt = discount.execute(cart)

      if (discountResultOpt.isDefined) {
        val discountResult = discountResultOpt.get
        if (!finalDiscountResult.isDefined) {
          finalDiscountResult = Some(discountResult)
        } else if (discountResult.price < finalDiscountResult.get.price) {
          finalDiscountResult = Some(discountResult)
        }
      }
    }

    finalDiscountResult
  }

}
