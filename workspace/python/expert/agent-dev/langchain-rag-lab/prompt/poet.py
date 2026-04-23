# coding: UTF-8
"""
@software: PyCharm
@author: Beau Dean
@contact: https://fairy.host
@organization: https://github.com/FairylandFuture
@datetime: 2026-04-23 09:59:44 UTC+08:00
"""
from __future__ import annotations

import typing as t

from langchain_core.messages import HumanMessage, SystemMessage, BaseMessage
from langchain_core.prompts import ChatPromptTemplate, MessagesPlaceholder


class PoetChatPrompt:

    def __init__(self, input: HumanMessage):
        self.__input: HumanMessage = input
        self.__messages: list[BaseMessage] = [
            SystemMessage("你是一个诗人。"),
            input,
        ]
        self.__chat_prompt_template: t.Optional[ChatPromptTemplate] = None

    @property
    def messages(self):
        return self.__messages

    def add_history(self, histories: list[BaseMessage]):
        self.__chat_prompt_template = ChatPromptTemplate.from_messages([*self.__messages, MessagesPlaceholder("history"), ])

        if self.__chat_prompt_template:
            self.__messages = self.__chat_prompt_template.invoke({"history": histories}).to_messages()
            self.__chat_prompt_template = None
