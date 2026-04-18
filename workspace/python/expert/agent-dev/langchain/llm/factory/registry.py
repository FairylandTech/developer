# coding: UTF-8
"""
@software: PyCharm
@author: Lionel Johnson
@contact: https://fairy.host
@organization: https://github.com/FairylandFuture
@datetime: 2026-04-18 00:44:16 UTC+08:00
"""
from __future__ import annotations

import typing as t

from llm.factory import LLMCreator


class LLMFactoryRegistry:
    __llm__: dict[str, t.Type[LLMCreator]] = {}

    @classmethod
    def register(cls, provider: str, creator: t.Type[LLMCreator]):
        cls.__llm__[provider.lower()] = creator

    @classmethod
    def get_creator(cls, provider: str) -> t.Type[LLMCreator]:
        provider = provider.lower()

        if provider not in cls.__llm__:
            raise ValueError(f"不支持的厂商: {provider}。支持的厂商: {list(cls.__llm__.keys())}")

        return cls.__llm__[provider]
