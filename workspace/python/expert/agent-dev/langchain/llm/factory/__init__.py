# coding: UTF-8
"""
@software: PyCharm
@author: Lionel Johnson
@contact: https://fairy.host
@organization: https://github.com/FairylandFuture
@datetime: 2026-04-17 23:40:53 UTC+08:00
"""
from __future__ import annotations

import time
import typing as t

from langchain_core.language_models import BaseLLM

from llm.factory.base import LLMCreator
from llm.factory.registry import LLMFactoryRegistry
from llm.factory.deepseek import DeepSeekLLMCreator
from llm.factory.zai import ZAILLMCreator
from llm.factory.qwen import QwenLLMCreator
from llm.factory.doubao import DoubaoLLMCreator
from llm.factory.hunyuan import HunYuanLLMCreator

# LLM Registry
LLMFactoryRegistry.register("deepseek", DeepSeekLLMCreator)
LLMFactoryRegistry.register("zai", ZAILLMCreator)
LLMFactoryRegistry.register("qwen", QwenLLMCreator)
LLMFactoryRegistry.register("ark", DoubaoLLMCreator)
LLMFactoryRegistry.register("hunyuan", HunYuanLLMCreator)

_T_Porovider: t.TypeAlias = t.Literal["deepseek", "qwen", "zai", "ark", "hunyuan",]


# LLM Factory
class LLMFactory:

    @classmethod
    def create(cls, provider: str | _T_Porovider, **kwargs):
        print(f"正在获取 {provider.lower()} LLM Creator")
        creator: t.Type[LLMCreator] = LLMFactoryRegistry.get_creator(provider)
        creator_instance = creator()

        print(f"正在创建 {provider.lower()} LLM 实例")
        start_time = time.time()
        llm: BaseLLM = creator_instance.create(**kwargs)
        print(f"LLM 实例创建完成，耗时：{time.time() - start_time:.2f}秒")
        return llm
