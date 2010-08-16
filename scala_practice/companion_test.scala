class Person( firstName:String ,lastName:String, n:Int ){

  println( "person created. %s %s".format( firstName, lastName ) )

  val fullName = "%s %s".format( firstName, lastName )
  private val age = n
  def greeting = { println( "Hello " + fullName ) }
}

object Person{
  def getAge( p:Person ) = p.age
}

val me = new Person("pokuda", "tunahiko", 22)
me.greeting
println(Person.getAge(me))
