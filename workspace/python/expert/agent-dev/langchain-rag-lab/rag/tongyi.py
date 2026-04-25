# coding: UTF-8
"""
@software: PyCharm
@author: Beau Dean
@contact: https://fairy.host
@organization: https://github.com/FairylandFuture
@datetime: 2026-04-24 13:12:37 UTC+08:00
"""
from __future__ import annotations

import typing as t

from langchain_community.chat_models import ChatTongyi
from langchain_openai import ChatOpenAI
from langchain_community.llms.tongyi import Tongyi
from pydantic import SecretStr

from utils.env import EnvUtils

__DASHSCOPE_API_KEY: str = EnvUtils.getenv("DASHSCOPE_API_KEY")
__DASHSCOPE_BASE_URL: str = EnvUtils.getenv("DASHSCOPE_BASE_URL")

tongyi_llm: Tongyi = Tongyi(
    model="qwen-flash-2025-07-28",
    api_key=__DASHSCOPE_API_KEY
)

tongyi_chat_model: ChatOpenAI = ChatOpenAI(
    model="qwen-flash-2025-07-28",
    api_key=SecretStr(__DASHSCOPE_API_KEY),
    base_url=__DASHSCOPE_BASE_URL,
    temperature=0.8,
    top_p=0.5
)
