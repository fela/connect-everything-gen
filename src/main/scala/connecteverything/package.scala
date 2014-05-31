/**
 * Created by fela on 5/31/14.
 */
import scala.util.Random.nextInt

package object connecteverything {
   implicit class IterableOps[T](val iter : Seq[T]) {

     def takeRandom : T = {
       val n = iter.size
       val i = nextInt(n)
       iter(i)
     }
   }
}
