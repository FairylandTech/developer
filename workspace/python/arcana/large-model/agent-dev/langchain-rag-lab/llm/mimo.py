# coding: UTF-8
"""
@software: PyCharm
@author: Beau Dean
@contact: https://fairy.host
@organization: https://github.com/FairylandFuture
@datetime: 2026-05-02 20:29:19 UTC+08:00
"""

from __future__ import annotations

import typing as t

from langchain_community.llms.tongyi import Tongyi
from langchain_community.embeddings import DashScopeEmbeddings
from langchain_openai import ChatOpenAI
from pydantic import SecretStr

from utils.env import EnvUtils


class MIMOModelManager:
    __MIMO_API_KEY__: str = EnvUtils.getenv("MIMO_API_KEY")
    __MIMO_BASE_URL__: str = EnvUtils.getenv("MIMO_BASE_URL")

    __CHAT_MODEL_MAP__: t.ClassVar[dict[str, ChatOpenAI]] = {}

    @classmethod
    def create_chat_model(cls, model: t.Optional[str] = None) -> ChatOpenAI:
        if not model:
            model = "mimo-v2-omni"
        if model and model in cls.__CHAT_MODEL_MAP__.keys():
            return cls.__CHAT_MODEL_MAP__[model]

        llm = ChatOpenAI(model=model, api_key=SecretStr(cls.__MIMO_API_KEY__), base_url=cls.__MIMO_BASE_URL__)

        cls.__CHAT_MODEL_MAP__[model] = llm
        return llm
