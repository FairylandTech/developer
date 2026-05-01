# coding: UTF-8
"""
@software: PyCharm
@author: Beau Dean
@contact: https://fairy.host
@organization: https://github.com/FairylandFuture
@datetime: 2026-05-01 01:04:31 UTC+08:00
"""
from __future__ import annotations

import typing as t

from langchain_core.messages import HumanMessage, AIMessageChunk, ToolMessage

from agent.weather import WeatherAgent
from agent.health import HealthAgent
from domain.model.agent.message import InputMessages
import warnings

warnings.filterwarnings("ignore")


def test_weather_agent():
    agent = WeatherAgent.get_agent()

    input: InputMessages = InputMessages(
        messages=[
            HumanMessage("杭州天气怎么样？"),
        ]
    )

    for chunk, metadata in agent.stream(input, stream_mode="messages"):
        chunk: AIMessageChunk
        print(chunk.content, end="", flush=True)


def test_weather_agent_shanghai():
    agent = WeatherAgent.get_agent()

    input: InputMessages = InputMessages(
        messages=[
            HumanMessage("上海天气怎么样？"),
        ]
    )

    for chunk, metadata in agent.stream(input, stream_mode="messages"):
        chunk: AIMessageChunk
        print(chunk.content, end="", flush=True)


def test_health_agent():
    agent = HealthAgent.get_agent()

    input: InputMessages = InputMessages(
        messages=[
            HumanMessage("计算一下我的BMI"),
        ]
    )

    for chunk, metadata in agent.stream(input, stream_mode="messages"):
        chunk: AIMessageChunk | ToolMessage

        if isinstance(chunk, ToolMessage):
            continue

        print(chunk.content, end="", flush=True)


if __name__ == '__main__':
    # test_weather_agent()
    test_weather_agent_shanghai()
    test_health_agent()
