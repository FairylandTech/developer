package pkg

import (
	"demo/internal/utils"
	"fmt"
)

func init() {
	utils.F("init")
	fmt.Println("Init call.")
}

func CallInit() {
	fmt.Println()
}
