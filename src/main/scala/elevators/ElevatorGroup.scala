package elevators

/** A group of elevators.
  *
  * @constructor create a new group of elevators.
  * @param elevators the elevators in this group.
  */
case class ElevatorGroup(elevators: Seq[Elevator])

/** Factory for [[elevators.ElevatorGroup]] instances. */
object ElevatorGroup {

  /** Constructs a new elevator group with all elevators starting at
    * position 0 and with no goals.
    *
    * @param config the config containing the number of elevators in the group.
    */
  def apply()(implicit config: ElevatorConfig): ElevatorGroup =
    apply(for (i <- 0 until config.elevators) yield {
      Elevator(Set(), Position(0))
    })
}
