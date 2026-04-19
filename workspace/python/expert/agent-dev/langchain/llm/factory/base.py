# coding: UTF-8
"""
@software: PyCharm
@author: Lionel Johnson
@contact: https://fairy.host
@organization: https://github.com/FairylandFuture
@datetime: 2026-04-17 23:36:48 UTC+08:00
"""
from __future__ import annotations

import typing as t
from abc import ABC, abstractmethod

from langchain_core.language_models import BaseChatModel


class LLMCreator(ABC):

    @abstractmethod
    def create(self, **kwargs) -> BaseChatModel:
        pass
