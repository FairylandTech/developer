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

from langchain_community.llms.tongyi import Tongyi
from langchain_community.embeddings import DashScopeEmbeddings
from langchain_openai import ChatOpenAI
from pydantic import SecretStr

from utils.env import EnvUtils


class TongyiModelManager:
    __DASHSCOPE_API_KEY__: str = EnvUtils.getenv("DASHSCOPE_API_KEY")
    __DASHSCOPE_BASE_URL__: str = EnvUtils.getenv("DASHSCOPE_BASE_URL")

    __CHAT_MODEL_MAP__: t.ClassVar[dict[str, ChatOpenAI]] = {}

    @classmethod
    def create_chat_model(cls, model: t.Optional[str] = None) -> ChatOpenAI:
        if not model:
            model = "qwen3.5-flash"
        if model and model in cls.__CHAT_MODEL_MAP__.keys():
            return cls.__CHAT_MODEL_MAP__[model]

        llm = ChatOpenAI(
            model=model,
            api_key=SecretStr(cls.__DASHSCOPE_API_KEY__),
            base_url=cls.__DASHSCOPE_BASE_URL__,
            top_p=0.5,
        )

        cls.__CHAT_MODEL_MAP__[model] = llm
        return llm

    @classmethod
    def create_llm(cls) -> Tongyi:
        return Tongyi(model="qwen-flash-2025-07-28", api_key=cls.__DASHSCOPE_API_KEY__)

    @classmethod
    def create_dashscope_embedding(cls) -> DashScopeEmbeddings:
        return DashScopeEmbeddings(
            model="text-embedding-v3",
            dashscope_api_key=cls.__DASHSCOPE_API_KEY__,
        )

    @classmethod
    def get_chat_model_map(cls) -> dict[str, ChatOpenAI]:
        return cls.__CHAT_MODEL_MAP__
