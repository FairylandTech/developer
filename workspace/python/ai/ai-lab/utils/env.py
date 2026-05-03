# coding: UTF-8
"""
@software: PyCharm
@author: Beau Dean
@contact: https://fairy.host
@organization: https://github.com/FairylandFuture
@datetime: 2026-04-24 13:13:59 UTC+08:00
"""
from __future__ import annotations

import os
import typing as t
from dotenv import load_dotenv


class EnvUtilsMeta(type):

    def __new__(mcls, name: str, bases: tuple[type, ...], namespace: dict[str, t.Any]) -> type:
        cls = super().__new__(mcls, name, bases, namespace)
        cls.load()
        return cls

    def load(cls):
        raise NotImplementedError("Method: `load` must be impl subclass and use `@classmethod` decorator.")


class EnvUtils(metaclass=EnvUtilsMeta):
    __loaded: bool = False

    @classmethod
    def load(cls):
        if not cls.__loaded:
            try:
                load_dotenv()
                cls.__loaded = True
            except Exception as error:
                raise RuntimeError(f"Failed to load environment variables: {error}")

    @classmethod
    def getenv(cls, key: str, overload: bool = False) -> str:
        if overload:
            cls.load()
        value: t.Optional[str] = os.getenv(key)
        return value if value else ""

    ### deepseek
    @classmethod
    def get_deekseek_api_key(cls):
        return os.getenv("DEEPSEEK_API_KEY")

    @classmethod
    def get_deekseek_api_url(cls):
        return os.getenv("DEEPSEEK_API_URL")

    ### dashscope - aliyun bailian
    @classmethod
    def get_dashscope_api_key(cls):
        return os.getenv("DASHSCOPE_API_KEY")

    @classmethod
    def get_dashscope_api_url(cls):
        return os.getenv("DASHSCOPE_BASE_URL")

    ### hunyuan - tencent
    @classmethod
    def get_hunyuan_api_key(cls):
        return os.getenv("HUNYUAN_API_KEY")

    @classmethod
    def get_hunyuan_api_url(cls):
        return os.getenv("HUNYUAN_BASE_URL")

    @classmethod
    def get_hunyuan_secret_id(cls):
        return os.getenv("HUNYUAN_SECRET_ID")

    @classmethod
    def get_hunyuan_secret_key(cls):
        return os.getenv("HUNYUAN_SECRET_KEY")

    ### tokenhub - tencent
    @classmethod
    def get_tencent_tokenhub_api_key(cls):
        return os.getenv("TENCENT_TOKENHUB_API_KEY")

    @classmethod
    def get_tencent_tokenhub_api_url(cls):
        return os.getenv("TENCENT_TOKENHUB_BASE_URL")

    # bigmodel - zhipu
    @classmethod
    def get_zai_api_key(cls):
        return os.getenv("ZAI_API_KEY")

    @classmethod
    def get_zai_api_url(cls):
        return os.getenv("ZAI_BASE_URL")

    # mimo - xiaomi
    @classmethod
    def get_mimo_api_key(cls):
        return os.getenv("MIMO_API_KEY")

    @classmethod
    def get_mimo_api_url(cls):
        return os.getenv("MIMO_BASE_URL")

    # siliconflow
    @classmethod
    def get_silicon_api_key(cls):
        return os.getenv("SILICON_API_KEY")

    @classmethod
    def get_silicon_api_url(cls):
        return os.getenv("SILICON_BASE_URL")

    # doubao - bytedance
    @classmethod
    def get_ark_api_key(cls):
        return os.getenv("ARK_API_KEY")

    @classmethod
    def get_ark_api_url(cls):
        return os.getenv("ARK_BASE_URL")

    @classmethod
    def get_ark_ak(cls):
        return os.getenv("ARK_AK")

    @classmethod
    def get_ark_sk(cls):
        return os.getenv("ARK_SK")

    # kimi
    @classmethod
    def get_moonshot_api_key(cls):
        return os.getenv("MOONSHOT_API_KEY")

    @classmethod
    def get_moonshot_api_url(cls):
        return os.getenv("MOONSHOT_BASE_URL")

    # nvidia
    @classmethod
    def get_nvidia_api_key(cls):
        return os.getenv("NVIDIA_API_KEY")

    @classmethod
    def get_nvidia_api_url(cls):
        return os.getenv("NVIDIA_BASE_URL")

    # model scope
    @classmethod
    def get_moda_api_key(cls):
        return os.getenv("MODA_API_KEY")

    @classmethod
    def get_moda_api_url(cls):
        return os.getenv("MODA_BASE_URL")

    # Web Search - Tavily
    @classmethod
    def get_tavily_api_key(cls):
        return os.getenv("TAVILY_API_KEY")

    # LangSmith
    @classmethod
    def get_langsmith_api_key(cls):
        return os.getenv("LANGSMITH_API_KEY")

    @classmethod
    def get_langsmith_tracing(cls):
        return os.getenv("LAGNSMITH_TRACING")

    @classmethod
    def get_langsmith_project(cls):
        return os.getenv("LANGSMITH_PROJECT")
