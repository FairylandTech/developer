# coding: UTF-8
"""
@software: PyCharm
@author: Beau Dean
@contact: https://fairy.host
@organization: https://github.com/FairylandFuture
@datetime: 2026-04-17 07:02:39 UTC+08:00
"""
from __future__ import annotations

import typing as t


import sqlite3
from langgraph.checkpoint.sqlite import SqliteSaver
from pathlib import Path


def generate_checkpointer(db: Path):
    connection = sqlite3.connect(db.resolve().as_posix(), check_same_thread=False)
    checkpointer = SqliteSaver(connection)
    checkpointer.setup()
    return checkpointer
