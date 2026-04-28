# coding: UTF-8
"""
@software: PyCharm
@author: Beau Dean
@contact: https://fairy.host
@organization: https://github.com/FairylandFuture
@datetime: 2026-04-27 16:50:31 UTC+08:00
"""

from __future__ import annotations

import typing as t

from langchain_openai.chat_models import ChatOpenAI
from langchain_community.embeddings import DashScopeEmbeddings
from pydantic import SecretStr

from utils.env import EnvUtils


class TongyiLLMManager:
    __dashscope_api_key__ = EnvUtils.getenv("DASHSCOPE_API_KEY")
    __dashscope_base_url__ = EnvUtils.getenv("DASHSCOPE_BASE_URL")

    @classmethod
    def create_chat_model(cls, model: str = None) -> ChatOpenAI:
        if not model:
            model = "qwen-flash-2025-07-28"

        return ChatOpenAI(
            model=model,
            api_key=SecretStr(cls.__dashscope_api_key__),
            base_url=cls.__dashscope_base_url__,
            temperature=1,
            top_p=0.6,
        )

    @classmethod
    def create_embedding_model(cls, model: str = None) -> DashScopeEmbeddings:
        if not model:
            model = "text-embedding-v3"
        return DashScopeEmbeddings(
            model=model,
            dashscope_api_key=cls.__dashscope_api_key__,
        )
