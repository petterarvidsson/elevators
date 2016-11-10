package elevators

import org.scalatest._

class PositionSpec extends WordSpec with Matchers {

  "A Position" when {
    "above another" should {
      "return directionTo Down" in {
        Position(1).directionTo(Position(0)) shouldEqual Some(Down)
      }
    }
    "below another" should {
      "return directionTo Up" in {
        Position(0).directionTo(Position(1)) shouldEqual Some(Up)
      }
    }
    "at the same position as another" should {
      "return directionTo None" in {
        Position(1).directionTo(Position(1)) shouldEqual None
      }
    }
    "above a floor" should {
      "return directionTo Down" in {
        Position(11).directionTo(Floor(1)) shouldEqual Some(Down)
      }
    }
    "below a floor" should {
      "return directionTo Up" in {
        Position(0).directionTo(Floor(1)) shouldEqual Some(Up)
      }
    }

  }

}
