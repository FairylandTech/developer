# coding: UTF-8
"""
@software: PyCharm
@author: Beau Dean
@contact: https://fairy.host
@organization: https://github.com/FairylandFuture
@datetime: 2026-05-03 04:32:23 UTC+08:00
"""

from __future__ import annotations

import typing as t

from langchain.chat_models import BaseChatModel
from langchain_openai.chat_models import ChatOpenAI
from pydantic import SecretStr

from utils.env import EnvUtils


class KimiModelManager:
    __MOONSHOT_API_KEY__: t.ClassVar[str] = EnvUtils.getenv("MOONSHOT_API_KEY")
    __MOONSHOT_BASE_URL__: t.ClassVar[str] = EnvUtils.getenv("MOONSHOT_BASE_URL")

    @classmethod
    def create_chat_model(cls, model: str = None) -> BaseChatModel:
        if not model:
            model = "kimi-k2-turbo-preview"
        return ChatOpenAI(
            model=model,
            api_key=SecretStr(cls.__MOONSHOT_API_KEY__),
            base_url=cls.__MOONSHOT_BASE_URL__,
        )
