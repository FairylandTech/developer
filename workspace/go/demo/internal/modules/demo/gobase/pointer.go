package gobase

import "fmt"

func hander1(n int) {
	n++
}

func hander2(n *int) {
	*n++
}

func Pointer(n int) {
	hander1(n)
	fmt.Printf("Handler 1: n -> %v\n", n)
	hander2(&n)
	fmt.Printf("Handler 1: n -> %v\n", n)
}
