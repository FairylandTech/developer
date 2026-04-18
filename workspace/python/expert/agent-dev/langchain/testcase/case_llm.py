# coding: UTF-8
"""
@software: PyCharm
@author: Lionel Johnson
@contact: https://fairy.host
@organization: https://github.com/FairylandFuture
@datetime: 2026-04-17 04:08:05 UTC+08:00
"""
from __future__ import annotations

import typing as t

from langchain_core.messages import AIMessage, AIMessageChunk, SystemMessage, HumanMessage
from pydantic import Field

from domain.model import BaseModel
from llm.factory import LLMFactory


def output_struct_pydanic():
    """
    结构化输出 pydantic

    :return:
    :rtype:
    """

    class OutputStructured(BaseModel):
        weather: str = Field(None, description="今天的天气")

    llm = LLMFactory.create("qwen").with_structured_output(OutputStructured)

    input_message = [
        SystemMessage("你是一个的助手。JSON 结构化输出结果。没有匹配的字段为 null。"),
        HumanMessage("今天天气怎么样？")
    ]

    response = llm.invoke(input_message)
    print(response, type(response))


def creator_llm():
    response = LLMFactory.create("qwen").stream("你是谁？")

    for chunk in response:
        chunk: AIMessageChunk
        print(chunk.content, end="", flush=True)


if __name__ == '__main__':
    output_struct_pydanic()
    creator_llm()
