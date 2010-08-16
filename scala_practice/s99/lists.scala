val list = List(1,1,2,3,5,8)

//P01
def last[A](ls:List[A]) = ls.last
last(list) == 8
def lastRec[A](ls:List[A]):A = ls match {
  case h :: Nil => h
  case _ :: tail => lastRec(tail)
  case _ => throw new NoSuchElementException
}
lastRec(list) == 8

//P02
def penultimate[A](ls:List[A]) = ls(ls.size-2)
penultimate(list)

list.init.last //builtin
list.takeRight(2).head
//List#init select all elements except the last

def penultimateRec[A](ls:List[A]):A = ls match {
  case elem :: _ :: Nil => elem
  case _ :: tail => penultimateRec(tail)
  case _ => throw new NoSuchElementException
}
penultimateRec(list)

//P03
def nth[A](n:Int, ls:List[A]):A = ls(n)
nth(2,list)

def nthRec[A](n:Int, ls:List[A]):A = (n,ls) match {
  case (0, h :: _) => h
  case (n, _ :: tail) => nthRec(n-1, tail)
  case (_, Nil) => throw new NoSuchElementException
}
nthRec(2,list)

//P04
def length[A](ls:List[A]):Int = ls.length
length(list)

def lengthRec[A](ls:List[A]):Int = ls match {
  case Nil => 0
  case _ :: tail => 1 + lengthRec(tail)
}
def lengthFunctional[A](ls: List[A]): Int = ls.foldLeft(0) { (c, _) => c + 1 }

//P05
def reverse[A](ls:List[A]):List[A] = ls.reverse
reverse(list)

def reverseRec[A](ls:List[A]):List[A] = ls match {
  case Nil => Nil
  case h :: tail => reverseRec(tail) ::: List(h)
}
reverseRec(list)

def reverseFunctional[A](ls:List[A]):List[A] = {
  ls.foldLeft(List[A]()){ (r,h) => h :: r}
}
reverseFunctional(list)

//P06
def isPalindrome[A](ls:List[A]):Boolean = ls == ls.reverse
isPalindrome(List(1,2,3,2,1))

//P07 **
def flatten(ls:List[Any]):List[Any] = ls flatMap { (l) =>
  println(l)
  l match { 
  case l:List[_] => flatten(l)
  case e => List(e)
  }
}
flatten(List(List(1, 1), 2, List(3, List(5, 8))))
// List(1, 1)
// 1
// 1
// 2
// List(3, List(5, 8))
// 3
// List(5, 8)
// 5
// 8
flatten(List(List("1", "1"), 2, List(3, List(5.0,8.0))))

//P08 **
val symbols = List('a, 'a, 'a, 'a, 'b, 'c, 'c, 'a, 'a, 'd, 'e, 'e, 'e, 'e)
def compress[A](ls:List[A]):List[A] = ls match {
  case Nil => Nil
  case h :: tail => h :: compress(tail.dropWhile(_ == h))
}
compress(symbols)

def compressTailRec[A](ls:List[A]):List[A] = {
  def compressR(result:List[A], curList:List[A]):List[A] = curList match {
    case h :: tail => compressR(h::result, curList.dropWhile(h == _))
    case Nil => result.reverse
  }
  compressR(Nil,ls)
}
compressTailRec(symbols)

//P09
def pack[A](ls:List[A]):List[List[A]] = {
  if(ls.isEmpty) List(List())
  else{
    val (packed, next) = ls.span(_ == ls.head)
    if(next == Nil) List(packed)
    else packed :: pack(next)
  }
}
pack(symbols)

//P10
def encode[A](ls:List[A]):List[(Int,A)] = {
  if(ls.isEmpty) List()
  else{
    val (packed, next) = ls.span(_ == ls.head)
    if (next == Nil) List((packed.size,packed.head))
    else (packed.size,packed.head) :: encode(next)
  }
}
encode(symbols)
pack(symbols).map(e => (e.size, e.head)) //simple+

//P11
def encodeModified[A](ls:List[A]):List[Any] = {
  pack(ls).map(e =>
    if(e.size == 1) e.head else (e.size, e.head))
}
encodeModified(symbols)
// def encodeModified[A](ls: List[A]): List[Any] =
//   encode(ls) map { t => if (t._1 == 1) t._2 else t }

// // Just for fun, here's a more typesafe version.
// def encodeModified2[A](ls: List[A]): List[Either[A, (Int, A)]] =
//   encode(ls) map { t => if (t._1 == 1) Left(t._2) else Right(t) }

//P12
val runlength = List((4, 'a), (1, 'b), (2, 'c), (2, 'a), (1, 'd), (4, 'e))
def decode[A](ls:List[(Int,A)]):List[A] = ls match {
  case Nil => Nil
  case h :: tail => List.fill(h._1)(h._2) ::: decode(tail)
  case _ => throw new NoSuchElementException
}
decode(runlength)

//P13
def encodeDirect[A](ls:List[A]):List[(Int,A)] = ls match {
  case Nil => Nil
  case h :: tail =>
    val (pack, next) = ls.span(_ == h)
    (pack.size, pack.head) :: encodeDirect(next)
}
encodeDirect(symbols)
encodeDirect(List[Double]())

//P14
def duplicate[A](ls:List[A]):List[A] =
  ls flatMap{ (e) => List(e,e) }
//   ls match {
//   case Nil => Nil
//   case h :: tail => h :: h :: duplicate(tail)
// }
duplicate(List('a, 'b, 'c, 'c, 'd))

//P15
def duplicateN[A](n:Int, ls:List[A]):List[A] = {
  ls flatMap { List.fill(n)(_)}
}
duplicateN(3, List('a, 'b, 'c, 'c, 'd))


//P16
def drop[A](n:Int, ls:List[A]):List[A] = {
  ls.zipWithIndex.filterNot(_._2 == n-1).map(_._1)
}
drop(3, List('a, 'b, 'c, 'd, 'e, 'f, 'g, 'h, 'i, 'j, 'k))

def dropRec[A](n:Int, ls:List[A]):List[A] = (n, ls) match {
  case (_, Nil) => Nil
  case (1, h :: tail) => dropRec(n-1, tail)
  case (_, h :: tail) => h :: dropRec(n-1, tail)
}
dropRec(3, List('a, 'b, 'c, 'd, 'e, 'f, 'g, 'h, 'i, 'j, 'k))
dropRec(0, List('a, 'b, 'c, 'd, 'e, 'f, 'g, 'h, 'i, 'j, 'k))
dropRec(1, List('a, 'b, 'c, 'd, 'e, 'f, 'g, 'h, 'i, 'j, 'k))
dropRec(100, List('a, 'b, 'c, 'd, 'e, 'f, 'g, 'h, 'i, 'j, 'k))
dropRec(11, List('a, 'b, 'c, 'd, 'e, 'f, 'g, 'h, 'i, 'j, 'k))

//P17
def split[A](n:Int, ls:List[A]):(List[A],List[A]) = {
  ls splitAt n
}
split(3, List('a, 'b, 'c, 'd, 'e, 'f, 'g, 'h, 'i, 'j, 'k))

//P18
def slice[A](begin:Int, end:Int, ls:List[A]):List[A] =
  ls slice(begin, end)
slice(3, 7, List('a, 'b, 'c, 'd, 'e, 'f, 'g, 'h, 'i, 'j, 'k))

def sliceRec[A](begin:Int, end:Int, ls:List[A]):List[A] =
  (begin, end, ls) match {
    case (_, _, Nil)                 => Nil
    case (_, e, _)         if e <= 0 => Nil
    case (s, e, h :: tail) if s <= 0 => h :: sliceRec(0, e-1, tail)
    case (s, e, h :: tail)           => sliceRec(s-1, e-1, tail)
  }
sliceRec(3, 7, List('a, 'b, 'c, 'd, 'e, 'f, 'g, 'h, 'i, 'j, 'k))

//P19
def rotate[A](n:Int, ls:List[A]):List[A] = {
  val nBound = if(ls.isEmpty) 0 else n % ls.length
  if(nBound < 0) rotate(nBound + ls.length, ls)
  else (ls drop nBound) ::: (ls take nBound) 
}
rotate(3, List('a, 'b, 'c, 'd, 'e, 'f, 'g, 'h, 'i, 'j, 'k))
rotate(-2, List('a, 'b, 'c, 'd, 'e, 'f, 'g, 'h, 'i, 'j, 'k))

//P20
def removeAt[A](idx:Int, ls:List[A]):(List[A], A) = {
  (ls.zipWithIndex filterNot(_._2 == idx) map(_._1), ls(idx))
}
removeAt(1, List('a, 'b, 'c, 'd))

def removeAtRec[A](idx:Int, ls:List[A]):(List[A],A) = ls splitAt(idx) match {
  case (Nil, _) => throw new NoSuchElementException
  case (pre, e :: tail) => (pre ::: tail, e)
  case (pre, Nil) => throw new NoSuchElementException
}
removeAtRec(1, List('a, 'b, 'c, 'd))
removeAtRec(0, List('a, 'b, 'c, 'd))
removeAtRec(4, List('a, 'b, 'c, 'd))
removeAtRec(3, List('a, 'b, 'c, 'd))

//P21
def insertAt[A](elem:A, idx:Int, ls:List[A]):List[A] = ls splitAt(idx) match {
  case (_, Nil) | (Nil, _) => throw new NoSuchElementException
  case (head, tail) => head ::: List(elem) ::: tail 
}
insertAt('new, 1, List('a, 'b, 'c, 'd))

//P22
def range(start:Int, end:Int):List[Int] = start to end toList
range(4,9)

def rangeTailRec(start:Int, end:Int):List[Int] ={
  def rangeR(e:Int, result:List[Int]):List[Int] = {
    if (e < start) result
    else rangeR(e-1, e :: result)
  }
  rangeR(end, Nil)
}

//P23
def randomSelect[A](takes:Int, ls:List[A]):List[A] = {
  if(takes < 1) Nil
  else util.Random.shuffle(ls).take(takes)
}
randomSelect(3, List('a, 'b, 'c, 'd, 'f, 'g, 'h))

//P24
def lotto(count:Int, max:Int):List[Int] = {
  //util.Random.shuffle(1 to max toList) take count
  randomSelect(count, 1 to max toList)
}
lotto(6,49)

//P25
def randomPermute[A](ls:List[A]):List[A] = util.Random.shuffle(ls)
randomPermute(List('a, 'b, 'c, 'd, 'e, 'f))

//P26
// def flatMapSublists[A,B](ls:List[A])(f:(List[A]) => List[B]):List[B] = {
//   println("called flatMapSublists, ls: "+ls)
//   println("  in match")
//   ls match {
//     case Nil =>
//       println("    "+Nil)
//       Nil
//     case sublist@(_ :: tail) =>
// //      println("  sublist: "+sublist)
//       val fls = f(ls)
//       val fms = flatMapSublists(tail)(f)
//       println("    f(ls): "+fls)
//       println("    flatMapSublists(tail)(f): "+fms)
//       fls ::: fms
//   }
// }
// def combinations[A](n:Int, ls:List[A]):List[List[A]] = {
//   println("called combination, n: "+n+" ls: "+ls)
//   if(n==0) List(Nil)
//   else flatMapSublists(ls){ l =>
//     val comb = combinations(n-1, l.tail)
//     println("  comb: "+comb)
//     val mapped = comb.map{ i =>
//       println("  l.head: ("+l.head +")  i: ("+i+")")
//       l.head :: i}
//     println("  mapped: "+mapped)
//     mapped                       
//                          }
// }
combinations(3, List('a, 'b, 'c, 'd, 'e, 'f))
//combinations(3, List('a, 'b, 'c))

def flatMapSublists[A,B](ls: List[A])(f: (List[A]) => List[B]): List[B] = ls match {
  case Nil => Nil
  case sublist@(_ :: tail) => f(sublist) ::: flatMapSublists(tail)(f)
}
def combinations[A](n: Int, ls: List[A]): List[List[A]] = { 
  if (n == 0) List(Nil)
  else flatMapSublists(ls) { sl =>
    combinations(n - 1, sl.tail) map {sl.head :: _}
  }
}
combinations(1, List('a, 'b, 'c, 'd, 'e, 'f))

//P27 pending
def group3[A](ls:List[A]):List[List[List[A]]] = {
  for {
    a <- combinations(2, ls)
    notA = ls filterNot(a.contains _)
    b <- combinations(3, notA)
  } yield List(a, b, notA filterNot(b.contains _))
}
group3(List("Aldo", "Beat", "Carla", "David", "Evi", "Flip", "Gary", "Hugo", "Ida"))

def group[A](ns: List[Int], ls: List[A]): List[List[List[A]]] = ns match {
  case Nil     => List(Nil)
  case n :: ns => combinations(n, ls) flatMap { c =>
    group(ns, ls filterNot(c contains)) map {c :: _}
                                             }
}
group(List(2, 2, 5), List("Aldo", "Beat", "Carla", "David", "Evi", "Flip", "Gary", "Hugo", "Ida"))

//P28 pending
