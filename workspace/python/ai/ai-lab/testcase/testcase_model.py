# coding: UTF-8
"""
@software: PyCharm
@author: Beau Dean
@contact: https://fairy.host
@organization: https://github.com/FairylandFuture
@datetime: 2026-04-21 23:17:09 UTC+08:00
"""
from __future__ import annotations

import copy
import pickle
import typing as t
import uuid

from langchain_core.language_models import LanguageModelInput
from langchain_core.messages import HumanMessage, SystemMessage, BaseMessage, AIMessageChunk, AIMessage
from langchain_core.output_parsers import StrOutputParser, JsonOutputParser
from langchain_core.prompts import ChatPromptTemplate, MessagesPlaceholder, PromptTemplate
from langchain_core.runnables import RunnableLambda, RunnableGenerator, RunnableConfig

from common.enum.gender import GenderEnum
from memory.history import TempChatMessageHistory, FileChatMessageHistory
from prompt.antonym import antonym_prompt
from prompt.generate_name import generate_name_prompt
from prompt.poet import PoetChatPrompt
from llm.tongyi import TongyiModelManager
from llm.hunyuan import hunyuan_chat_model
from llm.zai import zai_chat_model, zai_embedding_model, zai_embedding


def invoke_llm(prompt: str):
    """ 阻塞调用 LLM """
    print(TongyiModelManager.create_llm().invoke(prompt))


def stream_llm(prompt: str):
    """ 流式调用 LLM """
    output_stream = TongyiModelManager.create_llm().stream(prompt)

    for chunk in output_stream:
        print(chunk, end="", flush=True)


def invoke_chat(input: LanguageModelInput):
    """ 阻塞调用 ChatLLM """
    output_messages = TongyiModelManager.create_chat_model().invoke(input)
    output_messages.pretty_print()


def stream_chat(input: LanguageModelInput):
    """ 流式调用 ChatLLM """
    output: t.Iterator[AIMessageChunk] = zai_chat_model.stream(input)

    for chunk in output:
        print(chunk.content, end="", flush=True)


def enbed_query():
    """ 向量转化 """
    result = zai_embedding.embed_query("我喜欢你")
    print(result)


def use_prompt_template():
    """ 使用 prompt 基础模版 """
    for chunk in zai_chat_model.stream(generate_name_prompt(GenderEnum.FEMALE)):
        print(chunk.content, end="", flush=True)


def antonym(word: str):
    """ 使用 Few shot 模版 """
    input = antonym_prompt(word)
    print(input)

    for chunk in zai_chat_model.stream(input):
        print(chunk.content, end="", flush=True)


def poet_prompt(input: str):
    """ 使用 ChatPrompt 模版 """
    prompts = PoetChatPrompt(HumanMessage(input))
    model = copy.deepcopy(zai_chat_model)

    chunks: list[str] = []
    for chunk in model.stream(prompts.messages):
        content: str = t.cast(str, chunk.content)
        print(content, end="", flush=True)
        chunks.append(content)

    print()
    print("=" * 50)

    prompts.add_history([AIMessage("".join(chunks)), HumanMessage("请在写一首")])

    for chunk in model.stream(prompts.messages):
        print(chunk.content, end="", flush=True)


def use_chain():
    """ 使用 Chain """

    def transform(message: AIMessage):
        print(message, type(message))
        return [
            SystemMessage("你是一个诗人，请润色以下诗歌"),
            HumanMessage(message.content)
        ]

    input_prompt: ChatPromptTemplate = ChatPromptTemplate.from_messages([
        SystemMessage("你是一个诗人"),
        MessagesPlaceholder("input")
    ])
    model = copy.deepcopy(zai_chat_model)

    chain = input_prompt | model | RunnableLambda(lambda message: transform(message)) | model

    for chunk in chain.stream(input={"input": [HumanMessage("写一首诗"), ]}):
        print(chunk.content, end="", flush=True)


def use_chain_stream():
    """ 使用 Chain 流式获取已经调用模型的结果 """

    def transform(chunks: t.Iterator[AIMessageChunk]) -> t.Iterator[list[BaseMessage]]:
        contents: list[str] = []
        for chunk in chunks:
            content = t.cast(str, chunk.content)
            print(content, end="", flush=True)
            contents.append(content)
        print()
        print("=" * 80)

        yield [
            SystemMessage("你是一个诗人，请润色以下诗歌"),
            HumanMessage("".join(contents))
        ]

    input_prompt: ChatPromptTemplate = ChatPromptTemplate.from_messages([
        SystemMessage("你是一个诗人"),
        MessagesPlaceholder("input")
    ])
    model = copy.deepcopy(zai_chat_model)

    chain = input_prompt | model | RunnableGenerator(transform) | model

    for chunk in chain.stream(input={"input": [HumanMessage("写一首诗"), ]}):
        print(chunk.content, end="", flush=True)


def test_case_parser_aimessage():
    """ 使用 Chain 多次调用模型，使用 StrOutputParseer 解析结果 """
    input_prompt: ChatPromptTemplate = ChatPromptTemplate.from_messages([
        SystemMessage("你是一个诗人"),
        MessagesPlaceholder("input")
    ])
    model = copy.deepcopy(zai_chat_model)
    parser = StrOutputParser()

    chain = input_prompt | model | parser | model

    for chunk in chain.stream(input={"input": [HumanMessage("写一首诗"), ]}):
        print(chunk.content, end="", flush=True)


def test_case_json_parser_aimessage():
    """ 使用 Chain 多次调用模型，使用 JsonOutputParseer 解析结果 """
    # model = copy.deepcopy(tongyi_chat_model)
    model = TongyiModelManager.create_chat_model()
    parser = JsonOutputParser()

    input_prompt: ChatPromptTemplate = ChatPromptTemplate.from_messages([
        SystemMessage("你是一个起名专家"),
        HumanMessage(
            "我的朋友姓{lastname}，刚刚生了一个{gender}，请帮他给他女儿起一个中文名字。只输出最后的结果，分装为 JSON 格式，{“name”：“你起的名字”}，严格遵守。")
    ])
    step2_prompt = PromptTemplate.from_template("名字：{name}，帮我解释一下这个名字的含义")

    chain = input_prompt | model | parser | step2_prompt | model

    for chunk in chain.stream(input={"lastname": "李", "gender": "女儿"}):
        print(chunk.content, end="", flush=True)


def test_chain_runnablelambda():
    """ 使用 Chain 调用 RunnableLambda """

    def transform(message: AIMessage):
        prompt: ChatPromptTemplate = ChatPromptTemplate.from_messages([
            AIMessage(message.content),
            HumanMessage("请帮我解释一下这个名字的含义")
        ])

        return prompt.messages

    model = TongyiModelManager.create_chat_model()

    input_prompt: ChatPromptTemplate = ChatPromptTemplate.from_template(
        "我的朋友姓{lastname}，刚刚生了一个{gender}，请帮他给他女儿起一个中文名字。只输出名字")

    transform_messages = RunnableLambda(lambda message: transform(message))

    chain = input_prompt | model | transform_messages | model | StrOutputParser()

    for chunk in chain.stream({"lastname": "李", "gender": "女儿"}):
        print(chunk, end="", flush=True)


def test_chain_add_history():
    """ 使用 Chain 调用并添加短期的（临时）历史记录 """

    def pretty_print(message: t.Iterator[AIMessageChunk]):
        for chunk in message:
            print(chunk.content, end="", flush=True)
        print()
        print("=" * 80)

    # temp_chain = TempChatMessageHistory.runnable(tongyi_chat_model)
    chain = FileChatMessageHistory.runnable(TongyiModelManager.create_chat_model())

    config: RunnableConfig = {
        "configurable": {
            "session_id": uuid.uuid4().hex.replace("-", "")
        }
    }

    pretty_print(chain.stream("小明有一只猫", config))
    pretty_print(chain.stream("小明有两条狗", config))
    pretty_print(chain.stream("一共有多少宠物", config))


if __name__ == '__main__':
    user_prompt = "你是谁，可以做什么？"

    input_messages: t.List[BaseMessage] = [
        SystemMessage("你是一个智能AI助手。"),
        HumanMessage("写一首诗。")
    ]

    # invoke_llm(user_prompt)
    # stream_llm(user_prompt)
    # stream_chat(input_messages)
    # enbed_query()
    # use_prompt_template()
    # antonym("做")
    # poet_prompt("写一首边塞诗")
    # use_chain()
    # use_chain_stream()
    # test_case_parser_aimessage()
    # test_case_json_parser_aimessage()
    # test_chain_runnablelambda()
    test_chain_add_history()
