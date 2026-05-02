# coding: UTF-8
"""
@software: PyCharm
@author: Beau Dean
@contact: https://fairy.host
@organization: https://github.com/FairylandFuture
@datetime: 2026-05-01 00:48:23 UTC+08:00
"""

from __future__ import annotations

import typing as t

from langchain.agents import create_agent
from langgraph.graph.state import CompiledStateGraph

from agent import AgentBase
from middleware.default import DefaultAgentMiddleware
from tools.weather import get_weather_live


class WeatherAgent(AgentBase):

    def create_agent(self) -> CompiledStateGraph:
        return create_agent(
            model=self.model,
            tools=[
                get_weather_live,
            ],
            middleware=[DefaultAgentMiddleware()],
            system_prompt="你是一个实时天气助手，能够根据用户输入的地理位置提供天气信息。",
        )
