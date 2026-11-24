package gobase

import (
	"fmt"
	"strings"
)

func Practice() {
	for {
		var (
			username   string
			password   string
			repassword string
		)

		fmt.Print("请输入账号：")
		fmt.Scan(&username)

		fmt.Print("请输入密码：")
		fmt.Scan(&password)

		fmt.Print("请确认密码：")
		fmt.Scan(&repassword)

		username = strings.TrimSpace(username)
		password = strings.TrimSpace(password)
		repassword = strings.TrimSpace(repassword)

		if username == "" || password == "" || repassword == "" {
			fmt.Println("输入不得为空，请重新输入！\n")
			continue
		}

		if password != repassword {
			fmt.Println("提示: 两次输入的密码不一致，请重新输入！\n")
			continue
		}

		fmt.Println("注册成功")
		break
	}
}
