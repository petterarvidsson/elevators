package elevators

/** Elevator state.
  *
  * This elevator state keeps track of the elevators position and a
  * set of goal floors that the elevator should service.
  *
  * @constructor creates a new elevator state.
  * @param goals set of floors that the elevator should service.
  * @param position the position of the elevator
  * @param config the config to get number of floors.
  */
case class Elevator(goals: Set[Floor], position: Position)(
    implicit config: ElevatorConfig) {

  // N to be used by nearest elevator heuristic
  private val N = config.floors - 1

  // The nearest goal floor to be serviced next
  private val nearestGoal =
    goals.toSeq.sortBy(_.toPosition.distanceTo(position)).headOption

  /** The some current direction of the elevator or none. */
  val direction = nearestGoal flatMap { floor =>
    position.directionTo(floor)
  }

  /* The following functions implements the heuristic described in this presentation:
   * http://www.columbia.edu/~cs2035/courses/ieor4405.S13/p14.pdf
   */

  /* Calculate score when elevator is traveling towards the caller and
   * the caller wants to continue in the same direction.
   */
  private def towardsCallSameDirection(floor: Floor): Int =
    (N + 2) - floor.floorsBetween(position)

  /* Calculate score when elevator is traveling towards the caller and
   * the caller wants to continue in the opposite direction.
   */
  private def towardsCallOppositeDirection(floor: Floor): Int =
    (N + 1) - floor.floorsBetween(position)

  /* Calculate score when elevator is traveling away from the caller.
   */
  private def awayFromCall(floor: Floor) =
    1

  /** Returns the score for the given floor request.
    *
    * For a given floor to be serviced and a direction that the user
    * want to continue in, calculate the score of this elevator if it
    * were to fulfill the request.
    *
    * @param floor the requested floor.
    * @param requestDirection the direction requested to continue in.
    */
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

  /** Returns an elevator state that is one step further in the
    * simulation.
    */
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

  /** Returns an elevator state that is the given number of steps
    * further in the simulation.
    *
    * @param steps the number of steps to take
    */
  def step(steps: Int): Elevator =
    if (steps > 0) {
      step.step(steps - 1)
    } else {
      this
    }

}
