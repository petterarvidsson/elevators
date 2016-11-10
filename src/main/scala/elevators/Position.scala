package elevators

case class Position(position: Int) {

  def directionTo(other: Position): Option[Direction] =
    if(this == other) {
      None
    } else if(other.position > position) {
      Some(Up)
    } else {
      Some(Down)
    }

  def directionTo(floor: Floor): Option[Direction] =
    directionTo(floor.toPosition)

  def distanceTo(other: Position): Int =
    Math.abs(other.position - position)

  def step(direction: Direction): Position =
    direction match {
      case Up =>
        copy(position = position + 1)
      case Down =>
        copy(position = position - 1)
    }
}
