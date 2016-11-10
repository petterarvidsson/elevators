package elevators

import org.scalatest._

class ElevatorGroupSpec extends WordSpec with Matchers {

  "An ElevatorGroup" when {

    "having 2 elevators traveling Up from floor 2 and 5" should {
      val state =
        ElevatorGroup(
          Seq(
            Elevator(Floor(2).toPosition, Some(Up)),
            Elevator(Floor(5).toPosition, Some(Up))
          )
        )

      "choose elevator 1 for floor 4" in {
        val result = ElevatorGroup(
          Seq(
            Elevator(Set(Floor(4)), Floor(2).toPosition, Some(Up)),
            Elevator(Floor(5).toPosition, Some(Up))
          )
        )

        floorRequest(Floor(4), Up, state) shouldEqual result
      }

      "choose elevator 2 for floor 6" in {
        val result = ElevatorGroup(
          Seq(
            Elevator(Floor(2).toPosition, Some(Up)),
            Elevator(Set(Floor(6)), Floor(5).toPosition, Some(Up))
          )
        )

        floorRequest(Floor(6), Up, state) shouldEqual result
      }

      "choose an elevator floor 6 traveling down" in {
        val result = ElevatorGroup(
          Seq(
            Elevator(Floor(2).toPosition, Some(Up)),
            Elevator(Set(Floor(6)), Floor(5).toPosition, Some(Up))
          )
        )

        floorRequest(Floor(6), Down, state) shouldEqual result
      }
    }

    "having first elevator traveling Up from floor 2 and second traveling down from 5" should {
      val state =
        ElevatorGroup(
          Seq(
            Elevator(Floor(2).toPosition, Some(Up)),
            Elevator(Floor(5).toPosition, Some(Down))
          )
        )

      "choose elevator 1 for floor 4 Up" in {
        val result = ElevatorGroup(
          Seq(
            Elevator(Set(Floor(4)), Floor(2).toPosition, Some(Up)),
            Elevator(Floor(5).toPosition, Some(Down))
          )
        )

        floorRequest(Floor(4), Up, state) shouldEqual result
      }

      "choose elevator 2 for floor 4 Down" in {
        val result = ElevatorGroup(
          Seq(
            Elevator(Floor(2).toPosition, Some(Up)),
            Elevator(Set(Floor(4)), Floor(5).toPosition, Some(Down))
          )
        )

        floorRequest(Floor(4), Down, state) shouldEqual result
      }
    }

    "having both elevators standing still at floor 2 and 5" should {
      val state =
        ElevatorGroup(
          Seq(
            Elevator(Floor(2).toPosition, None),
            Elevator(Floor(5).toPosition, None)
          )
        )
      "choose elevator 2 for floor 4 Down" in {
        val result = ElevatorGroup(
          Seq(
            Elevator(Floor(2).toPosition, None),
            Elevator(Set(Floor(4)), Floor(5).toPosition, None)
          )
        )

        floorRequest(Floor(4), Down, state) shouldEqual result
      }

      "choose elevator 2 for floor 4 Up" in {
        val result = ElevatorGroup(
          Seq(
            Elevator(Floor(2).toPosition, None),
            Elevator(Set(Floor(4)), Floor(5).toPosition, None)
          )
        )

        floorRequest(Floor(4), Up, state) shouldEqual result
      }


      "choose elevator 1 for floor 3 Up" in {
        val result = ElevatorGroup(
          Seq(
            Elevator(Set(Floor(3)), Floor(2).toPosition, None),
            Elevator(Floor(5).toPosition, None)
          )
        )

        floorRequest(Floor(3), Up, state) shouldEqual result
      }

    }
  }

}
