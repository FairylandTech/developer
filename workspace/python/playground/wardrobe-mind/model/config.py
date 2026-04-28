# coding: UTF-8
"""
@software: PyCharm
@author: Beau Dean
@contact: https://fairy.host
@organization: https://github.com/FairylandFuture
@datetime: 2026-04-27 12:10:45 UTC+08:00
"""

from __future__ import annotations

import os
import typing as t

from pydantic import BaseModel, Field, field_validator

from common import ROOT_DIR


class ConfigModelBase(BaseModel):

    @classmethod
    def _fix_path(cls, v: str) -> str:
        if not v:
            return v
        abspath = os.path.join(ROOT_DIR, v) if not os.path.isabs(v) else v
        os.makedirs(os.path.dirname(abspath), exist_ok=True)
        return abspath


class ChromaConfig(ConfigModelBase):
    path: str = Field("", description="Chroma文件路径")

    @field_validator("path", mode="before")
    @classmethod
    def fix_path(cls, v: str) -> str:
        return cls._fix_path(v)


class SplitterConfig(BaseModel):
    chunk: int = Field(100, description="Chunk大小")
    overlap: int = Field(2, description="Overlap大小")
    separator: list[str] = Field([], description="分隔符")
    max_char: int = Field(1000, description="最大字符数")


class Config(ConfigModelBase):
    md5_path: str = Field("", description="MD5文件路径")
    chroma: ChromaConfig = Field(ChromaConfig(), description="Chroma配置")
    splitter: SplitterConfig = Field(SplitterConfig(), description="Splitter配置")

    @field_validator("md5_path", mode="before")
    @classmethod
    def fix_md5_path(cls, v: str) -> str:
        return cls._fix_path(v)
