# coding: UTF-8
"""
@software: PyCharm
@author: Beau Dean
@contact: https://fairy.host
@organization: https://github.com/FairylandFuture
@datetime: 2026-04-27 20:08:55 UTC+08:00
"""

from __future__ import annotations

import typing as t
import uuid

from langchain_chroma import Chroma
from langchain_core.documents import Document
from langchain_core.prompt_values import PromptValue
from langchain_core.prompts import ChatPromptTemplate, MessagesPlaceholder
from langchain_core.runnables import RunnablePassthrough, RunnableLambda, RunnableParallel, RunnableConfig, AddableDict
from langchain_core.vectorstores import VectorStoreRetriever
from langchain_openai import ChatOpenAI

from llm.tongyi import TongyiLLMManager
from memory.history import FileChatMessageHistory
from vector.chroma import ChromaVectorStorageManager

__system_prompt = """
你是一个商品（衣物类）智能客服，根据知识库检索的信息片段，回答问题。
尺码推荐规则：
1. 当用户询问尺码时，需要同时考虑身高和体重两个维度。
2. 优先选择身高和体重都落在推荐范围内的尺码（身高范围正负5cm，体重正负5kg）。
3. 如果身高和体重分别匹配不同尺码，以体重作为主要参考依据，因为体重对合身程度影响更大。
4. 请逐一对比每个尺码区间，找出最佳匹配，而非仅匹配第一个符合的区间。
"""


def __parase_documents(documents: list[Document]):
    if not documents:
        return "没有找到相关信息"

    return ";".join([f"片段{index}：{document.page_content}" for index, document in enumerate(documents)])


def __parse_prompt_text(text: PromptValue) -> PromptValue:
    print("=" * 80)
    print(text.to_string())
    print("=" * 80)
    return text


def __parse_china_output(value: t.Any) -> t.Any:
    print(f"Parse China Output: {value}, type: {type(value)}")
    return value


def __parse_retriever_input_str(input: AddableDict) -> str:
    return input.get("input") or ""


def __parse_chat_prompt_input(input: AddableDict) -> dict[str, str | list[t.Any]]:
    return {
        "input": input.get("input", {}).get("input", ""),
        "history": input.get("input", {}).get("history", []),
        "knowledge": input.get("knowledge", ""),
    }


def wardrobe_mind_agent(input: t.Any):
    session_id = uuid.uuid4().hex.replace("-", "")
    print(f"session id: {session_id}")

    model: ChatOpenAI = TongyiLLMManager.create_chat_model()
    vector: Chroma = ChromaVectorStorageManager.get_chroma("wardrobe-mind")
    retriever: VectorStoreRetriever = vector.as_retriever(search_kwargs={"k": 5})
    input_retriever_format = RunnableLambda(lambda input: __parse_retriever_input_str(input))
    runnable_config: RunnableConfig = {"configurable": {"session_id": "a32f7991855e4b239d4b475c4882629b"}}
    input_prompt_format = RunnableLambda(lambda input: __parse_chat_prompt_input(input))

    input_prompt = ChatPromptTemplate.from_messages(
        [
            ("system", __system_prompt),
            ("human", "以下是历史消息"),
            MessagesPlaceholder("history"),
            ("human", "知识库检索信息片段内容：{knowledge}"),
            ("human", "{input}"),
        ]
    )

    input_chain: RunnableParallel[str] = RunnableParallel(
        input=RunnablePassthrough(),
        knowledge=input_retriever_format | retriever | RunnableLambda(lambda documents: __parase_documents(documents)),
    )

    parse_prompt_text = RunnableLambda(lambda text: __parse_prompt_text(text))

    chain = input_chain | input_prompt_format | input_prompt | parse_prompt_text | model

    runnable_history = FileChatMessageHistory.runnable(chain)

    for chunk in runnable_history.stream(input, config=runnable_config):
        print(chunk.content, end="", flush=True)


if __name__ == "__main__":
    wardrobe_mind_agent({"input": "春天穿什么颜色的衣服好看？"})
