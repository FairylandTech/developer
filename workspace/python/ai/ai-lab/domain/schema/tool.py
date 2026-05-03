# coding: UTF-8
"""
@software: PyCharm
@author: Beau Dean
@contact: https://fairy.host
@organization: https://github.com/FairylandFuture
@datetime: 2026-04-17 03:25:49 UTC+08:00
"""
from __future__ import annotations

import typing as t

from pydantic import Field

from domain.model import ModelBase


class Reference(ModelBase):
    title: str = Field(description="Web搜索结果的标题")
    url: str = Field(description="Web搜索结果的URL")


class TavilySearchToolOutput(ModelBase):
    answer: str = Field(None, description="Web搜索结果的摘要")
    references: t.List[Reference] = Field(None, description="Web搜索结果的参考文献列表")


class WeatherLiveToolInput(ModelBase):
    location: str = Field(description="地理位置，城市名称")
