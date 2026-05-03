# coding: UTF-8
"""
@software: PyCharm
@author: Beau Dean
@contact: https://fairy.host
@organization: https://github.com/FairylandFuture
@datetime: 2026-05-03 02:32:01 UTC+08:00
"""

from __future__ import annotations

import typing as t

from langchain_openai.chat_models import ChatOpenAI
from langchain.chat_models import BaseChatModel
from pydantic import SecretStr

from utils.env import EnvUtils


class SiliconFlowModelManager:
    __SILICON_API_KEY__: t.ClassVar[str] = EnvUtils.getenv("SILICON_API_KEY")
    __SILICON_BASE_URL__: t.ClassVar[str] = EnvUtils.getenv("SILICON_BASE_URL")

    @classmethod
    def create_chat_model(cls, model: str = None) -> BaseChatModel:
        if not model:
            model = "stepfun-ai/Step-3.5-Flash"
            # model = "inclusionAI/Ling-flash-2.0"
        return ChatOpenAI(
            model=model,
            api_key=SecretStr(cls.__SILICON_API_KEY__),
            base_url=cls.__SILICON_BASE_URL__,
        )
