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
    def create_chat_model(cls, name: str, model: str = None) -> ChatOpenAI:
        if name in cls.__CHAT_MODEL_MAP__:
            return cls.__CHAT_MODEL_MAP__[name]

        tongyi_chat_model = ChatOpenAI(
            model=model if model else "qwen-flash-2025-07-28",
            api_key=SecretStr(cls.__DASHSCOPE_API_KEY__),
            base_url=cls.__DASHSCOPE_BASE_URL__,
            temperature=0.8,
            top_p=0.5
        )

        cls.__CHAT_MODEL_MAP__[name] = tongyi_chat_model
        return tongyi_chat_model

    @classmethod
    def create_llm(cls) -> Tongyi:
        return Tongyi(
            model="qwen-flash-2025-07-28",
            api_key=cls.__DASHSCOPE_API_KEY__
        )

    @classmethod
    def create_embedding(cls) -> DashScopeEmbeddings:
        return DashScopeEmbeddings(
            model="text-embedding-v3",
            dashscope_api_key=cls.__DASHSCOPE_API_KEY__,
        )

    @classmethod
    def get_chat_model_map(cls) -> dict[str, ChatOpenAI]:
        return cls.__CHAT_MODEL_MAP__
