package elevators

import org.scalatest._

class ElevatorGroupSpec extends WordSpec with Matchers {

  implicit val config = ElevatorConfig.defaultConfig

  "An ElevatorGroup" when {

    "having 2 elevators traveling to 9 from floor 2 and 5" should {
      val state =
        ElevatorGroup(
          Seq(
            Elevator(Set(Floor(9)), Floor(2).toPosition),
            Elevator(Set(Floor(9)), Floor(5).toPosition)
          )
        )

      "choose elevator 1 for floor 4" in {
        val result = ElevatorGroup(
          Seq(
            Elevator(Set(Floor(4), Floor(9)), Floor(2).toPosition),
            Elevator(Set(Floor(9)), Floor(5).toPosition)
          )
        )

        floorRequest(Floor(4), Up, state) shouldEqual result
      }

      "choose elevator 2 for floor 4 after it reaches the floor 9" in {
        val result = ElevatorGroup(
          Seq(
            Elevator(Set(Floor(9)), Floor(6).toPosition),
            Elevator(Set(Floor(4)), Floor(9).toPosition)
          )
        )

        floorRequest(Floor(4), Up, step(state, 40)) shouldEqual result
      }


      "choose elevator 2 for floor 6" in {
        val result = ElevatorGroup(
          Seq(
            Elevator(Set(Floor(9)), Floor(2).toPosition),
            Elevator(Set(Floor(6), Floor(9)), Floor(5).toPosition)
          )
        )

        floorRequest(Floor(6), Up, state) shouldEqual result
      }

      "choose an elevator 2 for floor 6 traveling down" in {
        val result = ElevatorGroup(
          Seq(
            Elevator(Set(Floor(9)), Floor(2).toPosition),
            Elevator(Set(Floor(6), Floor(9)), Floor(5).toPosition)
          )
        )

        floorRequest(Floor(6), Down, state) shouldEqual result
      }


    }

    "having first elevator traveling to floor 9 from floor 2 and second traveling to floor 0 from 5" should {
      val state =
        ElevatorGroup(
          Seq(
            Elevator(Set(Floor(9)), Floor(2).toPosition),
            Elevator(Set(Floor(0)), Floor(5).toPosition)
          )
        )

      "choose elevator 1 for floor 4 Up" in {
        val result = ElevatorGroup(
          Seq(
            Elevator(Set(Floor(4), Floor(9)), Floor(2).toPosition),
            Elevator(Set(Floor(0)), Floor(5).toPosition)
          )
        )

        floorRequest(Floor(4), Up, state) shouldEqual result
      }

      "choose elevator 2 for floor 4 Down" in {
        val result = ElevatorGroup(
          Seq(
            Elevator(Set(Floor(9)), Floor(2).toPosition),
            Elevator(Set(Floor(4), Floor(0)), Floor(5).toPosition)
          )
        )

        floorRequest(Floor(4), Down, state) shouldEqual result
      }
    }

    "having both elevators standing still at floor 2 and 5" should {
      val state =
        ElevatorGroup(
          Seq(
            Elevator(Set(), Floor(2).toPosition),
            Elevator(Set(), Floor(5).toPosition)
          )
        )

      "choose elevator 2 for floor 4 Down" in {
        val result = ElevatorGroup(
          Seq(
            Elevator(Set(), Floor(2).toPosition),
            Elevator(Set(Floor(4)), Floor(5).toPosition)
          )
        )

        floorRequest(Floor(4), Down, state) shouldEqual result
      }

      "choose elevator 2 for floor 4 Up" in {
        val result = ElevatorGroup(
          Seq(
            Elevator(Set(), Floor(2).toPosition),
            Elevator(Set(Floor(4)), Floor(5).toPosition)
          )
        )

        floorRequest(Floor(4), Up, state) shouldEqual result
      }


      "choose elevator 1 for floor 3 Up" in {
        val result = ElevatorGroup(
          Seq(
            Elevator(Set(Floor(3)), Floor(2).toPosition),
            Elevator(Set(), Floor(5).toPosition)
          )
        )

        floorRequest(Floor(3), Up, state) shouldEqual result
      }

    }
  }

}
