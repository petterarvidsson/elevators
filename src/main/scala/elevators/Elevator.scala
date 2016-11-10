package elevators

case class Elevator(goals: Set[Floor], position: Position, direction: Option[Direction]) {
  private val N = ElevatorGroup.Floors - 1

  private def towardsCallSameDirection(floor: Floor): Int =
    (N + 2) - floor.floorsBetween(position)

  private def towardsCallOppositeDirection(floor: Floor): Int =
    (N + 1) - floor.floorsBetween(position)

  private def awayFromCall(floor: Floor) =
    1

  def floorRequestScore(floor: Floor, direction: Direction): Int = {
    val directionToCall = position.directionTo(floor)

    (directionToCall, this.direction) match {
      case (Some(toDirection), Some(thisDirection)) // Towards the call
          if toDirection == thisDirection =>
        if(thisDirection == direction) {
          towardsCallSameDirection(floor)
        } else {
          towardsCallOppositeDirection(floor)
        }
      case (Some(toDirection), Some(thisDirection)) if toDirection != thisDirection =>
        awayFromCall(floor)
      case (_, None) => // Standing still
        towardsCallSameDirection(floor)
    }
  }
}

object Elevator {
  def apply(position: Position, direction: Option[Direction]): Elevator =
    apply(Set(), position, direction)
}
