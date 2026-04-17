# coding: UTF-8
"""
@software: PyCharm
@author: Lionel Johnson
@contact: https://fairy.host
@organization: https://github.com/FairylandFuture
@datetime: 2026-04-17 17:03:29 UTC+08:00
"""
from __future__ import annotations

import typing as t
import os

from langchain_openai import ChatOpenAI
from pydantic import SecretStr

DASHSCOPE_API_URL = os.getenv("DASHSCOPE_API_URL", "https://dashscope.aliyuncs.com/compatible-mode/v1")
DASHSCOPE_API_KEY = os.getenv("DASHSCOPE_API_KEY")

# 多模态：视觉理解
llm_tongyi = ChatOpenAI(
    model="qwen3.6-plus",
    base_url=DASHSCOPE_API_URL,
    api_key=SecretStr(DASHSCOPE_API_KEY) if DASHSCOPE_API_KEY else None,
    temperature=0.5,
)
