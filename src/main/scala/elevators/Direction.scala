package elevators

/** A direction of travel */
sealed trait Direction

/** Travel in the direction up */
case object Up extends Direction

/** Travel in the direction down */
case object Down extends Direction
