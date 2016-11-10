package elevators

case class Floor(floor: Int)(implicit config: ElevatorConfig) {

  def toPosition: Position =
    Position(floor * config.stepsBetweenFloors)

  def floorsBetween(other: Floor): Int =
    Math.abs(other.floor - floor)

  def floorsBetween(position: Position): Int =
    floorsBetween(Floor(position))

}

object Floor {
  def apply(position: Position)(implicit config: ElevatorConfig): Floor =
    apply(position.position / config.stepsBetweenFloors)
}
