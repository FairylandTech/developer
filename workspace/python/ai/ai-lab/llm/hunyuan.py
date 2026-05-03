# coding: UTF-8
"""
@software: PyCharm
@author: Beau Dean
@contact: https://fairy.host
@organization: https://github.com/FairylandFuture
@datetime: 2026-04-24 13:19:55 UTC+08:00
"""

from __future__ import annotations

import typing as t

from langchain_community.embeddings import HunyuanEmbeddings
from langchain_core.language_models import BaseChatModel
from openai import OpenAI
from pydantic import SecretStr
from langchain_openai import ChatOpenAI

from utils.env import EnvUtils


class HunYuanModelManager:
    __HUNYUAN_API_KEY__: str = EnvUtils.getenv("HUNYUAN_API_KEY")
    __HUNYUAN_BASE_URL__: str = EnvUtils.getenv("HUNYUAN_BASE_URL")
    __HUNYUAN_SECRET_ID__: str = EnvUtils.getenv("HUNYUAN_SECRET_ID")
    __HUNYUAN_SECRET_KEY__: str = EnvUtils.getenv("HUNYUAN_SECRET_KEY")

    @classmethod
    def create_chat_model(cls, model: str = None) -> BaseChatModel:
        if not model:
            model = "hunyuan-turbos-latest"
        llm = ChatOpenAI(
            model=model,
            api_key=SecretStr(cls.__HUNYUAN_API_KEY__),
            base_url=cls.__HUNYUAN_BASE_URL__,
        )
        return llm

    @classmethod
    def create_hunyuan_embedding(cls) -> HunyuanEmbeddings:
        return HunyuanEmbeddings(
            secret_id=SecretStr(cls.__HUNYUAN_SECRET_ID__),
            secret_key=SecretStr(cls.__HUNYUAN_SECRET_KEY__),
            region="ap-guangzhou",
        )

    @classmethod
    def list_supported_models(cls) -> list[str]:
        client = OpenAI(api_key=cls.__HUNYUAN_API_KEY__, base_url=cls.__HUNYUAN_BASE_URL__)
        models_response = client.models.list()
        return [model_item.id for model_item in models_response.data]
