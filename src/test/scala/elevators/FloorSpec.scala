package elevators

import org.scalatest._

class FloorSpec extends WordSpec with Matchers {

  implicit val config = ElevatorConfig.defaultConfig

  "A Floor" when {
    "floor 1" should {
      "return position 10" in {
        Floor(1).toPosition shouldEqual Position(10)
      }
      "be 2 floors from 3" in {
        Floor(1).floorsBetween(Floor(3)) shouldEqual 2
      }
      "be 1 floors from 0" in {
        Floor(1).floorsBetween(Floor(0)) shouldEqual 1
      }
    }
    "floor 0" should {
      "return position 0" in {
        Floor(0).toPosition shouldEqual Position(0)
      }
    }
  }

}
