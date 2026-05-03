# coding: UTF-8
"""
@software: PyCharm
@author: Beau Dean
@contact: https://fairy.host
@organization: https://github.com/FairylandFuture
@datetime: 2026-04-25 23:45:00 UTC+08:00
"""
from __future__ import annotations

import typing as t

from langchain_community.document_loaders import JSONLoader


class DocumentJSONLoader:

    @classmethod
    def get_loader(cls, file_path, json_lines: bool = False) -> JSONLoader:
        return JSONLoader(
            file_path=file_path,  # 文件路径
            jq_schema=".",  # jq schema 语法
            text_content=False,  # 抽取的是否是字符串, 默认是 True
            json_lines=json_lines,  # 是否是 JSONLines 文件（每一行都是 JSON 的文件）
        )
