# coding: UTF-8
"""
@software: PyCharm
@author: Beau Dean
@contact: https://fairy.host
@organization: https://github.com/FairylandFuture
@datetime: 2026-04-26 20:31:19 UTC+08:00
"""

from __future__ import annotations

import typing as t

from langchain_chroma import Chroma
from langchain_core.documents import Document
from langchain_core.language_models import BaseChatModel
from langchain_core.messages import SystemMessage, HumanMessage, AIMessage, AIMessageChunk
from langchain_core.output_parsers import StrOutputParser
from langchain_core.prompt_values import PromptValue
from langchain_core.prompts import ChatPromptTemplate
from langchain_core.runnables import RunnableLambda, RunnableSerializable, RunnablePassthrough, RunnableParallel
from langchain_core.vectorstores import VectorStore
from langchain_openai import ChatOpenAI

from llm.hunyuan import HunYuanModelManager
from llm.tongyi import TongyiModelManager
from vector.chroma import ChromaVectorStoreManager
from vector.postgres import PostgresVector


def add_documents_to_vector():
    vector = ChromaVectorStoreManager.create_vector_store()
    # vector: VectorStore = PostgresVector.create_vector()

    vector.add_texts(["减肥就是要少吃多练", "在减脂期间吃东西很重要，清淡少油控制卡路里摄入并运动起来", "跑步是很好的运动"])


def testcase_rag():
    def pretty_print(prompt: PromptValue):
        print("=" * 80)
        print(prompt.to_string())
        print("=" * 80)
        return prompt

    def parse_docs(documents: list[Document]) -> str:
        print(f"documents: {documents}")
        if not documents:
            return "无相关参考资料"

        context: str = "[{}]".format(",".join([doc.page_content for doc in documents]))
        return context

    model: BaseChatModel = TongyiModelManager.create_chat_model()
    vector: VectorStore = ChromaVectorStoreManager.create_vector_store()
    # vector: VectorStore = PostgresVector.create_vector()
    retriever = vector.as_retriever(search_type="mmr", search_kwargs={"k": 1})

    prompt: ChatPromptTemplate = ChatPromptTemplate.from_messages(
        [
            ("system", "以我提供的参考资料为主，简介和专业的回答我的问题。参考资料：{context}"),
            ("human", "{input}"),
        ]
    )

    input_text: str = "怎么减肥"

    # vector_search_result: list[Document] = vector.similarity_search(input_text, 5)
    # context: str = "[{}]".format(",".join([doc.page_content for doc in vector_search_result]))

    input_chain: RunnableParallel[str] = RunnableParallel(input=RunnablePassthrough(), context=retriever | RunnableLambda(lambda documents: parse_docs(documents)))

    chain: RunnableSerializable[str, AIMessage] = input_chain | prompt | RunnableLambda(lambda prompt: pretty_print(prompt)) | model

    for chunk in chain.stream(input=input_text):
        print(chunk.content, end="", flush=True)


if __name__ == "__main__":
    add_documents_to_vector()
    testcase_rag()
