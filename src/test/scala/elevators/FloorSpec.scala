package elevators

import org.scalatest._

class FloorSpec extends WordSpec with Matchers {

  "A Floor" when {
    "floor 1" should {
      "return position 10" in {
        Floor(1).toPosition shouldEqual Position(10)
      }
    }
    "floor 0" should {
      "return position 0" in {
        Floor(0).toPosition shouldEqual Position(0)
      }
    }
  }

}
