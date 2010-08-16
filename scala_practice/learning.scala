val intArray = Array(1,2,3,4)
intArray(1) //Int = 2
intArray.apply(1) //Int = 2
intArray(1) = 10 //Array(1,10,3,4)
intArray.update(1,20) //Array(1,20,3,4)

val i = 2
var res = if(i % 2 == 0) "even" //res: Any = even
res = if(i % 2 == 0) "even" else "odd" //res: java.lang.String = even

0 to 10 by 2 //0,2,4,6,8,10
0.to(10).by(2)

for (n <- 0 to 10
     if n % 2 == 0
     if n % 4 == 0) {
  println(n)
} //0,4,8

"hoge".length // 4
val p = "hoge".length _ //() => Int = <function 0>
p //() => Int = <function 0>
p() //Int = 4
p.apply //Int = 4

def boolean2String(f:(Int) => Boolean, n: Int) = {
  if (f(n)) "even" else "odd"
} //boolean2String: (f: (Int) => Boolean,n: Int)java.lang.String

def isEven(n: Int): Boolean = { n%2 == 0 }

boolean2String(isEven, 2) //even
boolean2String(isEven _ , 2) //even
val func = isEven _ ; boolean2String(func, 2) //even
boolean2String({ n: Int => n%2 == 0}, 2) //even
boolean2String({ n => n%2 == 0}, 2) //even
boolean2String(n => n%2 == 0, 2) //even
boolean2String((n: Int) => { n%2 == 0 }, 2) //even
boolean2String(n => { n%2 == 0 }, 2) //even
//boolean2String(n: Int => { n%2 == 0 }, 2)   error これダメなの…
boolean2String((n: Int) => n%2 == 0, 2) //even
//boolean2String(n: Int => n%2 == 0, 2) error

isEven _ //(Int) => Boolean = <function>
isEven(_) //(Int) => Boolean = <function>

def without_brackets = { println("without brackets!")}
without_brackets
// without_brackets() error
var func_literal = (a:Int, b:Int) =>{ a+b }
func_literal(1,2)
var func_literal: (Int, Int) => Int = { _+_ }
func_literal.tupled(new Tuple2(1,2))

val l = List("a","bb","ccc","d","ee","fff")
l.filter(_.length >= 2)
val nums = List(1,2,3,4,5,6,7)
nums.filter(isEven _)

//closure
def repeat(n:Int) = { s:String => s*n }
val repeat3 = repeat(3)
repeat3("hoge")

  // def printOverlength( s:String ) = {
  //   val len = s.length
  //   (xs:List[String]) => {
  //     for( e <- xs ;if e.length > len ){
  //       println( e + ":" + (e.length -len ) )
  //     }
  //   }
  //}
def printOverlength(s: String) = {
  (l: List[String]) => { 
    l.filter{ _.length > s.length }.
    foreach(str => println( str+":"+(str.length-s.length ))) 
  }
}
val pol = printOverlength("hoge")
pol(List("piyopiyo", "fuga", "baz", "mogera"))

//nest define
  // def contaisOfString(xs: List[String], s:String) = {
  //   def convert(e: String) = if( e.contains(s) ) "has" else "not"
  //   for (e <- xs) yield { convert(e)}
  // }
def contaisOfString(xs: List[String], s:String) =
  xs.map(x => if( x.contains(s) ) "has" else "not") 
contaisOfString(List("hoge","fuga","foo","piyo","bar"), "o")

//recursive call
def factorial(n: BigInt): BigInt = {
  if( n <= 1 ) 1 else n * factorial(n-1)
}
factorial(5)
(1 to 10).foreach( n=> print(factorial(n)+",") )

//tail call
def add(n: Long): Long = if( n <= 1) 1 else n+add(n-1)
add(10)
  //add(10000) => StackOverflow
def add(n: Long): Long = {
  def calc(sum:Long, m:Long): Long =
    if (m <= 1) sum + 1 else calc(sum + m, m - 1)
  calc(n, n-1)
}
add(10000)

//for pattern guard
def findDupChar(s1: String, s2: String) =
  (for(c1<-s1; c2<-s2 if c1==c2) yield { c1 }).mkString

for(n<-(1 to 3); m<-(5 to 7)) print(n+"&"+m+",")
for(n<-(1 to 10); m<-(5 to 15) if n==m) print(n+"&"+m+",")
for{n<-1 to 10
    m<-5 to 15 if n==m} print(n+"&"+m+",")

//partial apply
findDupChar("abcdefg", "bfDgjkza")
val partialApply = findDupChar("abcdefg", _: String)
def sum(a:Int, b:Int, c:Int) = a+b+c
val partialFirst = sum(_: Int, 2, 3)
partialFirst(1)
val partialEnds = sum(1, _: Int, 3)
partialEnds(2)
val partialCenter = sum(_: Int, 2, _: Int)
partialCenter(1,3)

//curry
def curriedSum(a: Int)(b: Int)(c: Int) = a+b+c
curriedSum(1)(2)(3)
val curry1 = curriedSum(1)(2)(_)  
curry1(3)
val curry2 = curriedSum(1) _
curry2(2)(3)
val curry3 = curriedSum _
Function.uncurried(curry3)(1,2,3)

//variable-length arg
def printStrings(args: String*) = args.foreach{ s => println(s)}
printStrings("hoge","fuga","piyo")
val l = List("foo","bar","baz")
printStrings(l:_*) //expand :*

//var&val
var a = 10
val b = a //b = 10
a = 20
b // b = 10

//symbol
Symbol("abc") == 'abc

//word
val xyz_++= = 1
val abc_ = 123
//val abc_=123  error
val ++- = 123
val `my name is` = "pokutuna"
if (List(1,1,1) == List(1,1,1)) true else false //true
if (List(1,1,1) == 1::1::1::Nil) true else false //true
if (Array(1,1) == Array(1,1)) true else false //false
if (Array(1,1) sameElements Array(1,1)) true else false //true
//deepEquals is deprecated

//loop
for(i <- 1 to 10) print(i+" ") //1 2 3 4 5 6 7 8 9 10
for(i <- 1 until 10) print(i+" ") //1 2 3 4 5 6 7 8 9 
for(i <- 1 to 10) print(i+' ') //33343536373839404142
print(' '+0) //32
for(str <- List("hoge","piyo","foo")) print(str+" ")
//hoge piyo foo 
for(x <- 0 to 2; y <- 0 to 2) print(x+":"+y+" ")
//0:0 0:1 0:2 1:0 1:1 1:2 2:0 2:1 2:2 
val l = List("abc","def","gab","hij")
for(str <- l if str.contains("ab")) print(str+" ") //abc gab
for(str <- l
    if str.contains("ab");
    if !str.startsWith("ga")
  ) print(str+" ") //abc
for{ str <- l
  if str.contains("ab")
  if !str.startsWith("ga")} print(str+" ") //abc (without ;)

case class Book(val name:String, val price:Int)
val books = List(Book("hoge",1000),
                 Book("piyo",2000),
                 Book("fuga",3000),
                 Book("foobar",2500))
for(b <- books if b.price >= 2000) yield b.name
for(b <- books.filter{_.price >= 2000}) yield b.name
//List[String] = List(piyo, fuga, foobar)

case class Friend(val name:String, val car:String, val house:String
)
val friends = List(
  Map('name -> "hoge"),
  Map('name -> "piyo", 'car -> "sedan"),
  Map('name -> "fuga", 'car -> "crown"),
  Map('name -> "mogera", 'car -> "BMW", 'house -> "big"))

val f = for{
  f<-friends
  name <- f.get('name)
  car <- f.get('car)
  house <- f.get('house)} yield Friend(name, car, house)
f.foreach(print(_)) //Friend(mogera,BMW,big)

var i = 3
while(i != 0) {
  println(i); i-=1
}
var i = 3
do {
  println(i)
  i = i-1
}while(i != 0)


//def op unary
class Hoge {
  def unary_! = println("prefix op")
  def ! = println("postfix op")
}
val a = new Hoge
!a
a!

//def right method :
class Hoge {
  def <-*:(i:Int) = i * 2
}
val a = new Hoge
10 <-*: a

//list
var list = List(1,2,3)
list = 4 :: list //List(4,1,2,3)
var a = List(1,2,3)
var b = List(4,5,6)
a ::: b //List(1, 2, 3, 4, 5, 6)

//arg check
def test(x:Int, y:Int) = {
  require(x > 0)
  assume(x - y == 0)
  format("x=%d, y=%d",x,y)
}
test(1,1) //String = x=1, y=1
//test(-1,1) java.lang.IllegalArgumentException: requirement failed
//test(1,0) java.lang.AssertionError: assumption failed
