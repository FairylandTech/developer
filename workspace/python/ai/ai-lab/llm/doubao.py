# coding: UTF-8
"""
@software: PyCharm
@author: Beau Dean
@contact: https://fairy.host
@organization: https://github.com/FairylandFuture
@datetime: 2026-05-03 02:56:13 UTC+08:00
"""

from __future__ import annotations

import typing as t

from langchain.chat_models import BaseChatModel
from langchain_openai.chat_models import ChatOpenAI
from langchain_community.embeddings import VolcanoEmbeddings
from pydantic import SecretStr

from utils.env import EnvUtils


class DoubaoModelManager:
    __ARK_API_KEY__: t.ClassVar[str] = EnvUtils.getenv("ARK_API_KEY")
    __ARK_BASE_URL__: t.ClassVar[str] = EnvUtils.getenv("ARK_BASE_URL")
    __ARK_AK__: t.ClassVar[str] = EnvUtils.getenv("ARK_AK")
    __ARK_SK__: t.ClassVar[str] = EnvUtils.getenv("ARK_SK")

    @classmethod
    def create_chat_model(cls, model: str = None) -> BaseChatModel:
        if not model:
            model = "ep-20260503032456-rtgxw"  # Doubao-Seed-2.0-pro
        return ChatOpenAI(
            model=model,
            api_key=SecretStr(cls.__ARK_API_KEY__),
            base_url=cls.__ARK_BASE_URL__,
        )

    @classmethod
    def create_volcano_embedding(cls):
        return VolcanoEmbeddings(
            model="ep-20260503074328-hfjvh",
            volcano_ak=cls.__ARK_AK__,
            volcano_sk=cls.__ARK_SK__,
        )
