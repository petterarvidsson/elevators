package elevators

case class Elevator(goals: Set[Floor], position: Position)(
    implicit config: ElevatorConfig) {
  private val N = config.floors - 1

  private val nearestGoal =
    goals.toSeq.sortBy(_.toPosition.distanceTo(position)).headOption

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
        if (thisDirection == requestDirection) {
          towardsCallSameDirection(floor)
        } else {
          towardsCallOppositeDirection(floor)
        }
      case (Some(toDirection), Some(thisDirection))
          if toDirection != thisDirection =>
        awayFromCall(floor)
      case (_, None) => // Standing still
        towardsCallSameDirection(floor)
    }
  }

  def step: Elevator =
    (nearestGoal, direction) match {
      case (Some(floor), Some(direction)) =>
        val nextPosition = position.step(direction)
        if (nextPosition == floor.toPosition) { // Arrived at goal floor
          copy(goals = goals - floor, position = nextPosition)
        } else { // Still traveling
          copy(position = nextPosition)
        }
      case _ => // We have no goals so we are standing still
        this
    }

  def step(steps: Int): Elevator =
    if (steps > 0) {
      step.step(steps - 1)
    } else {
      this
    }

}
