# coding: UTF-8
"""
@software: PyCharm
@author: Beau Dean
@contact: https://fairy.host
@organization: https://github.com/FairylandFuture
@datetime: 2026-05-02 22:56:00 UTC+08:00
"""

from __future__ import annotations

import typing as t

from langgraph.constants import START, END
from langgraph.graph import StateGraph
from pydantic import BaseModel
import math


class StateQucikStartGraphMessage(BaseModel):
    x: int | float
    y: int | float


def node_func(state: StateQucikStartGraphMessage):
    return StateQucikStartGraphMessage(x=state.x * 2, y=math.pow(state.y, 2))


workflow = StateGraph(StateQucikStartGraphMessage)
workflow.add_node("node1", node_func)
workflow.add_node("node2", node_func)
workflow.add_edge(START, "node1")
workflow.add_edge("node1", "node2")
workflow.add_edge("node2", END)

quickstart_graph = workflow.compile()
