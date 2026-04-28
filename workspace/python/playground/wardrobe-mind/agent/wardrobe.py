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

from langchain_chroma import Chroma
from langchain_core.documents import Document
from langchain_core.prompt_values import PromptValue
from langchain_core.prompts import ChatPromptTemplate
from langchain_core.runnables import RunnablePassthrough, RunnableLambda, RunnableParallel
from langchain_core.vectorstores import VectorStoreRetriever
from langchain_openai import ChatOpenAI

from llm.tongyi import TongyiLLMManager
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


def wardrobe_mind_agent(input: str):
    model: ChatOpenAI = TongyiLLMManager.create_chat_model("qwen3-max")
    vector: Chroma = ChromaVectorStorageManager.get_chroma("wardrobe-mind")
    retriever: VectorStoreRetriever = vector.as_retriever(search_kwargs={"k": 5})

    input_prompt = ChatPromptTemplate.from_messages(
        [
            ("system", __system_prompt),
            ("human", "知识库检索信息片段内容：{knowledge}"),
            ("human", "{input}"),
        ]
    )

    input_chain: RunnableParallel[str] = RunnableParallel(
        input=RunnablePassthrough(),
        knowledge=retriever | RunnableLambda(lambda documents: __parase_documents(documents)),
    )

    parse_prompt_text = RunnableLambda(lambda text: __parse_prompt_text(text))

    chain = input_chain | input_prompt | parse_prompt_text | model

    for chunk in chain.stream(input):
        print(chunk.content, end="", flush=True)


if __name__ == "__main__":
    wardrobe_mind_agent("我身高170cm，体重65kg，穿什么码的衣服")
