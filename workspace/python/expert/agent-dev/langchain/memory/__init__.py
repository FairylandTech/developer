# coding: UTF-8
"""
@software: PyCharm
@author: Beau Dean
@contact: https://fairy.host
@organization: https://github.com/FairylandFuture
@datetime: 2026-04-17 07:02:17 UTC+08:00
"""
from __future__ import annotations

import typing as t
from pathlib import Path

from const import ROOT_DIR
from memory.checkpointer import generate_checkpointer

__all__ = [
    "default_checkpointer"
]

default_checkpointer = generate_checkpointer(Path(ROOT_DIR) / "resources" / "checkpoint.db")
