# coding: UTF-8
"""
@software: PyCharm
@author: Beau Dean
@contact: https://fairy.host
@organization: https://github.com/FairylandFuture
@datetime: 2026-04-27 16:46:18 UTC+08:00
"""

from __future__ import annotations

import os
import typing as t

from langchain_chroma import Chroma

from llm.tongyi import TongyiLLMManager
from model.config import ChromaConfig
from utils.config import ConfigUtils


class ChromaVectorStorageManager:
    config: ChromaConfig = ConfigUtils.get_config().chroma

    @classmethod
    def get_chroma(cls, collection_name: str) -> Chroma:
        return Chroma(
            collection_name=collection_name,
            embedding_function=TongyiLLMManager.create_embedding_model(),
            persist_directory=os.path.join(cls.config.path, collection_name),
        )
