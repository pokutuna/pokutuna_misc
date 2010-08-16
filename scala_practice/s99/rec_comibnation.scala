
//P26
var nestFMS = 0
var nestCMB = 0
def printWithNest(n:Int,str:String):Unit ={
  print(" "*n)
  println(str)
}
def flatMapSublists[A,B](ls:List[A])(f:(List[A]) => List[B]):List[B] = {
  printWithNest(nestFMS,"called flatMapSublists, ls: "+ls)
  nestFMS += 1
  printWithNest(nestFMS,"in match")
  ls match {
    case Nil =>
      printWithNest(nestFMS,"in case Nil")
      printWithNest(nestFMS,"return Nil from flatMapSublists")
      nestFMS -= 1
      Nil
    case sublist@(_ :: tail) =>
      printWithNest(nestFMS,"in case sublist")
      val fls = f(ls)
      val fms = flatMapSublists(tail)(f)
      printWithNest(nestFMS,"f(ls): "+fls)
      printWithNest(nestFMS,"flatMapSublists(tail)(f): "+fms)
      printWithNest(nestFMS,"return "+(fls ::: fms)+" from flatMapSublists")
      nestFMS -=1
      fls ::: fms
  }
}
def combinations[A](n:Int, ls:List[A]):List[List[A]] = {
  printWithNest(nestCMB,"called combination, n: "+n+" ls: "+ls)
  nestCMB += 1
  if(n==0){ 
    printWithNest(nestCMB,"return List(Nil) from combination")
    nestCMB -=1
    List(Nil)}
  else{ flatMapSublists(ls){ l =>
    val comb = combinations(n-1, l.tail)
    printWithNest(nestCMB,"comb: "+comb)
    val mapped = comb.map{ i =>
      printWithNest(nestCMB,"l.head: ("+l.head +")  i: ("+i+")")
      l.head :: i}
    printWithNest(nestCMB,"return mapped: "+mapped+" from combination")
    nestCMB -= 1                       
    mapped                       
    }
     }
}
println()
combinations(3, List('a, 'b, 'c, 'd, 'e, 'f))

