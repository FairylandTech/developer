# coding: UTF-8
"""
@software: PyCharm
@author: Beau Dean
@contact: https://fairy.host
@organization: https://github.com/FairylandFuture
@datetime: 2026-04-26 17:08:51 UTC+08:00
"""

from __future__ import annotations

import typing as t

from langchain_core.vectorstores import InMemoryVectorStore

from llm.tongyi import TongyiModelManager

memory_vector_store = InMemoryVectorStore(embedding=TongyiModelManager.create_dashscope_embedding())
