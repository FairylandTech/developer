# coding: UTF-8
"""
@software: PyCharm
@author: Lionel Johnson
@contact: https://fairy.host
@organization: https://github.com/FairylandFuture
@datetime: 2026-04-17 04:05:47 UTC+08:00
"""
from __future__ import annotations

import typing as t
import time
from pathlib import Path

from dotenv import load_dotenv

load_dotenv()

if __name__ == '__main__':
    start_timestamp = time.time()
    print(f"开始运行： {start_timestamp}")
    from const import ROOT_DIR
    print(f"导入 ROOT_DIR， 总耗时：{time.time() - start_timestamp}")

    from agents.weather import get_weather_live_invoke, get_weather_live_stream
    print(f"导入 weather， 总耗时：{time.time() - start_timestamp}")

    from agents.search import search, tool_search
    print(f"导入 search， 总耗时：{time.time() - start_timestamp}")

    from agents.session import session_agent
    print(f"导入 session， 总耗时：{time.time() - start_timestamp}")

    from agents.memory.session import session_memory_agent
    print(f"导入 memory.session， 总耗时：{time.time() - start_timestamp}")

    from agents.case.eat import cheif
    print(f"导入 cheif， 总耗时：{time.time() - start_timestamp}")

    from llm.factoryimport LLMFactory

    llm = LLMFactory.create("zai")

    print(f"开始测试：")

    res = llm.invoke("给我介绍下《申肖克的救赎》这部电影")
    print(res)

    # search("开脚本打LOL被封机器码，如何解决？")

    # food_path = Path(ROOT_DIR) / "resources" / "img" / "food.jpg"
    # cheif("我想吃点清淡的东西，能不能推荐一下？", food_path)

    print(f"测试完成：总耗时：{time.time() - start_timestamp}")
