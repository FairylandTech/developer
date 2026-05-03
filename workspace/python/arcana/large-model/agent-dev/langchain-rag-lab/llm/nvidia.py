# coding: UTF-8
"""
@software: PyCharm
@author: Beau Dean
@contact: https://fairy.host
@organization: https://github.com/FairylandFuture
@datetime: 2026-05-03 04:25:03 UTC+08:00
"""

from __future__ import annotations

import typing as t

from langchain.chat_models import BaseChatModel
from langchain_openai.chat_models import ChatOpenAI
from langchain_nvidia_ai_endpoints import NVIDIAEmbeddings
from pydantic import SecretStr

from utils.env import EnvUtils


class NvidiaModelManager:
    __NVIDIA_API_KEY__: t.ClassVar[str] = EnvUtils.getenv("NVIDIA_API_KEY")
    __NVIDIA_BASE_URL__: t.ClassVar[str] = EnvUtils.getenv("NVIDIA_BASE_URL")

    @classmethod
    def create_chat_model(cls, model: str = None) -> BaseChatModel:
        if not model:
            model = "google/gemma-4-31b-it"
        return ChatOpenAI(
            model=model,
            api_key=SecretStr(cls.__NVIDIA_API_KEY__),
            base_url=cls.__NVIDIA_BASE_URL__,
        )

    @classmethod
    def create_nvidia_embedding(cls):
        return NVIDIAEmbeddings(
            model="nvidia/llama-nemotron-embed-1b-v2",
            api_key=cls.__NVIDIA_API_KEY__,
            truncate="NONE",
            dimensions=1024,
        )
