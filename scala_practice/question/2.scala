//O(n^2)
def fib(n:BigInt):BigInt = {
  if(n < 2) n else { fib(n-2) + fib(n-1)}
}

//O(n)
def fib2(n:BigInt):BigInt = {
  def f(a:BigInt, b:BigInt, c:BigInt):BigInt = {
    if(c <= 2) a else f(a+b, a, c-1)
  }
  f(1,1,n)
}

//Memoize
var nums = Map[BigInt,BigInt](BigInt(1)->BigInt(1), BigInt(2)->BigInt(1))
def fib_memoize(n:BigInt):BigInt = {
  if(!nums.contains(n)){
    nums += (n -> (fib_memoize(n-1) + fib_memoize(n-2)))
  }
  nums(n)
}

for(i <- 1 to 150){
  println(fib2(i))
  println(fib_memoize(i))
}
