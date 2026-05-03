# coding: UTF-8
"""
@software: PyCharm
@author: Beau Dean
@contact: https://fairy.host
@organization: https://github.com/FairylandFuture
@datetime: 2026-05-03 01:24:46 UTC+08:00
"""

from __future__ import annotations

import typing as t

from langchain_core.vectorstores import VectorStore
from langchain_postgres import PGVector

from common.enum.database import DatabaseTypeEnum
from llm.tongyi import TongyiModelManager
from utils.config import ConfigUtils


class PostgresVector:
    __config__ = ConfigUtils.get_config().database.get(DatabaseTypeEnum.POSTGRESQL)

    name = "rag_dev"

    @classmethod
    def create_vector(cls) -> VectorStore:
        return PGVector(
            collection_name=cls.name,
            embeddings=TongyiModelManager.create_dashscope_embedding(),
            connection=cls.__config__.url,
            use_jsonb=True,
            pre_delete_collection=False,
        )
