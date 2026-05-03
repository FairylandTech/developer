# coding: UTF-8
"""
@software: PyCharm
@author: Beau Dean
@contact: https://fairy.host
@organization: https://github.com/FairylandFuture
@datetime: 2026-05-02 14:21:05 UTC+08:00
"""

from __future__ import annotations

import typing as t
import warnings

from langchain_core.messages import AIMessage, SystemMessage, HumanMessage, AnyMessage, ToolMessage
from langchain_core.runnables import RunnableConfig
from langchain_core.tools import BaseTool
from langgraph.checkpoint.memory import MemorySaver
from langgraph.constants import END
from langgraph.graph import StateGraph
from langgraph.prebuilt import ToolNode
from pydantic import BaseModel

from graph.quickstart import quickstart_graph, StateQucikStartGraphMessage
from llm.deepseek import DeepSeekModelManager
from llm.doubao import DoubaoModelManager
from llm.hunyuan import HunYuanModelManager
from llm.kimi import KimiModelManager
from llm.mimo import MIMOModelManager
from llm.nvidia import NvidiaModelManager
from llm.siliconflow import SiliconFlowModelManager
from llm.tongyi import TongyiModelManager
from llm.zai import ZAIModelManager
from tools.weather import get_weather_live

warnings.filterwarnings("ignore")


class StateGraphMessage(BaseModel):
    messages: list[AnyMessage]


def test_run_graph():
    def should_call_tool(state: StateGraphMessage):
        messages = state.messages
        last_message = messages[-1]
        if isinstance(last_message, AIMessage) and last_message.tool_calls:
            return "tools"
        return END

    def call_model(state: StateGraphMessage):
        messages = state.messages
        result: AIMessage = model.invoke(messages)
        return StateGraphMessage(messages=[result])

    tools: list[BaseTool] = [get_weather_live]
    # model = TongyiModelManager.create_chat_model().bind_tools(tools)
    model = HunYuanModelManager.create_chat_model().bind_tools(tools)
    # model = MIMOModelManager.create_chat_model().bind_tools(tools)
    # model = SiliconFlowModelManager.create_chat_model().bind_tools(tools)
    # model = DoubaoModelManager.create_chat_model().bind_tools(tools)
    # model = NvidiaModelManager.create_chat_model().bind_tools(tools)
    checkpointer = MemorySaver()

    # node
    tool_node = ToolNode(tools)

    # graph
    workflow = StateGraph(StateGraphMessage)
    workflow.set_entry_point("model")
    # nodes
    workflow.add_node("model", call_model)
    workflow.add_node("tools", tool_node)
    # edges
    workflow.add_conditional_edges("model", should_call_tool, {"tools": "tools", END: END})
    workflow.add_edge("tools", "model")

    compile_graph = workflow.compile(checkpointer=checkpointer)
    with open("compiled_graph.png", "wb") as image:
        image.write(compile_graph.get_graph().draw_mermaid_png())

    input = {
        "messages": [
            SystemMessage("你是一个实时天气助手，能够根据用户输入的地理位置提供天气信息。参考可以调用工具的信息。"),
            HumanMessage("儋州天气怎么样？适合去哪里玩？"),
        ],
    }
    config: RunnableConfig = {
        "configurable": {
            "thread_id": 1,
        },
    }

    for chunk, metadata in compile_graph.stream(input, config=config, stream_mode="messages"):
        chunk: AnyMessage
        if isinstance(chunk, ToolMessage):
            continue
        print(chunk.content, end="", flush=True)


def test_graph():
    with open("quickstart_graph.png", "wb") as image:
        image.write(quickstart_graph.get_graph().draw_mermaid_png())

    result = quickstart_graph.invoke(StateQucikStartGraphMessage(x=2, y=3))
    print(result)


if __name__ == "__main__":
    test_run_graph()
    test_graph()
