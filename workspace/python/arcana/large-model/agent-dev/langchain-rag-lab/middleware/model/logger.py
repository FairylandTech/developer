# coding: UTF-8
"""
@software: PyCharm
@author: Beau Dean
@contact: https://fairy.host
@organization: https://github.com/FairylandFuture
@datetime: 2026-05-01 19:46:20 UTC+08:00
"""
from __future__ import annotations

import typing as t

from langchain.agents import AgentState
from langchain.agents.middleware import before_model, after_model, wrap_model_call, ModelRequest
from langgraph.runtime import Runtime


@before_model()
def log_before_model(state: AgentState, runtime: Runtime):
    print(f"[Model Log] Model is about to execute. Current state: {state}, runtime: {runtime}")


@after_model()
def log_after_model(state: AgentState, runtime: Runtime):
    print(f"[Model Log] Model has finished executing. Current state: {state}, runtime: {runtime}")


@wrap_model_call()
def model_hook(request: ModelRequest, handler: t.Callable[[ModelRequest], t.Any]):
    print(f"[Model Hook] request type: {type(request)}, content: {request}")
    print(f"[Model Hook] handler type: {type(handler)}, content: {handler}")

    return handler(request)
