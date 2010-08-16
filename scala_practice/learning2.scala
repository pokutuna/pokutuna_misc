class Person(firstName: String, lastName:String, n:Int){
  println("in construction")

  val fullName = "%s %s".format(firstName, lastName)
  private val age = n
  def greeting: Unit = { println("Hello " + fullName) }
}

val poku = new Person("Pokuda", "Tunahiko", 22)
poku.toString
poku.fullName
poku.greeting

//singleton => object
object OnlyOnePerson {
  val fullName = "Stevie Ray Vaughan"
  var albums = List("Texas Flood", "In Step", "Soul to Soul")
  def showBio = albums.foreach{ println _ }
}
OnlyOnePerson.fullName
OnlyOnePerson.showBio

//companion object
// object Person{
//   def getAge(p: Person) = p.age
// }
// Person.getAge(poku)

class Foo(i: Int){
  

}
