package gobase

import "fmt"

func Switch(n int) {
	switch {
	case n < 10:
		fmt.Println("Low")
	case n < 50:
		fmt.Println("Mid")
	default:
		fmt.Println("High")
	}
}

func SwitchValudMatch(n int) {
	switch n {
	case 1:
		fmt.Println("111")
	case 2:
		fmt.Println("222")
	default:
		fmt.Println("000")
	}
}
