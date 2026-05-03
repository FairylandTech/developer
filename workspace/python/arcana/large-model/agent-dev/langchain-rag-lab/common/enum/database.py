# coding: UTF-8
"""
@software: PyCharm
@author: Beau Dean
@contact: https://fairy.host
@organization: https://github.com/FairylandFuture
@datetime: 2026-05-03 01:00:46 UTC+08:00
"""

from __future__ import annotations

import typing as t
from enum import Enum


class DatabaseTypeEnum(str, Enum):
    MYSQL = "mysql"
    POSTGRESQL = "postgresql"

    @classmethod
    def from_value(cls, v: str):
        return cls._value2member_map_.get(v.lower())
