package elevators

case class Elevator(goals: Set[Floor], position: Position) {
  private val N = ElevatorGroup.Floors - 1

  private val nearestGoal = goals.toSeq.sortBy(_.toPosition.distanceTo(position)).headOption

  private val direction = nearestGoal flatMap { floor =>
    position.directionTo(floor)
  }

  private def towardsCallSameDirection(floor: Floor): Int =
    (N + 2) - floor.floorsBetween(position)

  private def towardsCallOppositeDirection(floor: Floor): Int =
    (N + 1) - floor.floorsBetween(position)

  private def awayFromCall(floor: Floor) =
    1

  def floorRequestScore(floor: Floor, requestDirection: Direction): Int = {
    val directionToCall = position.directionTo(floor)

    (directionToCall, direction) match {
      case (Some(toDirection), Some(thisDirection)) // Towards the call
          if toDirection == thisDirection =>
        if(thisDirection == requestDirection) {
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
