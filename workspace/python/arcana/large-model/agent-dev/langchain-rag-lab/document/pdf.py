# coding: UTF-8
"""
@software: PyCharm
@author: Beau Dean
@contact: https://fairy.host
@organization: https://github.com/FairylandFuture
@datetime: 2026-04-25 23:45:06 UTC+08:00
"""
from __future__ import annotations

import typing as t

from langchain_community.document_loaders import PyPDFLoader


class DocumentPDFLoader:

    @classmethod
    def get_loader(cls, file_path: str):
        return PyPDFLoader(
            file_path=file_path,  # 文件路径
            mode="page",  # 读取模式，page 表示按页读取划分不同的 Document；single 表示整个文件读取为一个 Document
            # password=""  # 文件密码
        )
