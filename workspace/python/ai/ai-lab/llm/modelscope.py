# coding: UTF-8
"""
@software: PyCharm
@author: Beau Dean
@contact: https://fairy.host
@organization: https://github.com/FairylandFuture
@datetime: 2026-05-03 18:24:44 UTC+08:00
"""
from __future__ import annotations

import typing as t

from langchain_openai.embeddings import OpenAIEmbeddings
from pydantic import SecretStr

from utils.env import EnvUtils


class ModelScopeModelManager:
    __MODA_API_KEY__: t.ClassVar[str] = EnvUtils.getenv("MODA_API_KEY")
    __MODA_BASE_URL__: t.ClassVar[str] = EnvUtils.getenv("MODA_BASE_URL")

    @classmethod
    def create_embedding(cls):
        return OpenAIEmbeddings(
            model="Qwen/Qwen3-Embedding-4B",
            api_key=SecretStr(cls.__MODA_API_KEY__),
            base_url=cls.__MODA_BASE_URL__,
        )
