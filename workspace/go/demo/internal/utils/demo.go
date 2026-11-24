package utils

import "fmt"

var i = 0
var F = func(s string) int {
	fmt.Printf("调用: %s\n", s)
	i++
	return i
}
