# coding: UTF-8
"""
@software: PyCharm
@author: Lionel Johnson
@contact: https://fairy.host
@organization: https://github.com/FairylandFuture
@datetime: 2026-04-17 06:41:07 UTC+08:00
"""
from __future__ import annotations

import typing as t
import os
from pydantic import SecretStr

from langchain_openai import ChatOpenAI

ZAI_API_URL = os.getenv("ZAI_API_URL", "https://open.bigmodel.cn/api/paas/v4")
ZAI_API_KEY = os.getenv("ZAI_API_KEY")

zai_llm = ChatOpenAI(
    model="glm-4.5-air",
    base_url="https://open.bigmodel.cn/api/paas/v4",
    api_key=SecretStr(ZAI_API_KEY) if ZAI_API_KEY else None,
    temperature=0.6,
)

zai_image = ChatOpenAI(
    model="glm-4.5-air",
    openai_api_base="https://open.bigmodel.cn/api/paas/v4",
    api_key=SecretStr(ZAI_API_KEY) if ZAI_API_KEY else None,
    temperature=0.6,
)
