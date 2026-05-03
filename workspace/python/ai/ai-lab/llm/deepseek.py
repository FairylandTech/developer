# coding: UTF-8
"""
@software: PyCharm
@author: Beau Dean
@contact: https://fairy.host
@organization: https://github.com/FairylandFuture
@datetime: 2026-05-02 21:01:33 UTC+08:00
"""

from __future__ import annotations

import typing as t

from langchain_core.language_models import BaseChatModel
from langchain_openai import ChatOpenAI
from pydantic import SecretStr

from utils.env import EnvUtils


class DeepSeekModelManager:

    __DEEPSEEK_API_KEY__: str = EnvUtils.getenv("DEEPSEEK_API_KEY")
    __DEEPSEEK_BASE_URL__: str = EnvUtils.getenv("DEEPSEEK_BASE_URL")

    @classmethod
    def create_chat_model(cls, model: str = None) -> BaseChatModel:
        if not model:
            # model = "deepseek-v4-pro"
            model = "deepseek-v4-flash"

        llm = ChatOpenAI(
            model=model,
            api_key=SecretStr(cls.__DEEPSEEK_API_KEY__),
            base_url=cls.__DEEPSEEK_BASE_URL__,
        )

        return llm
