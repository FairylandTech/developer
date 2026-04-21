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

from domain.model import BaseModel


class WeatherLiveToolsInput(BaseModel):
    location: str = Field(description="地理位置，城市名称")
