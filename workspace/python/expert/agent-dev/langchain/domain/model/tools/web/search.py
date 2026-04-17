# coding: UTF-8
"""
@software: PyCharm
@author: Lionel Johnson
@contact: https://fairy.host
@organization: https://github.com/FairylandFuture
@datetime: 2026-04-17 05:17:22 UTC+08:00
"""
from __future__ import annotations

import typing as t

from domain.model import BaseModel
from pydantic import Field


class Reference(BaseModel):
    title: str = Field(description="Web搜索结果的标题")
    url: str = Field(description="Web搜索结果的URL")


class TavilySearchResult(BaseModel):
    answer: str = Field(None, description="Web搜索结果的摘要")
    references: t.List[Reference] = Field(None, description="Web搜索结果的参考文献列表")
