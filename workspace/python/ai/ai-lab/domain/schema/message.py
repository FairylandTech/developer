# coding: UTF-8
"""
@software: PyCharm
@author: Beau Dean
@contact: https://fairy.host
@organization: https://github.com/FairylandFuture
@datetime: 2026-04-16 22:34:19 UTC+08:00
"""
from __future__ import annotations

import typing as t

from domain.model import ModelBase


class Message(ModelBase):
    role: t.Literal["system", "user"]
    content: str
