# coding: UTF-8
"""
@software: PyCharm
@author: Beau Dean
@contact: https://fairy.host
@organization: https://github.com/FairylandFuture
@datetime: 2026-05-03 00:05:20 UTC+08:00
"""

from __future__ import annotations

import re
import typing as t

from pydantic import Field

from common.enum.database import DatabaseTypeEnum
from domain.model import BaseModel


class DatabaseConfig(BaseModel):
    type: DatabaseTypeEnum = Field(default=DatabaseTypeEnum.MYSQL, description="Database type")
    host: str = Field(default="localhost", description="Database host")
    port: int = Field(default=3306, description="Database port")
    user: str = Field(default="root", description="Database user")
    password: str = Field(default="", description="Database password")
    database: str = Field(default="db", description="Database name")

    @property
    def url(self) -> str:
        return f"{self.type.value}+psycopg://{self.user}:{self.password}@{self.host}:{self.port}/{self.database}"

    def __repr__(self) -> str:
        result = super().__repr__()
        return re.sub(r"password='.*?'", "password='****'", result)

    def __str__(self) -> str:
        return self.__repr__()


class SplitterConfig(BaseModel):
    chunk_size: int = Field(default=100, description="Chunk size for splitting")
    chunk_overlap: int = Field(default=20, description="Chunk overlap for splitting")
    max_length: int = Field(default=256, description="Maximum length for splitting")


class Config(BaseModel):
    database: t.Optional[dict[DatabaseTypeEnum, DatabaseConfig]] = Field(default=None, description="Database configuration")
    splitter: t.Optional[SplitterConfig] = Field(default=None, description="Splitter configuration")

    @classmethod
    def __get_database_type(cls, t: str) -> DatabaseTypeEnum:
        result = DatabaseTypeEnum.from_value(t)
        if not result:
            raise ValueError(f"Unsupported database type: {t}")
        return result

    @classmethod
    def from_dict(cls, data: dict[str, t.Any]) -> Config:
        database_config = {cls.__get_database_type(key): DatabaseConfig(type=cls.__get_database_type(key), **value) for key, value in data.get("database", {}).items()}
        splitter_config = SplitterConfig(**data.get("splitter", {})) if "splitter" in data.keys() else SplitterConfig()
        return cls(database=database_config, splitter=splitter_config)
