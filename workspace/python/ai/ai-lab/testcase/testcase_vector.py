# coding: UTF-8
"""
@software: PyCharm
@author: Beau Dean
@contact: https://fairy.host
@organization: https://github.com/FairylandFuture
@datetime: 2026-04-26 17:15:23 UTC+08:00
"""
from __future__ import annotations

import typing as t

from langchain_core.documents import Document

from common.const import ROOT_DIR
from vector.memory import memory_vector_store
from document.csv import CSVLoader
from vector.chroma import ChromaVectorStoreManager


def testcase_inmemory_vector():
    loader = CSVLoader(ROOT_DIR / "resources" / "info.csv")

    documents: list[Document] = loader.load()
    memory_vector_store.add_documents(documents, ids=[f"id-{i}" for i in range(len(documents))])

    result: list[Document] = memory_vector_store.similarity_search("Python 是不是容易学", 3)

    for doc in result:
        print(doc)

def testcase_chroma_vector():
    loader = CSVLoader(ROOT_DIR / "resources" / "info.csv")

    documents: list[Document] = loader.load()
    vector = ChromaVectorStoreManager.create_vector_store()

    # vector.add_documents(documents, ids=[f"id-{i}" for i in range(len(documents))])

    result: list[Document] = vector.similarity_search("Python 是不是容易学", 3)

    for doc in result:
        print(doc)


if __name__ == '__main__':
    # testcase_inmemory_vector()
    testcase_chroma_vector()
