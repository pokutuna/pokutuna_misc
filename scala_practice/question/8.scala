import scala.io._
import java.io._

val text = Source.fromFile("input.txt")
val out = new BufferedWriter(new FileWriter(new File("out.txt")))
out.write("<html>",0,"<html>".length)
for(n <- text.getLines) out.write(n,0,n.length)
out.write("</html>\n",0,"</html>\n".length)
out.close
text.close
