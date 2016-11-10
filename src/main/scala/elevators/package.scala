package elevators

object `package` {

  /** Returns an updated ElevatorGroup with an elevator selected to handle the request.
    *
    * @param floor the requested floor to be serviced.
    * @param direction the direction of travel after entering the elevator.
    * @param group the elevator group to be updated.
    */
  def floorRequest(floor: Floor, direction: Direction, group: ElevatorGroup)(
      implicit config: ElevatorConfig): ElevatorGroup = {
    val bestIndex = group.elevators
      .map(_.floorRequestScore(floor, direction))
      .zipWithIndex
      .sortWith(_._1 > _._1)
      .map(_._2)
      .head

    val bestElevator = group.elevators(bestIndex)
    val updatedElevator = bestElevator.copy(goals = bestElevator.goals + floor)
    group.copy(elevators = group.elevators.updated(bestIndex, updatedElevator))
  }

  /** Returns an update ElevatorGroup which has been increment a given
    * number of steps.
    *
    * @param group the elevator group to be updated.
    * @param steps the number of steps to iterate the simulation.
    */
  def step(group: ElevatorGroup, steps: Int): ElevatorGroup = {
    val updatedElevators = group.elevators.map(_.step(steps))
    group.copy(elevators = updatedElevators)
  }

  /** Returns an update ElevatorGroup which has been incremented one step.
    *
    * @param group the elevator group to be updated.
    */
  def step(group: ElevatorGroup): ElevatorGroup =
    step(group, 1)

}
