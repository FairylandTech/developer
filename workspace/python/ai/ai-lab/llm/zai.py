# coding: UTF-8
"""
@software: PyCharm
@author: Beau Dean
@contact: https://fairy.host
@organization: https://github.com/FairylandFuture
@datetime: 2026-04-24 13:26:35 UTC+08:00
"""

from __future__ import annotations

import typing as t

from langchain_community.chat_models.zhipuai import ChatZhipuAI
from langchain_community.embeddings.zhipuai import ZhipuAIEmbeddings
from langchain_core.language_models import BaseChatModel
from langchain_openai import ChatOpenAI
from pydantic import SecretStr

from utils.env import EnvUtils

__ZAI_API_KEY = EnvUtils.getenv("ZAI_API_KEY")
__ZAI_API_URL = EnvUtils.getenv("ZAI_BASE_URL")

zai_chat_model: ChatZhipuAI = ChatZhipuAI(
    model="GLM-4.5-Air",
    api_key=__ZAI_API_KEY,
)

zai_embedding_model: ChatZhipuAI = ChatZhipuAI(
    model="embedding-2",
    api_key=__ZAI_API_KEY,
)

zai_embedding: ZhipuAIEmbeddings = ZhipuAIEmbeddings(
    model="embedding-2",
    api_key=__ZAI_API_KEY,
)


class ZAIModelManager:
    __ZAI_API_KEY__: str = EnvUtils.getenv("ZAI_API_KEY")
    __ZAI_BASE_URL__: str = EnvUtils.getenv("ZAI_BASE_URL")

    __CHAT_MODEL_MAP__: t.ClassVar[dict[str, BaseChatModel]] = {}

    @classmethod
    def create_chat_model(cls, model: t.Optional[str] = None) -> BaseChatModel:
        if not model:
            model = "glm-4.5-air"
        if model and model in cls.__CHAT_MODEL_MAP__.keys():
            return cls.__CHAT_MODEL_MAP__[model]

        llm = ChatOpenAI(model=model, api_key=SecretStr(cls.__ZAI_API_KEY__), base_url=cls.__ZAI_BASE_URL__)

        cls.__CHAT_MODEL_MAP__[model] = llm
        return llm

    @classmethod
    def create_zhipu_embedding(cls) -> ZhipuAIEmbeddings:
        return ZhipuAIEmbeddings(
            model="embedding-2",
            api_key=cls.__ZAI_API_KEY__,
        )
