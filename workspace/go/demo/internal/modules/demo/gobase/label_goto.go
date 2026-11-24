package gobase

import (
	"fmt"
)

func low() {
	fmt.Println("Low...")
}

func high() {
	fmt.Println("High")
}

func LabelGoto() {
outer:
	for i := 1; i < 10; i++ {
		for j := 1; j < 10; j++ {
			fmt.Printf("%v*%v=%v\t", j, i, i*j)
			if j == i {
				break outer
			}
		}
		fmt.Println()
	}

	i := 0

low:
	low()
high:
	high()

	i++
	if i < 10 {
		goto low
	} else if i >= 10 && i < 20 {
		goto high
	}
}
