# coding: UTF-8
"""
@software: PyCharm
@author: Beau Dean
@contact: https://fairy.host
@organization: https://github.com/FairylandFuture
@datetime: 2026-05-03 19:00:34 UTC+08:00
"""
from __future__ import annotations

import typing as t

from langchain_core.messages import BaseMessage

from domain.model import ModelBase
from domain.schema.message import Message


class InputMessages(ModelBase):
    messages: t.List[Message | BaseMessage]

    def to_dict(self):
        return {
            "messages": self.messages
        }
