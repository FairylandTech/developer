# coding: UTF-8
"""
@software: PyCharm
@author: Beau Dean
@contact: https://fairy.host
@organization: https://github.com/FairylandFuture
@datetime: 2026-04-25 23:07:39 UTC+08:00
"""
from __future__ import annotations

import typing as t

from langchain_community.document_loaders import CSVLoader


class DocumentCSVLoader:

    @classmethod
    def get_loader(cls, file_path) -> CSVLoader:
        return CSVLoader(
            file_path=file_path,
            csv_args={
                "delimiter": ",",  # 指定分隔符
                "quotechar": "\"",  # 指定带有分隔符文本的引号是单引号还是双引号
                # "fieldnames": [], # 指定表头，没有表头可以用 fieldnames 来指定表头
            },
            encoding="UTF-8",
        )
