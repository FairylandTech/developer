# coding: UTF-8
"""
@software: PyCharm
@author: Beau Dean
@contact: https://fairy.host
@organization: https://github.com/FairylandFuture
@datetime: 2026-05-01 17:03:29 UTC+08:00
"""
from __future__ import annotations

import typing as t

from langchain_core.tools import tool


@tool(description="获取体重")
def get_weight():
    return "体重是75公斤。"


@tool(description="获取身高")
def get_height():
    return "身高是175厘米。"
