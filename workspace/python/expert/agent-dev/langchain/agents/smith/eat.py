# coding: UTF-8
"""
@software: PyCharm
@author: Lionel Johnson
@contact: https://fairy.host
@organization: https://github.com/FairylandFuture
@datetime: 2026-04-17 18:15:00 UTC+08:00
"""
from __future__ import annotations

import base64
import typing as t
from pathlib import Path

from langchain.agents import create_agent
from langchain_core.messages import HumanMessage
from langchain_core.runnables import RunnableConfig

from domain.model.agent.message import InputMessages
from llm.tongyi import llm_tongyi
from memory import default_checkpointer
from tools.web.search import tavily

system_pompt = """
你是一名私人厨师。收到用户提供的食材照片或清单后，请按以下流程操作：
1。识别和评估食材：诺用户提供照片，首先辨识所有可见食材。基于食材的外观状态，评估其新鲜度与可用量，整理出一份“当前可用食材清单”。
2。智能食谱检索：优先调用web_search工具，以“可用食材清单"为核心关键词，查找可行菜谱。
3.多维度评估与排序：从营养价值和制作难度两个维度对检索到的候选食谱进行量化打分，并根据得分排序，制作简单且营养丰富的排名靠前。
4。结构化方案输出：把排序后的食谱整理为一份结构清晰的建议报告，要包含食谱信息、得分、推荐理由、食谱的参考图片，帮助用户快速做出决策。
请严格按照流程，优先调用web_search工具搜索食谱，搜索不到的情况下才能自己发挥。
"""

agent = create_agent(
    model=llm_tongyi,
    tools=[
        tavily
    ],
    system_prompt=system_pompt,
    # checkpointer=default_checkpointer
)


def cheif(human: str, food: Path):
    agent = create_agent(
        model=llm_tongyi,
        tools=[
            tavily
        ],
        system_prompt=system_pompt,
        checkpointer=default_checkpointer
    )

    config: RunnableConfig = {
        "configurable": {
            "thread_id": "cheif_agent_thread_1"
        }
    }

    result = agent.invoke(
        InputMessages(
            messages=[
                HumanMessage(
                    [
                        {"type": "text", "text": human, },
                        {"type": "image", "mime_type": "image/jpeg", "base64": base64.b64encode(food.read_bytes()).decode("UTF-8"), }
                    ]
                )
            ]
        ),
        config
    )

    messages = result.get("messages", [])

    for message in messages:
        message.pretty_print()
