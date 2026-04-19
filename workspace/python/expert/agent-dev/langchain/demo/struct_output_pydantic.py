# coding: UTF-8
"""
@software: PyCharm
@author: Lionel Johnson
@contact: https://fairy.host
@organization: https://github.com/FairylandFuture
@datetime: 2026-04-18 22:28:15 UTC+08:00
"""
from __future__ import annotations

import time
import typing as t

from langchain_core.messages import HumanMessage, SystemMessage, AIMessageChunk
from pydantic import Field

from domain.model import BaseModel
from llm.factory import LLMFactory


class OutputStructured(BaseModel):
    weather: str = Field(None, description="今天的天气")


def output_struct_pydanic():
    """
    结构化输出 pydantic

    :return:
    :rtype:
    """
    start_time = time.time()
    print(f"Starting output_struct_pydanic at {start_time}")

    llm = LLMFactory.create("qwen")
    llm = llm.with_structured_output(OutputStructured)

    input_message = [
        SystemMessage("你是一个的助手。JSON 结构化输出结果。没有匹配的字段为 null。"),
        HumanMessage("今天天气怎么样？")
    ]

    response = llm.stream(input_message)
    for chunk in response:
        chunk: AIMessageChunk
        print(chunk.content, end="", flush=True)
    end_time = time.time()
    print(f"output_struct_pydanic took {end_time - start_time:.2f} seconds")


if __name__ == '__main__':
    output_struct_pydanic()
