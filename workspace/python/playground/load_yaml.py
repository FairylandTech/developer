# coding: UTF-8
"""
@software: PyCharm
@author: Lionel Johnson
@contact: https://fairy.host
@organization: https://github.com/FairylandFuture
@datetime: 2026-01-31 03:40:23 UTC+08:00
"""
from __future__ import annotations

import typing as t
import yaml
from pathlib import Path
from loguru import logger


def main():
    file = Path(r"D:\Develops\Github\FairylandTech\pypi-fairylandlogger\fairyland-logger.yaml")
    with file.open(encoding="UTF-8") as stream:
        content = yaml.safe_load(stream)

    print()



if __name__ == '__main__':
    main()
