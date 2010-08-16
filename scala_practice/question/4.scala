println("Reyonlds = ULρ/μ")
val reg = """(.+),(.+),(.+),(.+)""".r

def inputPrompt:Unit = { 
  print("input U[m/s], L[m], ρ[kg/m^3], μ[Pa・s] >>> ")
  try{ 
    var reg(u,l,rho,mu) = readLine
    println("Re = " + calcRe(List(u,l,rho,mu).map(_.toDouble):_*))
  } catch{
    case ex:MatchError => println("input valid number")
    case ex:NumberFormatException => println("input valid number")
    case ex:ArithmeticException => println("μ must not be zero")
    case ex => println(ex)
  } finally{
    repeatPrompt
  }
}

def calcRe(args:Double*) = {
  if(args(3) == 0.0) throw new ArithmeticException
  args.take(3).reduceLeft(_*_) / args(3)
}

def repeatPrompt:Unit = {
  print("calc more? [y/n] >>> ")
  readLine.toLowerCase match{
    case "y" => inputPrompt
    case "n" =>
    case _ => repeatPrompt
  }
}

inputPrompt
//while? なにそれ?
//Rubyでいうところの
//def add(a,b,c)
//  a+b+c
//end
//ary = [1,2,3]
//add(*ary) #=> 6
//が欲しい
