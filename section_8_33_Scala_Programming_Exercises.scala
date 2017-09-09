/*
  1) Check for Single Even:
  Write a function that takes in an integer and returns a Boolean indicating
  whether or not it is even. See if yoy can Write this in one line!
*/

def isEven(number: Int): Boolean = {
  return number % 2 == 0
}

/*
  2) Check for Evens in a List:
  Write a function that returns True if there is an even number inside of a List,
  otherwise, return False.
*/

def isThereAnEven(numbers: List[Int]): Boolean = {
  for (number <- numbers) {
    if (number % 2 == 0) {
      return true
    }
  }
  return false
}

/*
  3) Lucky Number Seven:
  Take in a list of integers and calculate ther sum. However, sevens are Lucky
  and they should be counted twice, meaning ther value is 14 for the sum. Assume
  the list isn't empty
*/

def luckyNumberSeven(numbers: List[Int]): Int = {
  var counter: Int = 0
  for (number <- numbers) {
    counter += number
    if (number == 7) {
      counter += 7
    }
  }
  return counter
}

/*
  4) Can you Balance?
  Give a non-empty list of integers, return true if there is a place to
  split the list so that the sum of numbers on one side is equal to the sum of
  the numbers on the otherside. For example, given the list (1,5,3,3) would
  return true, you can split it in the middle. Another example (7,3,4) would
  return true 3 + 4 = 7. Remember you just need to return the boolean, not the
  split index point.
*/

def isBalanced(numbers: List[Int]): Boolean = {
  var leftFlag: Int = 0
  var rightFlag: Int = numbers.size - 1
  var leftCounter: Int = numbers(leftFlag)
  var rightCounter: Int = numbers(rightFlag)
  while (leftFlag < rightFlag) {
    if (leftCounter < rightCounter) {
      leftFlag += 1
      leftCounter += numbers(leftFlag)
    } else {
      rightFlag -= 1
      if (rightFlag != leftFlag){
          rightCounter += numbers(rightFlag)
      }
    }
  }
  return leftCounter == rightCounter
}

/*
  5) Palindrome Check
  Given a String, returns a boolean indicating whether or no it is a Palindrome.
  (Spelled the same forwards and backwards)
*/
def isPalindrome(stringParam: String): Boolean = {
  val string = stringParam.toLowerCase
  return string == string.reverse
}
