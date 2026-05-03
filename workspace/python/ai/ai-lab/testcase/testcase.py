# coding: UTF-8
"""
@software: PyCharm
@author: Beau Dean
@contact: https://fairy.host
@organization: https://github.com/FairylandFuture
@datetime: 2026-05-03 00:27:04 UTC+08:00
"""

from __future__ import annotations

import typing as t

from common.enum.database import DatabaseTypeEnum
from llm.hunyuan import HunYuanModelManager
from utils.config import ConfigUtils


def main():
    print("Hello, World!")
    config = ConfigUtils.get_config()
    print(config)
    print(config.database.get(DatabaseTypeEnum.POSTGRESQL).url)
    print(HunYuanModelManager.list_supported_models())


if __name__ == "__main__":
    main()
