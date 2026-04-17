# coding: UTF-8
"""
@software: PyCharm
@author: Lionel Johnson
@contact: https://fairy.host
@organization: https://github.com/FairylandFuture
@datetime: 2026-04-17 06:21:04 UTC+08:00
"""
from __future__ import annotations

import typing as t

from langchain.agents import create_agent
from langchain_core.messages import HumanMessage
from langgraph.checkpoint.memory import InMemorySaver
from llm.zai import zai_llm

from domain.model.agent.message import InputMessages


def session_agent():
    """
    带有会话记忆的Agent，使用InMemorySaver保存会话历史记录。

    :return:
    :rtype:
    """

    agent = create_agent(
        zai_llm,
        checkpointer=InMemorySaver(),
    )

    config = {
        "configurable": {
            "thread_id": "session_agent_thread_1",
        }
    }

    response = agent.invoke(InputMessages(messages=[HumanMessage("你好。我喜欢猫咪。")]), config)
    print(response)

    result = agent.invoke(InputMessages(messages=[HumanMessage("我最喜欢的动物是什么？")]), config)
    print(result)
