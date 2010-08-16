//P31
object P31 { 
val primes = Stream.cons(2, Stream.from(3, 2) filter { isPrime(_) })
def isPrime(start:Int): Boolean = { 
      (start > 1) && (primes takeWhile { _ <= Math.sqrt(start) } forall { start % _ != 0 })
  }
}
P31.isPrime(7)
println(P31.primes)



