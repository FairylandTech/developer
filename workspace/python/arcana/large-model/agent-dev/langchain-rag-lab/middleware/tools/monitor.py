# coding: UTF-8
"""
@software: PyCharm
@author: Beau Dean
@contact: https://fairy.host
@organization: https://github.com/FairylandFuture
@datetime: 2026-05-01 19:51:55 UTC+08:00
"""
from __future__ import annotations

import typing as t

from langchain.agents.middleware import wrap_tool_call
from langgraph.prebuilt.tool_node import ToolCallRequest


@wrap_tool_call()
def monitor_tools_hook(request: ToolCallRequest, handler: t.Callable[[ToolCallRequest], t.Any]):
    print(f"[Tool Monitor] Tool is about to execute. Request: {request}, Handler: {handler}")

    return handler(request)
