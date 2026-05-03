# coding: UTF-8
"""
@software: PyCharm
@author: Beau Dean
@contact: https://fairy.host
@organization: https://github.com/FairylandFuture
@datetime: 2026-04-17 23:32:39 UTC+08:00
"""
from __future__ import annotations

import os

from dotenv import load_dotenv

load_dotenv()


class LLMConfig:
    class DefaultModel:
        DEEPSEEK = "deepseek-chat"
        # https://bigmodel.cn/console/modelcenter/square
        ZAI = "glm-4.5-air"
        # https://bailian.console.aliyun.com/cn-beijing/?tab=model#/model-usage/free-quota?modelType=Text
        DASHSCOPE = "qwen3.6-plus-2026-04-02"
        # https://console.volcengine.com/ark/region:ark+cn-beijing/openManagement?LLM=%7B%7D&advancedActiveKey=model
        ARK = "Doubao-1.5-pro-32k"
        # https://cloud.tencent.com/document/product/1729/104753
        HUNYUAN = "hunyuan-turbos-latest"
        # https://cloud.siliconflow.cn/me/models
        SILICON = "stepfun-ai/Step-3.5-Flash"
        # https://platform.kimi.com/playground
        MOONSHOT = "kimi-k2-thinking"
        # https://platform.xiaomimimo.com/#/docs/pricing
        MIMO = "mimo-v2-flash"

    # DeepSeek
    DEEPSEEK_API_KEY = os.getenv("DEEPSEEK_API_KEY")
    DEEPSEEK_BASE_URL = os.getenv("DEEPSEEK_BASE_URL")

    # 智普
    ZAI_API_KEY = os.getenv("ZAI_API_KEY")
    ZAI_BASE_URL = os.getenv("ZAI_BASE_URL")

    # 阿里
    DASHSCOPE_API_KEY = os.getenv("DASHSCOPE_API_KEY")
    DASHSCOPE_BASE_URL = os.getenv("DASHSCOPE_BASE_URL")

    # 字节
    ARK_API_KEY = os.getenv("ARK_API_KEY")
    ARK_BASE_URL = os.getenv("ARK_BASE_URL")

    # 腾讯
    HUNYUAN_API_KEY = os.getenv("HUNYUAN_API_KEY")
    HUNYUAN_BASE_URL = os.getenv("HUNYUAN_BASE_URL")

    # 硅基流动
    SILICON_API_KEY = os.getenv("SILICON_API_KEY")
    SILICON_BASE_URL = os.getenv("SILICON_BASE_URL")

    # Kimi
    MOONSHOT_API_KEY = os.getenv("MOONSHOT_API_KEY")
    MOONSHOT_BASE_URL = os.getenv("MOONSHOT_BASE_URL")

    # Google - Gemini
    GEMINI_API_KEY = os.getenv("GEMINI_API_KEY")
