import library.Cart
import org.scalatest._
import matchers.should.Matchers._
import org.scalatest.flatspec.AnyFlatSpec

class CartSpec extends AnyFlatSpec {

  "Cart library" should "checkout without any errors" in {
    val cart = new Cart

    cart.addItem(TestData.chair1)
    cart.addItem(TestData.couch)
    cart.addItem(TestData.desk)
    cart.addItem(TestData.table)

    assert(cart.getTotal() == 1600)

    TestData.checkout.submit(cart)

    assert(cart.getTotal() == 0.0)
  }

  "Cart library" should "apply 20% discount after buying 4+ chair" in {
    val cart = new Cart

    cart.addItem(TestData.chair1)
    cart.addItem(TestData.chair2)
    cart.addItem(TestData.chair3)
    cart.addItem(TestData.chair4)

    assert(cart.getTotal() == 400.04)
    assert(TestData.checkout.calculateDiscount(cart).get.discount == 0.2)

    TestData.checkout.submit(cart)

    assert(cart.getTotal() == 0.0)
  }

  "Cart library" should "apply 17% discount after buying a set" in {
    val cart = new Cart

    cart.addItem(TestData.chair1)
    cart.addItem(TestData.couch)
    cart.addItem(TestData.desk)
    cart.addItem(TestData.table)

    assert(cart.getTotal() == 1600)
    assert(TestData.checkout.calculateDiscount(cart).get.discount == 0.17)

    TestData.checkout.submit(cart)

    assert(cart.getTotal() == 0.0)
  }

  "Cart library" should "apply 15% discount after total is > $1000" in {
    val cart = new Cart

    cart.addItem(TestData.couch)
    cart.addItem(TestData.couch1)

    assert(cart.getTotal() == 1499.98)
    assert(TestData.checkout.calculateDiscount(cart).get.discount == 0.15)

    TestData.checkout.submit(cart)

    assert(cart.getTotal() == 0.0)
  }

  "Cart library" should "apply best discount when multiple options apply" in {
    val cart = new Cart

    cart.addItem(TestData.couch)
    cart.addItem(TestData.couch1)

    assert(cart.getTotal() == 1499.98)
    assert(TestData.checkout.calculateDiscount(cart).get.discount == 0.15)

    TestData.checkout.submit(cart)

    assert(cart.getTotal() == 0.0)
  }

}
