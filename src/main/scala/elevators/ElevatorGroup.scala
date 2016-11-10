package elevators

case class ElevatorGroup(elevators: Seq[Elevator])

object ElevatorGroup {
  def apply()(implicit config: ElevatorConfig): ElevatorGroup =
    apply(for(i <- 0 until config.elevators) yield {
      Elevator(Set(), Position(0))
    })
}
