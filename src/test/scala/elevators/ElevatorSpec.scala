package elevators

import org.scalatest._

class ElevatorSpec extends WordSpec with Matchers {

  "An Elevator" when {
    "standing at floor 1" should {
      "have score 11 to floor 1" in {
        val elevator = Elevator(Floor(1).toPosition, None)
        elevator.floorRequestScore(Floor(1), Up) shouldEqual 11
      }
      "have score 10 to floor 0" in {
        val elevator = Elevator(Floor(1).toPosition, None)
        elevator.floorRequestScore(Floor(0), Up) shouldEqual 10
      }
      "have score 10 to floor 2" in {
        val elevator = Elevator(Floor(1).toPosition, None)
        elevator.floorRequestScore(Floor(2), Up) shouldEqual 10
      }
    }
    "traveling up from floor 1" should {
      "have score 1 to floor 0" in {
        val elevator = Elevator(Floor(1).toPosition, Some(Up))
        elevator.floorRequestScore(Floor(0), Up) shouldEqual 1
      }
      "have score 10 to floor 2 when passenger travels Up" in {
        val elevator = Elevator(Floor(1).toPosition, Some(Up))
        elevator.floorRequestScore(Floor(2), Up) shouldEqual 10
      }
      "have score 9 to floor 2 when passenger travels Down" in {
        val elevator = Elevator(Floor(1).toPosition, Some(Up))
        elevator.floorRequestScore(Floor(2), Down) shouldEqual 9
      }
    }
    "traveling down from floor 1" should {
      "have score 1 to floor 2" in {
        val elevator = Elevator(Floor(1).toPosition, Some(Down))
        elevator.floorRequestScore(Floor(2), Up) shouldEqual 1
      }
      "have score 9 to floor 0 when passenger travels Up" in {
        val elevator = Elevator(Floor(1).toPosition, Some(Down))
        elevator.floorRequestScore(Floor(0), Up) shouldEqual 9
      }
    }
  }
}
