# coding: UTF-8
"""
@software: PyCharm
@author: Beau Dean
@contact: https://fairy.host
@organization: https://github.com/FairylandFuture
@datetime: 2026-04-27 11:58:31 UTC+08:00
"""

from __future__ import annotations

import os
import typing as t

import orjson

from common import ROOT_DIR
from model.config import Config


class ConfigUtils:
    __loaded__: bool = False
    __context__: Config = None

    @classmethod
    def load(cls):
        if not cls.__loaded__:
            with open(os.path.join(ROOT_DIR, "config.json")) as config_stream:
                config: Config = Config(**orjson.loads(config_stream.read()))

            cls.__loaded__ = True
            cls.__context__ = config

    @classmethod
    def get(cls, k: str):
        if not hasattr(cls.__context__, k):
            return None
        return getattr(cls.__context__, k)

    @classmethod
    def get_config(cls) -> Config:
        cls.load()
        return cls.__context__
