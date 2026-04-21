# coding: UTF-8
"""
@software: PyCharm
@author: Beau Dean
@contact: https://fairy.host
@organization: https://github.com/FairylandFuture
@datetime: 2026-04-18 05:04:36 UTC+08:00
"""
from __future__ import annotations

import typing as t

from langchain_openai import ChatOpenAI
from llm.factory import LLMCreator
from llm.config import LLMConfig


class DoubaoLLMCreator(LLMCreator):

    def create(self, **kwargs) -> ChatOpenAI:
        params = {
            "base_url": LLMConfig.ARK_BASE_URL,
            "api_key": LLMConfig.ARK_API_KEY,
            "model": LLMConfig.DefaultModel.ARK,
        }
        params.update(kwargs)
        params.pop("provider", None)
        return ChatOpenAI(**params)
