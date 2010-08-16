import javax.swing._
import java.awt._

object SwingTest{
  def main(args: Array[String]) = {
    var frame = new JFrame
    frame setDefaultCloseOperation (JFrame EXIT_ON_CLOSE)
    frame setSize new Dimension (640, 480)
    frame setVisible true
  }
}
