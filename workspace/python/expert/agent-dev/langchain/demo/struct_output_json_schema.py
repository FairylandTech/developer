# coding: UTF-8
"""
@software: PyCharm
@author: Lionel Johnson
@contact: https://fairy.host
@organization: https://github.com/FairylandFuture
@datetime: 2026-04-18 22:40:19 UTC+08:00
"""
from __future__ import annotations

import typing as t

from langchain_core.messages import AIMessageChunk

from llm.factory import LLMFactory

json_schema = {
    "title": "movie",
    "description": "A movie info",
    "type": "object",
    "properties": {
        "name": {"type": "string", "description": "The name of the movie"},
        "year": {"type": "integer", "description": "The year the movie was released"},
        "rating": {"type": "number", "description": "The rating of the movie"}
    },
    "required": ["name", "year", "rating"]
}


def struct_output_json_schema():
    """
    结构化输出 json schema

    :return:
    :rtype:
    """
    llm = LLMFactory.create("qwen")
    llm = llm.with_structured_output(json_schema)

    response = llm.stream("介绍一下《肖申克的救赎》")
    for chunk in response:
        chunk: AIMessageChunk
        print(chunk.content, end="", flush=True)


if __name__ == '__main__':
    struct_output_json_schema()
