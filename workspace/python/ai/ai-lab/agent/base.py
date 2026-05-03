# coding: UTF-8
"""
@software: PyCharm
@author: Beau Dean
@contact: https://fairy.host
@organization: https://github.com/FairylandFuture
@datetime: 2026-05-01 17:14:00 UTC+08:00
"""
from __future__ import annotations

import typing as t

from langchain_core.language_models import BaseChatModel
from langgraph.graph.state import CompiledStateGraph

from llm.tongyi import TongyiModelManager


class AgentBase:
    _instance_: t.ClassVar[t.Optional[AgentBase]] = None

    model: BaseChatModel

    def __init__(self, model: BaseChatModel = None):
        if not model:
            model = TongyiModelManager.create_chat_model()

        self.model = model

    def create_agent(self) -> CompiledStateGraph:
        raise NotImplementedError("Subclasses must implement create_agent method.")

    @classmethod
    def get_agent(cls, model: t.Optional[BaseChatModel] = None):
        instance = cls._instance_
        if instance is None:
            cls._instance_ = instance = cls(model)

        return instance.create_agent()
