print("input text >>>")
val str = Console.readLine
val reg = """List\((.*)\)""".r

val reg(ascending) = str.toList.sortWith(_ < _).toString
val reg(descending) = str.toList.sortWith(_ > _).toString
Seq(ascending, descending).foreach(println(_))

