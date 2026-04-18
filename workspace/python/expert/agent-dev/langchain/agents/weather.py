# coding: UTF-8
"""
@software: PyCharm
@author: Lionel Johnson
@contact: https://fairy.host
@organization: https://github.com/FairylandFuture
@datetime: 2026-04-15 01:28:58 UTC+08:00
"""
from __future__ import annotations

import typing as t

from langchain.agents import create_agent
from langchain_core.messages import BaseMessage, SystemMessage, HumanMessage, AIMessage

from llm.factory import LLMFactory
from tools.weather import get_weather_live

from domain.model.agent.message import Message, InputMessages


def get_weather_live_invoke():
    """
    invoke 阻塞模式，获取实时天气信息。

    :return:
    :rtype:
    """

    llm = LLMFactory.create("deepseek")

    agent = create_agent(
        llm,
        tools=[
            get_weather_live,
        ],
    )

    message = {
        "messages": [
            SystemMessage("你是一个实时天气助手，能够根据用户输入的地理位置提供天气信息。"),
            HumanMessage("你好"),
            AIMessage("你好！请告诉我你想查询哪个地方的天气？"),
            HumanMessage("杭州"),
        ]
    }

    response: dict[str, list[BaseMessage]] = agent.invoke(message)

    for message in response.get("messages", []):
        message.pretty_print()


def get_weather_live_stream():
    """
    stream 流式输出获取实时天气信息。

    :return:
    :rtype:
    """
    agent = create_agent(
        "deepseek-chat",
        tools=[
            get_weather_live,
        ]
    )

    input_messages: InputMessages = InputMessages(
        messages=[
            Message(role="system", content="你是一个实时天气助手，能够根据用户输入的地理位置提供天气信息。"),
            Message(role="user", content="杭州的天气怎么样？")
        ]
    )

    output_messages = agent.stream(input_messages.model_dump(), stream_mode="messages")

    for token, metadata in output_messages:
        if token.content:
            print(token.content, end="", flush=True)
