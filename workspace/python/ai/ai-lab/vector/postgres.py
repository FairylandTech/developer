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
from domain.model.common.config import DatabaseConfig
from llm.tongyi import TongyiModelManager
from utils.config import ConfigUtils


class PostgresVector:
    __config__: t.ClassVar[DatabaseConfig] = ConfigUtils.get_config().database.get(DatabaseTypeEnum.POSTGRESQL)

    name: t.ClassVar[str] = "rag_dev"

    @classmethod
    def create_vector(cls, collection_name: str = None, pre_delete_collection: bool = True) -> VectorStore:
        if not collection_name:
            collection_name = cls.name

        return PGVector(
            collection_name=collection_name,
            embeddings=TongyiModelManager.create_dashscope_embedding(),
            connection=cls.__config__.url,
            use_jsonb=True,
            pre_delete_collection=pre_delete_collection,
        )
