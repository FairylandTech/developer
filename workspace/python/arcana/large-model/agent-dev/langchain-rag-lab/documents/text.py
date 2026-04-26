# coding: UTF-8
"""
@software: PyCharm
@author: Beau Dean
@contact: https://fairy.host
@organization: https://github.com/FairylandFuture
@datetime: 2026-04-26 01:49:41 UTC+08:00
"""
from __future__ import annotations

import typing as t

from langchain_community.document_loaders import TextLoader


class DocumentTextLoader:

    @classmethod
    def get_loader(cls, file_path) -> TextLoader:
        return TextLoader(
            file_path=file_path,
            encoding="UTF-8"
        )
