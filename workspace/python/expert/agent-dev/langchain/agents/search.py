# coding: UTF-8
"""
@software: PyCharm
@author: Lionel Johnson
@contact: https://fairy.host
@organization: https://github.com/FairylandFuture
@datetime: 2026-04-17 03:51:41 UTC+08:00
"""
from __future__ import annotations

import typing as t

from langchain.agents import create_agent
from langchain_core.messages import HumanMessage, BaseMessage, ToolMessage

from domain.model.agent.message import InputMessages
from domain.model.tools.web.search import TavilySearchResult
from tools.web.search import tavily, tavily_search


def search(input: str):
    """
    智能搜索工具，使用 TavilySearch 进行搜索。

    :return:
    :rtype:
    """

    agent = create_agent(
        "deepseek-chat",
        system_prompt="你是一个智能搜索工具。",
        tools=[
            tavily,
        ],
    )

    result: dict[str, list[BaseMessage]] = agent.invoke(InputMessages(messages=[HumanMessage(input)]).to_dict())

    for message in result.get("messages", []):
        message.pretty_print()


def tool_search(input: str):
    """
    直接调用工具进行搜索。

    :return:
    :rtype:
    """

    agent = create_agent(
        "deepseek-chat",
        system_prompt="你是一个智能搜索工具。",
        tools=[
            tavily_search,
        ],
        response_format=TavilySearchResult,
    )

    result = agent.invoke(InputMessages(messages=[HumanMessage(input)]).to_dict())

    messages: list[BaseMessage] = result.get("messages", [])
    structured_response: TavilySearchResult = result.get("structured_response", TavilySearchResult())

    for message in messages:
        message.pretty_print()

    print(structured_response.answer)
