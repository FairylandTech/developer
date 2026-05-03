# coding: UTF-8
"""
@software: PyCharm
@author: Beau Dean
@contact: https://fairy.host
@organization: https://github.com/FairylandFuture
@datetime: 2026-04-16 22:33:45 UTC+08:00
"""
from __future__ import annotations

import typing as t

from pydantic import BaseModel as PydanticBaseModel, ConfigDict
from pydantic.alias_generators import to_camel


class ModelBase(PydanticBaseModel):
    model_config: t.ClassVar[ConfigDict] = ConfigDict(
        alias_generator=to_camel,
        populate_by_name=True,
        from_attributes=True,
        str_strip_whitespace=True,
        extra="ignore",
        validate_assignment=True,
    )


__all__ = [
    "ModelBase",
]
