package objsets

import org.scalatest.FunSuite

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class TweetSetSuite extends FunSuite {
  trait TestSets {
    val set1 = new Empty
    val set2 = set1.incl(new Tweet("a", "a body", 20))
    val set3 = set2.incl(new Tweet("b", "b body", 20))
    val c = new Tweet("c", "c body", 7)
    val d = new Tweet("d", "d body", 9)
    val set4c = set3.incl(c)
    val set4d = set3.incl(d)
    val set5 = set4c.incl(d)
    
    val e = new Tweet("e", "toto je vzorovy zahadny tweet e", 1)
    val f = new Tweet("f", "toto je ina zahada f", 21)
    val set6 = set5.incl(e)
    val set7 = set6.incl(f)
    val set8 = set7.incl(new Tweet("f", "toto je body f", 11))
  }

  def asSet(tweets: TweetSet): Set[Tweet] = {
    var res = Set[Tweet]()
    tweets.foreach(res += _)
    res
  }

  def size(set: TweetSet): Int = asSet(set).size

  test("filter: on empty set") {
    new TestSets {
      assert(size(set1.filter(tw => tw.user == "a")) === 0)
    }
  }

  test("filter: a on set5") {
    new TestSets {
      assert(size(set5.filter(tw => tw.user == "a")) === 1)
    }
  }

  test("filter: 20 on set5") {
    new TestSets {
      assert(size(set5.filter(tw => tw.retweets == 20)) === 2)
    }
  }

  test("filter: lower than 10 on set5") {
    new TestSets {
      assert(size(set5.filter(tw => tw.retweets < 10)) === 2)
    }
  }

  test("union: set4c and set4d") {
    new TestSets {
      assert(size(set4c.union(set4d)) === 4)
    }
  }

  test("union: with empty set (1)") {
    new TestSets {
      assert(size(set5.union(set1)) === 4)
    }
  }

  test("union: with empty set (2)") {
    new TestSets {
      assert(size(set1.union(set5)) === 4)
    }
  }

  test("union: set2 and set3") {
    new TestSets {
      assert(size(set2.union(set3)) === 2)
    }
  }

  test("mostRetweeted: on c & d = 9") {
    new TestSets {
      assert(set1.incl(d).incl(c).mostRetweeted.retweets === 9)
    }
  }

  test("mostRetweeted: set5 = 20") {
    new TestSets {
      assert(set5.mostRetweeted.retweets === 20)
    }
  }

  test("descending: empty set1") {
    new TestSets {
      val trends = set1.descendingByRetweet
      assert(trends.isEmpty, "result list must be empty")
    }
  }

  test("descending: set5") {
    new TestSets {
      println("descending: set5")
      val trends = set5.descendingByRetweet
      assert(!trends.isEmpty, "result list cannot be empty")
      assert(trends.head.user == "a" || trends.head.user == "b")
      assert(trends.head.retweets == 20)
      trends foreach println
    }
  }

  test("descending: set8") {
    new TestSets {
      println("descending: set8")
      val trends = set8.descendingByRetweet
      assert(!trends.isEmpty, "result list cannot be empty")
      assert(trends.head.user == "f")
      assert(trends.head.retweets == 21)
      trends foreach println
    }
  }

  test("GoogleVsApple: containsKeywords on E and F") {
    new TestSets {
      val keys = List("ta", "nieco", "zahada", "zahrada", "body")
      assert(!GoogleVsApple.containsKeywords(e, keys), "Tweet E cannot contain any keyword")
      assert(GoogleVsApple.containsKeywords(f, keys), "Tweet F must contain at least one keyword")
    }
  }

  test("GoogleVsApple: findTweets on set8 - found: 6") {
    new TestSets {
      val keys = List("ta", "nieco", "zahada", "zahrada", "body")
      val found = GoogleVsApple.findTweets(set8, keys)
      assert(!found.isEmpty, "found set cannot be empty")
      assert(size(found) === 6, "found must contain 6 tweets")
    }
  }

  test("GoogleVsApple: findTweets on set6 - found: 4") {
    new TestSets {
      val keys = List("body", "tralala")
      val found = GoogleVsApple.findTweets(set6, keys)
      assert(!found.isEmpty, "found set cannot be empty")
      assert(size(found) === 4, "found must contain 4 tweets")
    }
  }

  test("GoogleVsApple: findTweets on set8 - found: 0") {
    new TestSets {
      val keys = List("nic", "chyba")
      val found = GoogleVsApple.findTweets(set8, keys)
      assert(found.isEmpty, "found set must be empty")
    }
  }

  test("GoogleVsApple: findTweets on set8 - found: all") {
    new TestSets {
      val keys = List()
      val found = GoogleVsApple.findTweets(set8, keys)
      assert(!found.isEmpty, "found set cannot be empty")
      assert(size(found) === size(set8), "found must contain all tweets")
    }
  }

  test("GoogleVsApple: findTweets on empty set - found: 0") {
    new TestSets {
      val keys = List("body", "tralala")
      val found = GoogleVsApple.findTweets(set1, keys)
      assert(found.isEmpty, "found set must be empty")
    }
  }
}
