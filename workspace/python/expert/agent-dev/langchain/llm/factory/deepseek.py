# coding: UTF-8
"""
@software: PyCharm
@author: Beau Dean
@contact: https://fairy.host
@organization: https://github.com/FairylandFuture
@datetime: 2026-04-17 23:41:14 UTC+08:00
"""
from __future__ import annotations

import typing as t

from langchain_deepseek import ChatDeepSeek
from llm.factory import LLMCreator
from llm.config import LLMConfig


class DeepSeekLLMCreator(LLMCreator):

    def create(self, **kwargs) -> ChatDeepSeek:
        params = {
            "base_url": LLMConfig.DEEPSEEK_BASE_URL,
            "api_key": LLMConfig.DEEPSEEK_API_KEY,
            "model": LLMConfig.DefaultModel.DEEPSEEK,
        }
        params.update(kwargs)
        params.pop("provider", None)
        return ChatDeepSeek(**params)
