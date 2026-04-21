# coding: UTF-8
"""
@software: PyCharm
@author: Beau Dean
@contact: https://fairy.host
@organization: https://github.com/FairylandFuture
@datetime: 2026-04-18 22:30:36 UTC+08:00
"""
from __future__ import annotations

import typing as t

from langchain_core.messages import AIMessageChunk

from llm.factory import LLMFactory


class Movie(t.TypedDict):
    name: t.Annotated[str, "电影的名称"]
    year: t.Annotated[int, "电影的年份"]
    rating: t.Annotated[float, "电影的评分"]


def struct_output_typeddict():
    """
    结构化输出 typeddict

    :return:
    :rtype:
    """

    llm = LLMFactory.create("qwen")
    llm = llm.with_structured_output(Movie)

    response = llm.stream("介绍一下《肖申克的救赎》")
    for chunk in response:
        chunk: AIMessageChunk
        print(chunk.content, end="", flush=True)


if __name__ == '__main__':
    struct_output_typeddict()
