# coding: UTF-8
"""
@software: PyCharm
@author: Beau Dean
@contact: https://fairy.host
@organization: https://github.com/FairylandFuture
@datetime: 2026-04-16 23:17:58 UTC+08:00
"""
from __future__ import annotations

import typing as t
import time
from pathlib import Path

import jwt


class QWeatherUtils:

    @classmethod
    def get_private_pem(cls, path: Path) -> str:
        with path.open("r", encoding="UTF-8") as f:
            return f.read()

    @classmethod
    def generate_token(cls, pem: Path):
        private_key = cls.get_private_pem(pem)

        payload = {
            "iat": int(time.time()) - 30,
            "exp": int(time.time()) + 3600,
            "sub": "3JKQM4282U"
        }

        headers = {
            "kid": "KAB6R8UA29"
        }

        return jwt.encode(payload, private_key, algorithm="EdDSA", headers=headers)
