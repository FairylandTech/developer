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
from pathlib import Path

from dotenv import load_dotenv

load_dotenv()

if __name__ == '__main__':
    from const import ROOT_DIR

    from agents.weather import get_weather_live_invoke, get_weather_live_stream
    from agents.search import search, tool_search
    from agents.session import session_agent
    from agents.memory.session import session_memory_agent
    from agents.case.eat import cheif

    print(ROOT_DIR)

    food_path = Path(ROOT_DIR) / "resources" / "img" / "food.jpg"
    cheif("我想吃点清淡的东西，能不能推荐一下？", food_path)
