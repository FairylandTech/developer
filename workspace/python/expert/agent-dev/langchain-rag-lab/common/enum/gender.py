# coding: UTF-8
"""
@software: PyCharm
@author: Beau Dean
@contact: https://fairy.host
@organization: https://github.com/FairylandFuture
@datetime: 2026-04-24 13:09:56 UTC+08:00
"""
from __future__ import annotations

import typing as t

from enum import Enum


class GenderEnum(Enum):
    MALE = "男"
    FEMALE = "女"

    @property
    def value(self) -> str:
        return t.cast(str, t.cast(object, super().value))
