package elevators

/** A floor that can be services by an elevator.
  *
  * @constructor create a new floor represented by an integer.
  * @param floor the int value of the floor.
  * @param config the config to get number of steps between two floors.
  */
case class Floor(floor: Int)(implicit config: ElevatorConfig) {

  /** Returns the position of this floor.*/
  def toPosition: Position =
    Position(floor * config.stepsBetweenFloors)

  /** Returns the number of floors between this floor and another.
    *
    * @param other the other floor.
    */
  def floorsBetween(other: Floor): Int =
    Math.abs(other.floor - floor)

  /** Returns the number of floors between this floor and a position.
    *
    * @param position the position.
    */
  def floorsBetween(position: Position): Int =
    floorsBetween(Floor(position))

}

/** Factory for [[elevators.Floor]] instances. */
object Floor {

  /** Creates a floor from a given position.
    *
    * @param position the position to create the floor from.
    * @param config the config to get number of steps between two floors.
    */
  def apply(position: Position)(implicit config: ElevatorConfig): Floor =
    apply(position.position / config.stepsBetweenFloors)
}
