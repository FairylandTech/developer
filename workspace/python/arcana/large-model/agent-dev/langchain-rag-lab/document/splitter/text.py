# coding: UTF-8
"""
@software: PyCharm
@author: Beau Dean
@contact: https://fairy.host
@organization: https://github.com/FairylandFuture
@datetime: 2026-04-26 01:53:47 UTC+08:00
"""
from __future__ import annotations

import typing as t

from langchain_text_splitters import RecursiveCharacterTextSplitter


class TextSplitter:

    @classmethod
    def get_splitter(cls) -> RecursiveCharacterTextSplitter:
        return RecursiveCharacterTextSplitter(
            chunk_size=500,
            chunk_overlap=50,
            separators=["\n\n", "\n", " ", "", "。", "，", "？", "！", ".", "?", "!"],
            length_function=len,
        )
