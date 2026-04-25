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
