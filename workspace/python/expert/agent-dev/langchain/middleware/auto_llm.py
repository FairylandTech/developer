# coding: UTF-8
"""
@software: PyCharm
@author: Lionel Johnson
@contact: https://fairy.host
@organization: https://github.com/FairylandFuture
@datetime: 2026-04-19 12:46:03 UTC+08:00
"""
from __future__ import annotations

import typing as t

from langchain.agents.middleware import wrap_model_call, ModelRequest, ModelResponse
from langchain.agents.middleware.types import ResponseT, ContextT
from langchain_openai import ChatOpenAI


@wrap_model_call
def auto_llm(request: ModelRequest, handler: t.Callable[[ModelRequest[ContextT]], ModelResponse[ResponseT]]) -> ModelResponse:
    return handler(request)
