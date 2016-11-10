package elevators

case class Floor(floor: Int) {

  def toPosition: Position =
    Position(floor * ElevatorGroup.StepsBetweenFloors)

}
