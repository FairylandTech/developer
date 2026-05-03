# coding: UTF-8
"""
@software: PyCharm
@author: Beau Dean
@contact: https://fairy.host
@organization: https://github.com/FairylandFuture
@datetime: 2026-04-17 05:46:34 UTC+08:00
"""
from __future__ import annotations

import typing as t

from langchain_core.messages import BaseMessage

from domain.model.tools.web.search import TavilySearchResult


class AgentResult(t.TypedDict):
    message: t.Optional[list[BaseMessage]]
    structured_response: t.Optional[TavilySearchResult]
