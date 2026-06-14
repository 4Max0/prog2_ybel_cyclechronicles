package cyclechronicles;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;

public class ShopTest {

  /**
   * Helper function that creates a new Order mock. The mock is needed because to test
   * Shop.accept(), we need some Order objects, but since it is not implemented, yet we have to set
   * up these mocks for the class and it's functions.
   */
  private Order setupOrder(String name, Type bike) {
    Order order = mock(Order.class);
    when(order.getBicycleType()).thenReturn(bike);
    when(order.getCustomer()).thenReturn(name);
    return order;
  }

  /**
   * Test for if a commission gets accepted, when the customer has a Bike. Equivalence class:
   * "Normal Bike"; Accepted result: true
   */
  @Test
  void testMethodAcceptBike() {
    // given
    // we have a new valid order and the shop is not full
    Shop shop = new Shop();
    Order order1 = setupOrder("apEX", Type.RACE);

    // when
    // the shop checks if it can accept the order
    boolean accept = shop.accept(order1);

    // then
    // it will accept the order
    assertTrue(accept);
  }

  /**
   * Test for if a commission gets accepted, when the customer has an E-Bike. Equivalence class:
   * "E-Bike"; Accepted result: false
   */
  @Test
  void testMethodAcceptEBike() {
    // given
    // we have a new order concerning an E-Bike and the shop is not full
    Shop shop = new Shop();
    Order order1 = setupOrder("ZywOo", Type.EBIKE);

    // when
    // the shop checks if it can accept the order
    boolean accept = shop.accept(order1);

    // then
    // it will not accept the order
    assertFalse(accept);
  }

  /**
   * Test for if a commission gets accepted, when the customer has a Gravel Bike. Equivalence class:
   * "Gravel Bike"; Accepted result: false
   */
  @Test
  void testMethodAcceptGravelBike() {
    // given
    // we have a new order concerning a Gravel Bike and the shop is not full
    Shop shop = new Shop();
    Order order1 = setupOrder("ropz", Type.GRAVEL);

    // when
    // the shop checks if it can accept the order
    boolean accept = shop.accept(order1);

    // then
    // it will not accept the order
    assertFalse(accept);
  }

  /**
   * Test for if a commission gets accepted, when the customer does already have a commission in
   * progress. Equivalence class: "Customer has open commission"; Accepted result: false
   */
  @Test
  void testMethodAcceptOpenCustomerOrderExistsAlready() {
    // given
    // we have 2 orders from the same customer for the shop
    Shop shop = new Shop();
    Order order1 = setupOrder("Aleksib", Type.SINGLE_SPEED);
    Order order2 = setupOrder("Aleksib", Type.RACE);

    // when
    // the shop checks if it can accept the order
    shop.accept(order1);
    boolean accept = shop.accept(order2);

    // then
    // it will not accept the order, because there can only be 1 active order per customer
    assertFalse(accept);
  }

  /**
   * Test for if a commission gets accepted, when the customer does already have a commission in
   * progress. Equivalence class: "Customer has not open commission"; Accepted result: true; NOTE:
   * The same Equivalence class is indirectly represented by testMethodAcceptBike()
   */
  @Test
  void testMethodAcceptOpenCustomerOrderExistsAlreadyNot() {
    // given
    // we have orders from a customer that has no order open currently
    Shop shop = new Shop();
    Order order1 = setupOrder("Donk", Type.SINGLE_SPEED);

    // when
    // the shop checks if it can accept the order
    boolean accept = shop.accept(order1);

    // then
    // it will accept the order, because there is no other order active
    assertTrue(accept);
  }

  /**
   * Limit value test: there are too Commissions queued Aspect. Limit: "maximum 5 pending orders";
   * Accepted result: false
   */
  @Test
  void testMethodAcceptOrderQueueFull() {
    // given
    // we have six orders from customers
    Shop shop = new Shop();
    Order order1 = setupOrder("apEX", Type.RACE);
    Order order2 = setupOrder("Donk", Type.SINGLE_SPEED);
    Order order3 = setupOrder("tabseN", Type.RACE);
    Order order4 = setupOrder("makazze", Type.FIXIE);
    Order order5 = setupOrder("magixx", Type.FIXIE);
    Order order6 = setupOrder("XertioN", Type.FIXIE);

    // when
    // the shop checks if it can accept the order
    shop.accept(order1);
    shop.accept(order2);
    shop.accept(order3);
    shop.accept(order4);
    shop.accept(order5);
    boolean accept = shop.accept(order6);

    // then
    // it will not accept the last orders, because the shop is full
    assertFalse(accept);
  }

  /**
   * Limit value test: there is still space in the Commissions queued Aspect. Limit: "maximum 5
   * pending orders"; Accepted result: true
   */
  @Test
  void testMethodAcceptOrderQueueFullNot() {
    // given
    // we have five orders from customers
    Shop shop = new Shop();
    Order order1 = setupOrder("apEX", Type.RACE);
    Order order2 = setupOrder("Donk", Type.SINGLE_SPEED);
    Order order3 = setupOrder("tabseN", Type.RACE);
    Order order4 = setupOrder("makazze", Type.FIXIE);
    Order order5 = setupOrder("magixx", Type.FIXIE);

    // when
    // the shop checks if it can accept the order
    shop.accept(order1);
    shop.accept(order2);
    shop.accept(order3);
    shop.accept(order4);
    boolean accept = shop.accept(order5);

    // then
    // it will accept the orders, because the shop is not full yet
    assertTrue(accept);
  }
}
