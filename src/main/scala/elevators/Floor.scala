package elevators

case class Floor(floor: Int) {

  def toPosition: Position =
    Position(floor * ElevatorGroup.StepsBetweenFloors)

  def floorsBetween(other: Floor): Int =
    Math.abs(other.floor - floor)

  def floorsBetween(other: Position): Int =
    floorsBetween(Floor(other))

}

object Floor {
  def apply(position: Position): Floor =
    apply(position.position / ElevatorGroup.StepsBetweenFloors)
}
