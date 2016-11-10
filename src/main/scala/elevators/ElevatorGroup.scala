package elevators

case class ElevatorGroup(elevators: Seq[Elevator])

case object ElevatorGroup {
  val StepsBetweenFloors = 10
}
