package elevators

case class Elevator(goals: Set[Floor], position: Position, direction: Option[Direction])

object Elevator {
  def apply(position: Position, direction: Option[Direction]): Elevator =
    apply(Set(), position, direction)
}
