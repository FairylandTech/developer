# coding: UTF-8
"""
@software: PyCharm
@author: Beau Dean
@contact: https://fairy.host
@organization: https://github.com/FairylandFuture
@datetime: 2026-05-01 17:09:50 UTC+08:00
"""
from __future__ import annotations

import typing as t

from langchain.agents import create_agent
from langgraph.graph.state import CompiledStateGraph

from agent import AgentBase
from tools.health import get_height, get_weight

_system_prompt = "你是一个健康助手，能够根据用户输入的症状提供健康建议。(每次只能调用一个工具)"


class HealthAgent(AgentBase):

    def create_agent(self) -> CompiledStateGraph:
        return create_agent(
            model=self.model,
            tools=[get_height, get_weight],
            system_prompt=_system_prompt,
        )
