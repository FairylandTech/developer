# coding: UTF-8
"""
@software: PyCharm
@author: Lionel Johnson
@contact: https://fairy.host
@organization: https://github.com/FairylandFuture
@datetime: 2026-04-16 22:34:19 UTC+08:00
"""
from __future__ import annotations

import typing as t

from langchain_core.messages import BaseMessage

from domain.model import BaseModel


class Message(BaseModel):
    role: t.Literal["system", "user"]
    content: str


class InputMessages(BaseModel):
    messages: t.List[Message | BaseMessage]

    def to_dict(self):
        return {
            "messages": self.messages
        }
