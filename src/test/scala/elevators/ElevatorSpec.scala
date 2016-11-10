package elevators

import org.scalatest._

class ElevatorSpec extends WordSpec with Matchers {

  "An Elevator" when {
    "standing at floor 1" should {
      val elevator = Elevator(Set(), Floor(1).toPosition)
      "have score 11 to floor 1" in {
        elevator.floorRequestScore(Floor(1), Up) shouldEqual 11
      }
      "have score 10 to floor 0" in {
        elevator.floorRequestScore(Floor(0), Up) shouldEqual 10
      }
      "have score 10 to floor 2" in {
        elevator.floorRequestScore(Floor(2), Up) shouldEqual 10
      }
      "don't move when stepped" in {
        elevator.step shouldEqual elevator
      }
    }
    "traveling to floor 5 from floor 1" should {
      val elevator = Elevator(Set(Floor(5)), Floor(1).toPosition)
      "have score 1 to floor 0" in {
        elevator.floorRequestScore(Floor(0), Up) shouldEqual 1
      }
      "have score 10 to floor 2 when passenger travels Up" in {
        elevator.floorRequestScore(Floor(2), Up) shouldEqual 10
      }
      "have score 9 to floor 2 when passenger travels Down" in {
        elevator.floorRequestScore(Floor(2), Down) shouldEqual 9
      }
      "progress up when stepped" in {
        elevator.step.position shouldEqual Floor(1).toPosition.step(Up)
      }
      "remove goal when reached" in {
        elevator.step(40) shouldEqual Elevator(Set(), Floor(5).toPosition)
      }
      "still be traveling until goal is reached" in {
        elevator.step(39) shouldEqual Elevator(Set(Floor(5)), Position(49))
      }
    }
    "traveling to floor 0 from floor 1" should {
      val elevator = Elevator(Set(Floor(0)), Floor(1).toPosition)
      "have score 1 to floor 2" in {
        elevator.floorRequestScore(Floor(2), Up) shouldEqual 1
      }
      "have score 9 to floor 0 when passenger travels Up" in {
        elevator.floorRequestScore(Floor(0), Up) shouldEqual 9
      }
    }
  }
}
