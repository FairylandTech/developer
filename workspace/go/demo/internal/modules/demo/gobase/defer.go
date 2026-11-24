package gobase

import "fmt"

func tool() func(int) int {
	i := 0

	return func(n int) int {
		fmt.Printf("接收到n=%v。\n", n)
		i++
		fmt.Printf("函数被第%v次调用。\n", i)
		fmt.Println()
		return i
	}
}

func Defer() int {
	t := tool()

	defer t(10)
	defer t(20)
	defer t(t(30))
	return t(40)
}

func DeferReverse()
