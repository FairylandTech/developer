# coding: UTF-8
"""
@software: PyCharm
@author: Beau Dean
@contact: https://fairy.host
@organization: https://github.com/FairylandFuture
@datetime: 2026-04-18 00:55:44 UTC+08:00
"""
from __future__ import annotations

import typing as t

from langchain_openai import ChatOpenAI
from llm.factory import LLMCreator
from llm.config import LLMConfig


class QwenLLMCreator(LLMCreator):

    def create(self, **kwargs) -> ChatOpenAI:
        params = {
            "base_url": LLMConfig.DASHSCOPE_BASE_URL,
            "api_key": LLMConfig.DASHSCOPE_API_KEY,
            "model": LLMConfig.DefaultModel.DASHSCOPE,
        }
        params.update(kwargs)
        params.pop("provider", None)
        return ChatOpenAI(**params)
