package elevators

case class ElevatorConfig(stepsBetweenFloors: Int = 10, floors: Int = 10, elevators: Int = 2)

object ElevatorConfig {
  val defaultConfig = ElevatorConfig()
}
