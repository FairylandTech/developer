# coding: UTF-8
"""
@software: PyCharm
@author: Beau Dean
@contact: https://fairy.host
@organization: https://github.com/FairylandFuture
@datetime: 2026-04-24 13:19:55 UTC+08:00
"""
from __future__ import annotations

import typing as t

from langchain_community.chat_models import ChatHunyuan
from pydantic import SecretStr

from utils.env import EnvUtils

hunyuan_chat_model: ChatHunyuan = ChatHunyuan(
    model="hunyuan",
    hunyuan_app_id=int(EnvUtils.getenv("TENCENT_APP_ID")),
    hunyuan_secret_id=EnvUtils.getenv("TENCENT_SECRET_ID"),
    hunyuan_secret_key=SecretStr(EnvUtils.getenv("TENCENT_SECRET_KEY"))
)
