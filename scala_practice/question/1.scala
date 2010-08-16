import scala.actors.Actor //これないとActor.State.Terminatedが見えない
import scala.actors.Actor._

val counter = actor {
  var n:BigInt = 1
  loop{ 
    reactWithin(100){
      case 'stop => println("stop received"); exit
      case _ => println(n); n+= 1
    }
  }
}

val input = actor {
  readLine
  counter ! 'stop
}

def waitTerminated = {
  def check :Unit =
    if(counter.getState != Actor.State.Terminated) check
  check
}
waitTerminated
