# coding: UTF-8
"""
@software: PyCharm
@author: Beau Dean
@contact: https://fairy.host
@organization: https://github.com/FairylandFuture
@datetime: 2026-04-26 19:10:56 UTC+08:00
"""

from __future__ import annotations

import typing as t

from langchain_chroma import Chroma

from common.const import ROOT_DIR
from llm.doubao import DoubaoModelManager
from llm.hunyuan import HunYuanModelManager
from llm.modelscope import ModelScopeModelManager
from llm.nvidia import NvidiaModelManager
from llm.tongyi import TongyiModelManager
from llm.zai import ZAIModelManager


class ChromaVectorStoreManager:

    @classmethod
    def create_vector_store(cls, collection_name: str = None) -> Chroma:
        if not collection_name:
            collection_name = "test_collection"

        chroma = Chroma(
            collection_name=collection_name,  # 当前向量存储的名字，类似数据库中表的名称
            embedding_function=ModelScopeModelManager.create_embedding(),  # 嵌入模型
            persist_directory=ROOT_DIR / "storage" / "chroma" / "chroma.db",  # 向量存储的目录
        )

        return chroma
