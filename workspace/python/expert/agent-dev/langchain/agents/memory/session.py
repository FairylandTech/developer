# coding: UTF-8
"""
@software: PyCharm
@author: Lionel Johnson
@contact: https://fairy.host
@organization: https://github.com/FairylandFuture
@datetime: 2026-04-17 07:16:28 UTC+08:00
"""
from __future__ import annotations

import typing as t

from langchain_core.messages import HumanMessage

from domain.model.agent.message import InputMessages
from memory import default_checkpointer
from llm.zai import zai_llm

from langchain.agents import create_agent
from langchain_core.runnables import RunnableConfig
from langchain.agents.middleware import SummarizationMiddleware


def session_memory_agent():
    trigger: tuple[t.Literal["messages"], int] = ("messages", 3)
    keep: tuple[t.Literal["messages"], int] = ("messages", 2)

    agent = create_agent(
        zai_llm,
        checkpointer=default_checkpointer,
        middleware=[
            SummarizationMiddleware(
                zai_llm,
                trigger=trigger,
                keep=keep,
            )
        ]
    )

    config: RunnableConfig = {"configurable": {"thread_id": "session_memory_agent_thread_1"}}

    response = agent.invoke(
        InputMessages(messages=[HumanMessage("你好。我喜欢猫咪。")]),
        config,
    )

    messages = response.get("messages", [])
