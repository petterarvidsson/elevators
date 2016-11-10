package elevators

/** A position that an elevator can have.
  *
  * @constructor create a new position represented by an integer.
  * @param position the int value of this position.
  */
case class Position(position: Int) {

  /** Returns the direction to another position.
    *
    * Returns some direction to another position or none if this.
    * position equals the other position.
    * @param other the other position.
    */
  def directionTo(other: Position): Option[Direction] =
    if (this == other) {
      None
    } else if (other.position > position) {
      Some(Up)
    } else {
      Some(Down)
    }

  /** Returns direction to a floor.
    *
    * Returns some direction to a floor or none if this.
    * position equals the position of the floor.
    * @param floor The floor to be compared.
    */
  def directionTo(floor: Floor): Option[Direction] =
    directionTo(floor.toPosition)

  /** Returns the distance to another position.
    *
    * @param other The other position.
    */
  def distanceTo(other: Position): Int =
    Math.abs(other.position - position)

  /** Returns a new position one step further in the given direction.
    *
    * @param the direction in which the step should be taken.
    */
  def step(direction: Direction): Position =
    direction match {
      case Up =>
        copy(position = position + 1)
      case Down =>
        copy(position = position - 1)
    }
}
