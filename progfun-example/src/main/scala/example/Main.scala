package example

object Main extends App {
  //pouzivaj radsej testy
  println("sum = " + Lists.sum(List(0)))
  println("sum = " + Lists.sum(List(1, 3, 2, 6000)))
  println("sum = " + Lists.sum(List(1000, 3, 2, 500)))
  println("sum = " + Lists.sum(List(-100)))
  println("sum = " + Lists.sum(List()))
  println(" ")
  println("max = " + Lists.max(List(0)))
  println("max = " + Lists.max(List(1, 3, 2, 6000)))
  println("max = " + Lists.max(List(1000, 3, 2, 500)))
  println("max = " + Lists.max(List(-100)))
  println("max = " + Lists.max(List()))
}