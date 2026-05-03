# coding: UTF-8
"""
@software: PyCharm
@author: Beau Dean
@contact: https://fairy.host
@organization: https://github.com/FairylandFuture
@datetime: 2026-04-24 13:13:59 UTC+08:00
"""
from __future__ import annotations

import os
import typing as t
from dotenv import load_dotenv


class EnvUtils:
    __loaded: bool = False

    @classmethod
    def load(cls):
        if not cls.__loaded:
            load_dotenv()
            cls.__loaded = True

    @classmethod
    def getenv(cls, key: str) -> str:
        cls.load()
        value: t.Optional[str] = os.getenv(key)
        return value if value else ""
