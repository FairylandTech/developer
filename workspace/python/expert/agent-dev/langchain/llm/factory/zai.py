# coding: UTF-8
"""
@software: PyCharm
@author: Lionel Johnson
@contact: https://fairy.host
@organization: https://github.com/FairylandFuture
@datetime: 2026-04-18 01:07:06 UTC+08:00
"""
from __future__ import annotations

import typing as t

from langchain_openai import ChatOpenAI
from llm.factory import LLMCreator
from llm.config import LLMConfig


class ZAILLMCreator(LLMCreator):

    def create(self, **kwargs) -> ChatOpenAI:
        params = {
            "base_url": LLMConfig.ZAI_BASE_URL,
            "api_key": LLMConfig.ZAI_API_KEY,
            "model": LLMConfig.DefaultModel.ZAI,
        }
        params.update(kwargs)
        params.pop("provider", None)
        return ChatOpenAI(**params)
