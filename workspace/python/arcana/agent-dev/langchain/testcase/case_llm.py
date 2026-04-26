# coding: UTF-8
"""
@software: PyCharm
@author: Beau Dean
@contact: https://fairy.host
@organization: https://github.com/FairylandFuture
@datetime: 2026-04-17 04:08:05 UTC+08:00
"""
from __future__ import annotations

import time
import typing as t

from langchain_core.messages import AIMessageChunk, HumanMessage, BaseMessage
from pydantic import Field
from langchain.chat_models import init_chat_model
from domain.model import BaseModel
from langchain.agents import create_agent
from domain.model.agent.message import InputMessages

from llm.factory import LLMFactory
from llm.config import LLMConfig
from middleware.auto_llm import auto_llm
from tools.weather import get_weather_live


def creator_llm():
    start_time = time.time()
    print(f"Starting creator_llm at {start_time}")

    response = LLMFactory.create("qwen").stream("你是谁？")

    for chunk in response:
        chunk: AIMessageChunk
        print(chunk.content, end="", flush=True)
    end_time = time.time()
    print(f"\ncreator_llm took {end_time - start_time:.2f} seconds")


def output_struct_pydanic():
    """
    结构化输出 pydantic

    :return:
    :rtype:
    """
    start_time = time.time()
    print(f"Starting output_struct_pydanic at {start_time}")

    class OutputStructured(BaseModel):
        weather: str = Field(None, description="今天的天气")

    llm = LLMFactory.create("qwen")
    # llm = llm.with_structured_output(OutputStructured)

    input_message = [
        # SystemMessage("你是一个的助手。JSON 结构化输出结果。没有匹配的字段为 null。"),
        HumanMessage("今天天气怎么样？")
    ]

    response = llm.stream(input_message)
    # print(response, type(response))
    for chunk in response:
        chunk: AIMessageChunk
        print(chunk.content, end="", flush=True)
    end_time = time.time()
    print(f"output_struct_pydanic took {end_time - start_time:.2f} seconds")


def llm_call_tools():
    """
    LLM 调用工具

    :return:
    :rtype:
    """

    llm = init_chat_model(
        model=LLMConfig.DefaultModel.DASHSCOPE,
        model_provider="openai",
        api_key=LLMConfig.DASHSCOPE_API_KEY,
        base_url=LLMConfig.DASHSCOPE_BASE_URL
    )

    llm_with_tools = llm.bind_tools([get_weather_live, ])

    message: list[BaseMessage] = [HumanMessage("今天北京天气怎么样？")]

    response = llm_with_tools.invoke(message)
    message.append(response)

    for tool_call in response.tool_calls:
        if tool_call.get("name") == "get_weather_live":
            tool_result = get_weather_live.invoke(tool_call)
            message.append(tool_result)

    result = llm_with_tools.invoke(message)

    result.pretty_print()


def agent_call_tools():
    """
    Agent 调用工具

    :return:
    :rtype:
    """

    llm = LLMFactory.create("qwen")

    agent = create_agent(
        model=llm,
        tools=[
            get_weather_live,
        ]
    )

    input: InputMessages = InputMessages(
        messages=[
            HumanMessage("今天长沙天气怎么样？")
        ]
    )

    result = agent.invoke(input)

    result_messages: list[BaseMessage] = result.get("messages", [])

    for message in result_messages:
        message.pretty_print()


def agent_call_middleware():
    """
    Agent 调用中间件。

    :return:
    :rtype:
    """

    llm = LLMFactory.create("qwen")

    agent = create_agent(
        model=llm,
        tools=[

        ],
        middleware=[
            auto_llm
        ]
    )

    input: InputMessages = InputMessages(
        messages=[
            HumanMessage("你是谁？")
        ]
    )

    result = agent.invoke(input)

    result_messages: list[BaseMessage] = result.get("messages", [])

    for message in result_messages:
        message.pretty_print()


if __name__ == '__main__':
    start_timestamp = time.time()

    # creator_llm()
    # output_struct_pydanic()
    # llm_call_tools()
    # agent_call_tools()
    agent_call_middleware()

    print(f"测试 LLM 耗时：{time.time() - start_timestamp:.2f} 秒")
