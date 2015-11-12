package recfun
import common._

object Main {
  def main(args: Array[String]) {
    println("Pascal's Triangle")
    for (row <- 0 to 10) {
      for (col <- 0 to row)
        print(pascal(col, row) + " ")
      println()
    }
  }

  /**
   * Exercise 1
   * URL: http://stackoverflow.com/questions/1763954/c-pascals-triangle
   */
  def pascal(c: Int, r: Int): Int = {
    if (c == 0 || c == r) 1 else
      pascal(c - 1, r - 1) + pascal(c, r - 1);
  }

  /**
   * Exercise 2
   */
  def balance(chars: List[Char]): Boolean = {
    def process(count: Int, rest: List[Char]): Boolean =
      if (count < 0) false else
        if (rest.isEmpty) (count == 0) else
          if (rest.head == '(') process(count+1, rest.tail) else
            if (rest.head == ')') process(count-1, rest.tail) else process(count, rest.tail)

    process(0, chars)
  }

  /**
   * Exercise 3
   * URL: http://www.algorithmist.com/index.php/Coin_Change
   */
  def countChange(money: Int, coins: List[Int]): Int = {
    def count(cislo: Int, love: Int, mince: List[Int]): Int =
      if (love == 0) 1 else
        if (love < 0) 0 else
          if (cislo <= 0 && love >= 1) 0 else
            count((cislo - 1), love, mince.tail) + count(cislo, (love - mince.head), mince)
      
    count(coins.length, money, coins)
  }
}
