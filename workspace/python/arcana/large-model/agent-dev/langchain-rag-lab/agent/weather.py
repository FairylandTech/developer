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
from tools.weather import get_weather_live
from middleware.agent.logger import log_after_agent, log_before_agent
from middleware.model.logger import log_before_model, log_after_model, model_hook
from middleware.tools.monitor import monitor_tools_hook


class WeatherAgent(AgentBase):

    def create_agent(self) -> CompiledStateGraph:
        return create_agent(
            model=self.model,
            tools=[
                get_weather_live,
            ],
            middleware=[
                log_before_agent,
                log_after_agent,
                log_before_model,
                log_after_model,
                model_hook,
                monitor_tools_hook,
            ],
            system_prompt="你是一个实时天气助手，能够根据用户输入的地理位置提供天气信息。",
        )
