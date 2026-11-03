package main

import "fmt"

func factorial(n int) int {
	if n == 0 {
		return 0
	}

	if n == 1 {
		return 1
	}

	return n * factorial(n-1)
}

func sumFromZeroToN(n int) int {
	if n == 0 {
		return 0
	}

	return n + sumFromZeroToN(n-1)
}

func main() {
	result := factorial(5)
	fmt.Println("result:", result)

	result2 := sumFromZeroToN(2)
	fmt.Println("result2:", result2)
}
