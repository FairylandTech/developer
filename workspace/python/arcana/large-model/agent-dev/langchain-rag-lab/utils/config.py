# coding: UTF-8
"""
@software: PyCharm
@author: Beau Dean
@contact: https://fairy.host
@organization: https://github.com/FairylandFuture
@datetime: 2026-05-03 00:01:45 UTC+08:00
"""

from __future__ import annotations

import os
import typing as t
import yaml

from common import ROOT_DIR
from domain.model.config import Config


class ConfigUtils:
    __loaded__: t.ClassVar[bool] = False
    __content__: t.ClassVar[t.Optional[Config]] = None

    @classmethod
    def __load_config(cls):
        config_path = os.path.join(ROOT_DIR, "config.yaml")
        if not os.path.exists(config_path):
            raise FileNotFoundError(f"Config file not found: {config_path}")

        try:
            with open(config_path, "r", encoding="UTF-8") as stream:
                cls.__content__ = Config.from_dict(yaml.safe_load(stream))
                cls.__loaded__ = True
        except Exception as error:
            raise RuntimeError(f"Failed to load config: {error}")

    @classmethod
    def get_config(cls) -> Config:
        if not cls.__loaded__:
            cls.__load_config()
        return cls.__content__ if cls.__content__ else Config()
