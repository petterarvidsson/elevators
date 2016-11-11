# elevators
Simple elevator scheduling.

## Step 1: Get a grip
The first thing that comes to mind when thinking about elevator
scheduling, is that it seems to have all the characteristics of a
problem with a really big solution space. Therefore I am pretty sure
that it is a good idea to try to find an optimal solution. A short
google session leads to
[this presentation](http://www.columbia.edu/~cs2035/courses/ieor4405.S13/p14.pdf). This
seems to be on the correct level for this mini project (as simple as
possible). According to slide 5 it reduces to Time-Dependent Traveling
Sales Person (TDTSP) which is a generalization of the Traveling Sales
Person (TSP). It is thus at least as complex as TSP which already is
in the class of NP-complete problems. Basically that means that there
is no choice, but using an heuristic. I'll go with the naive heuristic
on slide 12, which seems good enough. The heuristic takes the maximum
number of floors, subtracts the distance between the target and the
current floor, and gives a small penalty if the elevator is traveling
in the opposite direction as the user wants to travel. It is a big
improvement from the First Come First Server (FCFS) heuristic since it
takes into account both the distance between the elevator and the
requested floor, the direction of travel of the elevator (to or away
from that floor) and the direction the user wants to travel in after
entering the elevator.

## Step 2: Design some data structures
Elevator seems to be central, so there will be an elevator
class. Floor and direction seems to be a core part of the selected
heuristic, so it makes sense to have classes for them as well. We want
to be able to do some stepping. This means that the smallest distance
is not a floor, but something smaller. We can add a position class to
keep track of that. In the end we want to act on a bunch of elevators,
so something like an elevator group would make sense. Then there will
be settings like the number of elevators and the number of floors, so
a config case class is probably also required. Please generate the
documentation to see the end result:

```
sbt doc
```

You can open it by opening `target/scala-2.11/api/index.html`.

## Step 3: Code it

(See commits)

# How can you use the API?

To use the API you need to create an instance of the `ElevatorConfig`
class and specify the number of elevators, the number of floors and
the steps between floors in your elevator group. If you provide it
implicitly it will be picked up by all parts that requires it. The you
need to create a `ElevatorGroup` from your config. You will then have
a group of elevators that you can simulate.

Interacting with the simulation is done using two functions: `step`
and `floorRequest`. `step` iterates the simulation the number of steps
given and returns an updated `ElevatorGroup` that represents the state
of the system after iteration. `floorRequest` requests the system to
service a user that wants to go in a certain direction from a given
floor. It also returns an `ElevatorGroup` that represents the state of
the system after the request, i.e. which elevator that will service
the floor.

# Playing around with the API

Fire up the sbt console:

```
sbt console
```

Import elevators namespace:

```
scala> import elevators._
import elevators._
```

Create your own implicit elevator configuration:

```
// Skyscraper config :-)
scala> implicit val config = ElevatorConfig(floors = 100, elevators = 5)
config: elevators.ElevatorConfig = ElevatorConfig(10,100,5)
```

Create an elevator group instance:

```
scala> var group = ElevatorGroup()
group: elevators.ElevatorGroup = ElevatorGroup(Vector(Elevator(Set(),Position(0)), Elevator(Set(),Position(0)), Elevator(Set(),Position(0)), Elevator(Set(),Position(0)), Elevator(Set(),Position(0))))
```

Start asking the elevators to do stuff using the `floorRequest` and `step` functions:

```
// Request to go from floor 5 upwards
scala> group = floorRequest(Floor(5), Up, group)
group: elevators.ElevatorGroup = ElevatorGroup(Vector(Elevator(Set(Floor(5)),Position(0)), Elevator(Set(),Position(0)), Elevator(Set(),Position(0)), Elevator(Set(),Position(0)), Elevator(Set(),Position(0))))

// Seems like first elevator took it, request to go from floor 3 upwards
scala> group = floorRequest(Floor(3), Up, group)
group: elevators.ElevatorGroup = ElevatorGroup(Vector(Elevator(Set(Floor(5), Floor(3)),Position(0)), Elevator(Set(),Position(0)), Elevator(Set(),Position(0)), Elevator(Set(),Position(0)), Elevator(Set(),Position(0))))

// Also taken by first elevator, lets step forward 40 iterations
scala> group = step(group, 40)
group: elevators.ElevatorGroup = ElevatorGroup(Vector(Elevator(Set(Floor(5)),Position(40)), Elevator(Set(),Position(0)), Elevator(Set(),Position(0)), Elevator(Set(),Position(0)), Elevator(Set(),Position(0))))

// Floor 3 was reached and removed from goals, let's request a floor above 5 and going upward
scala> group = floorRequest(Floor(7), Up, group)
group: elevators.ElevatorGroup = ElevatorGroup(Vector(Elevator(Set(Floor(5), Floor(7)),Position(40)), Elevator(Set(),Position(0)), Elevator(Set(),Position(0)), Elevator(Set(),Position(0)), Elevator(Set(),Position(0))))

// Also goes to the same, let's request something from 3 and going downwards
scala> group = floorRequest(Floor(3), Down, group)
group: elevators.ElevatorGroup = ElevatorGroup(Vector(Elevator(Set(Floor(5), Floor(7)),Position(40)), Elevator(Set(Floor(3)),Position(0)), Elevator(Set(),Position(0)), Elevator(Set(),Position(0)), Elevator(Set(),Position(0))))

// That was taken by the next elevator
// Let's now check out what the first elevator is doing

// Heading for floor 5 and 7
scala> group.elevators(0).goals
res0: Set[elevators.Floor] = Set(Floor(5), Floor(7))

// At position 40
scala> group.elevators(0).position
res1: elevators.Position = Position(40)

// Traveling upwards
scala> group.elevators(0).direction
res2: Option[elevators.Direction] = Some(Up)

//I guess you can now play around as much as you like :-)

```

# Drawbacks of this implementation
In no particular order:

- Does not cover corner cases like requesting a floor outside of the
  elevators capability.
- Does not support elevator capacities.
- May contain bugs, please check out the tests to see where coverage
  may be lacking.
- Heuristic may be a bit too naive.
- Time that elevator stays at floor (opening and closing doors) not
  taken into account.
- And all that other stuff I forgot to write.
