//DynamicVariable
import scala.util.DynamicVariable
val dv = new DynamicVariable("global")
println(dv.value)
dv.withValue("in block"){
  println(dv.value)
  dv.value = "change in block"
  println(dv.value)
}
println(dv.value) 

//Tuple
def tuple(a:Int, b:Double, c:String) = (a,b,c)
val t = tuple(1, 2.5, "hoge")
t._1 //1
t._2 //2.5
t._3 //"hoge"
//多値代入につかえる
val (a, b, c) = (2, 3.5, "piyo")
1->2
Tuple(1,2) //depricated
Pair(1,2)
Tuple2(1,2)
(1,2)
val tup3 = 1->2->3 //((Int, Int), Int) = ((1,2),3)
tup3._1 //(1,2)
tup3._2 // 3
tup3._1._2 //2

//Option
val m = Map('key1 -> "hoge", 'key2 ->"piyo")
m.get('key1) //Some("hoge")
m.get('key3) //None 
m.get('key1).getOrElse("nothing!") //hoge
m.get('key4).getOrElse("nothing!") //nothing!

//Enumeration
object Color extends Enumeration{
  val RED, GREEN, BLUE, WHITE, BLACK = Value
}
for(c <- Color) c match {
  case Color.RED => println("red: "+c.id)
  case Color.WHITE => println("white: "+c.id)
  case c => println("other: "+ c.id)
}
import Color._ //RED, GREENだけでアクセス

//match
"abc" match {
  case "abc" => "match"
  case _ => "other"
} //match

"abcd" match {
  case "abc" => "match"
  case _ => "other"
} //other

//match Class
val a = List(1, 2.5, "piyo")
a.foreach{
 n=> n match {
  case i:Int => println("match Int" + i)
  case d:Double => println("match Double" + d)
  case _ => println("match other" + n.toString)
}}

//match List
val a = List(1,5,8,10) //match 4elems 3rd 8 list
val b = List(1,3,5,7) //match 1st 1 list
val c = List() //other
val d = List(1) //match 1st 1 list
for(l <- List(a,b,c,d)){
  l match {
  case List(_, _, 8, _) => println("match 4elems 3rd 8 list")
  case List(1, _*) => println("match 1st 1 list")
  case _ => println("other")
  }
}

//head :: tail
def printElement(lst: List[Any]):Unit = {
  lst match {
    case head :: tail =>
      print(head+" ")
      printElement(tail)
    case Nil =>
      println("Nil")
  }
}
val l1 = List(1)
val l2 = List(1,5,10,15)
val l3 = List("hoge","fuga","piyo")
val l4 = List()
for(l <- List(l1,l2,l3,l4)){
  print("List: ")
  printElement(l)
}

//match tuple
val numTup = (123,456) //match 2nd is 456
val strTup = ("foo", "bar") //match foo is first
val anyTup = (123, "bar") //123 bar
val longTup = (123,456,789,"foo","bar") // other
for(tup <- List(numTup, strTup, anyTup, longTup)){
  tup match {
    case (t1, t2) if t1 == "foo" => println("match foo")
    case (_, 456) => println("match 2nd is 456")
    case (t1, t2) => println(t1+" "+t2)
    case _ => println("other")
  }
}

//case class
case class Yasai(name:String, price:Int)
val a = Yasai("negi", 99) //negi: 99
val b = Yasai("tomato", 50) //tomato: 50
val c = Yasai("potato", 120) // other potato: 120
val d = Yasai("negi",1000) //negi: 1000
for(vegi <- List(a,b,c,d)){ vegi match {
    case Yasai("negi", _) => println("negi: "+vegi.price)
    case Yasai("tomato",50) => println(vegi.name+": "+vegi.price)
    case _ => println("other "+vegi.name+": "+vegi.price)
  }
}

//match Regexp
import scala.util.matching._
val reg = "^ab.+".r
def regMatch(str:String, reg:Regex): String ={
  str match {
    case reg() => "match" //need ()
    case _ => "not match"
  }
}
regMatch("abc123",reg)
regMatch("bca123",reg)

//extractor
val reg = """(\d+)-(\d+)""".r
val reg(e1, e2) = "123-456"
//e1 = 123
//e2 = 456

val reg = """usage:cmd <filename> \[(.*)\]""".r
val reg(option) = """usage:cmd <filename> [-a]"""
//option = -a

val reg = """(\d\d\d\d)-(\d\d)-(\d\d)""".r
val ymd = List("2008-07-07","2009-12-31","1999-01-01","9999-99-99")
for (data <- ymd){
  data match {
    case reg(y,m,d) if y == "2008" => println("2008 match "+data)
    case reg(y,m,d) if m == "12" => println("12 match "+data)
    case reg(y,m,d) if d == "01" => println("01 match "+data)
    case _ => println("not match "+data)
  }
}
val reg = """(.*)-(.*)?""".r
val reg(a,b) = """hoge-piyo"""
val reg(a,b) = """hoge-""" //a = "hoge", b = ""
//val reg(a,b) = """hoge""" scala.MatchError
//val reg(a) = """hoge-""" scala.MatchError

//match Class which instance has as member
class Color
case object Red extends Color
case object Blue extends Color
case object White extends Color

case class Car(name:String, price:Int, color:Color)
val mark2 = new Car("Mark2", 3200000, Red)
val crown = new Car("Crown", 5200000, White)
val rx7 = new Car("Rx7", 2800000, Blue)

for(item <- Map(1 -> mark2, 2 -> crown, 3 -> rx7)) {
  item match{
    case (id, car @ Car(_, _, Red)) => println("match Red")
    case (id, car @ Car(_, 5200000, _)) => println("match 5200000")
    case (id, car) => println("match other")
  } //こっちの例だとcarに束縛しつつcarの内容についてmatchできる
}
for(item <- Map(1 -> mark2, 2 -> crown, 3 -> rx7)) {
  item match{
    case (id, Car(_, _, Red)) => println("match Red")
    case (id, Car(_, 5200000, _)) => println("match 5200000")
    case (id, car) => println("match other")
  }
}


//exception
try{
  val str = "abc"
  println(str.substring(3,4))
} catch {
    case e:StringIndexOutOfBoundsException => println("index err")
    case ex => println("other err")
  }
} finally {
  println("done")
}


//List
val x = List(1,2,3,4,5,6,7,8,9,10)
val y = 1 to 10 toList
1::2::3::4::5::6::7::8::9::10::Nil
//List(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)

List(1, 44.5, 8d) //List[Double] = List(1.0, 44.5, 8.0)
List[Number](1, 44.5, 8d)
  //List[java.lang.Number] = List(1, 44.5, 8.0)

x.filter(i => i%2 == 0)
x.filter(_%2 == 0)
x.map(_*3) //List[Int] = List(3, 6, 9, 12, 15, 18, 21, 24, 27, 30)
val a = List(1,2,3)
a.map(i => a.map( j => i*j))
//List(List(1, 2, 3), List(2, 4, 6), List(3, 6, 9))
a.flatMap(i => a.map(j => i*j)) // List(1, 2, 3, 2, 4, 6, 3, 6, 9)

x.reduceLeft(_ + _) // 55
x.foldLeft(1)(_ * _)//3628800

x.remove(_%2 == 0) //deprecated
x.filterNot(_%2 == 0)
x.takeWhile(_ < 7) //List(1, 2, 3, 4, 5, 6)
x.sort(_ > _) //deprecated
x.sortBy(10 - _) //List(10, 9, 8, 7, 6, 5, 4, 3, 2, 1)
x.sortWith(_ > _) //List(10, 9, 8, 7, 6, 5, 4, 3, 2, 1)
val (bList,cList) = List(0,1,2,3,4,5,6).splitAt(3)
//bList: List[Int] = List(0, 1, 2)
//cList: List[Int] = List(3, 4, 5, 6)



//Range
1 to 10 by 2 //Range(1, 3, 5, 7, 9)

val veryLongList = (1 to Integer.MAX_VALUE)
veryLongList.take(5) //Range(1, 2, 3, 4, 5)
veryLongList.takeRight(5) //Range(2147483643, 2147483644, 2147483645, 2147483646, 2147483647)
veryLongList.slice(1000, 1020)
//Range(1001, 1002, 1003, 1004, 1005, 1006, 1007, 1008, 1009, 1010, 1011, 1012, 1013, 1014, 1015, 1016, 1017, 1018, 1019, 1020)


//Map
var p = Map(1->"David", 9->"Elwood")
var p = Map[Int,String](1->"David", 9->"Elwood") //with generics
p + (8 -> "Archer") //add Pair
// => Map((1,David), (9,Elwood), (8,Archer))
p // Map((1,David), (9,Elwood))  Map is immutable
p = p + (8 -> "Archer")
p // Map((1,David), (9,Elwood), (8,Archer))  var p is variable
p(4) = "pokutuna" //deprecated
p.updated(4, "pokutuna")
p = p ++ Seq(2->"hoge", 3->"piyo")  //add tuples

p.get(1) //Some(David)
p.get(4) //None
p.getOrElse(1,"Nobody") //David
p.getOrElse(4,"Nobody") //Nobody
1 to 5 map(p.get)
  //Vector(Some(David), Some(hoge), Some(piyo), None, None)
1 to 5 flatMap(p.get)
  //Vector(David, hoge, piyo)
p.contains(1) // true
p - (1) //delete by key
p.keys
p.values
p.filter(_._2.length > 5) //Map((9,Elwood), (8,Archer))

//Stream
Stream.continually(2) take 10 print
Stream.continually(1 to 5).flatMap(v=>v) take 10 print
Stream.from(5,3) take 10 print
Stream.from(2) take 10 print
Stream.from(10) takeWhile(_ < 100) foreach(a => print(a + ","))

Stream.iterate(1){ a =>
  println(" in create stream : "+a)
  a*2} take 10 foreach println


