package elevators

/** Configuration for a group of elevators.
  *
  * @constructor constructs a new configuration for a group of elevators.
  * @param stepsBetweenFloors the number of steps between two floors.
  * @param floors the number of floors.
  * @param elevators the number of elevators in the group.
  */
case class ElevatorConfig(stepsBetweenFloors: Int = 10,
                          floors: Int = 10,
                          elevators: Int = 2)

/** Contains the default configuration */
object ElevatorConfig {

  /** Default configuration */
  val defaultConfig = ElevatorConfig()
}
