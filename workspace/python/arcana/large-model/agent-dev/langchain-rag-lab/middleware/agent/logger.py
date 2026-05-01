# coding: UTF-8
"""
@software: PyCharm
@author: Beau Dean
@contact: https://fairy.host
@organization: https://github.com/FairylandFuture
@datetime: 2026-05-01 19:40:10 UTC+08:00
"""
from __future__ import annotations

import typing as t

from langchain.agents import AgentState
from langchain.agents.middleware import before_agent, after_agent
from langgraph.runtime import Runtime


@before_agent()
def log_before_agent(state: AgentState, runtime: Runtime):
    print(f"[Agent Log] Agent is about to execute. Current state: {state}, runtime: {runtime}")


@after_agent()
def log_after_agent(state: AgentState, runtime: Runtime):
    print(f"[Agent Log] Agent has finished executing. Current state: {state}, runtime: {runtime}")
