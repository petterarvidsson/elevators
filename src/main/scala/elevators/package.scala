package elevators

object `package` {
  def floorRequest(floor: Floor, direction: Direction, group: ElevatorGroup): ElevatorGroup = {
    val bestIndex = group
      .elevators
      .map(_.floorRequestScore(floor, direction))
      .zipWithIndex
      .sortWith(_._1 > _._1)
      .map(_._2)
      .head

    val bestElevator = group.elevators(bestIndex)
    val updatedElevator = bestElevator.copy(goals = bestElevator.goals + floor)
    group.copy(elevators = group.elevators.updated(bestIndex, updatedElevator))
  }

  def step(group: ElevatorGroup, steps: Int): ElevatorGroup = {
    val updatedElevators = group.elevators.map(_.step(steps))
    group.copy(elevators = updatedElevators)
  }

  def step(group: ElevatorGroup): ElevatorGroup =
    step(group, 1)

}
