# coding: UTF-8
"""
@software: PyCharm
@author: Beau Dean
@contact: https://fairy.host
@organization: https://github.com/FairylandFuture
@datetime: 2026-04-25 17:21:52 UTC+08:00
"""
from __future__ import annotations

import os
import pickle
import typing as t
import uuid

import orjson
from langchain_core.chat_history import InMemoryChatMessageHistory, BaseChatMessageHistory
from langchain_core.messages import BaseMessage, messages_to_dict, messages_from_dict
from langchain_core.runnables import RunnableSerializable
from langchain_core.runnables.history import RunnableWithMessageHistory

from common.const import ROOT_DIR


class TempChatMessageHistory:
    __TEMP_CONTEXT: dict[str, InMemoryChatMessageHistory] = {}

    @classmethod
    def __get_temp_history(cls, session_id: str) -> BaseChatMessageHistory:
        if session_id not in cls.__TEMP_CONTEXT:
            cls.__TEMP_CONTEXT[session_id] = InMemoryChatMessageHistory()

        history = cls.__TEMP_CONTEXT.get(session_id)

        if history:
            return history
        else:
            raise RuntimeError(f"Failed to get temp history for session ID: {session_id}")

    @classmethod
    def runnable(cls, chain: RunnableSerializable) -> RunnableWithMessageHistory:
        conversation = RunnableWithMessageHistory(
            chain,
            lambda session_id: cls.__get_temp_history(session_id),
            # input_messages_key="input",
            # history_messages_key="history",
        )

        return conversation


class FileChatMessageHistory(BaseChatMessageHistory):

    def __init__(self, session_id: str):
        self.__session_id = session_id

        self.__file_path = ROOT_DIR / "storage" / f"{session_id}.dat"
        self.__serialized_file_path = self.__file_path.with_suffix(".json")

        os.makedirs(os.path.dirname(self.__file_path), exist_ok=True)

    @property
    def messages(self) -> list[BaseMessage]:
        if not os.path.exists(self.__file_path):
            return []

        try:
            with open(self.__file_path, "rb") as file:
                content = pickle.load(file)
            return messages_from_dict(content)
        except Exception as error:
            print(f"Failed to load messages from {self.__file_path}: {error}")
            return []

    def add_messages(self, messages: t.Sequence[BaseMessage]) -> None:
        existing = self.messages
        existing.extend(messages)

        serialized = messages_to_dict(existing)

        with open(self.__file_path, "wb") as file:
            pickle.dump(serialized, file)

        with open(self.__serialized_file_path, "wb") as file:
            file.write(orjson.dumps(serialized))

    def clear(self) -> None:
        os.remove(self.__file_path)

    @classmethod
    def runnable(cls, chain: RunnableSerializable) -> RunnableWithMessageHistory:
        conversation = RunnableWithMessageHistory(
            chain,
            lambda session_id: cls(session_id),
            # input_messages_key="input",
            # history_messages_key="history",
        )

        return conversation
